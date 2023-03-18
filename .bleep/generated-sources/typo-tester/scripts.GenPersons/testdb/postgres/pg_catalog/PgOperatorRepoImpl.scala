package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgOperatorRepoImpl extends PgOperatorRepo {
  override def selectAll(implicit c: Connection): List[PgOperatorRow] = {
    SQL"""select oid, oprname, oprnamespace, oprowner, oprkind, oprcanmerge, oprcanhash, oprleft, oprright, oprresult, oprcom, oprnegate, oprcode, oprrest, oprjoin from pg_catalog.pg_operator""".as(PgOperatorRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgOperatorFieldValue[_]])(implicit c: Connection): List[PgOperatorRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgOperatorFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgOperatorFieldValue.oprname(value) => NamedParameter("oprname", ParameterValue.from(value))
          case PgOperatorFieldValue.oprnamespace(value) => NamedParameter("oprnamespace", ParameterValue.from(value))
          case PgOperatorFieldValue.oprowner(value) => NamedParameter("oprowner", ParameterValue.from(value))
          case PgOperatorFieldValue.oprkind(value) => NamedParameter("oprkind", ParameterValue.from(value))
          case PgOperatorFieldValue.oprcanmerge(value) => NamedParameter("oprcanmerge", ParameterValue.from(value))
          case PgOperatorFieldValue.oprcanhash(value) => NamedParameter("oprcanhash", ParameterValue.from(value))
          case PgOperatorFieldValue.oprleft(value) => NamedParameter("oprleft", ParameterValue.from(value))
          case PgOperatorFieldValue.oprright(value) => NamedParameter("oprright", ParameterValue.from(value))
          case PgOperatorFieldValue.oprresult(value) => NamedParameter("oprresult", ParameterValue.from(value))
          case PgOperatorFieldValue.oprcom(value) => NamedParameter("oprcom", ParameterValue.from(value))
          case PgOperatorFieldValue.oprnegate(value) => NamedParameter("oprnegate", ParameterValue.from(value))
          case PgOperatorFieldValue.oprcode(value) => NamedParameter("oprcode", ParameterValue.from(value))
          case PgOperatorFieldValue.oprrest(value) => NamedParameter("oprrest", ParameterValue.from(value))
          case PgOperatorFieldValue.oprjoin(value) => NamedParameter("oprjoin", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_operator where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgOperatorRow.rowParser.*)
    }

  }
}
