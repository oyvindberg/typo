/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_attribute

import anorm.SqlStringInterpolation
import java.sql.Connection

object PgAttributeRepoImpl extends PgAttributeRepo {
  override def delete(compositeId: PgAttributeId)(implicit c: Connection): Boolean = {
    SQL"delete from pg_catalog.pg_attribute where attrelid = ${compositeId.attrelid} AND attnum = ${compositeId.attnum}".executeUpdate() > 0
  }
  override def insert(unsaved: PgAttributeRow)(implicit c: Connection): PgAttributeRow = {
    SQL"""insert into pg_catalog.pg_attribute(attrelid, attname, atttypid, attstattarget, attlen, attnum, attndims, attcacheoff, atttypmod, attbyval, attalign, attstorage, attcompression, attnotnull, atthasdef, atthasmissing, attidentity, attgenerated, attisdropped, attislocal, attinhcount, attcollation, attacl, attoptions, attfdwoptions, attmissingval)
          values (${unsaved.attrelid}::oid, ${unsaved.attname}::name, ${unsaved.atttypid}::oid, ${unsaved.attstattarget}::int4, ${unsaved.attlen}::int2, ${unsaved.attnum}::int2, ${unsaved.attndims}::int4, ${unsaved.attcacheoff}::int4, ${unsaved.atttypmod}::int4, ${unsaved.attbyval}, ${unsaved.attalign}::char, ${unsaved.attstorage}::char, ${unsaved.attcompression}::char, ${unsaved.attnotnull}, ${unsaved.atthasdef}, ${unsaved.atthasmissing}, ${unsaved.attidentity}::char, ${unsaved.attgenerated}::char, ${unsaved.attisdropped}, ${unsaved.attislocal}, ${unsaved.attinhcount}::int4, ${unsaved.attcollation}::oid, ${unsaved.attacl}::_aclitem, ${unsaved.attoptions}::_text, ${unsaved.attfdwoptions}::_text, ${unsaved.attmissingval}::anyarray)
          returning attrelid, attname, atttypid, attstattarget, attlen, attnum, attndims, attcacheoff, atttypmod, attbyval, attalign, attstorage, attcompression, attnotnull, atthasdef, atthasmissing, attidentity, attgenerated, attisdropped, attislocal, attinhcount, attcollation, attacl, attoptions, attfdwoptions, attmissingval
       """
      .executeInsert(PgAttributeRow.rowParser(1).single)
  
  }
  override def selectAll(implicit c: Connection): List[PgAttributeRow] = {
    SQL"""select attrelid, attname, atttypid, attstattarget, attlen, attnum, attndims, attcacheoff, atttypmod, attbyval, attalign, attstorage, attcompression, attnotnull, atthasdef, atthasmissing, attidentity, attgenerated, attisdropped, attislocal, attinhcount, attcollation, attacl, attoptions, attfdwoptions, attmissingval
          from pg_catalog.pg_attribute
       """.as(PgAttributeRow.rowParser(1).*)
  }
  override def selectById(compositeId: PgAttributeId)(implicit c: Connection): Option[PgAttributeRow] = {
    SQL"""select attrelid, attname, atttypid, attstattarget, attlen, attnum, attndims, attcacheoff, atttypmod, attbyval, attalign, attstorage, attcompression, attnotnull, atthasdef, atthasmissing, attidentity, attgenerated, attisdropped, attislocal, attinhcount, attcollation, attacl, attoptions, attfdwoptions, attmissingval
          from pg_catalog.pg_attribute
          where attrelid = ${compositeId.attrelid} AND attnum = ${compositeId.attnum}
       """.as(PgAttributeRow.rowParser(1).singleOpt)
  }
  override def update(row: PgAttributeRow)(implicit c: Connection): Boolean = {
    val compositeId = row.compositeId
    SQL"""update pg_catalog.pg_attribute
          set attname = ${row.attname}::name,
              atttypid = ${row.atttypid}::oid,
              attstattarget = ${row.attstattarget}::int4,
              attlen = ${row.attlen}::int2,
              attndims = ${row.attndims}::int4,
              attcacheoff = ${row.attcacheoff}::int4,
              atttypmod = ${row.atttypmod}::int4,
              attbyval = ${row.attbyval},
              attalign = ${row.attalign}::char,
              attstorage = ${row.attstorage}::char,
              attcompression = ${row.attcompression}::char,
              attnotnull = ${row.attnotnull},
              atthasdef = ${row.atthasdef},
              atthasmissing = ${row.atthasmissing},
              attidentity = ${row.attidentity}::char,
              attgenerated = ${row.attgenerated}::char,
              attisdropped = ${row.attisdropped},
              attislocal = ${row.attislocal},
              attinhcount = ${row.attinhcount}::int4,
              attcollation = ${row.attcollation}::oid,
              attacl = ${row.attacl}::_aclitem,
              attoptions = ${row.attoptions}::_text,
              attfdwoptions = ${row.attfdwoptions}::_text,
              attmissingval = ${row.attmissingval}::anyarray
          where attrelid = ${compositeId.attrelid} AND attnum = ${compositeId.attnum}
       """.executeUpdate() > 0
  }
  override def upsert(unsaved: PgAttributeRow)(implicit c: Connection): PgAttributeRow = {
    SQL"""insert into pg_catalog.pg_attribute(attrelid, attname, atttypid, attstattarget, attlen, attnum, attndims, attcacheoff, atttypmod, attbyval, attalign, attstorage, attcompression, attnotnull, atthasdef, atthasmissing, attidentity, attgenerated, attisdropped, attislocal, attinhcount, attcollation, attacl, attoptions, attfdwoptions, attmissingval)
          values (
            ${unsaved.attrelid}::oid,
            ${unsaved.attname}::name,
            ${unsaved.atttypid}::oid,
            ${unsaved.attstattarget}::int4,
            ${unsaved.attlen}::int2,
            ${unsaved.attnum}::int2,
            ${unsaved.attndims}::int4,
            ${unsaved.attcacheoff}::int4,
            ${unsaved.atttypmod}::int4,
            ${unsaved.attbyval},
            ${unsaved.attalign}::char,
            ${unsaved.attstorage}::char,
            ${unsaved.attcompression}::char,
            ${unsaved.attnotnull},
            ${unsaved.atthasdef},
            ${unsaved.atthasmissing},
            ${unsaved.attidentity}::char,
            ${unsaved.attgenerated}::char,
            ${unsaved.attisdropped},
            ${unsaved.attislocal},
            ${unsaved.attinhcount}::int4,
            ${unsaved.attcollation}::oid,
            ${unsaved.attacl}::_aclitem,
            ${unsaved.attoptions}::_text,
            ${unsaved.attfdwoptions}::_text,
            ${unsaved.attmissingval}::anyarray
          )
          on conflict (attrelid, attnum)
          do update set
            attname = EXCLUDED.attname,
            atttypid = EXCLUDED.atttypid,
            attstattarget = EXCLUDED.attstattarget,
            attlen = EXCLUDED.attlen,
            attndims = EXCLUDED.attndims,
            attcacheoff = EXCLUDED.attcacheoff,
            atttypmod = EXCLUDED.atttypmod,
            attbyval = EXCLUDED.attbyval,
            attalign = EXCLUDED.attalign,
            attstorage = EXCLUDED.attstorage,
            attcompression = EXCLUDED.attcompression,
            attnotnull = EXCLUDED.attnotnull,
            atthasdef = EXCLUDED.atthasdef,
            atthasmissing = EXCLUDED.atthasmissing,
            attidentity = EXCLUDED.attidentity,
            attgenerated = EXCLUDED.attgenerated,
            attisdropped = EXCLUDED.attisdropped,
            attislocal = EXCLUDED.attislocal,
            attinhcount = EXCLUDED.attinhcount,
            attcollation = EXCLUDED.attcollation,
            attacl = EXCLUDED.attacl,
            attoptions = EXCLUDED.attoptions,
            attfdwoptions = EXCLUDED.attfdwoptions,
            attmissingval = EXCLUDED.attmissingval
          returning attrelid, attname, atttypid, attstattarget, attlen, attnum, attndims, attcacheoff, atttypmod, attbyval, attalign, attstorage, attcompression, attnotnull, atthasdef, atthasmissing, attidentity, attgenerated, attisdropped, attislocal, attinhcount, attcollation, attacl, attoptions, attfdwoptions, attmissingval
       """
      .executeInsert(PgAttributeRow.rowParser(1).single)
  
  }
}