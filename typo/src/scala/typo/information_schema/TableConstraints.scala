package typo.information_schema

import anorm.{RowParser, SqlStringInterpolation}

import java.sql.Connection

object TableConstraints {
  case class Row(
      constraint_catalog: String,
      constraint_schema: String,
      constraint_name: String,
      table_catalog: String,
      table_schema: String,
      table_name: String,
      constraint_type: String
  )

  object Row {
    val parser: RowParser[TableConstraints.Row] =
      row =>
        anorm.Success {
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
