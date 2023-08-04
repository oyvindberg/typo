import typo.internal.*
import typo.internal.codegen.*
import typo.internal.metadb.load

import java.nio.file.Path
import java.sql.Connection
import scala.collection.immutable.SortedMap

package object typo {
  def fromDbAndScripts(options: Options, scriptsPath: Path, selector: Selector)(implicit c: Connection): Generated =
    fromMetaDb(options, load(maybeScriptPath = Some(scriptsPath))._1, selector)

  def fromDb(options: Options, selector: Selector)(implicit c: Connection): Generated =
    fromMetaDb(options, load(maybeScriptPath = None)._1, selector)

  // use this constructor if you need to run `typo` multiple times with different options but same database/scripts
  def fromMetaDb(publicOptions: Options, metaDb: MetaDb, selector: Selector): Generated = {
    val pkg = sc.Type.Qualified(publicOptions.pkg).value
    val naming = publicOptions.naming(pkg)
    val default: ComputedDefault =
      ComputedDefault(naming)

    val options = InternalOptions(
      pkg = pkg,
      jsonLibs = publicOptions.jsonLibs.map {
        case JsonLibName.Circe    => JsonLibCirce(pkg, default, publicOptions.inlineImplicits)
        case JsonLibName.PlayJson => JsonLibPlay(pkg, default, publicOptions.inlineImplicits)
      },
      dbLib = publicOptions.dbLib.map {
        case DbLibName.Anorm  => new DbLibAnorm(pkg, publicOptions.inlineImplicits)
        case DbLibName.Doobie => new DbLibDoobie(pkg, publicOptions.inlineImplicits)
      },
      naming = naming,
      typeOverride = publicOptions.typeOverride,
      generateMockRepos = publicOptions.generateMockRepos,
      header = publicOptions.header,
      enableFieldValue = publicOptions.enableFieldValue,
      enableDsl = publicOptions.enableDsl,
      debugTypes = publicOptions.debugTypes
    )
    val customTypes = new CustomTypes(options.pkg)
    val genOrdering = new GenOrdering(customTypes, options.pkg)
    val scalaTypeMapper = TypeMapperScala(options.typeOverride, publicOptions.nullabilityOverride, naming, customTypes)

    val computeds: SortedMap[db.RelationName, Lazy[HasSource]] =
      rewriteDependentData(metaDb.relations.map(rel => rel.name -> rel).toMap).apply[HasSource] {
        case (_, dbTable: db.Table, eval) =>
          ComputedTable(options, default, dbTable, naming, scalaTypeMapper, eval)
        case (_, dbView: db.View, eval) =>
          ComputedView(dbView, naming, scalaTypeMapper, eval, options.enableFieldValue, options.enableDsl)
      }

    // note, these statements will force the evaluation of some of the lazy values
    val computedSqlFiles = metaDb.sqlFiles.map(sqlScript => ComputedSqlFile(sqlScript, options.pkg, naming, scalaTypeMapper, computeds.get))
    computeds.foreach { case (relName, lazyValue) =>
      if (selector.include(relName)) lazyValue.get
    }
    // here we keep only the values which have been evaluated. as such, the selector pattern should be safe
    val computedRelations = computeds.flatMap { case (_, lazyValue) => lazyValue.getIfEvaluated }

    // yeah, sorry about the naming overload. this is a list of output files generated for each input sql file
    val sqlFileFiles: List[sc.File] =
      computedSqlFiles.flatMap(x => SqlFileFiles(x, naming, options).all)

    val relationFilesByName = computedRelations.flatMap {
      case viewComputed: ComputedView   => ViewFiles(viewComputed, options).all.map(x => (viewComputed.view.name, x))
      case tableComputed: ComputedTable => TableFiles(tableComputed, options, genOrdering).all.map(x => (tableComputed.dbTable.name, x))
      case _                            => Nil
    }

    val mostFiles: List[sc.File] =
      List(
        List(DefaultFile(default, options.jsonLibs).file),
        metaDb.enums.map(StringEnumFile(naming, options)),
        metaDb.domains.map(DomainFile(naming, options, scalaTypeMapper, genOrdering)),
        customTypes.All.map(CustomTypeFile(options, genOrdering)),
        relationFilesByName.map { case (_, f) => f },
        sqlFileFiles
      ).flatten

    val entryPoints: Iterable[sc.File] =
      sqlFileFiles.map { f => f } ++ relationFilesByName.collect { case (name, f) if selector.include(name) => f }

    val keptMostFiles: List[sc.File] = minimize(mostFiles, entryPoints)

    val knownNamesByPkg: Map[sc.QIdent, Map[sc.Ident, sc.Type.Qualified]] =
      keptMostFiles.groupBy(_.pkg).map { case (pkg, files) =>
        (pkg, files.flatMap(f => (f.name, f.tpe) :: f.secondaryTypes.map(tpe => (tpe.value.name, tpe))).toMap)
      }

    // package objects have weird scoping, so don't attempt to automatically write imports for them.
    // this should be a stop-gap solution anyway
    val pkgObject = PackageObjectFile.packageObject(options)

    val allFiles = keptMostFiles.map(file => addPackageAndImports(knownNamesByPkg, file)) ++ pkgObject
    Generated(allFiles.map(file => file.copy(contents = options.header.code ++ file.contents)))
  }

  /** Keep those files among `allFiles` which are part of or reachable (through type references) from `entryPoints`.
    * @return
    */
  def minimize(allFiles: List[sc.File], entryPoints: Iterable[sc.File]): List[sc.File] = {
    val filesByQident = allFiles.iterator.map(x => (x.tpe.value, x)).toMap
    val toKeep: Set[sc.QIdent] = {
      val b = collection.mutable.HashSet.empty[sc.QIdent]
      b ++= entryPoints.map(_.tpe.value)
      entryPoints.foreach { f =>
        def goTree(tree: sc.Tree): Unit = {
          tree match {
            case sc.Ident(_) => ()
            case x: sc.QIdent =>
              if (!b(x)) {
                b += x
                filesByQident.get(x).foreach(f => go(f.contents))
              }

            case sc.Param(_, tpe, maybeCode) =>
              goTree(tpe)
              maybeCode.foreach(go)
            case sc.StrLit(_) => ()
            case sc.Summon(tpe) =>
              goTree(tpe)
            case sc.StringInterpolate(i, prefix, content) =>
              goTree(i)
              goTree(prefix)
              go(content)
            case sc.Given(tparams, name, implicitParams, tpe, body) =>
              tparams.foreach(goTree)
              goTree(name)
              implicitParams.foreach(goTree)
              goTree(tpe)
              go(body)
            case sc.Value(tparams, name, params, tpe, body) =>
              tparams.foreach(goTree)
              goTree(name)
              params.foreach(goTree)
              goTree(tpe)
              go(body)
            case sc.Obj(name, members, body) =>
              goTree(name)
              members.foreach(goTree)
              body.foreach(go)
            case sc.Type.Wildcard =>
              ()
            case sc.Type.TApply(underlying, targs) =>
              goTree(underlying)
              targs.foreach(goTree)
            case sc.Type.Qualified(value)         => goTree(value)
            case sc.Type.Abstract(_)              => ()
            case sc.Type.Commented(underlying, _) => goTree(underlying)
            case sc.Type.UserDefined(underlying)  => goTree(underlying)
            case sc.Type.ByName(underlying)       => goTree(underlying)
          }
        }

        def go(code: sc.Code): Unit = {
          code match {
            case sc.Code.Interpolated(_, args) =>
              args.foreach(go)
            case sc.Code.Combined(codes) =>
              codes.foreach(go)
            case sc.Code.Str(_) =>
              ()
            case sc.Code.Tree(tree) =>
              goTree(tree)
          }
        }

        go(f.contents)
      }

      b.toSet
    }

    allFiles.filter(f => toKeep(f.tpe.value))
  }
}
