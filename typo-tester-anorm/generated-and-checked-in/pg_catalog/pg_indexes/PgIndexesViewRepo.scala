/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_indexes

import java.sql.Connection

trait PgIndexesViewRepo {
  def selectAll(implicit c: Connection): List[PgIndexesViewRow]
}