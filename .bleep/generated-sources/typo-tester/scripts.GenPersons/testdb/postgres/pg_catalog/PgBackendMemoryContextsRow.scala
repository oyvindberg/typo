package testdb
package postgres
package pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PgBackendMemoryContextsRow(
  name: /* unknown nullability */ Option[String],
  ident: /* unknown nullability */ Option[String],
  parent: /* unknown nullability */ Option[String],
  level: /* unknown nullability */ Option[Int],
  totalBytes: /* unknown nullability */ Option[Long],
  totalNblocks: /* unknown nullability */ Option[Long],
  freeBytes: /* unknown nullability */ Option[Long],
  freeChunks: /* unknown nullability */ Option[Long],
  usedBytes: /* unknown nullability */ Option[Long]
)

object PgBackendMemoryContextsRow {
  implicit val rowParser: RowParser[PgBackendMemoryContextsRow] = { row =>
    Success(
      PgBackendMemoryContextsRow(
        name = row[/* unknown nullability */ Option[String]]("name"),
        ident = row[/* unknown nullability */ Option[String]]("ident"),
        parent = row[/* unknown nullability */ Option[String]]("parent"),
        level = row[/* unknown nullability */ Option[Int]]("level"),
        totalBytes = row[/* unknown nullability */ Option[Long]]("total_bytes"),
        totalNblocks = row[/* unknown nullability */ Option[Long]]("total_nblocks"),
        freeBytes = row[/* unknown nullability */ Option[Long]]("free_bytes"),
        freeChunks = row[/* unknown nullability */ Option[Long]]("free_chunks"),
        usedBytes = row[/* unknown nullability */ Option[Long]]("used_bytes")
      )
    )
  }

  implicit val oFormat: OFormat[PgBackendMemoryContextsRow] = new OFormat[PgBackendMemoryContextsRow]{
    override def writes(o: PgBackendMemoryContextsRow): JsObject =
      Json.obj(
        "name" -> o.name,
      "ident" -> o.ident,
      "parent" -> o.parent,
      "level" -> o.level,
      "total_bytes" -> o.totalBytes,
      "total_nblocks" -> o.totalNblocks,
      "free_bytes" -> o.freeBytes,
      "free_chunks" -> o.freeChunks,
      "used_bytes" -> o.usedBytes
      )

    override def reads(json: JsValue): JsResult[PgBackendMemoryContextsRow] = {
      JsResult.fromTry(
        Try(
          PgBackendMemoryContextsRow(
            name = json.\("name").toOption.map(_.as[String]),
            ident = json.\("ident").toOption.map(_.as[String]),
            parent = json.\("parent").toOption.map(_.as[String]),
            level = json.\("level").toOption.map(_.as[Int]),
            totalBytes = json.\("total_bytes").toOption.map(_.as[Long]),
            totalNblocks = json.\("total_nblocks").toOption.map(_.as[Long]),
            freeBytes = json.\("free_bytes").toOption.map(_.as[Long]),
            freeChunks = json.\("free_chunks").toOption.map(_.as[Long]),
            usedBytes = json.\("used_bytes").toOption.map(_.as[Long])
          )
        )
      )
    }
  }
}
