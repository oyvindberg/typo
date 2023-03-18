package testdb
package postgres
package pg_catalog

import anorm.Column
import anorm.RowParser
import anorm.SqlParser
import anorm.ToStatement
import play.api.libs.json.Format

case class PgSequenceId(value: Long) extends AnyVal
object PgSequenceId {
  implicit val ordering: Ordering[PgSequenceId] = Ordering.by(_.value)
  implicit val format: Format[PgSequenceId] = implicitly[Format[Long]].bimap(PgSequenceId.apply, _.value)
  implicit val toStatement: ToStatement[PgSequenceId] = implicitly[ToStatement[Long]].contramap(_.value)
  implicit val column: Column[PgSequenceId] = implicitly[Column[Long]].map(PgSequenceId.apply)
  implicit val rowParser: RowParser[PgSequenceId] = SqlParser.get[PgSequenceId]("seqrelid")
}
