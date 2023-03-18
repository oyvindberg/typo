package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgTransformRepoImpl extends PgTransformRepo {
  override def selectAll(implicit c: Connection): List[PgTransformRow] = {
    SQL"""select oid, trftype, trflang, trffromsql, trftosql from pg_catalog.pg_transform""".as(PgTransformRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgTransformFieldValue[_]])(implicit c: Connection): List[PgTransformRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgTransformFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgTransformFieldValue.trftype(value) => NamedParameter("trftype", ParameterValue.from(value))
          case PgTransformFieldValue.trflang(value) => NamedParameter("trflang", ParameterValue.from(value))
          case PgTransformFieldValue.trffromsql(value) => NamedParameter("trffromsql", ParameterValue.from(value))
          case PgTransformFieldValue.trftosql(value) => NamedParameter("trftosql", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_transform where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgTransformRow.rowParser.*)
    }

  }
}
