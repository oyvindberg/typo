/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_ts_config

import anorm.Column
import anorm.ParameterMetaData
import anorm.ToStatement
import play.api.libs.json.Reads
import play.api.libs.json.Writes
import typo.dsl.Bijection

/** Type for the primary key of table `pg_catalog.pg_ts_config` */
case class PgTsConfigId(value: /* oid */ Long) extends AnyVal
object PgTsConfigId {
  implicit lazy val arrayColumn: Column[Array[PgTsConfigId]] = Column.columnToArray(column, implicitly)
  implicit lazy val arrayToStatement: ToStatement[Array[PgTsConfigId]] = implicitly[ToStatement[Array[/* oid */ Long]]].contramap(_.map(_.value))
  implicit lazy val bijection: Bijection[PgTsConfigId, /* oid */ Long] = Bijection[PgTsConfigId, /* oid */ Long](_.value)(PgTsConfigId.apply)
  implicit lazy val column: Column[PgTsConfigId] = implicitly[Column[/* oid */ Long]].map(PgTsConfigId.apply)
  implicit lazy val ordering: Ordering[PgTsConfigId] = Ordering.by(_.value)
  implicit lazy val parameterMetadata: ParameterMetaData[PgTsConfigId] = new ParameterMetaData[PgTsConfigId] {
    override def sqlType: String = implicitly[ParameterMetaData[/* oid */ Long]].sqlType
    override def jdbcType: Int = implicitly[ParameterMetaData[/* oid */ Long]].jdbcType
  }
  implicit lazy val reads: Reads[PgTsConfigId] = Reads.LongReads.map(PgTsConfigId.apply)
  implicit lazy val toStatement: ToStatement[PgTsConfigId] = implicitly[ToStatement[/* oid */ Long]].contramap(_.value)
  implicit lazy val writes: Writes[PgTsConfigId] = Writes.LongWrites.contramap(_.value)
}