/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_operator

import anorm.Column
import anorm.ParameterMetaData
import anorm.ToStatement
import play.api.libs.json.Format

/** Type for the primary key of table `pg_catalog.pg_operator` */
case class PgOperatorId(value: /* oid */ Long) extends AnyVal
object PgOperatorId {
  implicit val ordering: Ordering[PgOperatorId] = Ordering.by(_.value)
  implicit val format: Format[PgOperatorId] = implicitly[Format[/* oid */ Long]].bimap(PgOperatorId.apply, _.value)
  implicit val toStatement: ToStatement[PgOperatorId] = implicitly[ToStatement[/* oid */ Long]].contramap(_.value)
  implicit val toStatementArray: ToStatement[Array[PgOperatorId]] = implicitly[ToStatement[Array[/* oid */ Long]]].contramap(_.map(_.value))
  implicit val column: Column[PgOperatorId] = implicitly[Column[/* oid */ Long]].map(PgOperatorId.apply)
  implicit val parameterMetadata: ParameterMetaData[PgOperatorId] = new ParameterMetaData[PgOperatorId] {
    override def sqlType: String = implicitly[ParameterMetaData[/* oid */ Long]].sqlType
    override def jdbcType: Int = implicitly[ParameterMetaData[/* oid */ Long]].jdbcType
  }

}