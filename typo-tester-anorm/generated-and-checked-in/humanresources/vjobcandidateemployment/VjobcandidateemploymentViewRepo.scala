/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package humanresources
package vjobcandidateemployment

import java.sql.Connection

trait VjobcandidateemploymentViewRepo {
  def selectAll(implicit c: Connection): List[VjobcandidateemploymentViewRow]
  def selectByFieldValues(fieldValues: List[VjobcandidateemploymentViewFieldOrIdValue[_]])(implicit c: Connection): List[VjobcandidateemploymentViewRow]
}