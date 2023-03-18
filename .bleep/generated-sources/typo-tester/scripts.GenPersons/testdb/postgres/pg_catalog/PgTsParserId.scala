package testdb
package postgres
package pg_catalog

import anorm.Column
import anorm.RowParser
import anorm.SqlParser
import anorm.ToStatement
import play.api.libs.json.Format

case class PgTsParserId(value: Long) extends AnyVal
object PgTsParserId {
  implicit val ordering: Ordering[PgTsParserId] = Ordering.by(_.value)
  implicit val format: Format[PgTsParserId] = implicitly[Format[Long]].bimap(PgTsParserId.apply, _.value)
  implicit val toStatement: ToStatement[PgTsParserId] = implicitly[ToStatement[Long]].contramap(_.value)
  implicit val column: Column[PgTsParserId] = implicitly[Column[Long]].map(PgTsParserId.apply)
  implicit val rowParser: RowParser[PgTsParserId] = SqlParser.get[PgTsParserId]("oid")
}
