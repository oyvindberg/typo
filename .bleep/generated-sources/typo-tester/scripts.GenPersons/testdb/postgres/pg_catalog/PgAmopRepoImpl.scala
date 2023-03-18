package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgAmopRepoImpl extends PgAmopRepo {
  override def selectAll(implicit c: Connection): List[PgAmopRow] = {
    SQL"""select oid, amopfamily, amoplefttype, amoprighttype, amopstrategy, amoppurpose, amopopr, amopmethod, amopsortfamily from pg_catalog.pg_amop""".as(PgAmopRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgAmopFieldValue[_]])(implicit c: Connection): List[PgAmopRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgAmopFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgAmopFieldValue.amopfamily(value) => NamedParameter("amopfamily", ParameterValue.from(value))
          case PgAmopFieldValue.amoplefttype(value) => NamedParameter("amoplefttype", ParameterValue.from(value))
          case PgAmopFieldValue.amoprighttype(value) => NamedParameter("amoprighttype", ParameterValue.from(value))
          case PgAmopFieldValue.amopstrategy(value) => NamedParameter("amopstrategy", ParameterValue.from(value))
          case PgAmopFieldValue.amoppurpose(value) => NamedParameter("amoppurpose", ParameterValue.from(value))
          case PgAmopFieldValue.amopopr(value) => NamedParameter("amopopr", ParameterValue.from(value))
          case PgAmopFieldValue.amopmethod(value) => NamedParameter("amopmethod", ParameterValue.from(value))
          case PgAmopFieldValue.amopsortfamily(value) => NamedParameter("amopsortfamily", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_amop where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgAmopRow.rowParser.*)
    }

  }
}
