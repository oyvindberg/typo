package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgForeignDataWrappersRow(
  /** Points to [[testdb.pg_catalog.PgForeignDataWrapperRow.oid]] */
  oid: Long,
  /** Points to [[testdb.pg_catalog.PgForeignDataWrapperRow.fdwowner]] */
  fdwowner: Long,
  /** Points to [[testdb.pg_catalog.PgForeignDataWrapperRow.fdwoptions]] */
  fdwoptions: Option[Array[String]],
  foreignDataWrapperCatalog: /* unknown nullability */ Option[String],
  foreignDataWrapperName: /* unknown nullability */ Option[String],
  authorizationIdentifier: /* unknown nullability */ Option[String],
  foreignDataWrapperLanguage: /* unknown nullability */ Option[String]
)

object PgForeignDataWrappersRow {
  implicit val rowParser: RowParser[PgForeignDataWrappersRow] = { row =>
    Success(
      PgForeignDataWrappersRow(
        oid = row[Long]("oid"),
        fdwowner = row[Long]("fdwowner"),
        fdwoptions = row[Option[Array[String]]]("fdwoptions"),
        foreignDataWrapperCatalog = row[/* unknown nullability */ Option[String]]("foreign_data_wrapper_catalog"),
        foreignDataWrapperName = row[/* unknown nullability */ Option[String]]("foreign_data_wrapper_name"),
        authorizationIdentifier = row[/* unknown nullability */ Option[String]]("authorization_identifier"),
        foreignDataWrapperLanguage = row[/* unknown nullability */ Option[String]]("foreign_data_wrapper_language")
      )
    )
  }

  implicit val oFormat: OFormat[PgForeignDataWrappersRow] = Json.format
}
