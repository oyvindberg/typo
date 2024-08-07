/**
 * File has been automatically generated by `typo` for internal use.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 *
 * (If you're developing `typo` and want to change it: run `bleep generate-sources`)
 */
package typo
package generated
package custom
package table_comments

import anorm.SqlStringInterpolation
import java.sql.Connection

class TableCommentsSqlRepoImpl extends TableCommentsSqlRepo {
  override def apply()(implicit c: Connection): List[TableCommentsSqlRow] = {
    val sql =
      SQL"""SELECT n.nspname                                     as "schema?",
                   c.relname                                     as name,
                   pg_catalog.obj_description(c.oid, 'pg_class') as description
            FROM pg_catalog.pg_class c
                     JOIN pg_catalog.pg_namespace n ON n.oid = c.relnamespace
            WHERE c.relkind = 'r'"""
    sql.as(TableCommentsSqlRow.rowParser(1).*)
  }
}
