/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package pm

import java.sql.Connection
import typo.dsl.SelectBuilder

trait PmViewRepo {
  def select: SelectBuilder[PmViewFields, PmViewRow]
  def selectAll(implicit c: Connection): List[PmViewRow]
}
