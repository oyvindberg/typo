package testdb
package postgres
package pg_catalog

import anorm.Column
import anorm.RowParser
import anorm.SqlParser
import anorm.ToStatement
import play.api.libs.json.Format

case class PgTsTemplateId(value: Long) extends AnyVal
object PgTsTemplateId {
  implicit val ordering: Ordering[PgTsTemplateId] = Ordering.by(_.value)
  implicit val format: Format[PgTsTemplateId] = implicitly[Format[Long]].bimap(PgTsTemplateId.apply, _.value)
  implicit val toStatement: ToStatement[PgTsTemplateId] = implicitly[ToStatement[Long]].contramap(_.value)
  implicit val column: Column[PgTsTemplateId] = implicitly[Column[Long]].map(PgTsTemplateId.apply)
  implicit val rowParser: RowParser[PgTsTemplateId] = SqlParser.get[PgTsTemplateId]("oid")
}
