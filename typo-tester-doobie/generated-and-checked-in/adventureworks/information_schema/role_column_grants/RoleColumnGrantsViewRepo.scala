/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package role_column_grants

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.SelectBuilder

trait RoleColumnGrantsViewRepo {
  def select: SelectBuilder[RoleColumnGrantsViewFields, RoleColumnGrantsViewRow]
  def selectAll: Stream[ConnectionIO, RoleColumnGrantsViewRow]
}