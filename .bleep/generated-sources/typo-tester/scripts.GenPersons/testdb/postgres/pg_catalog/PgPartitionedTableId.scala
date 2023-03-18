package testdb
package postgres
package pg_catalog

import anorm.Column
import anorm.RowParser
import anorm.SqlParser
import anorm.ToStatement
import play.api.libs.json.Format

case class PgPartitionedTableId(value: Long) extends AnyVal
object PgPartitionedTableId {
  implicit val ordering: Ordering[PgPartitionedTableId] = Ordering.by(_.value)
  implicit val format: Format[PgPartitionedTableId] = implicitly[Format[Long]].bimap(PgPartitionedTableId.apply, _.value)
  implicit val toStatement: ToStatement[PgPartitionedTableId] = implicitly[ToStatement[Long]].contramap(_.value)
  implicit val column: Column[PgPartitionedTableId] = implicitly[Column[Long]].map(PgPartitionedTableId.apply)
  implicit val rowParser: RowParser[PgPartitionedTableId] = SqlParser.get[PgPartitionedTableId]("partrelid")
}
