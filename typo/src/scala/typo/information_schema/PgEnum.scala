package typo.information_schema

import anorm.{RowParser, SqlStringInterpolation}

import java.sql.Connection

object PgEnum {
  case class Row(
      oid: Int,
      enumtypid: Int,
      enumsortorder: Float,
      enumlabel: String
  )

  object Row {
    val parser: RowParser[PgEnum.Row] =
      row =>
        anorm.Success {
          PgEnum.Row(
            oid = row[Int]("oid"),
            enumtypid = row[Int]("enumtypid"),
            enumsortorder = row[Float]("enumsortorder"),
            enumlabel = row[String]("enumlabel")
          )
        }
  }

  def all(implicit c: Connection): List[PgEnum.Row] =
    SQL"""
      select *
      from pg_catalog.pg_enum
    """
      .as(PgEnum.Row.parser.*)
}
