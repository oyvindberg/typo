/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package constraint_column_usage

import adventureworks.information_schema.SqlIdentifier
import doobie.Get
import doobie.Read
import doobie.enumerated.Nullability
import io.circe.Decoder
import io.circe.Encoder
import io.circe.HCursor
import io.circe.Json
import java.sql.ResultSet

case class ConstraintColumnUsageViewRow(
  tableCatalog: Option[SqlIdentifier],
  tableSchema: Option[SqlIdentifier],
  tableName: Option[SqlIdentifier],
  columnName: Option[SqlIdentifier],
  constraintCatalog: Option[SqlIdentifier],
  constraintSchema: Option[SqlIdentifier],
  constraintName: Option[SqlIdentifier]
)

object ConstraintColumnUsageViewRow {
  implicit val decoder: Decoder[ConstraintColumnUsageViewRow] =
    (c: HCursor) =>
      for {
        tableCatalog <- c.downField("table_catalog").as[Option[SqlIdentifier]]
        tableSchema <- c.downField("table_schema").as[Option[SqlIdentifier]]
        tableName <- c.downField("table_name").as[Option[SqlIdentifier]]
        columnName <- c.downField("column_name").as[Option[SqlIdentifier]]
        constraintCatalog <- c.downField("constraint_catalog").as[Option[SqlIdentifier]]
        constraintSchema <- c.downField("constraint_schema").as[Option[SqlIdentifier]]
        constraintName <- c.downField("constraint_name").as[Option[SqlIdentifier]]
      } yield ConstraintColumnUsageViewRow(tableCatalog, tableSchema, tableName, columnName, constraintCatalog, constraintSchema, constraintName)
  implicit val encoder: Encoder[ConstraintColumnUsageViewRow] = {
    import io.circe.syntax._
    row =>
      Json.obj(
        "table_catalog" := row.tableCatalog,
        "table_schema" := row.tableSchema,
        "table_name" := row.tableName,
        "column_name" := row.columnName,
        "constraint_catalog" := row.constraintCatalog,
        "constraint_schema" := row.constraintSchema,
        "constraint_name" := row.constraintName
      )}
  implicit val read: Read[ConstraintColumnUsageViewRow] =
    new Read[ConstraintColumnUsageViewRow](
      gets = List(
        (Get[SqlIdentifier], Nullability.Nullable),
        (Get[SqlIdentifier], Nullability.Nullable),
        (Get[SqlIdentifier], Nullability.Nullable),
        (Get[SqlIdentifier], Nullability.Nullable),
        (Get[SqlIdentifier], Nullability.Nullable),
        (Get[SqlIdentifier], Nullability.Nullable),
        (Get[SqlIdentifier], Nullability.Nullable)
      ),
      unsafeGet = (rs: ResultSet, i: Int) => ConstraintColumnUsageViewRow(
        tableCatalog = Get[SqlIdentifier].unsafeGetNullable(rs, i + 0),
        tableSchema = Get[SqlIdentifier].unsafeGetNullable(rs, i + 1),
        tableName = Get[SqlIdentifier].unsafeGetNullable(rs, i + 2),
        columnName = Get[SqlIdentifier].unsafeGetNullable(rs, i + 3),
        constraintCatalog = Get[SqlIdentifier].unsafeGetNullable(rs, i + 4),
        constraintSchema = Get[SqlIdentifier].unsafeGetNullable(rs, i + 5),
        constraintName = Get[SqlIdentifier].unsafeGetNullable(rs, i + 6)
      )
    )
  

}