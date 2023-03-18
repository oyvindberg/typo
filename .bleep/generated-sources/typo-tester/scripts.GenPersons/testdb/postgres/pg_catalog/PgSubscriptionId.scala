package testdb
package postgres
package pg_catalog

import anorm.Column
import anorm.RowParser
import anorm.SqlParser
import anorm.ToStatement
import play.api.libs.json.Format

case class PgSubscriptionId(value: Long) extends AnyVal
object PgSubscriptionId {
  implicit val ordering: Ordering[PgSubscriptionId] = Ordering.by(_.value)
  implicit val format: Format[PgSubscriptionId] = implicitly[Format[Long]].bimap(PgSubscriptionId.apply, _.value)
  implicit val toStatement: ToStatement[PgSubscriptionId] = implicitly[ToStatement[Long]].contramap(_.value)
  implicit val column: Column[PgSubscriptionId] = implicitly[Column[Long]].map(PgSubscriptionId.apply)
  implicit val rowParser: RowParser[PgSubscriptionId] = SqlParser.get[PgSubscriptionId]("oid")
}
