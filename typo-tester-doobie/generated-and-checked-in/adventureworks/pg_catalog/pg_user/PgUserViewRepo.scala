/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_user

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.SelectBuilder

trait PgUserViewRepo {
  def select: SelectBuilder[PgUserViewFields, PgUserViewRow]
  def selectAll: Stream[ConnectionIO, PgUserViewRow]
}