package typo

import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funsuite.AnyFunSuite

import java.sql.{Connection, DriverManager}

class AppTest extends AnyFunSuite with TypeCheckedTripleEquals {
  test("works") {
    val url = "jdbc:postgresql://localhost:5432/samordnaopptak?user=postgres&password=postgres"
    implicit val conn: Connection = DriverManager.getConnection(url)

    try {
      val tables = information_schema.Tables.all
      val columns = information_schema.Columns.all
      val tableConstraints = information_schema.TableConstraints.all
      val referentialConstraints = information_schema.ReferentialConstraints.all
      val constraintColumnUsage = information_schema.ConstraintColumnUsage.all

      val typeLookup = TypeLookup(pgTypes = information_schema.PgType.all, pgEnums = information_schema.PgEnum.all)

      val filteredTables =
        tables
          .filter(t =>
            t.table_schema == "institusjoner"
              && t.table_name == "institution"
          )

      def processed =
        filteredTables
          .map { table =>
            val cols =
              columns
                .filter(c =>
                  c.table_catalog == table.table_catalog
                    && c.table_schema == table.table_schema
                    && c.table_name == table.table_name
                )
                .sortBy(_.ordinal_position)
                .map { c =>
                  Column(
                    name = ColumnName(c.column_name),
                    default = c.column_default,
                    nullable = c.is_nullable == "YES",
                    tpe = typeLookup.fromUdtName(c.udt_name, c.character_maximum_length)
                  )
                }

            Table(
              name = TableName(
                catalog = table.table_catalog,
                schema = table.table_schema,
                tableName = table.table_name
              ),
              columns = cols
            )
          }

      println(processed)
      val foo = columns
        .filter(t =>
          List(
            "audit",
            "common",
            "institusjoner",
            "opptak",
            "posten",
            "public",
            "regelverk",
            "saksbehandling",
            "sokerportal",
            "statistikk",
            "studier",
            "systemkonfigurasjon",
            "tilgangskontroll"
          ).contains(t.table_schema)
        )
        .map(c =>
          Column(
            name = ColumnName(c.column_name),
            default = c.column_default,
            nullable = c.is_nullable == "YES",
            tpe = typeLookup.fromUdtName(c.udt_name, c.character_maximum_length)
          )
        )

//      val filteredFoo = foo.filter(_.tpe.isInstanceOf[Type.Enum])
//      println(filteredFoo.mkString("\n"))

    } finally {
      conn.close()
    }
    assert(true)
  }
}

case class TypeLookup(pgTypes: List[information_schema.PgType.Row], pgEnums: List[information_schema.PgEnum.Row]) {
  def fromUdtName(udtName: String, characterMaximumLength: Option[Int]): Type = {
    udtName match {
      case "bool"        => Type.Boolean
      case "float4"      => Type.Float4
      case "float8"      => Type.Float8
      case "hstore"      => Type.Hstore
      case "inet"        => Type.Inet
      case "int4"        => Type.Int4
      case "int8"        => Type.Int8
      case "json"        => Type.Json
      case "numeric"     => Type.Numeric
      case "oid"         => Type.Oid
      case "text"        => Type.Text
      case "timestamp"   => Type.Timestamp
      case "timestamptz" => Type.TimestampTz
      case "varchar"     => Type.Varchar(characterMaximumLength)

      case typeName =>
        pgTypes.find(_.typname == typeName) match {
          case Some(pgType) if pgType.typcategory == 'E' =>
            val enumValues =
              pgEnums
                .filter(_.enumtypid == pgType.oid)
                .map(_.enumlabel)
            Type.Enum(typeName, enumValues)
          case None =>
            throw new NotImplementedError(s"$typeName not implemend")
        }

    }
  }

}

case class TableName(catalog: String, schema: String, tableName: String)

case class Table(name: TableName, columns: List[Column])

sealed trait Type

object Type {
  case class Enum(name: String, values: List[String]) extends Type
  case object Hstore extends Type
  case object Inet extends Type
  case object Oid extends Type
  case class Varchar(maxLength: Option[Int]) extends Type
  case object Boolean extends Type
  case object Float4 extends Type
  case object Float8 extends Type
  case object Int4 extends Type
  case object Int8 extends Type
  case object Json extends Type
  case object Numeric extends Type
  case object Text extends Type
  case object Timestamp extends Type
  case object TimestampTz extends Type
}

case class ColumnName(value: String) extends AnyVal

case class Column(name: ColumnName, default: Option[String], nullable: Boolean, tpe: Type)
