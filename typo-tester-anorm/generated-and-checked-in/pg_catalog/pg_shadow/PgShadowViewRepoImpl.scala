/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_shadow

import anorm.SqlStringInterpolation
import java.sql.Connection

object PgShadowViewRepoImpl extends PgShadowViewRepo {
  override def selectAll(implicit c: Connection): List[PgShadowViewRow] = {
    SQL"""select usename, usesysid, usecreatedb, usesuper, userepl, usebypassrls, passwd, valuntil, useconfig
          from pg_catalog.pg_shadow
       """.as(PgShadowViewRow.rowParser(1).*)
  }
}