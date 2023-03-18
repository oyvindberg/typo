package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgAttrdefRepoImpl extends PgAttrdefRepo {
  override def selectAll(implicit c: Connection): List[PgAttrdefRow] = {
    SQL"""select oid, adrelid, adnum, adbin from pg_catalog.pg_attrdef""".as(PgAttrdefRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgAttrdefFieldValue[_]])(implicit c: Connection): List[PgAttrdefRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgAttrdefFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgAttrdefFieldValue.adrelid(value) => NamedParameter("adrelid", ParameterValue.from(value))
          case PgAttrdefFieldValue.adnum(value) => NamedParameter("adnum", ParameterValue.from(value))
          case PgAttrdefFieldValue.adbin(value) => NamedParameter("adbin", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_attrdef where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgAttrdefRow.rowParser.*)
    }

  }
}
