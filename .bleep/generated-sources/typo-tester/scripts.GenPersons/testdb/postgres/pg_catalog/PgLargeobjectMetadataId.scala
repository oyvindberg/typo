package testdb
package postgres
package pg_catalog

import anorm.Column
import anorm.RowParser
import anorm.SqlParser
import anorm.ToStatement
import play.api.libs.json.Format

case class PgLargeobjectMetadataId(value: Long) extends AnyVal
object PgLargeobjectMetadataId {
  implicit val ordering: Ordering[PgLargeobjectMetadataId] = Ordering.by(_.value)
  implicit val format: Format[PgLargeobjectMetadataId] = implicitly[Format[Long]].bimap(PgLargeobjectMetadataId.apply, _.value)
  implicit val toStatement: ToStatement[PgLargeobjectMetadataId] = implicitly[ToStatement[Long]].contramap(_.value)
  implicit val column: Column[PgLargeobjectMetadataId] = implicitly[Column[Long]].map(PgLargeobjectMetadataId.apply)
  implicit val rowParser: RowParser[PgLargeobjectMetadataId] = SqlParser.get[PgLargeobjectMetadataId]("oid")
}
