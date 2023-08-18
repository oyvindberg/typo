import typo.internal.*
import typo.internal.codegen.*
import typo.internal.metadb.load

import java.nio.file.Path
import java.sql.Connection
import scala.collection.immutable.SortedMap

package object typo {
  def fromDbAndScripts(options: Options, scriptsPath: Path, selector: Selector)(implicit c: Connection): Generated = {
    Banner.maybePrint(options)
    fromMetaDb(options, load(maybeScriptPath = Some(scriptsPath)), selector)
  }

  def fromDb(options: Options, selector: Selector)(implicit c: Connection): Generated = {
    Banner.maybePrint(options)
    fromMetaDb(options, load(maybeScriptPath = None), selector)
  }

  // use this constructor if you need to run `typo` multiple times with different options but same database/scripts
  def fromMetaDb(publicOptions: Options, metaDb: MetaDb, selector: Selector): Generated = {
    Banner.maybePrint(publicOptions)

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
      enableTestInserts = publicOptions.enableTestInserts,
      keepDependencies = publicOptions.keepDependencies,
      debugTypes = publicOptions.debugTypes
    )
    val customTypes = new CustomTypes(options.pkg)
    val genOrdering = new GenOrdering(customTypes, options.pkg)
    val customTypeFiles = customTypes.All.map(CustomTypeFile(options, genOrdering))
    val scalaTypeMapper = TypeMapperScala(options.typeOverride, publicOptions.nullabilityOverride, naming, customTypes)
    val enums = metaDb.enums.map(ComputedStringEnum(naming))
    val domains = metaDb.domains.map(ComputedDomain(naming, scalaTypeMapper))

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
      if (selector.include(relName)) {
        lazyValue.get
        ()
      }
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
        enums.map(enm => StringEnumFile(options, enm)),
        domains.map(d => DomainFile(d, options, genOrdering)),
        customTypeFiles,
        relationFilesByName.map { case (_, f) => f },
        sqlFileFiles
      ).flatten

    val keptMostFiles: List[sc.File] = {
      val entryPoints: Iterable[sc.File] =
        sqlFileFiles.map { f => f } ++ relationFilesByName.collect { case (name, f) if options.keepDependencies || selector.include(name) => f }
      internal.minimize(mostFiles, entryPoints)
    }
    lazy val keptTypes = keptMostFiles.flatMap(x => x.tpe :: x.secondaryTypes).toSet

    val knownNamesByPkg: Map[sc.QIdent, Map[sc.Ident, sc.Type.Qualified]] =
      keptMostFiles.groupBy(_.pkg).map { case (pkg, files) =>
        (pkg, files.flatMap(f => (f.name, f.tpe) :: f.secondaryTypes.map(tpe => (tpe.value.name, tpe))).toMap)
      }

    // package objects have weird scoping, so don't attempt to automatically write imports for them.
    // this should be a stop-gap solution anyway
    val pkgObject = PackageObjectFile.packageObject(options)

    val testDataFile = options.dbLib match {
      case Some(dbLib) if options.enableTestInserts =>
        val keptTables = computedRelations.collect { case x: ComputedTable if keptTypes(x.names.RepoImplName) => x }
        val computed = ComputedTestInserts(options, customTypes, domains, enums, keptTables)
        Some(FileTestInserts(computed, dbLib))
      case _ => None
    }

    val allFiles = (testDataFile.toList ::: keptMostFiles).map(file => addPackageAndImports(knownNamesByPkg, file)) ++ pkgObject
    Generated(allFiles.map(file => file.copy(contents = options.header.code ++ file.contents)))
  }
}
