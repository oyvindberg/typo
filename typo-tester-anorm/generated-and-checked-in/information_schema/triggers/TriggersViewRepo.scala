/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package triggers

import java.sql.Connection

trait TriggersViewRepo {
  def selectAll(implicit c: Connection): List[TriggersViewRow]
}