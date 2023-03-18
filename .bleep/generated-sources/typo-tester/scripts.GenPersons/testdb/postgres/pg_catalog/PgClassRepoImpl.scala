package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgClassRepoImpl extends PgClassRepo {
  override def selectAll(implicit c: Connection): List[PgClassRow] = {
    SQL"""select oid, relname, relnamespace, reltype, reloftype, relowner, relam, relfilenode, reltablespace, relpages, reltuples, relallvisible, reltoastrelid, relhasindex, relisshared, relpersistence, relkind, relnatts, relchecks, relhasrules, relhastriggers, relhassubclass, relrowsecurity, relforcerowsecurity, relispopulated, relreplident, relispartition, relrewrite, relfrozenxid, relminmxid, relacl, reloptions, relpartbound from pg_catalog.pg_class""".as(PgClassRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgClassFieldValue[_]])(implicit c: Connection): List[PgClassRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgClassFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgClassFieldValue.relname(value) => NamedParameter("relname", ParameterValue.from(value))
          case PgClassFieldValue.relnamespace(value) => NamedParameter("relnamespace", ParameterValue.from(value))
          case PgClassFieldValue.reltype(value) => NamedParameter("reltype", ParameterValue.from(value))
          case PgClassFieldValue.reloftype(value) => NamedParameter("reloftype", ParameterValue.from(value))
          case PgClassFieldValue.relowner(value) => NamedParameter("relowner", ParameterValue.from(value))
          case PgClassFieldValue.relam(value) => NamedParameter("relam", ParameterValue.from(value))
          case PgClassFieldValue.relfilenode(value) => NamedParameter("relfilenode", ParameterValue.from(value))
          case PgClassFieldValue.reltablespace(value) => NamedParameter("reltablespace", ParameterValue.from(value))
          case PgClassFieldValue.relpages(value) => NamedParameter("relpages", ParameterValue.from(value))
          case PgClassFieldValue.reltuples(value) => NamedParameter("reltuples", ParameterValue.from(value))
          case PgClassFieldValue.relallvisible(value) => NamedParameter("relallvisible", ParameterValue.from(value))
          case PgClassFieldValue.reltoastrelid(value) => NamedParameter("reltoastrelid", ParameterValue.from(value))
          case PgClassFieldValue.relhasindex(value) => NamedParameter("relhasindex", ParameterValue.from(value))
          case PgClassFieldValue.relisshared(value) => NamedParameter("relisshared", ParameterValue.from(value))
          case PgClassFieldValue.relpersistence(value) => NamedParameter("relpersistence", ParameterValue.from(value))
          case PgClassFieldValue.relkind(value) => NamedParameter("relkind", ParameterValue.from(value))
          case PgClassFieldValue.relnatts(value) => NamedParameter("relnatts", ParameterValue.from(value))
          case PgClassFieldValue.relchecks(value) => NamedParameter("relchecks", ParameterValue.from(value))
          case PgClassFieldValue.relhasrules(value) => NamedParameter("relhasrules", ParameterValue.from(value))
          case PgClassFieldValue.relhastriggers(value) => NamedParameter("relhastriggers", ParameterValue.from(value))
          case PgClassFieldValue.relhassubclass(value) => NamedParameter("relhassubclass", ParameterValue.from(value))
          case PgClassFieldValue.relrowsecurity(value) => NamedParameter("relrowsecurity", ParameterValue.from(value))
          case PgClassFieldValue.relforcerowsecurity(value) => NamedParameter("relforcerowsecurity", ParameterValue.from(value))
          case PgClassFieldValue.relispopulated(value) => NamedParameter("relispopulated", ParameterValue.from(value))
          case PgClassFieldValue.relreplident(value) => NamedParameter("relreplident", ParameterValue.from(value))
          case PgClassFieldValue.relispartition(value) => NamedParameter("relispartition", ParameterValue.from(value))
          case PgClassFieldValue.relrewrite(value) => NamedParameter("relrewrite", ParameterValue.from(value))
          case PgClassFieldValue.relfrozenxid(value) => NamedParameter("relfrozenxid", ParameterValue.from(value))
          case PgClassFieldValue.relminmxid(value) => NamedParameter("relminmxid", ParameterValue.from(value))
          case PgClassFieldValue.relacl(value) => NamedParameter("relacl", ParameterValue.from(value))
          case PgClassFieldValue.reloptions(value) => NamedParameter("reloptions", ParameterValue.from(value))
          case PgClassFieldValue.relpartbound(value) => NamedParameter("relpartbound", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_class where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgClassRow.rowParser.*)
    }

  }
}
