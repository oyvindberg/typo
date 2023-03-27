package typo

import typo.codegen._
import typo.internal.{Lazy, rewriteDependentData}
import typo.metadb.MetaDb
import typo.sqlscripts.SqlScript

import java.nio.file.Path
import java.sql.Connection
import scala.collection.immutable.SortedMap

object Gen {
  def fromDbAndScripts(options: Options, scriptsPath: Path, selector: Selector)(implicit c: Connection): List[sc.File] = {
    val metadb = MetaDb(MetaDb.Input.fromDb(c), selector)
    val enumsByName = metadb.enums.map(e => (e.name.name, e)).toMap
    val sqlScripts = sqlscripts.Load(scriptsPath, enumsByName)
    apply(options, metadb, sqlScripts)
  }

  def fromDb(options: Options, selector: Selector)(implicit c: Connection): List[sc.File] = {
    val metadb = MetaDb(MetaDb.Input.fromDb(c), selector)
    apply(options, metadb, sqlScripts = Nil)
  }

  def apply(options: Options, metaDb: MetaDb, sqlScripts: List[SqlScript]): List[sc.File] = {
    val default: DefaultComputed =
      DefaultComputed(options.pkg)

    val computeds: SortedMap[db.RelationName, Lazy[Either[ViewComputed, TableComputed]]] =
      rewriteDependentData(metaDb.relations.map(rel => rel.name -> rel).toMap)[Either[ViewComputed, TableComputed]] {
        case (_, dbTable: db.Table, eval) =>
          Right(TableComputed(options, default, dbTable, eval))
        case (_, dbView: db.View, eval) =>
          Left(ViewComputed(options.pkg, dbView, eval))
      }

    val mostFiles: List[sc.File] =
      List(
        List(DefaultFile(default, options.jsonLib).file),
        metaDb.enums.map(StringEnumFile.stringEnumClass(options)),
        computeds.flatMap { case (_, lazyComputed) =>
          lazyComputed.forceGet match {
            case Left(viewComputed)   => ViewFiles(viewComputed, options).all
            case Right(tableComputed) => TableFiles(tableComputed, options).all
          }
        },
        sqlScripts.flatMap(x => SqlScriptFiles(SqlScriptComputed(options.pkg, x), options).all)
      ).flatten

    val knownNamesByPkg: Map[sc.QIdent, Map[sc.Ident, sc.QIdent]] =
      mostFiles.groupBy(_.pkg).map { case (pkg, files) =>
        (pkg, files.map(f => (f.name, f.tpe.value)).toMap)
      }

    // package objects have weird scoping, so don't attempt to automatically write imports for them.
    // this should be a stop-gap solution anyway
    val pkgObject = List(PackageObjectFile.packageObject(options))

    val allFiles = mostFiles.map(file => addPackageAndImports(knownNamesByPkg, file)) ++ pkgObject
    allFiles.map(file => file.copy(contents = options.header ++ file.contents))
  }
}
