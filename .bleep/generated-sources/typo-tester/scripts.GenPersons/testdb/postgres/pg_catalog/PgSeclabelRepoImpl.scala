package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgSeclabelRepoImpl extends PgSeclabelRepo {
  override def selectAll(implicit c: Connection): List[PgSeclabelRow] = {
    SQL"""select objoid, classoid, objsubid, provider, label from pg_catalog.pg_seclabel""".as(PgSeclabelRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgSeclabelFieldValue[_]])(implicit c: Connection): List[PgSeclabelRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgSeclabelFieldValue.objoid(value) => NamedParameter("objoid", ParameterValue.from(value))
          case PgSeclabelFieldValue.classoid(value) => NamedParameter("classoid", ParameterValue.from(value))
          case PgSeclabelFieldValue.objsubid(value) => NamedParameter("objsubid", ParameterValue.from(value))
          case PgSeclabelFieldValue.provider(value) => NamedParameter("provider", ParameterValue.from(value))
          case PgSeclabelFieldValue.label(value) => NamedParameter("label", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_seclabel where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgSeclabelRow.rowParser.*)
    }

  }
}
