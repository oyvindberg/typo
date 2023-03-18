package testdb
package postgres
package pg_catalog

import anorm.Column
import anorm.RowParser
import anorm.SqlParser
import anorm.ToStatement
import play.api.libs.json.Format

case class PgRewriteId(value: Long) extends AnyVal
object PgRewriteId {
  implicit val ordering: Ordering[PgRewriteId] = Ordering.by(_.value)
  implicit val format: Format[PgRewriteId] = implicitly[Format[Long]].bimap(PgRewriteId.apply, _.value)
  implicit val toStatement: ToStatement[PgRewriteId] = implicitly[ToStatement[Long]].contramap(_.value)
  implicit val column: Column[PgRewriteId] = implicitly[Column[Long]].map(PgRewriteId.apply)
  implicit val rowParser: RowParser[PgRewriteId] = SqlParser.get[PgRewriteId]("oid")
}
