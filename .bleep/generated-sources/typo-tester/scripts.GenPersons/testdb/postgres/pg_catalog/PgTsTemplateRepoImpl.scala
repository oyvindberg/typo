package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgTsTemplateRepoImpl extends PgTsTemplateRepo {
  override def selectAll(implicit c: Connection): List[PgTsTemplateRow] = {
    SQL"""select oid, tmplname, tmplnamespace, tmplinit, tmpllexize from pg_catalog.pg_ts_template""".as(PgTsTemplateRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgTsTemplateFieldValue[_]])(implicit c: Connection): List[PgTsTemplateRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgTsTemplateFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgTsTemplateFieldValue.tmplname(value) => NamedParameter("tmplname", ParameterValue.from(value))
          case PgTsTemplateFieldValue.tmplnamespace(value) => NamedParameter("tmplnamespace", ParameterValue.from(value))
          case PgTsTemplateFieldValue.tmplinit(value) => NamedParameter("tmplinit", ParameterValue.from(value))
          case PgTsTemplateFieldValue.tmpllexize(value) => NamedParameter("tmpllexize", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_ts_template where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgTsTemplateRow.rowParser.*)
    }

  }
}
