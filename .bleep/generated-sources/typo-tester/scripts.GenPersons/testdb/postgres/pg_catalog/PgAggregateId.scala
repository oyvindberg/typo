package testdb
package postgres
package pg_catalog

import anorm.Column
import anorm.RowParser
import anorm.SqlParser
import anorm.ToStatement
import play.api.libs.json.Format

case class PgAggregateId(value: String) extends AnyVal
object PgAggregateId {
  implicit val ordering: Ordering[PgAggregateId] = Ordering.by(_.value)
  implicit val format: Format[PgAggregateId] = implicitly[Format[String]].bimap(PgAggregateId.apply, _.value)
  implicit val toStatement: ToStatement[PgAggregateId] = implicitly[ToStatement[String]].contramap(_.value)
  implicit val column: Column[PgAggregateId] = implicitly[Column[String]].map(PgAggregateId.apply)
  implicit val rowParser: RowParser[PgAggregateId] = SqlParser.get[PgAggregateId]("aggfnoid")
}
