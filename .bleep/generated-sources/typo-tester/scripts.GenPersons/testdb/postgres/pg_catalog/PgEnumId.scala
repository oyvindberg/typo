package testdb
package postgres
package pg_catalog

import anorm.Column
import anorm.RowParser
import anorm.SqlParser
import anorm.ToStatement
import play.api.libs.json.Format

case class PgEnumId(value: Long) extends AnyVal
object PgEnumId {
  implicit val ordering: Ordering[PgEnumId] = Ordering.by(_.value)
  implicit val format: Format[PgEnumId] = implicitly[Format[Long]].bimap(PgEnumId.apply, _.value)
  implicit val toStatement: ToStatement[PgEnumId] = implicitly[ToStatement[Long]].contramap(_.value)
  implicit val column: Column[PgEnumId] = implicitly[Column[Long]].map(PgEnumId.apply)
  implicit val rowParser: RowParser[PgEnumId] = SqlParser.get[PgEnumId]("oid")
}
