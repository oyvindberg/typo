package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class CollationsRow(
  collationCatalog: /* unknown nullability */ Option[String],
  collationSchema: /* unknown nullability */ Option[String],
  collationName: /* unknown nullability */ Option[String],
  padAttribute: /* unknown nullability */ Option[String]
)

object CollationsRow {
  implicit val rowParser: RowParser[CollationsRow] = { row =>
    Success(
      CollationsRow(
        collationCatalog = row[/* unknown nullability */ Option[String]]("collation_catalog"),
        collationSchema = row[/* unknown nullability */ Option[String]]("collation_schema"),
        collationName = row[/* unknown nullability */ Option[String]]("collation_name"),
        padAttribute = row[/* unknown nullability */ Option[String]]("pad_attribute")
      )
    )
  }

  implicit val oFormat: OFormat[CollationsRow] = Json.format
}
