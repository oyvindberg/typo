import typo.internal.*
import typo.internal.codegen.*
import typo.internal.metadb.MetaDb
import typo.internal.sqlfiles.SqlFile

import java.nio.file.Path
import java.sql.Connection
import scala.collection.immutable.SortedMap

package object typo {
  def fromDbAndScripts(options: Options, scriptsPath: Path, selector: Selector)(implicit c: Connection): Generated = {
    val metadb = MetaDb(MetaDb.Input.fromDb)
    val sqlScripts = sqlfiles.Load(scriptsPath, metadb.typeMapperDb)
    fromData(options, metadb.relations, metadb.enums, metadb.domains, sqlScripts, selector)
  }

  def fromDb(options: Options, selector: Selector)(implicit c: Connection): Generated = {
    val metadb = MetaDb(MetaDb.Input.fromDb)
    fromData(options, metadb.relations, metadb.enums, metadb.domains, sqlScripts = Nil, selector)
  }

  def fromData(
      publicOptions: Options,
      relations: List[db.Relation],
      enums: List[db.StringEnum],
      domains: List[db.Domain],
      sqlScripts: List[SqlFile],
      selector: Selector
  ): Generated = {
    val pkg = sc.Type.Qualified(publicOptions.pkg).value
    val naming = publicOptions.naming(pkg)
    val options = InternalOptions(
      pkg = pkg,
      jsonLib = publicOptions.jsonLib match {
        case JsonLibName.Circe    => JsonLibCirce
        case JsonLibName.PlayJson => JsonLibPlay
        case JsonLibName.None     => JsonLib.None
      },
      dbLib = publicOptions.dbLib match {
        case DbLibName.Anorm  => DbLibAnorm
        case DbLibName.Doobie => DbLibDoobie
      },
      naming = naming,
      typeOverride = publicOptions.typeOverride,
      generateMockRepos = publicOptions.generateMockRepos,
      header = publicOptions.header,
      debugTypes = publicOptions.debugTypes
    )
    val scalaTypeMapper = TypeMapperScala(options.typeOverride, publicOptions.nullabilityOverride, naming)

    val default: ComputedDefault =
      ComputedDefault(naming)

    val computeds: SortedMap[db.RelationName, Lazy[HasSource]] =
      rewriteDependentData(relations.map(rel => rel.name -> rel).toMap).apply[HasSource] {
        case (_, dbTable: db.Table, eval) =>
          ComputedTable(options, default, dbTable, naming, scalaTypeMapper, eval)
        case (_, dbView: db.View, eval) =>
          ComputedView(dbView, naming, scalaTypeMapper, eval)
      }

    // note, these statements will force the evaluation of some of the lazy values
    val computedScripts = sqlScripts.map(sqlScript => ComputedSqlFile(sqlScript, options.pkg, naming, scalaTypeMapper, computeds.apply))
    computeds.foreach { case (relName, lazyValue) =>
      if (selector.include(relName)) lazyValue.forceGet
    }
    // here we keep only the values which have been evaluated. as such, the selector pattern should be safe
    val computedRelations = computeds.flatMap { case (_, lazyValue) => lazyValue.getIfEvaluated }

    val scriptFiles = computedScripts.flatMap(x => SqlFileFiles(x, naming, options).all)
    val relationFilesByName = computedRelations.flatMap {
      case viewComputed: ComputedView   => ViewFiles(viewComputed, options).all.map(x => (viewComputed.view.name, x))
      case tableComputed: ComputedTable => TableFiles(tableComputed, options).all.map(x => (tableComputed.dbTable.name, x))
      case _                            => Nil
    }
    val mostFiles: List[sc.File] =
      List(
        List(DefaultFile(default, options.jsonLib).file),
        enums.map(StringEnumFile(naming, options)),
        domains.map(DomainFile(naming, options, scalaTypeMapper)),
        relationFilesByName.map { case (_, f) => f },
        scriptFiles
      ).flatten

    val entryPoints: Iterable[sc.File] =
      scriptFiles.map { f => f } ++ relationFilesByName.collect { case (name, f) if selector.include(name) => f }

    val keptMostFiles: List[sc.File] = minimize(mostFiles, entryPoints)

    val knownNamesByPkg: Map[sc.QIdent, Map[sc.Ident, sc.Type.Qualified]] =
      keptMostFiles.groupBy(_.pkg).map { case (pkg, files) =>
        (pkg, files.flatMap(f => (f.name, f.tpe) :: f.secondaryTypes.map(tpe => (tpe.value.name, tpe))).toMap)
      }

    // package objects have weird scoping, so don't attempt to automatically write imports for them.
    // this should be a stop-gap solution anyway
    val pkgObject = List(PackageObjectFile.packageObject(options))

    val allFiles = keptMostFiles.map(file => addPackageAndImports(knownNamesByPkg, file)) ++ pkgObject
    Generated(allFiles.map(file => file.copy(contents = options.header.code ++ file.contents)))
  }

  /** Keep those files among `allFiles` which are part of or reachable (through type references) from `entryPoints`.
    * @return
    */
  def minimize(allFiles: List[sc.File], entryPoints: Iterable[sc.File]): List[sc.File] = {
    val toKeep: Set[sc.QIdent] = {
      val b = Set.newBuilder[sc.QIdent]
      b ++= entryPoints.map(_.tpe.value)
      entryPoints.foreach { f =>
        def goTree(tree: sc.Tree): Unit = {
          tree match {
            case sc.Ident(_)  => ()
            case x: sc.QIdent => b += x
            case sc.Param(_, tpe, maybeCode) =>
              go(tpe)
              maybeCode.foreach(go)
            case sc.StrLit(_) => ()
            case sc.StringInterpolate(i, prefix, content) =>
              goTree(i)
              goTree(prefix)
              go(content)
            case sc.Type.Wildcard =>
              ()
            case sc.Type.TApply(underlying, targs) =>
              go(underlying)
              targs.foreach(goTree)
            case sc.Type.Qualified(value)         => go(value)
            case sc.Type.Abstract(_)              => ()
            case sc.Type.Commented(underlying, _) => go(underlying)
            case sc.Type.UserDefined(underlying)  => go(underlying)
            case sc.Type.ByName(underlying)       => go(underlying)
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
      b.result()
    }

    allFiles.filter(f => toKeep(f.tpe.value))
  }
}
