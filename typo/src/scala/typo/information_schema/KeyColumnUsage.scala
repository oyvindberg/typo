package typo.information_schema

import anorm._

import java.sql.Connection

object KeyColumnUsage {
  case class Row(
      constraint_catalog: String,
      constraint_schema: String,
      constraint_name: String,
      table_catalog: String,
      table_schema: String,
      table_name: String,
      column_name: String,
      ordinal_position: Int,
      position_in_unique_constraint: Option[Int]
  )

  object Row {
    val parser: RowParser[KeyColumnUsage.Row] =
      row =>
        anorm.Success {
          KeyColumnUsage.Row(
            constraint_catalog = row[String]("constraint_catalog"),
            constraint_schema = row[String]("constraint_schema"),
            constraint_name = row[String]("constraint_name"),
            table_catalog = row[String]("table_catalog"),
            table_schema = row[String]("table_schema"),
            table_name = row[String]("table_name"),
            column_name = row[String]("column_name"),
            ordinal_position = row[Int]("ordinal_position"),
            position_in_unique_constraint = row[Option[Int]]("position_in_unique_constraint")
          )
        }
  }

  def all(implicit c: Connection): List[KeyColumnUsage.Row] =
    SQL"""
        select *
        from information_schema.key_column_usage
      """
      .as(KeyColumnUsage.Row.parser.*)

}
