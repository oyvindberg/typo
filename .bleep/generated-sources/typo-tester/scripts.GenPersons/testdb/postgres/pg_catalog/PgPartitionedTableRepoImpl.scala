package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgPartitionedTableRepoImpl extends PgPartitionedTableRepo {
  override def selectAll(implicit c: Connection): List[PgPartitionedTableRow] = {
    SQL"""select partrelid, partstrat, partnatts, partdefid, partattrs, partclass, partcollation, partexprs from pg_catalog.pg_partitioned_table""".as(PgPartitionedTableRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgPartitionedTableFieldValue[_]])(implicit c: Connection): List[PgPartitionedTableRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgPartitionedTableFieldValue.partrelid(value) => NamedParameter("partrelid", ParameterValue.from(value))
          case PgPartitionedTableFieldValue.partstrat(value) => NamedParameter("partstrat", ParameterValue.from(value))
          case PgPartitionedTableFieldValue.partnatts(value) => NamedParameter("partnatts", ParameterValue.from(value))
          case PgPartitionedTableFieldValue.partdefid(value) => NamedParameter("partdefid", ParameterValue.from(value))
          case PgPartitionedTableFieldValue.partattrs(value) => NamedParameter("partattrs", ParameterValue.from(value))
          case PgPartitionedTableFieldValue.partclass(value) => NamedParameter("partclass", ParameterValue.from(value))
          case PgPartitionedTableFieldValue.partcollation(value) => NamedParameter("partcollation", ParameterValue.from(value))
          case PgPartitionedTableFieldValue.partexprs(value) => NamedParameter("partexprs", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_partitioned_table where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgPartitionedTableRow.rowParser.*)
    }

  }
}
