package testdb
package information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class ForeignDataWrappersRow(
  /** Points to [[testdb.information_schema.PgForeignDataWrappersRow.foreignDataWrapperCatalog]] */
  foreignDataWrapperCatalog: Option[String],
  /** Points to [[testdb.information_schema.PgForeignDataWrappersRow.foreignDataWrapperName]] */
  foreignDataWrapperName: Option[String],
  /** Points to [[testdb.information_schema.PgForeignDataWrappersRow.authorizationIdentifier]] */
  authorizationIdentifier: Option[String],
  libraryName: /* unknown nullability */ Option[String],
  /** Points to [[testdb.information_schema.PgForeignDataWrappersRow.foreignDataWrapperLanguage]] */
  foreignDataWrapperLanguage: Option[String]
)

object ForeignDataWrappersRow {
  implicit val rowParser: RowParser[ForeignDataWrappersRow] = { row =>
    Success(
      ForeignDataWrappersRow(
        foreignDataWrapperCatalog = row[Option[String]]("foreign_data_wrapper_catalog"),
        foreignDataWrapperName = row[Option[String]]("foreign_data_wrapper_name"),
        authorizationIdentifier = row[Option[String]]("authorization_identifier"),
        libraryName = row[/* unknown nullability */ Option[String]]("library_name"),
        foreignDataWrapperLanguage = row[Option[String]]("foreign_data_wrapper_language")
      )
    )
  }

  implicit val oFormat: OFormat[ForeignDataWrappersRow] = new OFormat[ForeignDataWrappersRow]{
    override def writes(o: ForeignDataWrappersRow): JsObject =
      Json.obj(
        "foreign_data_wrapper_catalog" -> o.foreignDataWrapperCatalog,
      "foreign_data_wrapper_name" -> o.foreignDataWrapperName,
      "authorization_identifier" -> o.authorizationIdentifier,
      "library_name" -> o.libraryName,
      "foreign_data_wrapper_language" -> o.foreignDataWrapperLanguage
      )

    override def reads(json: JsValue): JsResult[ForeignDataWrappersRow] = {
      JsResult.fromTry(
        Try(
          ForeignDataWrappersRow(
            foreignDataWrapperCatalog = json.\("foreign_data_wrapper_catalog").toOption.map(_.as[String]),
            foreignDataWrapperName = json.\("foreign_data_wrapper_name").toOption.map(_.as[String]),
            authorizationIdentifier = json.\("authorization_identifier").toOption.map(_.as[String]),
            libraryName = json.\("library_name").toOption.map(_.as[String]),
            foreignDataWrapperLanguage = json.\("foreign_data_wrapper_language").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
