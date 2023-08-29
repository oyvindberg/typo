/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_stat_xact_user_tables

import anorm.SqlStringInterpolation
import java.sql.Connection
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql

object PgStatXactUserTablesViewRepoImpl extends PgStatXactUserTablesViewRepo {
  override def select: SelectBuilder[PgStatXactUserTablesViewFields, PgStatXactUserTablesViewRow] = {
    SelectBuilderSql("pg_catalog.pg_stat_xact_user_tables", PgStatXactUserTablesViewFields, PgStatXactUserTablesViewRow.rowParser)
  }
  override def selectAll(implicit c: Connection): List[PgStatXactUserTablesViewRow] = {
    SQL"""select "relid", "schemaname", "relname", "seq_scan", "seq_tup_read", "idx_scan", "idx_tup_fetch", "n_tup_ins", "n_tup_upd", "n_tup_del", "n_tup_hot_upd"
          from pg_catalog.pg_stat_xact_user_tables
       """.as(PgStatXactUserTablesViewRow.rowParser(1).*)
  }
}