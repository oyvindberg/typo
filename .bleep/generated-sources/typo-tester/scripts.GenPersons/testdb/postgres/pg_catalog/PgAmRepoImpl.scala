package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgAmRepoImpl extends PgAmRepo {
  override def selectAll(implicit c: Connection): List[PgAmRow] = {
    SQL"""select oid, amname, amhandler, amtype from pg_catalog.pg_am""".as(PgAmRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgAmFieldValue[_]])(implicit c: Connection): List[PgAmRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgAmFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgAmFieldValue.amname(value) => NamedParameter("amname", ParameterValue.from(value))
          case PgAmFieldValue.amhandler(value) => NamedParameter("amhandler", ParameterValue.from(value))
          case PgAmFieldValue.amtype(value) => NamedParameter("amtype", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_am where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgAmRow.rowParser.*)
    }

  }
}
