package testdb.myschema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class MaritalStatusRow(
  id: MaritalStatusId
)
object MaritalStatusRow {
  implicit val rowParser: RowParser[MaritalStatusRow] = { row =>
    Success(
      MaritalStatusRow(
        id = row[MaritalStatusId]("id")
      )
    )
  }

  implicit val oFormat: OFormat[MaritalStatusRow] = Json.format
}
