package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgLargeobjectRepoImpl extends PgLargeobjectRepo {
  override def selectAll(implicit c: Connection): List[PgLargeobjectRow] = {
    SQL"""select loid, pageno, data from pg_catalog.pg_largeobject""".as(PgLargeobjectRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgLargeobjectFieldValue[_]])(implicit c: Connection): List[PgLargeobjectRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgLargeobjectFieldValue.loid(value) => NamedParameter("loid", ParameterValue.from(value))
          case PgLargeobjectFieldValue.pageno(value) => NamedParameter("pageno", ParameterValue.from(value))
          case PgLargeobjectFieldValue.data(value) => NamedParameter("data", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_largeobject where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgLargeobjectRow.rowParser.*)
    }

  }
}
