package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgCastRepoImpl extends PgCastRepo {
  override def selectAll(implicit c: Connection): List[PgCastRow] = {
    SQL"""select oid, castsource, casttarget, castfunc, castcontext, castmethod from pg_catalog.pg_cast""".as(PgCastRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgCastFieldValue[_]])(implicit c: Connection): List[PgCastRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgCastFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgCastFieldValue.castsource(value) => NamedParameter("castsource", ParameterValue.from(value))
          case PgCastFieldValue.casttarget(value) => NamedParameter("casttarget", ParameterValue.from(value))
          case PgCastFieldValue.castfunc(value) => NamedParameter("castfunc", ParameterValue.from(value))
          case PgCastFieldValue.castcontext(value) => NamedParameter("castcontext", ParameterValue.from(value))
          case PgCastFieldValue.castmethod(value) => NamedParameter("castmethod", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_cast where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgCastRow.rowParser.*)
    }

  }
}
