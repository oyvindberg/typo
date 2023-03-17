package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

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

  implicit val oFormat: OFormat[ForeignDataWrappersRow] = Json.format
}
