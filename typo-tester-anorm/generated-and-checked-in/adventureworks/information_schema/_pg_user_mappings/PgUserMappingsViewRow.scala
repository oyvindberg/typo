/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package `_pg_user_mappings`

import adventureworks.pg_catalog.pg_user_mapping.PgUserMappingId
import anorm.Column
import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.OWrites
import play.api.libs.json.Reads
import play.api.libs.json.Writes
import scala.collection.immutable.ListMap
import scala.util.Try

case class PgUserMappingsViewRow(
  /** Points to [[pg_catalog.pg_user_mapping.PgUserMappingRow.oid]] */
  oid: PgUserMappingId,
  /** Points to [[pg_catalog.pg_user_mapping.PgUserMappingRow.umoptions]] */
  umoptions: Option[Array[String]],
  /** Points to [[pg_catalog.pg_user_mapping.PgUserMappingRow.umuser]] */
  umuser: /* oid */ Long,
  authorizationIdentifier: /* nullability unknown */ Option[String],
  /** Points to [[`_pg_foreign_servers`.PgForeignServersViewRow.foreignServerCatalog]] */
  foreignServerCatalog: Option[/* nullability unknown */ String],
  /** Points to [[`_pg_foreign_servers`.PgForeignServersViewRow.foreignServerName]] */
  foreignServerName: Option[/* nullability unknown */ String],
  /** Points to [[`_pg_foreign_servers`.PgForeignServersViewRow.authorizationIdentifier]] */
  srvowner: Option[/* nullability unknown */ String]
)

object PgUserMappingsViewRow {
  implicit lazy val reads: Reads[PgUserMappingsViewRow] = Reads[PgUserMappingsViewRow](json => JsResult.fromTry(
      Try(
        PgUserMappingsViewRow(
          oid = json.\("oid").as(PgUserMappingId.reads),
          umoptions = json.\("umoptions").toOption.map(_.as(Reads.ArrayReads[String](Reads.StringReads, implicitly))),
          umuser = json.\("umuser").as(Reads.LongReads),
          authorizationIdentifier = json.\("authorization_identifier").toOption.map(_.as(Reads.StringReads)),
          foreignServerCatalog = json.\("foreign_server_catalog").toOption.map(_.as(Reads.StringReads)),
          foreignServerName = json.\("foreign_server_name").toOption.map(_.as(Reads.StringReads)),
          srvowner = json.\("srvowner").toOption.map(_.as(Reads.StringReads))
        )
      )
    ),
  )
  def rowParser(idx: Int): RowParser[PgUserMappingsViewRow] = RowParser[PgUserMappingsViewRow] { row =>
    Success(
      PgUserMappingsViewRow(
        oid = row(idx + 0)(PgUserMappingId.column),
        umoptions = row(idx + 1)(Column.columnToOption(Column.columnToArray[String](Column.columnToString, implicitly))),
        umuser = row(idx + 2)(Column.columnToLong),
        authorizationIdentifier = row(idx + 3)(Column.columnToOption(Column.columnToString)),
        foreignServerCatalog = row(idx + 4)(Column.columnToOption(Column.columnToString)),
        foreignServerName = row(idx + 5)(Column.columnToOption(Column.columnToString)),
        srvowner = row(idx + 6)(Column.columnToOption(Column.columnToString))
      )
    )
  }
  implicit lazy val writes: OWrites[PgUserMappingsViewRow] = OWrites[PgUserMappingsViewRow](o =>
    new JsObject(ListMap[String, JsValue](
      "oid" -> PgUserMappingId.writes.writes(o.oid),
      "umoptions" -> Writes.OptionWrites(Writes.arrayWrites[String](implicitly, Writes.StringWrites)).writes(o.umoptions),
      "umuser" -> Writes.LongWrites.writes(o.umuser),
      "authorization_identifier" -> Writes.OptionWrites(Writes.StringWrites).writes(o.authorizationIdentifier),
      "foreign_server_catalog" -> Writes.OptionWrites(Writes.StringWrites).writes(o.foreignServerCatalog),
      "foreign_server_name" -> Writes.OptionWrites(Writes.StringWrites).writes(o.foreignServerName),
      "srvowner" -> Writes.OptionWrites(Writes.StringWrites).writes(o.srvowner)
    ))
  )
}