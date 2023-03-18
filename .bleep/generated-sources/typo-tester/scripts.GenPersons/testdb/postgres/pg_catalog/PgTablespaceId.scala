package testdb
package postgres
package pg_catalog

import anorm.Column
import anorm.RowParser
import anorm.SqlParser
import anorm.ToStatement
import play.api.libs.json.Format

case class PgTablespaceId(value: Long) extends AnyVal
object PgTablespaceId {
  implicit val ordering: Ordering[PgTablespaceId] = Ordering.by(_.value)
  implicit val format: Format[PgTablespaceId] = implicitly[Format[Long]].bimap(PgTablespaceId.apply, _.value)
  implicit val toStatement: ToStatement[PgTablespaceId] = implicitly[ToStatement[Long]].contramap(_.value)
  implicit val column: Column[PgTablespaceId] = implicitly[Column[Long]].map(PgTablespaceId.apply)
  implicit val rowParser: RowParser[PgTablespaceId] = SqlParser.get[PgTablespaceId]("oid")
}
