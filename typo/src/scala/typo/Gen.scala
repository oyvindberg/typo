package typo

import typo.codegen._
import typo.metadb.MetaDb
import typo.sqlscripts.SqlScript

import java.nio.file.Path
import java.sql.Connection

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
    val enumFiles: List[sc.File] =
      metaDb.enums.map(StringEnumFile.stringEnumClass(options))
    val tableFiles: List[sc.File] =
      metaDb.tables.flatMap(table => TableFiles(TableComputed(options, default, table), options).all)
    val viewFiles: List[sc.File] =
      metaDb.views.flatMap(view => ViewFiles(ViewComputed(options.pkg, view), options).all)
    val scriptFiles: List[sc.File] =
      sqlScripts.flatMap(x => SqlScriptFiles(SqlScriptComputed(options.pkg, x), options).all)
    val mostFiles: List[sc.File] =
      List(DefaultFile(default, options.jsonLib).file) ++ enumFiles ++ tableFiles ++ viewFiles ++ scriptFiles

    val knownNamesByPkg: Map[sc.QIdent, Map[sc.Ident, sc.QIdent]] =
      mostFiles.groupBy { _.pkg }.map { case (pkg, files) =>
        (pkg, files.map { f => (f.name, f.tpe.value) }.toMap)
      }

    // package objects have weird scoping, so don't attempt to automatically write imports for them.
    // this should be a stop-gap solution anyway
    val pkgObject = List(PackageObjectFile.packageObject(options))

    val allFiles = mostFiles.map(file => addPackageAndImports(knownNamesByPkg, file)) ++ pkgObject
    allFiles.map { file => file.copy(contents = options.header ++ file.contents) }
  }
}
