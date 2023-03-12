package testdb

import anorm.Column
import anorm.ToStatement
import play.api.libs.json.Format

case class FootballClubId(value: Long) extends AnyVal
object FootballClubId {
  implicit val column: Column[FootballClubId] = implicitly[Column[Long]].map(FootballClubId.apply)
  implicit val ordering: Ordering[FootballClubId] = Ordering.by(_.value)
  implicit val toStatement: ToStatement[FootballClubId] = implicitly[ToStatement[Long]].contramap(_.value)
  implicit val format: Format[FootballClubId] = implicitly[Format[Long]].bimap(FootballClubId.apply, _.value)
}
