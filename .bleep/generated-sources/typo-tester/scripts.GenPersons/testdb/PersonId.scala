package testdb

import anorm.Column
import anorm.ToStatement
import play.api.libs.json.Format
import play.api.libs.json.Reads
import play.api.libs.json.Writes

case class PersonId(value: Long) extends AnyVal
object PersonId {
  implicit val column: Column[PersonId] = implicitly[Column[Long]].map(PersonId.apply)
  implicit val format: Format[PersonId] = implicitly[Format[Long]].bimap(PersonId.apply, _.value)
  implicit val ordering: Ordering[PersonId] = Ordering.by(_.value)
  implicit val reads: Reads[PersonId] = implicitly[Reads[Long]].map(PersonId.apply)
  implicit val toStatement: ToStatement[PersonId] = implicitly[ToStatement[Long]].contramap(_.value)
  implicit val writes: Writes[PersonId] = implicitly[Writes[Long]].contramap(_.value)
}
