package typo
package internal
package metadb

import anorm.{RowParser, SqlStringInterpolation}

import java.sql.Connection

object PgEnum {
  case class Row(
      name: db.RelationName,
      enum_sort_order: Float,
      enum_value: String
  )

  object Row {
    val parser: RowParser[PgEnum.Row] =
      row =>
        anorm.Success {
          PgEnum.Row(
            name = db.RelationName(
              Some(row[String]("enum_schema")),
              row[String]("enum_name")
            ),
            enum_sort_order = row[Float]("enum_sort_order"),
            enum_value = row[String]("enum_value")
          )
        }
  }

  def all(implicit c: Connection): List[PgEnum.Row] =
    SQL"""
select n.nspname as enum_schema,
           t.typname as enum_name,
           e.enumsortorder as enum_sort_order,
           e.enumlabel as enum_value
    from pg_type t
             join pg_enum e on t.oid = e.enumtypid
             join pg_catalog.pg_namespace n ON n.oid = t.typnamespace;
    """
      .as(PgEnum.Row.parser.*)
}
