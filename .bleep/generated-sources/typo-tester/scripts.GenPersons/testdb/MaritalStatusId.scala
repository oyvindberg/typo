package testdb

import anorm.Column
import anorm.ToStatement
import play.api.libs.json.Format

case class MaritalStatusId(value: Long) extends AnyVal
object MaritalStatusId {
  implicit val ordering: Ordering[MaritalStatusId] = Ordering.by(_.value)
  implicit val format: Format[MaritalStatusId] = implicitly[Format[Long]].bimap(MaritalStatusId.apply, _.value)
  implicit val toStatement: ToStatement[MaritalStatusId] = implicitly[ToStatement[Long]].contramap(_.value)
  implicit val column: Column[MaritalStatusId] = implicitly[Column[Long]].map(MaritalStatusId.apply)
}
