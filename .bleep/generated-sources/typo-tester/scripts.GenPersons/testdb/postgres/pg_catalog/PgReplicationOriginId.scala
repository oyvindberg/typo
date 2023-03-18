package testdb
package postgres
package pg_catalog

import anorm.Column
import anorm.RowParser
import anorm.SqlParser
import anorm.ToStatement
import play.api.libs.json.Format

case class PgReplicationOriginId(value: Long) extends AnyVal
object PgReplicationOriginId {
  implicit val ordering: Ordering[PgReplicationOriginId] = Ordering.by(_.value)
  implicit val format: Format[PgReplicationOriginId] = implicitly[Format[Long]].bimap(PgReplicationOriginId.apply, _.value)
  implicit val toStatement: ToStatement[PgReplicationOriginId] = implicitly[ToStatement[Long]].contramap(_.value)
  implicit val column: Column[PgReplicationOriginId] = implicitly[Column[Long]].map(PgReplicationOriginId.apply)
  implicit val rowParser: RowParser[PgReplicationOriginId] = SqlParser.get[PgReplicationOriginId]("roident")
}
