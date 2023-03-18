package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgProcRepoImpl extends PgProcRepo {
  override def selectAll(implicit c: Connection): List[PgProcRow] = {
    SQL"""select oid, proname, pronamespace, proowner, prolang, procost, prorows, provariadic, prosupport, prokind, prosecdef, proleakproof, proisstrict, proretset, provolatile, proparallel, pronargs, pronargdefaults, prorettype, proargtypes, proallargtypes, proargmodes, proargnames, proargdefaults, protrftypes, prosrc, probin, prosqlbody, proconfig, proacl from pg_catalog.pg_proc""".as(PgProcRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgProcFieldValue[_]])(implicit c: Connection): List[PgProcRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgProcFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgProcFieldValue.proname(value) => NamedParameter("proname", ParameterValue.from(value))
          case PgProcFieldValue.pronamespace(value) => NamedParameter("pronamespace", ParameterValue.from(value))
          case PgProcFieldValue.proowner(value) => NamedParameter("proowner", ParameterValue.from(value))
          case PgProcFieldValue.prolang(value) => NamedParameter("prolang", ParameterValue.from(value))
          case PgProcFieldValue.procost(value) => NamedParameter("procost", ParameterValue.from(value))
          case PgProcFieldValue.prorows(value) => NamedParameter("prorows", ParameterValue.from(value))
          case PgProcFieldValue.provariadic(value) => NamedParameter("provariadic", ParameterValue.from(value))
          case PgProcFieldValue.prosupport(value) => NamedParameter("prosupport", ParameterValue.from(value))
          case PgProcFieldValue.prokind(value) => NamedParameter("prokind", ParameterValue.from(value))
          case PgProcFieldValue.prosecdef(value) => NamedParameter("prosecdef", ParameterValue.from(value))
          case PgProcFieldValue.proleakproof(value) => NamedParameter("proleakproof", ParameterValue.from(value))
          case PgProcFieldValue.proisstrict(value) => NamedParameter("proisstrict", ParameterValue.from(value))
          case PgProcFieldValue.proretset(value) => NamedParameter("proretset", ParameterValue.from(value))
          case PgProcFieldValue.provolatile(value) => NamedParameter("provolatile", ParameterValue.from(value))
          case PgProcFieldValue.proparallel(value) => NamedParameter("proparallel", ParameterValue.from(value))
          case PgProcFieldValue.pronargs(value) => NamedParameter("pronargs", ParameterValue.from(value))
          case PgProcFieldValue.pronargdefaults(value) => NamedParameter("pronargdefaults", ParameterValue.from(value))
          case PgProcFieldValue.prorettype(value) => NamedParameter("prorettype", ParameterValue.from(value))
          case PgProcFieldValue.proargtypes(value) => NamedParameter("proargtypes", ParameterValue.from(value))
          case PgProcFieldValue.proallargtypes(value) => NamedParameter("proallargtypes", ParameterValue.from(value))
          case PgProcFieldValue.proargmodes(value) => NamedParameter("proargmodes", ParameterValue.from(value))
          case PgProcFieldValue.proargnames(value) => NamedParameter("proargnames", ParameterValue.from(value))
          case PgProcFieldValue.proargdefaults(value) => NamedParameter("proargdefaults", ParameterValue.from(value))
          case PgProcFieldValue.protrftypes(value) => NamedParameter("protrftypes", ParameterValue.from(value))
          case PgProcFieldValue.prosrc(value) => NamedParameter("prosrc", ParameterValue.from(value))
          case PgProcFieldValue.probin(value) => NamedParameter("probin", ParameterValue.from(value))
          case PgProcFieldValue.prosqlbody(value) => NamedParameter("prosqlbody", ParameterValue.from(value))
          case PgProcFieldValue.proconfig(value) => NamedParameter("proconfig", ParameterValue.from(value))
          case PgProcFieldValue.proacl(value) => NamedParameter("proacl", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_proc where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgProcRow.rowParser.*)
    }

  }
}
