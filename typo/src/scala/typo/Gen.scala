package typo

import typo.metadb.MetaDb
import typo.sc.syntax._

import java.sql.Connection

object Gen {

  def stringEnumClass(pkg: sc.QIdent, `enum`: db.StringEnum, dbLib: DbLib, jsonLib: JsonLib): sc.File = {
    val qident = names.EnumName(pkg, `enum`.name)
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
            |  ${dbLib.stringEnumInstances(EnumType, sc.Type.String, lookup = ByName).mkCode("\n  ")}
            |  ${jsonLib.stringEnumInstances(EnumType, sc.Type.String, lookup = ByName).mkCode("\n  ")}
            |}
            |""".stripMargin

    sc.File(EnumType, str)
  }

  def apply(pkg: sc.QIdent, jsonLib: JsonLib, dbLib: DbLib, c: Connection): List[sc.File] = {
    val views: List[View] =
      information_schema.ViewsRepo.all(c).map { view =>
        val AnalyzeSql.Analyzed(Nil, columns) = AnalyzeSql.from(c, view.view_definition)

        View(
          name = db.RelationName(view.table_schema, view.table_name),
          sql = view.view_definition,
          isMaterialized = view.relkind == "m",
          columns
        )
      }

    val metaDB = new MetaDb(c)
    apply(pkg, metaDB.tables.getAsList, metaDB.enums.getAsList, views, jsonLib, dbLib)
  }

  def packageObject(pkg: sc.QIdent, jsonLib: JsonLib, dbLib: DbLib) = {
    val parentPkg = pkg.idents.dropRight(1)
    val content =
      code"""|package ${parentPkg.map(_.code).mkCode(".")}
             |
             |package object ${pkg.name} {
             |  ${dbLib.missingInstances.mkCode("\n  ")}
             |  ${jsonLib.missingInstances.mkCode("\n  ")}
             |}
             |""".stripMargin

    sc.File(sc.Type.Qualified(pkg / sc.Ident("package")), content)
  }
  def apply(
      pkg: sc.QIdent,
      tables: List[db.Table],
      enums: List[db.StringEnum],
      views: List[View],
      jsonLib: JsonLib,
      dbLib: DbLib
  ): List[sc.File] = {
    val default = DefaultComputed(pkg)
    val enumFiles: List[sc.File] =
      enums.map(stringEnumClass(pkg, _, dbLib, jsonLib))
    val tableFiles: List[sc.File] =
      tables.flatMap(table => TableFiles(TableComputed(pkg, default, table), dbLib, jsonLib).all)
    val viewFiles = views.flatMap(view => ViewFiles(ViewComputed(pkg, view), dbLib, jsonLib).all)
    val allFiles: List[sc.File] =
      List(DefaultFile(default, jsonLib).file) ++ enumFiles ++ tableFiles ++ viewFiles

    val knownNamesByPkg: Map[sc.QIdent, Map[sc.Ident, sc.QIdent]] =
      allFiles.groupBy { _.pkg }.map { case (pkg, files) =>
        (pkg, files.map { f => (f.name, f.tpe.value) }.toMap)
      }

    // package objects have weird scoping, so don't attempt to automatically write imports for them.
    // this should be a stop-gap solution anyway
    val pkgObject = List(packageObject(pkg, jsonLib, dbLib))

    allFiles.map(file => addPackageAndImports(knownNamesByPkg, file)) ++ pkgObject
  }
}
