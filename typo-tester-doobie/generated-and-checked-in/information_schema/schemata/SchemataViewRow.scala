/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package schemata

import adventureworks.information_schema.CharacterData
import adventureworks.information_schema.SqlIdentifier
import doobie.Get
import doobie.Read
import doobie.enumerated.Nullability
import io.circe.Decoder
import io.circe.Encoder
import io.circe.HCursor
import io.circe.Json
import java.sql.ResultSet

case class SchemataViewRow(
  catalogName: Option[SqlIdentifier],
  schemaName: Option[SqlIdentifier],
  schemaOwner: Option[SqlIdentifier],
  defaultCharacterSetCatalog: Option[SqlIdentifier],
  defaultCharacterSetSchema: Option[SqlIdentifier],
  defaultCharacterSetName: Option[SqlIdentifier],
  sqlPath: Option[CharacterData]
)

object SchemataViewRow {
  implicit val decoder: Decoder[SchemataViewRow] =
    (c: HCursor) =>
      for {
        catalogName <- c.downField("catalog_name").as[Option[SqlIdentifier]]
        schemaName <- c.downField("schema_name").as[Option[SqlIdentifier]]
        schemaOwner <- c.downField("schema_owner").as[Option[SqlIdentifier]]
        defaultCharacterSetCatalog <- c.downField("default_character_set_catalog").as[Option[SqlIdentifier]]
        defaultCharacterSetSchema <- c.downField("default_character_set_schema").as[Option[SqlIdentifier]]
        defaultCharacterSetName <- c.downField("default_character_set_name").as[Option[SqlIdentifier]]
        sqlPath <- c.downField("sql_path").as[Option[CharacterData]]
      } yield SchemataViewRow(catalogName, schemaName, schemaOwner, defaultCharacterSetCatalog, defaultCharacterSetSchema, defaultCharacterSetName, sqlPath)
  implicit val encoder: Encoder[SchemataViewRow] = {
    import io.circe.syntax._
    row =>
      Json.obj(
        "catalog_name" := row.catalogName,
        "schema_name" := row.schemaName,
        "schema_owner" := row.schemaOwner,
        "default_character_set_catalog" := row.defaultCharacterSetCatalog,
        "default_character_set_schema" := row.defaultCharacterSetSchema,
        "default_character_set_name" := row.defaultCharacterSetName,
        "sql_path" := row.sqlPath
      )}
  implicit val read: Read[SchemataViewRow] =
    new Read[SchemataViewRow](
      gets = List(
        (Get[SqlIdentifier], Nullability.Nullable),
        (Get[SqlIdentifier], Nullability.Nullable),
        (Get[SqlIdentifier], Nullability.Nullable),
        (Get[SqlIdentifier], Nullability.Nullable),
        (Get[SqlIdentifier], Nullability.Nullable),
        (Get[SqlIdentifier], Nullability.Nullable),
        (Get[CharacterData], Nullability.Nullable)
      ),
      unsafeGet = (rs: ResultSet, i: Int) => SchemataViewRow(
        catalogName = Get[SqlIdentifier].unsafeGetNullable(rs, i + 0),
        schemaName = Get[SqlIdentifier].unsafeGetNullable(rs, i + 1),
        schemaOwner = Get[SqlIdentifier].unsafeGetNullable(rs, i + 2),
        defaultCharacterSetCatalog = Get[SqlIdentifier].unsafeGetNullable(rs, i + 3),
        defaultCharacterSetSchema = Get[SqlIdentifier].unsafeGetNullable(rs, i + 4),
        defaultCharacterSetName = Get[SqlIdentifier].unsafeGetNullable(rs, i + 5),
        sqlPath = Get[CharacterData].unsafeGetNullable(rs, i + 6)
      )
    )
  

}