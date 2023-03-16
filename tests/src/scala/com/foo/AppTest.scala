package com.foo

import anorm.*
import com.foo.Q.PgType
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funsuite.AnyFunSuite

import java.sql.{Connection, DriverManager}

class AppTest extends AnyFunSuite with TypeCheckedTripleEquals {
  test("works") {
    val url = "jdbc:postgresql://localhost:5432/samordnaopptak?user=postgres&password=postgres"
    implicit val conn: Connection = DriverManager.getConnection(url)

    try {
      val tables = Q.Tables.all
      val columns = Q.Columns.all
      val tableConstraints = Q.TableConstraints.all
      val referentialConstraints = Q.ReferentialConstraints.all
      val constraintColumnUsage = Q.ConstraintColumnUsage.all
      val pgTypes = PgType.all

      val filteredTables =
        tables
          .filter(t =>
            t.table_schema == "institusjoner"
              && t.table_name == "institution"
          )

      def processed =
        filteredTables
          .map {
            table =>
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
                      tpe = Type.fromUdtName(c.udt_name, c.character_maximum_length, pgTypes)
                    )
                  }

              Table(
                name = TableName(
                  catalog = table.table_catalog,
                  schema = table.table_schema,
                  tableName = table.table_name,
                ),
                columns = cols,
              )
          }

      //      println(processed)
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
            tpe = Type.fromUdtName(c.udt_name, c.character_maximum_length, pgTypes),
          )
        )

      println(foo.mkString("\n"))

    } finally {
      conn.close()
    }
    assert(true)
  }
}

case class TableName(catalog: String, schema: String, tableName: String)

case class Table(name: TableName, columns: List[Column])

sealed trait Type

object Type {
  def fromUdtName(udtName: String, characterMaximumLength: Option[Int], pgTypes: List[PgType.Row]): Type = {
    udtName match {
      case "bool" => Boolean
      case "float4" => Float4
      case "float8" => Float8
      case "hstore" => Hstore
      case "inet" => Inet
      case "int4" => Int4
      case "int8" => Int8
      case "json" => Json
      case "numeric" => Numeric
      case "oid" => Oid
      case "text" => Text
      case "timestamp" => Timestamp
      case "timestamptz" => TimestampTz
      case "varchar" => Varchar(characterMaximumLength)

      case typeName =>
        pgTypes.find(_.typname == typeName) match {
          case Some(pgType) if pgType.typcategory == 'E' => Type.Enum(typeName)
          case None =>
            throw new NotImplementedError(s"$typeName not implemend")
        }

    }
  }

  case class Enum(name: String) extends Type

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

object Q {
  object Schemata {
    case class Row(catalog_name: String, schema_name: String, schema_owner: String)

    object Row {
      val parser: RowParser[Schemata.Row] =
        row =>
          anorm.Success(
            Schemata.Row(
              catalog_name = row[String]("catalog_name"),
              schema_name = row[String]("schema_name"),
              schema_owner = row[String]("schema_owner")
            )
          )
    }

    def all(implicit c: Connection): List[Schemata.Row] =
      SQL"""
      select *
      from information_schema.schemata
     """
        .as(Schemata.Row.parser.*)
  }

  object Tables {
    case class Row(table_catalog: String, table_schema: String, table_name: String, table_type: String)

    object Row {
      val parser: RowParser[Tables.Row] = row => anorm.Success {
        Tables.Row(
          table_catalog = row[String]("table_catalog"),
          table_schema = row[String]("table_schema"),
          table_name = row[String]("table_name"),
          table_type = row[String]("table_type")
        )
      }
    }

    def all(implicit c: Connection): List[Tables.Row] =
      SQL"""
          select *
          from information_schema.tables
        """
        .as(Tables.Row.parser.*)
  }

  object Columns {
    case class Row(table_catalog: String,
                   table_schema: String,
                   table_name: String,
                   column_name: String,
                   ordinal_position: Int,
                   column_default: Option[String],
                   is_nullable: String,
                   data_type: String,
                   character_maximum_length: Option[Int],
                   udt_catalog: String,
                   udt_schema: String,
                   udt_name: String
                  )

    object Row {
      val parser: RowParser[Columns.Row] =
        row => anorm.Success {
          Columns.Row(
            table_catalog = row[String]("table_catalog"),
            table_schema = row[String]("table_schema"),
            table_name = row[String]("table_name"),
            column_name = row[String]("column_name"),
            ordinal_position = row[Int]("ordinal_position"),
            column_default = row[Option[String]]("column_default"),
            is_nullable = row[String]("is_nullable"),
            data_type = row[String]("data_type"),
            character_maximum_length = row[Option[Int]]("character_maximum_length"),
            udt_catalog = row[String]("udt_catalog"),
            udt_schema = row[String]("udt_schema"),
            udt_name = row[String]("udt_name")
          )
        }
    }

    def all(implicit c: Connection): List[Columns.Row] =
      SQL"""
          select *
          from information_schema.columns
        """
        .as(Columns.Row.parser.*)

  }

  object TableConstraints {
    case class Row(constraint_catalog: String,
                   constraint_schema: String,
                   constraint_name: String,
                   table_catalog: String,
                   table_schema: String,
                   table_name: String,
                   constraint_type: String
                  )

    object Row {
      val parser: RowParser[TableConstraints.Row] =
        row => anorm.Success {
          TableConstraints.Row(
            constraint_catalog = row[String]("constraint_catalog"),
            constraint_schema = row[String]("constraint_schema"),
            constraint_name = row[String]("constraint_name"),
            table_catalog = row[String]("table_catalog"),
            table_schema = row[String]("table_schema"),
            table_name = row[String]("table_name"),
            constraint_type = row[String]("constraint_type")
          )
        }
    }

    def all(implicit c: Connection): List[TableConstraints.Row] =
      SQL"""
          select *
          from information_schema.table_constraints
      """
        .as(TableConstraints.Row.parser.*)

  }

  object ReferentialConstraints {
    case class Row(constraint_catalog: String,
                   constraint_schema: String,
                   constraint_name: String,
                   unique_constraint_catalog: String,
                   unique_constraint_schema: String,
                   unique_constraint_name: String
                  )

    object Row {
      val parser: RowParser[ReferentialConstraints.Row] =
        row => anorm.Success {
          ReferentialConstraints.Row(
            constraint_catalog = row[String]("constraint_catalog"),
            constraint_schema = row[String]("constraint_schema"),
            constraint_name = row[String]("constraint_name"),
            unique_constraint_catalog = row[String]("unique_constraint_catalog"),
            unique_constraint_schema = row[String]("unique_constraint_schema"),
            unique_constraint_name = row[String]("unique_constraint_name")
          )
        }
    }

    def all(implicit c: Connection): List[ReferentialConstraints.Row] =
      SQL"""
          select *
          from information_schema.referential_constraints
        """
        .as(ReferentialConstraints.Row.parser.*)

  }

  object ConstraintColumnUsage {
    case class Row(table_catalog: String,
                   table_schema: String,
                   table_name: String,
                   column_name: String,
                   constraint_catalog: String,
                   constraint_schema: String,
                   constraint_name: String
                  )

    object Row {
      val parser: RowParser[ConstraintColumnUsage.Row] =
        row => anorm.Success {
          ConstraintColumnUsage.Row(
            table_catalog = row[String]("table_catalog"),
            table_schema = row[String]("table_schema"),
            table_name = row[String]("table_name"),
            column_name = row[String]("column_name"),
            constraint_catalog = row[String]("constraint_catalog"),
            constraint_schema = row[String]("constraint_schema"),
            constraint_name = row[String]("constraint_name")
          )
        }
    }

    def all(implicit c: Connection): List[ConstraintColumnUsage.Row] =
      SQL"""
        select *
        from information_schema.constraint_column_usage
      """
        .as(ConstraintColumnUsage.Row.parser.*)

  }

  object PgType {
    case class Row(oid: Int, typname: String, typtype: Char, typcategory: Char)

    object Row {
      val parser: RowParser[PgType.Row] = row =>
        anorm.Success {
          PgType.Row(
            oid = row[Int]("oid"),
            typname = row[String]("typname"),
            typtype = row[Char]("typtype"),
            typcategory = row[Char]("typcategory"),
          )
        }
    }

    def all(implicit c: Connection): List[PgType.Row] =
      SQL"""
         select *
         from pg_catalog.pg_type
       """
        .as(PgType.Row.parser.*)
  }

}