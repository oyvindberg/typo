package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

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

  implicit val oFormat: OFormat[PgShmemAllocationsRow] = new OFormat[PgShmemAllocationsRow]{
    override def writes(o: PgShmemAllocationsRow): JsObject =
      Json.obj(
        "name" -> o.name,
      "off" -> o.off,
      "size" -> o.size,
      "allocated_size" -> o.allocatedSize
      )

    override def reads(json: JsValue): JsResult[PgShmemAllocationsRow] = {
      JsResult.fromTry(
        Try(
          PgShmemAllocationsRow(
            name = json.\("name").toOption.map(_.as[String]),
            off = json.\("off").toOption.map(_.as[Long]),
            size = json.\("size").toOption.map(_.as[Long]),
            allocatedSize = json.\("allocated_size").toOption.map(_.as[Long])
          )
        )
      )
    }
  }
}
