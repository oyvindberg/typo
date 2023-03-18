package testdb
package postgres
package pg_catalog

import anorm.Column
import anorm.RowParser
import anorm.SqlParser
import anorm.ToStatement
import play.api.libs.json.Format

case class PgTsDictId(value: Long) extends AnyVal
object PgTsDictId {
  implicit val ordering: Ordering[PgTsDictId] = Ordering.by(_.value)
  implicit val format: Format[PgTsDictId] = implicitly[Format[Long]].bimap(PgTsDictId.apply, _.value)
  implicit val toStatement: ToStatement[PgTsDictId] = implicitly[ToStatement[Long]].contramap(_.value)
  implicit val column: Column[PgTsDictId] = implicitly[Column[Long]].map(PgTsDictId.apply)
  implicit val rowParser: RowParser[PgTsDictId] = SqlParser.get[PgTsDictId]("oid")
}
