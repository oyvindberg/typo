/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package collation_character_set_applicability

import java.sql.Connection
import typo.dsl.SelectBuilder

trait CollationCharacterSetApplicabilityViewRepo {
  def select: SelectBuilder[CollationCharacterSetApplicabilityViewFields, CollationCharacterSetApplicabilityViewRow]
  def selectAll(implicit c: Connection): List[CollationCharacterSetApplicabilityViewRow]
}