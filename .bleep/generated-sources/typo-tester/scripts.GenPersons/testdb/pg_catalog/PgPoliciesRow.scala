package testdb.pg_catalog

import anorm.RowParser
import anorm.Success
import play.api.libs.json.Json
import play.api.libs.json.OFormat

case class PgPoliciesRow(
  /** Points to [[testdb.pg_catalog.PgNamespaceRow.nspname]] */
  schemaname: String,
  /** Points to [[testdb.pg_catalog.PgClassRow.relname]] */
  tablename: String,
  /** Points to [[testdb.pg_catalog.PgPolicyRow.polname]] */
  policyname: String,
  permissive: /* unknown nullability */ Option[String],
  roles: /* typo doesn't know how to translate: columnType: Array, columnTypeName: _name, columnClassName: java.sql.Array */ Any,
  cmd: /* unknown nullability */ Option[String],
  qual: /* unknown nullability */ Option[String],
  withCheck: /* unknown nullability */ Option[String]
)

object PgPoliciesRow {
  implicit val rowParser: RowParser[PgPoliciesRow] = { row =>
    Success(
      PgPoliciesRow(
        schemaname = row[String]("schemaname"),
        tablename = row[String]("tablename"),
        policyname = row[String]("policyname"),
        permissive = row[/* unknown nullability */ Option[String]]("permissive"),
        roles = row[/* typo doesn't know how to translate: columnType: Array, columnTypeName: _name, columnClassName: java.sql.Array */ Any]("roles"),
        cmd = row[/* unknown nullability */ Option[String]]("cmd"),
        qual = row[/* unknown nullability */ Option[String]]("qual"),
        withCheck = row[/* unknown nullability */ Option[String]]("with_check")
      )
    )
  }

  implicit val oFormat: OFormat[PgPoliciesRow] = Json.format
}
