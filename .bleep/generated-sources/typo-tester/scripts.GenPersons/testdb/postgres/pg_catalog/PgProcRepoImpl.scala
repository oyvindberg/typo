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
  override def selectById(oid: PgProcId)(implicit c: Connection): Option[PgProcRow] = {
    SQL"""select oid, proname, pronamespace, proowner, prolang, procost, prorows, provariadic, prosupport, prokind, prosecdef, proleakproof, proisstrict, proretset, provolatile, proparallel, pronargs, pronargdefaults, prorettype, proargtypes, proallargtypes, proargmodes, proargnames, proargdefaults, protrftypes, prosrc, probin, prosqlbody, proconfig, proacl from pg_catalog.pg_proc where oid = $oid""".as(PgProcRow.rowParser.singleOpt)
  }
  override def selectByIds(oids: List[PgProcId])(implicit c: Connection): List[PgProcRow] = {
    SQL"""select oid, proname, pronamespace, proowner, prolang, procost, prorows, provariadic, prosupport, prokind, prosecdef, proleakproof, proisstrict, proretset, provolatile, proparallel, pronargs, pronargdefaults, prorettype, proargtypes, proallargtypes, proargmodes, proargnames, proargdefaults, protrftypes, prosrc, probin, prosqlbody, proconfig, proacl from pg_catalog.pg_proc where oid in $oids""".as(PgProcRow.rowParser.*)
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
  override def updateFieldValues(oid: PgProcId, fieldValues: List[PgProcFieldValue[_]])(implicit c: Connection): Int = {
    fieldValues match {
      case Nil => 0
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
        SQL"""update pg_catalog.pg_proc
          set ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(", ")}
          where oid = $oid"""
          .on(namedParams: _*)
          .executeUpdate()
    }

  }
  override def insert(oid: PgProcId, unsaved: PgProcRowUnsaved)(implicit c: Connection): Unit = {
    val namedParameters = List(
      Some(NamedParameter("proname", ParameterValue.from(unsaved.proname))),
      Some(NamedParameter("pronamespace", ParameterValue.from(unsaved.pronamespace))),
      Some(NamedParameter("proowner", ParameterValue.from(unsaved.proowner))),
      Some(NamedParameter("prolang", ParameterValue.from(unsaved.prolang))),
      Some(NamedParameter("procost", ParameterValue.from(unsaved.procost))),
      Some(NamedParameter("prorows", ParameterValue.from(unsaved.prorows))),
      Some(NamedParameter("provariadic", ParameterValue.from(unsaved.provariadic))),
      Some(NamedParameter("prosupport", ParameterValue.from(unsaved.prosupport))),
      Some(NamedParameter("prokind", ParameterValue.from(unsaved.prokind))),
      Some(NamedParameter("prosecdef", ParameterValue.from(unsaved.prosecdef))),
      Some(NamedParameter("proleakproof", ParameterValue.from(unsaved.proleakproof))),
      Some(NamedParameter("proisstrict", ParameterValue.from(unsaved.proisstrict))),
      Some(NamedParameter("proretset", ParameterValue.from(unsaved.proretset))),
      Some(NamedParameter("provolatile", ParameterValue.from(unsaved.provolatile))),
      Some(NamedParameter("proparallel", ParameterValue.from(unsaved.proparallel))),
      Some(NamedParameter("pronargs", ParameterValue.from(unsaved.pronargs))),
      Some(NamedParameter("pronargdefaults", ParameterValue.from(unsaved.pronargdefaults))),
      Some(NamedParameter("prorettype", ParameterValue.from(unsaved.prorettype))),
      Some(NamedParameter("proargtypes", ParameterValue.from(unsaved.proargtypes))),
      Some(NamedParameter("proallargtypes", ParameterValue.from(unsaved.proallargtypes))),
      Some(NamedParameter("proargmodes", ParameterValue.from(unsaved.proargmodes))),
      Some(NamedParameter("proargnames", ParameterValue.from(unsaved.proargnames))),
      Some(NamedParameter("proargdefaults", ParameterValue.from(unsaved.proargdefaults))),
      Some(NamedParameter("protrftypes", ParameterValue.from(unsaved.protrftypes))),
      Some(NamedParameter("prosrc", ParameterValue.from(unsaved.prosrc))),
      Some(NamedParameter("probin", ParameterValue.from(unsaved.probin))),
      Some(NamedParameter("prosqlbody", ParameterValue.from(unsaved.prosqlbody))),
      Some(NamedParameter("proconfig", ParameterValue.from(unsaved.proconfig))),
      Some(NamedParameter("proacl", ParameterValue.from(unsaved.proacl)))
    ).flatten

    SQL"""insert into pg_catalog.pg_proc(oid, ${namedParameters.map(_.name).mkString(", ")})
      values (${oid}, ${namedParameters.map(np => s"{${np.name}}").mkString(", ")})
      """
      .on(namedParameters :_*)
      .execute()

  }
  override def delete(oid: PgProcId)(implicit c: Connection): Boolean = {
    SQL"""delete from pg_catalog.pg_proc where oid = $oid""".executeUpdate() > 0
  }
  override def selectByUnique(proname: String, proargtypes: String, pronamespace: Long)(implicit c: Connection): Option[PgProcRow] = {
    ???
  }
}
