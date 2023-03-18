package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgShadowRepoImpl extends PgShadowRepo {
  override def selectAll(implicit c: Connection): List[PgShadowRow] = {
    SQL"""select usename, usesysid, usecreatedb, usesuper, userepl, usebypassrls, passwd, valuntil, useconfig from pg_catalog.pg_shadow""".as(PgShadowRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgShadowFieldValue[_]])(implicit c: Connection): List[PgShadowRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgShadowFieldValue.usename(value) => NamedParameter("usename", ParameterValue.from(value))
          case PgShadowFieldValue.usesysid(value) => NamedParameter("usesysid", ParameterValue.from(value))
          case PgShadowFieldValue.usecreatedb(value) => NamedParameter("usecreatedb", ParameterValue.from(value))
          case PgShadowFieldValue.usesuper(value) => NamedParameter("usesuper", ParameterValue.from(value))
          case PgShadowFieldValue.userepl(value) => NamedParameter("userepl", ParameterValue.from(value))
          case PgShadowFieldValue.usebypassrls(value) => NamedParameter("usebypassrls", ParameterValue.from(value))
          case PgShadowFieldValue.passwd(value) => NamedParameter("passwd", ParameterValue.from(value))
          case PgShadowFieldValue.valuntil(value) => NamedParameter("valuntil", ParameterValue.from(value))
          case PgShadowFieldValue.useconfig(value) => NamedParameter("useconfig", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_shadow where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgShadowRow.rowParser.*)
    }

  }
}
