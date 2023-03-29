package typo

import typo.codegen._
import typo.internal.{Lazy, rewriteDependentData}
import typo.metadb.MetaDb
import typo.sc.syntax._
import typo.sqlscripts.SqlScript

import java.nio.file.Path
import java.sql.Connection
import scala.collection.immutable.SortedMap

object Gen {
  def fromDbAndScripts(options: Options, scriptsPath: Path, selector: Selector)(implicit c: Connection): List[sc.File] = {
    val metadb = MetaDb(MetaDb.Input.fromDb(c))
    val enumsByName = metadb.enums.map(e => (e.name.name, e)).toMap
    val sqlScripts = sqlscripts.Load(scriptsPath, enumsByName)
    apply(options, metadb, sqlScripts, selector)
  }

  def fromDb(options: Options, selector: Selector)(implicit c: Connection): List[sc.File] = {
    val metadb = MetaDb(MetaDb.Input.fromDb(c))
    apply(options, metadb, sqlScripts = Nil, selector)
  }

  def apply(options: Options, metaDb: MetaDb, sqlScripts: List[SqlScript], selector: Selector): List[sc.File] = {
    val naming = options.naming(options.pkg)
    val scalaTypeMapper = TypeMapperScala(options.typeOverride, naming)

    val default: DefaultComputed =
      DefaultComputed(naming)

    val computeds: SortedMap[db.RelationName, Lazy[Either[ViewComputed, TableComputed]]] =
      rewriteDependentData(metaDb.relations.map(rel => rel.name -> rel).toMap).apply[Either[ViewComputed, TableComputed]] {
        case (_, dbTable: db.Table, eval) =>
          Right(TableComputed(options, default, dbTable, naming, scalaTypeMapper, eval))
        case (_, dbView: db.View, eval) =>
          Left(ViewComputed(dbView, naming, scalaTypeMapper, eval))
      }

    // note, these statements will force the evaluation of some of the lazy values
    val computedScripts = sqlScripts.map(sqlScript => SqlScriptComputed(sqlScript, options.pkg, options.naming, scalaTypeMapper, computeds.apply))
    computeds.foreach { case (relName, lazyValue) =>
      if (selector.include(relName)) lazyValue.forceGet
    }
    // here we keep only the values which have been evaluated. as such, the selector pattern should be safe
    val computedRelations = computeds.flatMap { case (_, lazyValue) => lazyValue.getIfEvaluated }

    val mostFiles: List[sc.File] =
      List(
        List(DefaultFile(default, options.jsonLib).file),
        metaDb.enums.map(StringEnumFile.stringEnumClass(naming, options)),
        computedRelations.flatMap {
          case Left(viewComputed)   => ViewFiles(viewComputed, options).all
          case Right(tableComputed) => TableFiles(tableComputed, options).all
        },
        computedScripts.flatMap(x => SqlScriptFiles(x, naming, options).all)
      ).flatten

    val knownNamesByPkg: Map[sc.QIdent, Map[sc.Ident, sc.QIdent]] =
      mostFiles.groupBy(_.pkg).map { case (pkg, files) =>
        (pkg, files.map(f => (f.name, f.tpe.value)).toMap)
      }

    // package objects have weird scoping, so don't attempt to automatically write imports for them.
    // this should be a stop-gap solution anyway
    val pkgObject = List(PackageObjectFile.packageObject(options))

    val allFiles = mostFiles.map(file => addPackageAndImports(knownNamesByPkg, file)) ++ pkgObject
    allFiles.map(file => file.copy(contents = options.header.code ++ file.contents))
  }
}
