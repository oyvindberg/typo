package typo

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

    val enums: List[db.StringEnum] =
      information_schema.PgEnum
        .all(c)
        .groupBy(_.name)
        .map { case (relName, values) =>
          db.StringEnum(relName, values.sortBy(_.enum_sort_order).map(_.enum_value))
        }
        .toList

    val tables = {
      val tableRows = information_schema.Tables.all(c)
      val columns = information_schema.Columns.all(c)
//      val tableConstraints = information_schema.TableConstraints.all(c)
//      val referentialConstraints = information_schema.ReferentialConstraints.all(c)
//      val constraintColumnUsage = information_schema.ConstraintColumnUsage.all(c)
      val pgTypes = information_schema.PgType.all(c)
      val typeLookup = TypeLookup(pgTypes = pgTypes, enums = enums.map(e => (e.name.name, e)).toMap)

      tableRows.map { table =>
        val cols =
          columns
            .filter(c => c.table_catalog == table.table_catalog && c.table_schema == table.table_schema && c.table_name == table.table_name)
            .sortBy(_.ordinal_position)
            .map { c =>
              db.Col(
                name = db.ColName(c.column_name),
                hasDefault = c.column_default.isDefined,
                isNotNull = c.is_nullable == "NO",
                tpe = typeLookup.fromUdtName(c.udt_name, c.character_maximum_length)
              )
            }

        db.Table(
          name = db.RelationName(
            schema = table.table_schema,
            name = table.table_name
          ),
          cols = cols,
          primaryKey = None,
          uniqueKeys = Nil,
          foreignKeys = Nil
        )
      }
    }

    apply(pkg, tables, enums, views, jsonLib, dbLib)
  }

  case class TypeLookup(pgTypes: List[information_schema.PgType.Row], enums: Map[String, db.StringEnum]) {
    def fromUdtName(udtName: String, characterMaximumLength: Option[Int]): db.Type = {
      udtName match {
        case "anyarray"                 => db.Type.AnyArray
        case "bool"                     => db.Type.Boolean
        case "char"                     => db.Type.Char
        case "float4"                   => db.Type.Float4
        case "float8"                   => db.Type.Float8
        case "hstore"                   => db.Type.Hstore
        case "inet"                     => db.Type.Inet
        case "int2"                     => db.Type.Int2
        case "int4"                     => db.Type.Int4
        case "int8"                     => db.Type.Int8
        case "json"                     => db.Type.Json
        case "name"                     => db.Type.Name
        case "numeric"                  => db.Type.Numeric
        case "oid"                      => db.Type.Oid
        case "text"                     => db.Type.Text
        case "timestamp"                => db.Type.Timestamp
        case "timestamptz"              => db.Type.TimestampTz
        case "varchar"                  => db.Type.VarChar(characterMaximumLength)
        case str if str.startsWith("_") => db.Type.Array(fromUdtName(udtName.drop(1), characterMaximumLength))
        case typeName =>
          enums.get(typeName) match {
            case Some(enum) =>
              db.Type.StringEnum(enum.name)
            case None =>
              println(udtName)
              db.Type.Text
          }

      }
    }
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

    allFiles.map(file => addPackageAndImports(knownNamesByPkg, file))
  }
}
