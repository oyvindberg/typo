/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_class

import doobie.free.connection.ConnectionIO
import doobie.syntax.string.toSqlInterpolator
import fs2.Stream

object PgClassRepoImpl extends PgClassRepo {
  override def delete(oid: PgClassId): ConnectionIO[Boolean] = {
    sql"delete from pg_catalog.pg_class where oid = $oid".update.run.map(_ > 0)
  }
  override def insert(unsaved: PgClassRow): ConnectionIO[PgClassRow] = {
    sql"""insert into pg_catalog.pg_class(oid, relname, relnamespace, reltype, reloftype, relowner, relam, relfilenode, reltablespace, relpages, reltuples, relallvisible, reltoastrelid, relhasindex, relisshared, relpersistence, relkind, relnatts, relchecks, relhasrules, relhastriggers, relhassubclass, relrowsecurity, relforcerowsecurity, relispopulated, relreplident, relispartition, relrewrite, relfrozenxid, relminmxid, relacl, reloptions, relpartbound)
          values (${unsaved.oid}::oid, ${unsaved.relname}::name, ${unsaved.relnamespace}::oid, ${unsaved.reltype}::oid, ${unsaved.reloftype}::oid, ${unsaved.relowner}::oid, ${unsaved.relam}::oid, ${unsaved.relfilenode}::oid, ${unsaved.reltablespace}::oid, ${unsaved.relpages}::int4, ${unsaved.reltuples}::float4, ${unsaved.relallvisible}::int4, ${unsaved.reltoastrelid}::oid, ${unsaved.relhasindex}, ${unsaved.relisshared}, ${unsaved.relpersistence}::char, ${unsaved.relkind}::char, ${unsaved.relnatts}::int2, ${unsaved.relchecks}::int2, ${unsaved.relhasrules}, ${unsaved.relhastriggers}, ${unsaved.relhassubclass}, ${unsaved.relrowsecurity}, ${unsaved.relforcerowsecurity}, ${unsaved.relispopulated}, ${unsaved.relreplident}::char, ${unsaved.relispartition}, ${unsaved.relrewrite}::oid, ${unsaved.relfrozenxid}::xid, ${unsaved.relminmxid}::xid, ${unsaved.relacl}::_aclitem, ${unsaved.reloptions}::_text, ${unsaved.relpartbound}::pg_node_tree)
          returning oid, relname, relnamespace, reltype, reloftype, relowner, relam, relfilenode, reltablespace, relpages, reltuples, relallvisible, reltoastrelid, relhasindex, relisshared, relpersistence, relkind, relnatts, relchecks, relhasrules, relhastriggers, relhassubclass, relrowsecurity, relforcerowsecurity, relispopulated, relreplident, relispartition, relrewrite, relfrozenxid, relminmxid, relacl, reloptions, relpartbound
       """.query[PgClassRow].unique
  }
  override def selectAll: Stream[ConnectionIO, PgClassRow] = {
    sql"select oid, relname, relnamespace, reltype, reloftype, relowner, relam, relfilenode, reltablespace, relpages, reltuples, relallvisible, reltoastrelid, relhasindex, relisshared, relpersistence, relkind, relnatts, relchecks, relhasrules, relhastriggers, relhassubclass, relrowsecurity, relforcerowsecurity, relispopulated, relreplident, relispartition, relrewrite, relfrozenxid, relminmxid, relacl, reloptions, relpartbound from pg_catalog.pg_class".query[PgClassRow].stream
  }
  override def selectById(oid: PgClassId): ConnectionIO[Option[PgClassRow]] = {
    sql"select oid, relname, relnamespace, reltype, reloftype, relowner, relam, relfilenode, reltablespace, relpages, reltuples, relallvisible, reltoastrelid, relhasindex, relisshared, relpersistence, relkind, relnatts, relchecks, relhasrules, relhastriggers, relhassubclass, relrowsecurity, relforcerowsecurity, relispopulated, relreplident, relispartition, relrewrite, relfrozenxid, relminmxid, relacl, reloptions, relpartbound from pg_catalog.pg_class where oid = $oid".query[PgClassRow].option
  }
  override def selectByIds(oids: Array[PgClassId]): Stream[ConnectionIO, PgClassRow] = {
    sql"select oid, relname, relnamespace, reltype, reloftype, relowner, relam, relfilenode, reltablespace, relpages, reltuples, relallvisible, reltoastrelid, relhasindex, relisshared, relpersistence, relkind, relnatts, relchecks, relhasrules, relhastriggers, relhassubclass, relrowsecurity, relforcerowsecurity, relispopulated, relreplident, relispartition, relrewrite, relfrozenxid, relminmxid, relacl, reloptions, relpartbound from pg_catalog.pg_class where oid = ANY($oids)".query[PgClassRow].stream
  }
  override def update(row: PgClassRow): ConnectionIO[Boolean] = {
    val oid = row.oid
    sql"""update pg_catalog.pg_class
          set relname = ${row.relname}::name,
              relnamespace = ${row.relnamespace}::oid,
              reltype = ${row.reltype}::oid,
              reloftype = ${row.reloftype}::oid,
              relowner = ${row.relowner}::oid,
              relam = ${row.relam}::oid,
              relfilenode = ${row.relfilenode}::oid,
              reltablespace = ${row.reltablespace}::oid,
              relpages = ${row.relpages}::int4,
              reltuples = ${row.reltuples}::float4,
              relallvisible = ${row.relallvisible}::int4,
              reltoastrelid = ${row.reltoastrelid}::oid,
              relhasindex = ${row.relhasindex},
              relisshared = ${row.relisshared},
              relpersistence = ${row.relpersistence}::char,
              relkind = ${row.relkind}::char,
              relnatts = ${row.relnatts}::int2,
              relchecks = ${row.relchecks}::int2,
              relhasrules = ${row.relhasrules},
              relhastriggers = ${row.relhastriggers},
              relhassubclass = ${row.relhassubclass},
              relrowsecurity = ${row.relrowsecurity},
              relforcerowsecurity = ${row.relforcerowsecurity},
              relispopulated = ${row.relispopulated},
              relreplident = ${row.relreplident}::char,
              relispartition = ${row.relispartition},
              relrewrite = ${row.relrewrite}::oid,
              relfrozenxid = ${row.relfrozenxid}::xid,
              relminmxid = ${row.relminmxid}::xid,
              relacl = ${row.relacl}::_aclitem,
              reloptions = ${row.reloptions}::_text,
              relpartbound = ${row.relpartbound}::pg_node_tree
          where oid = $oid
       """
      .update
      .run
      .map(_ > 0)
  }
  override def upsert(unsaved: PgClassRow): ConnectionIO[PgClassRow] = {
    sql"""insert into pg_catalog.pg_class(oid, relname, relnamespace, reltype, reloftype, relowner, relam, relfilenode, reltablespace, relpages, reltuples, relallvisible, reltoastrelid, relhasindex, relisshared, relpersistence, relkind, relnatts, relchecks, relhasrules, relhastriggers, relhassubclass, relrowsecurity, relforcerowsecurity, relispopulated, relreplident, relispartition, relrewrite, relfrozenxid, relminmxid, relacl, reloptions, relpartbound)
          values (
            ${unsaved.oid}::oid,
            ${unsaved.relname}::name,
            ${unsaved.relnamespace}::oid,
            ${unsaved.reltype}::oid,
            ${unsaved.reloftype}::oid,
            ${unsaved.relowner}::oid,
            ${unsaved.relam}::oid,
            ${unsaved.relfilenode}::oid,
            ${unsaved.reltablespace}::oid,
            ${unsaved.relpages}::int4,
            ${unsaved.reltuples}::float4,
            ${unsaved.relallvisible}::int4,
            ${unsaved.reltoastrelid}::oid,
            ${unsaved.relhasindex},
            ${unsaved.relisshared},
            ${unsaved.relpersistence}::char,
            ${unsaved.relkind}::char,
            ${unsaved.relnatts}::int2,
            ${unsaved.relchecks}::int2,
            ${unsaved.relhasrules},
            ${unsaved.relhastriggers},
            ${unsaved.relhassubclass},
            ${unsaved.relrowsecurity},
            ${unsaved.relforcerowsecurity},
            ${unsaved.relispopulated},
            ${unsaved.relreplident}::char,
            ${unsaved.relispartition},
            ${unsaved.relrewrite}::oid,
            ${unsaved.relfrozenxid}::xid,
            ${unsaved.relminmxid}::xid,
            ${unsaved.relacl}::_aclitem,
            ${unsaved.reloptions}::_text,
            ${unsaved.relpartbound}::pg_node_tree
          )
          on conflict (oid)
          do update set
            relname = EXCLUDED.relname,
            relnamespace = EXCLUDED.relnamespace,
            reltype = EXCLUDED.reltype,
            reloftype = EXCLUDED.reloftype,
            relowner = EXCLUDED.relowner,
            relam = EXCLUDED.relam,
            relfilenode = EXCLUDED.relfilenode,
            reltablespace = EXCLUDED.reltablespace,
            relpages = EXCLUDED.relpages,
            reltuples = EXCLUDED.reltuples,
            relallvisible = EXCLUDED.relallvisible,
            reltoastrelid = EXCLUDED.reltoastrelid,
            relhasindex = EXCLUDED.relhasindex,
            relisshared = EXCLUDED.relisshared,
            relpersistence = EXCLUDED.relpersistence,
            relkind = EXCLUDED.relkind,
            relnatts = EXCLUDED.relnatts,
            relchecks = EXCLUDED.relchecks,
            relhasrules = EXCLUDED.relhasrules,
            relhastriggers = EXCLUDED.relhastriggers,
            relhassubclass = EXCLUDED.relhassubclass,
            relrowsecurity = EXCLUDED.relrowsecurity,
            relforcerowsecurity = EXCLUDED.relforcerowsecurity,
            relispopulated = EXCLUDED.relispopulated,
            relreplident = EXCLUDED.relreplident,
            relispartition = EXCLUDED.relispartition,
            relrewrite = EXCLUDED.relrewrite,
            relfrozenxid = EXCLUDED.relfrozenxid,
            relminmxid = EXCLUDED.relminmxid,
            relacl = EXCLUDED.relacl,
            reloptions = EXCLUDED.reloptions,
            relpartbound = EXCLUDED.relpartbound
          returning oid, relname, relnamespace, reltype, reloftype, relowner, relam, relfilenode, reltablespace, relpages, reltuples, relallvisible, reltoastrelid, relhasindex, relisshared, relpersistence, relkind, relnatts, relchecks, relhasrules, relhastriggers, relhassubclass, relrowsecurity, relforcerowsecurity, relispopulated, relreplident, relispartition, relrewrite, relfrozenxid, relminmxid, relacl, reloptions, relpartbound
       """.query[PgClassRow].unique
  }
}