package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgLocksRepoImpl extends PgLocksRepo {
  override def selectAll(implicit c: Connection): List[PgLocksRow] = {
    SQL"""select locktype, database, relation, page, tuple, virtualxid, transactionid, classid, objid, objsubid, virtualtransaction, pid, mode, granted, fastpath, waitstart from pg_catalog.pg_locks""".as(PgLocksRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgLocksFieldValue[_]])(implicit c: Connection): List[PgLocksRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgLocksFieldValue.locktype(value) => NamedParameter("locktype", ParameterValue.from(value))
          case PgLocksFieldValue.database(value) => NamedParameter("database", ParameterValue.from(value))
          case PgLocksFieldValue.relation(value) => NamedParameter("relation", ParameterValue.from(value))
          case PgLocksFieldValue.page(value) => NamedParameter("page", ParameterValue.from(value))
          case PgLocksFieldValue.tuple(value) => NamedParameter("tuple", ParameterValue.from(value))
          case PgLocksFieldValue.virtualxid(value) => NamedParameter("virtualxid", ParameterValue.from(value))
          case PgLocksFieldValue.transactionid(value) => NamedParameter("transactionid", ParameterValue.from(value))
          case PgLocksFieldValue.classid(value) => NamedParameter("classid", ParameterValue.from(value))
          case PgLocksFieldValue.objid(value) => NamedParameter("objid", ParameterValue.from(value))
          case PgLocksFieldValue.objsubid(value) => NamedParameter("objsubid", ParameterValue.from(value))
          case PgLocksFieldValue.virtualtransaction(value) => NamedParameter("virtualtransaction", ParameterValue.from(value))
          case PgLocksFieldValue.pid(value) => NamedParameter("pid", ParameterValue.from(value))
          case PgLocksFieldValue.mode(value) => NamedParameter("mode", ParameterValue.from(value))
          case PgLocksFieldValue.granted(value) => NamedParameter("granted", ParameterValue.from(value))
          case PgLocksFieldValue.fastpath(value) => NamedParameter("fastpath", ParameterValue.from(value))
          case PgLocksFieldValue.waitstart(value) => NamedParameter("waitstart", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_locks where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgLocksRow.rowParser.*)
    }

  }
}
