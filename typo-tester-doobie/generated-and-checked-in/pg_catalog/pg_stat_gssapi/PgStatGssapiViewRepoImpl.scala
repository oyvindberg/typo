/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_stat_gssapi

import doobie.free.connection.ConnectionIO
import doobie.syntax.string.toSqlInterpolator
import fs2.Stream

object PgStatGssapiViewRepoImpl extends PgStatGssapiViewRepo {
  override def selectAll: Stream[ConnectionIO, PgStatGssapiViewRow] = {
    sql"""select pid, gss_authenticated, principal, "encrypted" from pg_catalog.pg_stat_gssapi""".query[PgStatGssapiViewRow].stream
  }
}