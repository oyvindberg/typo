package typo
package internal

import typo.internal.codegen.*
import typo.internal.sqlfiles.SqlFile

import scala.collection.immutable.SortedMap

object generate {
  // use this constructor if you need to run `typo` multiple times with different options but same database/scripts
  def apply(publicOptions: Options, metaDb: MetaDb, sqlFiles: List[SqlFile], selector: Selector): Generated = {
    Banner.maybePrint(publicOptions)

    val pkg = sc.Type.Qualified(publicOptions.pkg).value
    val customTypesPackage = pkg / sc.Ident("customtypes")

    val default: ComputedDefault =
      ComputedDefault(publicOptions.naming(customTypesPackage))

    val naming = publicOptions.naming(pkg)

    val options = InternalOptions(
      pkg = pkg,
      jsonLibs = publicOptions.jsonLibs.map {
        case JsonLibName.Circe    => JsonLibCirce(pkg, default, publicOptions.inlineImplicits)
        case JsonLibName.PlayJson => JsonLibPlay(pkg, default, publicOptions.inlineImplicits)
        case JsonLibName.ZioJson  => JsonLibZioJson(pkg, default, publicOptions.inlineImplicits)
      },
      dbLib = publicOptions.dbLib.map {
        case DbLibName.Anorm   => new DbLibAnorm(pkg, publicOptions.inlineImplicits)
        case DbLibName.Doobie  => new DbLibDoobie(pkg, publicOptions.inlineImplicits)
        case DbLibName.ZioJdbc => new DbLibZioJdbc(pkg, publicOptions.inlineImplicits, dslEnabled = publicOptions.enableDsl)
      },
      logger = publicOptions.logger,
      naming = naming,
      typeOverride = publicOptions.typeOverride,
      generateMockRepos = publicOptions.generateMockRepos,
      fileHeader = publicOptions.fileHeader,
      enableFieldValue = publicOptions.enableFieldValue,
      enableDsl = publicOptions.enableDsl,
      readonlyRepo = publicOptions.readonlyRepo,
      enableTestInserts = publicOptions.enableTestInserts,
      keepDependencies = publicOptions.keepDependencies,
      debugTypes = publicOptions.debugTypes
    )
    val customTypes = new CustomTypes(customTypesPackage)
    val genOrdering = new GenOrdering(customTypes, options.pkg)
    val scalaTypeMapper = TypeMapperScala(options.typeOverride, publicOptions.nullabilityOverride, naming, customTypes)
    val enums = metaDb.enums.map(ComputedStringEnum(naming))
    val domains = metaDb.domains.map(ComputedDomain(naming, scalaTypeMapper))

    val computeds: SortedMap[db.RelationName, Lazy[HasSource]] =
      rewriteDependentData(metaDb.relations).apply[HasSource] {
        case (_, dbTable: db.Table, eval) =>
          ComputedTable(options, default, dbTable, naming, scalaTypeMapper, eval)
        case (_, dbView: db.View, eval) =>
          ComputedView(dbView, naming, metaDb.typeMapperDb, scalaTypeMapper, eval, options.enableFieldValue.include(dbView.name), options.enableDsl)
      }

    // note, these statements will force the evaluation of some of the lazy values
    val computedSqlFiles = sqlFiles.map(sqlScript => ComputedSqlFile(options.logger, sqlScript, options.pkg, naming, metaDb.typeMapperDb, scalaTypeMapper, computeds.get))
    computeds.foreach { case (relName, lazyValue) =>
      if (selector.include(relName)) lazyValue.get
    }
    // here we keep only the values which have been evaluated. as such, the selector pattern should be safe
    val computedRelations = computeds.flatMap { case (_, lazyValue) => lazyValue.getIfEvaluated }

    // yeah, sorry about the naming overload. this is a list of output files generated for each input sql file
    val sqlFileFiles: List[sc.File] =
      computedSqlFiles.flatMap(x => FilesSqlFile(x, naming, options).all)

    val relationFilesByName = computedRelations.flatMap {
      case viewComputed: ComputedView   => FilesView(viewComputed, options).all.map(x => (viewComputed.view.name, x))
      case tableComputed: ComputedTable => FilesTable(tableComputed, options, genOrdering).all.map(x => (tableComputed.dbTable.name, x))
      case _                            => Nil
    }

    val mostFiles: List[sc.File] =
      List(
        List(FileDefault(default, options.jsonLibs).file),
        enums.map(enm => FileStringEnum(options, enm, genOrdering)),
        domains.map(d => FileDomain(d, options, genOrdering)),
        customTypes.All.values.map(FileCustomType(options, genOrdering)),
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
    val pkgObject = FilePackageObject.packageObject(options)

    val testInsertsDataFile = options.dbLib match {
      case Some(dbLib) =>
        val keptTables =
          computedRelations.collect { case x: ComputedTable if options.enableTestInserts.include(x.dbTable.name) && keptTypes(x.names.RepoImplName) => x }
        if (keptTables.nonEmpty) {
          val computed = ComputedTestInserts(options, customTypes, domains, enums, keptTables)
          Some(FileTestInserts(computed, dbLib))
        } else None
      case _ => None
    }

    val allFiles: Iterator[sc.File] = {
      val withImports = (testInsertsDataFile.iterator ++ keptMostFiles).map(file => addPackageAndImports(knownNamesByPkg, file))
      val all = withImports ++ pkgObject.iterator
      all.map(file => file.copy(contents = options.fileHeader.code ++ file.contents))
    }

    options.logger.info(s"Codegen complete")

    Generated(allFiles)
  }
}
