package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgReplicationOriginStatusRow(
  localId: /* unknown nullability */ Option[Long],
  externalId: /* unknown nullability */ Option[String],
  remoteLsn: /* unknown nullability */ Option[/* pg_lsn */ String],
  localLsn: /* unknown nullability */ Option[/* pg_lsn */ String]
)

object PgReplicationOriginStatusRow {
  implicit val rowParser: RowParser[PgReplicationOriginStatusRow] = { row =>
    Success(
      PgReplicationOriginStatusRow(
        localId = row[/* unknown nullability */ Option[Long]]("local_id"),
        externalId = row[/* unknown nullability */ Option[String]]("external_id"),
        remoteLsn = row[/* unknown nullability */ Option[/* pg_lsn */ String]]("remote_lsn"),
        localLsn = row[/* unknown nullability */ Option[/* pg_lsn */ String]]("local_lsn")
      )
    )
  }

  implicit val oFormat: OFormat[PgReplicationOriginStatusRow] = new OFormat[PgReplicationOriginStatusRow]{
    override def writes(o: PgReplicationOriginStatusRow): JsObject =
      Json.obj(
        "local_id" -> o.localId,
      "external_id" -> o.externalId,
      "remote_lsn" -> o.remoteLsn,
      "local_lsn" -> o.localLsn
      )

    override def reads(json: JsValue): JsResult[PgReplicationOriginStatusRow] = {
      JsResult.fromTry(
        Try(
          PgReplicationOriginStatusRow(
            localId = json.\("local_id").toOption.map(_.as[Long]),
            externalId = json.\("external_id").toOption.map(_.as[String]),
            remoteLsn = json.\("remote_lsn").toOption.map(_.as[/* pg_lsn */ String]),
            localLsn = json.\("local_lsn").toOption.map(_.as[/* pg_lsn */ String])
          )
        )
      )
    }
  }
}
