/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_subscription

import anorm.Column
import anorm.ParameterMetaData
import anorm.ToStatement
import play.api.libs.json.Format

/** Type for the primary key of table `pg_catalog.pg_subscription` */
case class PgSubscriptionId(value: /* oid */ Long) extends AnyVal
object PgSubscriptionId {
  implicit val ordering: Ordering[PgSubscriptionId] = Ordering.by(_.value)
  implicit val format: Format[PgSubscriptionId] = implicitly[Format[/* oid */ Long]].bimap(PgSubscriptionId.apply, _.value)
  implicit val toStatement: ToStatement[PgSubscriptionId] = implicitly[ToStatement[/* oid */ Long]].contramap(_.value)
  implicit val toStatementArray: ToStatement[Array[PgSubscriptionId]] = implicitly[ToStatement[Array[/* oid */ Long]]].contramap(_.map(_.value))
  implicit val column: Column[PgSubscriptionId] = implicitly[Column[/* oid */ Long]].map(PgSubscriptionId.apply)
  implicit val parameterMetadata: ParameterMetaData[PgSubscriptionId] = new ParameterMetaData[PgSubscriptionId] {
    override def sqlType: String = implicitly[ParameterMetaData[/* oid */ Long]].sqlType
    override def jdbcType: Int = implicitly[ParameterMetaData[/* oid */ Long]].jdbcType
  }

}