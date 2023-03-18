package typo.information_schema

import anorm.{RowParser, SqlStringInterpolation}

import java.sql.Connection

object Tables {
  case class Row(table_catalog: String, table_schema: String, table_name: String, table_type: String)

  object Row {
    val parser: RowParser[Tables.Row] = row =>
      anorm.Success {
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
        where table_type = 'BASE TABLE'
      """
      .as(Tables.Row.parser.*)
}
