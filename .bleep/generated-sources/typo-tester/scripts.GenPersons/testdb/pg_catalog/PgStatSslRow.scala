package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.math.BigDecimal

case class PgStatSslRow(
  pid: /* unknown nullability */ Option[Int],
  ssl: /* unknown nullability */ Option[Boolean],
  version: /* unknown nullability */ Option[String],
  cipher: /* unknown nullability */ Option[String],
  bits: /* unknown nullability */ Option[Int],
  clientDn: /* unknown nullability */ Option[String],
  clientSerial: /* unknown nullability */ Option[BigDecimal],
  issuerDn: /* unknown nullability */ Option[String]
)

object PgStatSslRow {
  implicit val rowParser: RowParser[PgStatSslRow] = { row =>
    Success(
      PgStatSslRow(
        pid = row[/* unknown nullability */ Option[Int]]("pid"),
        ssl = row[/* unknown nullability */ Option[Boolean]]("ssl"),
        version = row[/* unknown nullability */ Option[String]]("version"),
        cipher = row[/* unknown nullability */ Option[String]]("cipher"),
        bits = row[/* unknown nullability */ Option[Int]]("bits"),
        clientDn = row[/* unknown nullability */ Option[String]]("client_dn"),
        clientSerial = row[/* unknown nullability */ Option[BigDecimal]]("client_serial"),
        issuerDn = row[/* unknown nullability */ Option[String]]("issuer_dn")
      )
    )
  }

  implicit val oFormat: OFormat[PgStatSslRow] = Json.format
}
