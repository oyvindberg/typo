package typo.information_schema

import anorm.{RowParser, SqlStringInterpolation}

import java.sql.Connection

object ConstraintColumnUsage {
  case class Row(
      table_catalog: String,
      table_schema: String,
      table_name: String,
      column_name: String,
      constraint_catalog: String,
      constraint_schema: String,
      constraint_name: String
  )

  object Row {
    val parser: RowParser[ConstraintColumnUsage.Row] =
      row =>
        anorm.Success {
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
