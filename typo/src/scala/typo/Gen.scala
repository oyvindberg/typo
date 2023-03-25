package typo

import typo.metadb.MetaDb
import typo.sc.syntax._
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
      metaDb.enums.map(stringEnumClass(options))
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
    val pkgObject = List(packageObject(options))

    val allFiles = mostFiles.map(file => addPackageAndImports(knownNamesByPkg, file)) ++ pkgObject
    allFiles.map { file => file.copy(contents = options.header ++ file.contents) }
  }

  def stringEnumClass(options: Options)(`enum`: db.StringEnum): sc.File = {
    val qident = names.EnumName(options.pkg, `enum`.name)
    val EnumType = sc.Type.Qualified(qident)

    val members = `enum`.values.map { value =>
      val name = names.enumValue(value)
      name -> code"case object $name extends ${qident.name}(${sc.StrLit(value)})"
    }
    val ByName = sc.Ident("ByName")
    val str =
      code"""sealed abstract class ${qident.name}(val value: ${sc.Type.String})
            |object ${qident.name} {
            |  ${members.map { case (_, definition) => definition }.mkCode("\n  ")}
            |
            |  val All: ${sc.Type.List.of(EnumType)} = ${sc.Type.List}(${members.map { case (ident, _) => ident.code }.mkCode(", ")})
            |  val Names: ${sc.Type.String} = All.map(_.value).mkString(", ")
            |  val ByName: ${sc.Type.Map.of(sc.Type.String, EnumType)} = All.map(x => (x.value, x)).toMap
            |
            |  ${options.dbLib.stringEnumInstances(EnumType, sc.Type.String, lookup = ByName).mkCode("\n  ")}
            |  ${options.jsonLib.stringEnumInstances(EnumType, sc.Type.String, lookup = ByName).mkCode("\n  ")}
            |}
            |""".stripMargin

    sc.File(EnumType, str)
  }

  def packageObject(options: Options): sc.File = {
    val parentPkg = options.pkg.idents.dropRight(1)
    val content =
      code"""|package ${parentPkg.map(_.code).mkCode(".")}
             |
             |package object ${options.pkg.name} {
             |  ${options.dbLib.missingInstances.mkCode("\n  ")}
             |  ${options.jsonLib.missingInstances.mkCode("\n  ")}
             |  implicit val pgObjectOrdering: ${sc.Type.Ordering.of(sc.Type.PGobject)} =
             |    ${sc.Type.Ordering}.by(x => (x.getType, x.getValue))
             |}
             |""".stripMargin

    sc.File(sc.Type.Qualified(options.pkg / sc.Ident("package")), content)
  }
}
