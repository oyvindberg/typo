/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package sequences

import java.sql.Connection
import typo.dsl.SelectBuilder

trait SequencesViewRepo {
  def select: SelectBuilder[SequencesViewFields, SequencesViewRow]
  def selectAll(implicit c: Connection): List[SequencesViewRow]
}