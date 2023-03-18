package testdb
package postgres
package pg_catalog

import anorm.Column
import anorm.RowParser
import anorm.SqlParser
import anorm.ToStatement
import play.api.libs.json.Format

case class PgPublicationId(value: Long) extends AnyVal
object PgPublicationId {
  implicit val ordering: Ordering[PgPublicationId] = Ordering.by(_.value)
  implicit val format: Format[PgPublicationId] = implicitly[Format[Long]].bimap(PgPublicationId.apply, _.value)
  implicit val toStatement: ToStatement[PgPublicationId] = implicitly[ToStatement[Long]].contramap(_.value)
  implicit val column: Column[PgPublicationId] = implicitly[Column[Long]].map(PgPublicationId.apply)
  implicit val rowParser: RowParser[PgPublicationId] = SqlParser.get[PgPublicationId]("oid")
}
