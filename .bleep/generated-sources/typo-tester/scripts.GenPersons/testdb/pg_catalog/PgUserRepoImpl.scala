package testdb.pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgUserRepoImpl extends PgUserRepo {
  override def selectAll(implicit c: Connection): List[PgUserRow] = {
    SQL"""select usename, usesysid, usecreatedb, usesuper, userepl, usebypassrls, passwd, valuntil, useconfig from pg_catalog.pg_user""".as(PgUserRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgUserFieldValue[_]])(implicit c: Connection): List[PgUserRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgUserFieldValue.usename(value) => NamedParameter("usename", ParameterValue.from(value))
          case PgUserFieldValue.usesysid(value) => NamedParameter("usesysid", ParameterValue.from(value))
          case PgUserFieldValue.usecreatedb(value) => NamedParameter("usecreatedb", ParameterValue.from(value))
          case PgUserFieldValue.usesuper(value) => NamedParameter("usesuper", ParameterValue.from(value))
          case PgUserFieldValue.userepl(value) => NamedParameter("userepl", ParameterValue.from(value))
          case PgUserFieldValue.usebypassrls(value) => NamedParameter("usebypassrls", ParameterValue.from(value))
          case PgUserFieldValue.passwd(value) => NamedParameter("passwd", ParameterValue.from(value))
          case PgUserFieldValue.valuntil(value) => NamedParameter("valuntil", ParameterValue.from(value))
          case PgUserFieldValue.useconfig(value) => NamedParameter("useconfig", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_user where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgUserRow.rowParser.*)
    }

  }
}
