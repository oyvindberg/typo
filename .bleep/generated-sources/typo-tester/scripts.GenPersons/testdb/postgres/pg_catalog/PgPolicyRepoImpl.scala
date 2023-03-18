package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgPolicyRepoImpl extends PgPolicyRepo {
  override def selectAll(implicit c: Connection): List[PgPolicyRow] = {
    SQL"""select oid, polname, polrelid, polcmd, polpermissive, polroles, polqual, polwithcheck from pg_catalog.pg_policy""".as(PgPolicyRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgPolicyFieldValue[_]])(implicit c: Connection): List[PgPolicyRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgPolicyFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgPolicyFieldValue.polname(value) => NamedParameter("polname", ParameterValue.from(value))
          case PgPolicyFieldValue.polrelid(value) => NamedParameter("polrelid", ParameterValue.from(value))
          case PgPolicyFieldValue.polcmd(value) => NamedParameter("polcmd", ParameterValue.from(value))
          case PgPolicyFieldValue.polpermissive(value) => NamedParameter("polpermissive", ParameterValue.from(value))
          case PgPolicyFieldValue.polroles(value) => NamedParameter("polroles", ParameterValue.from(value))
          case PgPolicyFieldValue.polqual(value) => NamedParameter("polqual", ParameterValue.from(value))
          case PgPolicyFieldValue.polwithcheck(value) => NamedParameter("polwithcheck", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_policy where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgPolicyRow.rowParser.*)
    }

  }
}
