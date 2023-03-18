package testdb
package postgres
package pg_catalog

import anorm.Column
import anorm.RowParser
import anorm.SqlParser
import anorm.ToStatement
import play.api.libs.json.Format

case class PgConversionId(value: Long) extends AnyVal
object PgConversionId {
  implicit val ordering: Ordering[PgConversionId] = Ordering.by(_.value)
  implicit val format: Format[PgConversionId] = implicitly[Format[Long]].bimap(PgConversionId.apply, _.value)
  implicit val toStatement: ToStatement[PgConversionId] = implicitly[ToStatement[Long]].contramap(_.value)
  implicit val column: Column[PgConversionId] = implicitly[Column[Long]].map(PgConversionId.apply)
  implicit val rowParser: RowParser[PgConversionId] = SqlParser.get[PgConversionId]("oid")
}
