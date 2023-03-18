package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgOpfamilyRepoImpl extends PgOpfamilyRepo {
  override def selectAll(implicit c: Connection): List[PgOpfamilyRow] = {
    SQL"""select oid, opfmethod, opfname, opfnamespace, opfowner from pg_catalog.pg_opfamily""".as(PgOpfamilyRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgOpfamilyFieldValue[_]])(implicit c: Connection): List[PgOpfamilyRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgOpfamilyFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgOpfamilyFieldValue.opfmethod(value) => NamedParameter("opfmethod", ParameterValue.from(value))
          case PgOpfamilyFieldValue.opfname(value) => NamedParameter("opfname", ParameterValue.from(value))
          case PgOpfamilyFieldValue.opfnamespace(value) => NamedParameter("opfnamespace", ParameterValue.from(value))
          case PgOpfamilyFieldValue.opfowner(value) => NamedParameter("opfowner", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_opfamily where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgOpfamilyRow.rowParser.*)
    }

  }
}
