package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgSeclabelsRow(
  objoid: /* unknown nullability */ Option[Long],
  classoid: /* unknown nullability */ Option[Long],
  objsubid: /* unknown nullability */ Option[Int],
  objtype: /* unknown nullability */ Option[String],
  objnamespace: /* unknown nullability */ Option[Long],
  objname: /* unknown nullability */ Option[String],
  provider: /* unknown nullability */ Option[String],
  label: /* unknown nullability */ Option[String]
)

object PgSeclabelsRow {
  implicit val rowParser: RowParser[PgSeclabelsRow] = { row =>
    Success(
      PgSeclabelsRow(
        objoid = row[/* unknown nullability */ Option[Long]]("objoid"),
        classoid = row[/* unknown nullability */ Option[Long]]("classoid"),
        objsubid = row[/* unknown nullability */ Option[Int]]("objsubid"),
        objtype = row[/* unknown nullability */ Option[String]]("objtype"),
        objnamespace = row[/* unknown nullability */ Option[Long]]("objnamespace"),
        objname = row[/* unknown nullability */ Option[String]]("objname"),
        provider = row[/* unknown nullability */ Option[String]]("provider"),
        label = row[/* unknown nullability */ Option[String]]("label")
      )
    )
  }

  implicit val oFormat: OFormat[PgSeclabelsRow] = Json.format
}
