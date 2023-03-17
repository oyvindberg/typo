package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgHbaFileRulesRow(
  lineNumber: /* unknown nullability */ Option[Int],
  `type`: /* unknown nullability */ Option[String],
  database: /* unknown nullability */ Option[Array[String]],
  userName: /* unknown nullability */ Option[Array[String]],
  address: /* unknown nullability */ Option[String],
  netmask: /* unknown nullability */ Option[String],
  authMethod: /* unknown nullability */ Option[String],
  options: /* unknown nullability */ Option[Array[String]],
  error: /* unknown nullability */ Option[String]
)

object PgHbaFileRulesRow {
  implicit val rowParser: RowParser[PgHbaFileRulesRow] = { row =>
    Success(
      PgHbaFileRulesRow(
        lineNumber = row[/* unknown nullability */ Option[Int]]("line_number"),
        `type` = row[/* unknown nullability */ Option[String]]("type"),
        database = row[/* unknown nullability */ Option[Array[String]]]("database"),
        userName = row[/* unknown nullability */ Option[Array[String]]]("user_name"),
        address = row[/* unknown nullability */ Option[String]]("address"),
        netmask = row[/* unknown nullability */ Option[String]]("netmask"),
        authMethod = row[/* unknown nullability */ Option[String]]("auth_method"),
        options = row[/* unknown nullability */ Option[Array[String]]]("options"),
        error = row[/* unknown nullability */ Option[String]]("error")
      )
    )
  }

  implicit val oFormat: OFormat[PgHbaFileRulesRow] = Json.format
}
