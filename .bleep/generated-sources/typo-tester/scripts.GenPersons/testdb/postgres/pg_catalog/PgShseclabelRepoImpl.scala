package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgShseclabelRepoImpl extends PgShseclabelRepo {
  override def selectAll(implicit c: Connection): List[PgShseclabelRow] = {
    SQL"""select objoid, classoid, provider, label from pg_catalog.pg_shseclabel""".as(PgShseclabelRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgShseclabelFieldValue[_]])(implicit c: Connection): List[PgShseclabelRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgShseclabelFieldValue.objoid(value) => NamedParameter("objoid", ParameterValue.from(value))
          case PgShseclabelFieldValue.classoid(value) => NamedParameter("classoid", ParameterValue.from(value))
          case PgShseclabelFieldValue.provider(value) => NamedParameter("provider", ParameterValue.from(value))
          case PgShseclabelFieldValue.label(value) => NamedParameter("label", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_shseclabel where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgShseclabelRow.rowParser.*)
    }

  }
}
