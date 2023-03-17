package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgShmemAllocationsRow(
  name: /* unknown nullability */ Option[String],
  off: /* unknown nullability */ Option[Long],
  size: /* unknown nullability */ Option[Long],
  allocatedSize: /* unknown nullability */ Option[Long]
)

object PgShmemAllocationsRow {
  implicit val rowParser: RowParser[PgShmemAllocationsRow] = { row =>
    Success(
      PgShmemAllocationsRow(
        name = row[/* unknown nullability */ Option[String]]("name"),
        off = row[/* unknown nullability */ Option[Long]]("off"),
        size = row[/* unknown nullability */ Option[Long]]("size"),
        allocatedSize = row[/* unknown nullability */ Option[Long]]("allocated_size")
      )
    )
  }

  implicit val oFormat: OFormat[PgShmemAllocationsRow] = Json.format
}
