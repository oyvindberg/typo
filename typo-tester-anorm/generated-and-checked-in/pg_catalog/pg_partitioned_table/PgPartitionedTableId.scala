/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_partitioned_table

import anorm.Column
import anorm.ParameterMetaData
import anorm.ToStatement
import play.api.libs.json.Format

/** Type for the primary key of table `pg_catalog.pg_partitioned_table` */
case class PgPartitionedTableId(value: /* oid */ Long) extends AnyVal
object PgPartitionedTableId {
  implicit val ordering: Ordering[PgPartitionedTableId] = Ordering.by(_.value)
  implicit val format: Format[PgPartitionedTableId] = implicitly[Format[/* oid */ Long]].bimap(PgPartitionedTableId.apply, _.value)
  implicit val toStatement: ToStatement[PgPartitionedTableId] = implicitly[ToStatement[/* oid */ Long]].contramap(_.value)
  implicit val toStatementArray: ToStatement[Array[PgPartitionedTableId]] = implicitly[ToStatement[Array[/* oid */ Long]]].contramap(_.map(_.value))
  implicit val column: Column[PgPartitionedTableId] = implicitly[Column[/* oid */ Long]].map(PgPartitionedTableId.apply)
  implicit val parameterMetadata: ParameterMetaData[PgPartitionedTableId] = new ParameterMetaData[PgPartitionedTableId] {
    override def sqlType: String = implicitly[ParameterMetaData[/* oid */ Long]].sqlType
    override def jdbcType: Int = implicitly[ParameterMetaData[/* oid */ Long]].jdbcType
  }

}