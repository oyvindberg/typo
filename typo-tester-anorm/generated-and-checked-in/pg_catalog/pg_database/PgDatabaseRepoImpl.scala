/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_database

import anorm.SqlStringInterpolation
import java.sql.Connection
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import typo.dsl.UpdateBuilder

object PgDatabaseRepoImpl extends PgDatabaseRepo {
  override def delete(oid: PgDatabaseId)(implicit c: Connection): Boolean = {
    SQL"delete from pg_catalog.pg_database where oid = $oid".executeUpdate() > 0
  }
  override def delete: DeleteBuilder[PgDatabaseFields, PgDatabaseRow] = {
    DeleteBuilder("pg_catalog.pg_database", PgDatabaseFields)
  }
  override def insert(unsaved: PgDatabaseRow)(implicit c: Connection): PgDatabaseRow = {
    SQL"""insert into pg_catalog.pg_database(oid, datname, datdba, "encoding", datcollate, datctype, datistemplate, datallowconn, datconnlimit, datlastsysoid, datfrozenxid, datminmxid, dattablespace, datacl)
          values (${unsaved.oid}::oid, ${unsaved.datname}::name, ${unsaved.datdba}::oid, ${unsaved.encoding}::int4, ${unsaved.datcollate}::name, ${unsaved.datctype}::name, ${unsaved.datistemplate}, ${unsaved.datallowconn}, ${unsaved.datconnlimit}::int4, ${unsaved.datlastsysoid}::oid, ${unsaved.datfrozenxid}::xid, ${unsaved.datminmxid}::xid, ${unsaved.dattablespace}::oid, ${unsaved.datacl}::_aclitem)
          returning oid, datname, datdba, "encoding", datcollate, datctype, datistemplate, datallowconn, datconnlimit, datlastsysoid, datfrozenxid, datminmxid, dattablespace, datacl
       """
      .executeInsert(PgDatabaseRow.rowParser(1).single)
    
  }
  override def select: SelectBuilder[PgDatabaseFields, PgDatabaseRow] = {
    SelectBuilderSql("pg_catalog.pg_database", PgDatabaseFields, PgDatabaseRow.rowParser)
  }
  override def selectAll(implicit c: Connection): List[PgDatabaseRow] = {
    SQL"""select oid, datname, datdba, "encoding", datcollate, datctype, datistemplate, datallowconn, datconnlimit, datlastsysoid, datfrozenxid, datminmxid, dattablespace, datacl
          from pg_catalog.pg_database
       """.as(PgDatabaseRow.rowParser(1).*)
  }
  override def selectById(oid: PgDatabaseId)(implicit c: Connection): Option[PgDatabaseRow] = {
    SQL"""select oid, datname, datdba, "encoding", datcollate, datctype, datistemplate, datallowconn, datconnlimit, datlastsysoid, datfrozenxid, datminmxid, dattablespace, datacl
          from pg_catalog.pg_database
          where oid = $oid
       """.as(PgDatabaseRow.rowParser(1).singleOpt)
  }
  override def selectByIds(oids: Array[PgDatabaseId])(implicit c: Connection): List[PgDatabaseRow] = {
    SQL"""select oid, datname, datdba, "encoding", datcollate, datctype, datistemplate, datallowconn, datconnlimit, datlastsysoid, datfrozenxid, datminmxid, dattablespace, datacl
          from pg_catalog.pg_database
          where oid = ANY($oids)
       """.as(PgDatabaseRow.rowParser(1).*)
    
  }
  override def update(row: PgDatabaseRow)(implicit c: Connection): Boolean = {
    val oid = row.oid
    SQL"""update pg_catalog.pg_database
          set datname = ${row.datname}::name,
              datdba = ${row.datdba}::oid,
              "encoding" = ${row.encoding}::int4,
              datcollate = ${row.datcollate}::name,
              datctype = ${row.datctype}::name,
              datistemplate = ${row.datistemplate},
              datallowconn = ${row.datallowconn},
              datconnlimit = ${row.datconnlimit}::int4,
              datlastsysoid = ${row.datlastsysoid}::oid,
              datfrozenxid = ${row.datfrozenxid}::xid,
              datminmxid = ${row.datminmxid}::xid,
              dattablespace = ${row.dattablespace}::oid,
              datacl = ${row.datacl}::_aclitem
          where oid = $oid
       """.executeUpdate() > 0
  }
  override def update: UpdateBuilder[PgDatabaseFields, PgDatabaseRow] = {
    UpdateBuilder("pg_catalog.pg_database", PgDatabaseFields, PgDatabaseRow.rowParser)
  }
  override def upsert(unsaved: PgDatabaseRow)(implicit c: Connection): PgDatabaseRow = {
    SQL"""insert into pg_catalog.pg_database(oid, datname, datdba, "encoding", datcollate, datctype, datistemplate, datallowconn, datconnlimit, datlastsysoid, datfrozenxid, datminmxid, dattablespace, datacl)
          values (
            ${unsaved.oid}::oid,
            ${unsaved.datname}::name,
            ${unsaved.datdba}::oid,
            ${unsaved.encoding}::int4,
            ${unsaved.datcollate}::name,
            ${unsaved.datctype}::name,
            ${unsaved.datistemplate},
            ${unsaved.datallowconn},
            ${unsaved.datconnlimit}::int4,
            ${unsaved.datlastsysoid}::oid,
            ${unsaved.datfrozenxid}::xid,
            ${unsaved.datminmxid}::xid,
            ${unsaved.dattablespace}::oid,
            ${unsaved.datacl}::_aclitem
          )
          on conflict (oid)
          do update set
            datname = EXCLUDED.datname,
            datdba = EXCLUDED.datdba,
            "encoding" = EXCLUDED."encoding",
            datcollate = EXCLUDED.datcollate,
            datctype = EXCLUDED.datctype,
            datistemplate = EXCLUDED.datistemplate,
            datallowconn = EXCLUDED.datallowconn,
            datconnlimit = EXCLUDED.datconnlimit,
            datlastsysoid = EXCLUDED.datlastsysoid,
            datfrozenxid = EXCLUDED.datfrozenxid,
            datminmxid = EXCLUDED.datminmxid,
            dattablespace = EXCLUDED.dattablespace,
            datacl = EXCLUDED.datacl
          returning oid, datname, datdba, "encoding", datcollate, datctype, datistemplate, datallowconn, datconnlimit, datlastsysoid, datfrozenxid, datminmxid, dattablespace, datacl
       """
      .executeInsert(PgDatabaseRow.rowParser(1).single)
    
  }
}