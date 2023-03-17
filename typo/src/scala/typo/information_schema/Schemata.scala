package typo.information_schema

import anorm.{RowParser, SqlStringInterpolation}

import java.sql.Connection

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
