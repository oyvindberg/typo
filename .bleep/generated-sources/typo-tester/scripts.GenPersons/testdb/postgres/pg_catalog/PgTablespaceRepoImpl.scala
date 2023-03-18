package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgTablespaceRepoImpl extends PgTablespaceRepo {
  override def selectAll(implicit c: Connection): List[PgTablespaceRow] = {
    SQL"""select oid, spcname, spcowner, spcacl, spcoptions from pg_catalog.pg_tablespace""".as(PgTablespaceRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgTablespaceFieldValue[_]])(implicit c: Connection): List[PgTablespaceRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgTablespaceFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgTablespaceFieldValue.spcname(value) => NamedParameter("spcname", ParameterValue.from(value))
          case PgTablespaceFieldValue.spcowner(value) => NamedParameter("spcowner", ParameterValue.from(value))
          case PgTablespaceFieldValue.spcacl(value) => NamedParameter("spcacl", ParameterValue.from(value))
          case PgTablespaceFieldValue.spcoptions(value) => NamedParameter("spcoptions", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_tablespace where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgTablespaceRow.rowParser.*)
    }

  }
}
