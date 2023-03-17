package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

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

  implicit val oFormat: OFormat[PgBackendMemoryContextsRow] = Json.format
}
