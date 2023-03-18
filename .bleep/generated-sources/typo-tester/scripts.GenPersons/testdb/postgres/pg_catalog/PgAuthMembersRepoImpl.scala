package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgAuthMembersRepoImpl extends PgAuthMembersRepo {
  override def selectAll(implicit c: Connection): List[PgAuthMembersRow] = {
    SQL"""select roleid, member, grantor, admin_option from pg_catalog.pg_auth_members""".as(PgAuthMembersRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgAuthMembersFieldValue[_]])(implicit c: Connection): List[PgAuthMembersRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgAuthMembersFieldValue.roleid(value) => NamedParameter("roleid", ParameterValue.from(value))
          case PgAuthMembersFieldValue.member(value) => NamedParameter("member", ParameterValue.from(value))
          case PgAuthMembersFieldValue.grantor(value) => NamedParameter("grantor", ParameterValue.from(value))
          case PgAuthMembersFieldValue.adminOption(value) => NamedParameter("admin_option", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_auth_members where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgAuthMembersRow.rowParser.*)
    }

  }
}
