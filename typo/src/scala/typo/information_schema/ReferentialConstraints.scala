package typo.information_schema

import anorm.{RowParser, SqlStringInterpolation}

import java.sql.Connection

object ReferentialConstraints {
  case class Row(
      constraint_catalog: String,
      constraint_schema: String,
      constraint_name: String,
      unique_constraint_catalog: String,
      unique_constraint_schema: String,
      unique_constraint_name: String
  )

  object Row {
    val parser: RowParser[ReferentialConstraints.Row] =
      row =>
        anorm.Success {
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
