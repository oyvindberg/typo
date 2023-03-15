package com.foo

import anorm._
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funsuite.AnyFunSuite

import java.sql.{Connection, DriverManager}

class AppTest extends AnyFunSuite with TypeCheckedTripleEquals {
  val url = "jdbc:postgresql://localhost:5432/samordnaopptak?user=postgres&password=postgres"
  implicit val conn: Connection = DriverManager.getConnection(url)

  test("works") {
    val schematas = Q.Schemata.all
//    println(schematas)

    val tables = Q.Tables.all
//    println(tables)

    val columns = Q.Columns.all
//    println(columns)

    val tableConstraints = Q.TableConstraints.all
//    println(tableConstraints)

    val referentialConstraints = Q.ReferentialConstraints.all
    //    println(referentialConstraints)

    val constraintColumnUsage = Q.ConstraintColumnUsage.all
    println(constraintColumnUsage)

    assert(true)
  }
}

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

}