package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgCollationRepoImpl extends PgCollationRepo {
  override def selectAll(implicit c: Connection): List[PgCollationRow] = {
    SQL"""select oid, collname, collnamespace, collowner, collprovider, collisdeterministic, collencoding, collcollate, collctype, collversion from pg_catalog.pg_collation""".as(PgCollationRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgCollationFieldValue[_]])(implicit c: Connection): List[PgCollationRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgCollationFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgCollationFieldValue.collname(value) => NamedParameter("collname", ParameterValue.from(value))
          case PgCollationFieldValue.collnamespace(value) => NamedParameter("collnamespace", ParameterValue.from(value))
          case PgCollationFieldValue.collowner(value) => NamedParameter("collowner", ParameterValue.from(value))
          case PgCollationFieldValue.collprovider(value) => NamedParameter("collprovider", ParameterValue.from(value))
          case PgCollationFieldValue.collisdeterministic(value) => NamedParameter("collisdeterministic", ParameterValue.from(value))
          case PgCollationFieldValue.collencoding(value) => NamedParameter("collencoding", ParameterValue.from(value))
          case PgCollationFieldValue.collcollate(value) => NamedParameter("collcollate", ParameterValue.from(value))
          case PgCollationFieldValue.collctype(value) => NamedParameter("collctype", ParameterValue.from(value))
          case PgCollationFieldValue.collversion(value) => NamedParameter("collversion", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_collation where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgCollationRow.rowParser.*)
    }

  }
}
