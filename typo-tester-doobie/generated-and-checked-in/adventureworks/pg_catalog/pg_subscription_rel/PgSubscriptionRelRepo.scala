/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_subscription_rel

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder

trait PgSubscriptionRelRepo {
  def delete(compositeId: PgSubscriptionRelId): ConnectionIO[Boolean]
  def delete: DeleteBuilder[PgSubscriptionRelFields, PgSubscriptionRelRow]
  def insert(unsaved: PgSubscriptionRelRow): ConnectionIO[PgSubscriptionRelRow]
  def select: SelectBuilder[PgSubscriptionRelFields, PgSubscriptionRelRow]
  def selectAll: Stream[ConnectionIO, PgSubscriptionRelRow]
  def selectById(compositeId: PgSubscriptionRelId): ConnectionIO[Option[PgSubscriptionRelRow]]
  def update(row: PgSubscriptionRelRow): ConnectionIO[Boolean]
  def update: UpdateBuilder[PgSubscriptionRelFields, PgSubscriptionRelRow]
  def upsert(unsaved: PgSubscriptionRelRow): ConnectionIO[PgSubscriptionRelRow]
}