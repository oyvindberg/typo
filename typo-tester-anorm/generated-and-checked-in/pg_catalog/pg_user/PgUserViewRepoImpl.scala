/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_user

import anorm.SqlStringInterpolation
import java.sql.Connection

object PgUserViewRepoImpl extends PgUserViewRepo {
  override def selectAll(implicit c: Connection): List[PgUserViewRow] = {
    SQL"""select usename, usesysid, usecreatedb, usesuper, userepl, usebypassrls, passwd, valuntil, useconfig
          from pg_catalog.pg_user
       """.as(PgUserViewRow.rowParser(1).*)
  }
}