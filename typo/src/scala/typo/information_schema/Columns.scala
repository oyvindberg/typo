package typo.information_schema

import anorm.{RowParser, SqlStringInterpolation}

import java.sql.Connection

object Columns {
  case class Row(
      table_catalog: String,
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
      row =>
        anorm.Success {
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
