/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_rewrite

import anorm.SqlStringInterpolation
import java.sql.Connection

object PgRewriteRepoImpl extends PgRewriteRepo {
  override def delete(oid: PgRewriteId)(implicit c: Connection): Boolean = {
    SQL"delete from pg_catalog.pg_rewrite where oid = $oid".executeUpdate() > 0
  }
  override def insert(unsaved: PgRewriteRow)(implicit c: Connection): PgRewriteRow = {
    SQL"""insert into pg_catalog.pg_rewrite(oid, rulename, ev_class, ev_type, ev_enabled, is_instead, ev_qual, ev_action)
          values (${unsaved.oid}::oid, ${unsaved.rulename}::name, ${unsaved.evClass}::oid, ${unsaved.evType}::char, ${unsaved.evEnabled}::char, ${unsaved.isInstead}, ${unsaved.evQual}::pg_node_tree, ${unsaved.evAction}::pg_node_tree)
          returning oid, rulename, ev_class, ev_type, ev_enabled, is_instead, ev_qual, ev_action
       """
      .executeInsert(PgRewriteRow.rowParser(1).single)
  
  }
  override def selectAll(implicit c: Connection): List[PgRewriteRow] = {
    SQL"""select oid, rulename, ev_class, ev_type, ev_enabled, is_instead, ev_qual, ev_action
          from pg_catalog.pg_rewrite
       """.as(PgRewriteRow.rowParser(1).*)
  }
  override def selectById(oid: PgRewriteId)(implicit c: Connection): Option[PgRewriteRow] = {
    SQL"""select oid, rulename, ev_class, ev_type, ev_enabled, is_instead, ev_qual, ev_action
          from pg_catalog.pg_rewrite
          where oid = $oid
       """.as(PgRewriteRow.rowParser(1).singleOpt)
  }
  override def selectByIds(oids: Array[PgRewriteId])(implicit c: Connection): List[PgRewriteRow] = {
    SQL"""select oid, rulename, ev_class, ev_type, ev_enabled, is_instead, ev_qual, ev_action
          from pg_catalog.pg_rewrite
          where oid = ANY($oids)
       """.as(PgRewriteRow.rowParser(1).*)
  
  }
  override def update(row: PgRewriteRow)(implicit c: Connection): Boolean = {
    val oid = row.oid
    SQL"""update pg_catalog.pg_rewrite
          set rulename = ${row.rulename}::name,
              ev_class = ${row.evClass}::oid,
              ev_type = ${row.evType}::char,
              ev_enabled = ${row.evEnabled}::char,
              is_instead = ${row.isInstead},
              ev_qual = ${row.evQual}::pg_node_tree,
              ev_action = ${row.evAction}::pg_node_tree
          where oid = $oid
       """.executeUpdate() > 0
  }
  override def upsert(unsaved: PgRewriteRow)(implicit c: Connection): PgRewriteRow = {
    SQL"""insert into pg_catalog.pg_rewrite(oid, rulename, ev_class, ev_type, ev_enabled, is_instead, ev_qual, ev_action)
          values (
            ${unsaved.oid}::oid,
            ${unsaved.rulename}::name,
            ${unsaved.evClass}::oid,
            ${unsaved.evType}::char,
            ${unsaved.evEnabled}::char,
            ${unsaved.isInstead},
            ${unsaved.evQual}::pg_node_tree,
            ${unsaved.evAction}::pg_node_tree
          )
          on conflict (oid)
          do update set
            rulename = EXCLUDED.rulename,
            ev_class = EXCLUDED.ev_class,
            ev_type = EXCLUDED.ev_type,
            ev_enabled = EXCLUDED.ev_enabled,
            is_instead = EXCLUDED.is_instead,
            ev_qual = EXCLUDED.ev_qual,
            ev_action = EXCLUDED.ev_action
          returning oid, rulename, ev_class, ev_type, ev_enabled, is_instead, ev_qual, ev_action
       """
      .executeInsert(PgRewriteRow.rowParser(1).single)
  
  }
}