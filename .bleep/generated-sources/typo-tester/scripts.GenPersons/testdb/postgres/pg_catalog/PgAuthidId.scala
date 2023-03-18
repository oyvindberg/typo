package testdb
package postgres
package pg_catalog

import anorm.Column
import anorm.RowParser
import anorm.SqlParser
import anorm.ToStatement
import play.api.libs.json.Format

case class PgAuthidId(value: Long) extends AnyVal
object PgAuthidId {
  implicit val ordering: Ordering[PgAuthidId] = Ordering.by(_.value)
  implicit val format: Format[PgAuthidId] = implicitly[Format[Long]].bimap(PgAuthidId.apply, _.value)
  implicit val toStatement: ToStatement[PgAuthidId] = implicitly[ToStatement[Long]].contramap(_.value)
  implicit val column: Column[PgAuthidId] = implicitly[Column[Long]].map(PgAuthidId.apply)
  implicit val rowParser: RowParser[PgAuthidId] = SqlParser.get[PgAuthidId]("oid")
}
