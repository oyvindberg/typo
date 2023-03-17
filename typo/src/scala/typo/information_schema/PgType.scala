package typo.information_schema

import anorm.{RowParser, SqlStringInterpolation}

import java.sql.Connection

object PgType {
  case class Row(oid: Int, typname: String, typtype: Char, typcategory: Char)

  object Row {
    val parser: RowParser[PgType.Row] = row =>
      anorm.Success {
        PgType.Row(
          oid = row[Int]("oid"),
          typname = row[String]("typname"),
          typtype = row[Char]("typtype"),
          typcategory = row[Char]("typcategory")
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
