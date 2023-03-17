package testdb
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgIndexesRepoImpl extends PgIndexesRepo {
  override def selectAll(implicit c: Connection): List[PgIndexesRow] = {
    SQL"""select schemaname, tablename, indexname, tablespace, indexdef from pg_catalog.pg_indexes""".as(PgIndexesRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgIndexesFieldValue[_]])(implicit c: Connection): List[PgIndexesRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgIndexesFieldValue.schemaname(value) => NamedParameter("schemaname", ParameterValue.from(value))
          case PgIndexesFieldValue.tablename(value) => NamedParameter("tablename", ParameterValue.from(value))
          case PgIndexesFieldValue.indexname(value) => NamedParameter("indexname", ParameterValue.from(value))
          case PgIndexesFieldValue.tablespace(value) => NamedParameter("tablespace", ParameterValue.from(value))
          case PgIndexesFieldValue.indexdef(value) => NamedParameter("indexdef", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_indexes where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgIndexesRow.rowParser.*)
    }

  }
}
