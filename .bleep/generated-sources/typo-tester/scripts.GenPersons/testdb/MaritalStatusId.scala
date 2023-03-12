package testdb

import anorm.Column
import anorm.ToStatement
import play.api.libs.json.Format
import play.api.libs.json.Reads
import play.api.libs.json.Writes

case class MaritalStatusId(value: Long) extends AnyVal
object MaritalStatusId {
  implicit val column: Column[MaritalStatusId] = implicitly[Column[Long]].map(MaritalStatusId.apply)
  implicit val format: Format[MaritalStatusId] = implicitly[Format[Long]].bimap(MaritalStatusId.apply, _.value)
  implicit val ordering: Ordering[MaritalStatusId] = Ordering.by(_.value)
  implicit val reads: Reads[MaritalStatusId] = implicitly[Reads[Long]].map(MaritalStatusId.apply)
  implicit val toStatement: ToStatement[MaritalStatusId] = implicitly[ToStatement[Long]].contramap(_.value)
  implicit val writes: Writes[MaritalStatusId] = implicitly[Writes[Long]].contramap(_.value)
}
