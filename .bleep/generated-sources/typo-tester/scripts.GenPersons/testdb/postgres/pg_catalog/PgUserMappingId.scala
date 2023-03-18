package testdb
package postgres
package pg_catalog

import anorm.Column
import anorm.RowParser
import anorm.SqlParser
import anorm.ToStatement
import play.api.libs.json.Format

case class PgUserMappingId(value: Long) extends AnyVal
object PgUserMappingId {
  implicit val ordering: Ordering[PgUserMappingId] = Ordering.by(_.value)
  implicit val format: Format[PgUserMappingId] = implicitly[Format[Long]].bimap(PgUserMappingId.apply, _.value)
  implicit val toStatement: ToStatement[PgUserMappingId] = implicitly[ToStatement[Long]].contramap(_.value)
  implicit val column: Column[PgUserMappingId] = implicitly[Column[Long]].map(PgUserMappingId.apply)
  implicit val rowParser: RowParser[PgUserMappingId] = SqlParser.get[PgUserMappingId]("oid")
}
