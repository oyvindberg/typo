/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package check_constraint_routine_usage

import java.sql.Connection

trait CheckConstraintRoutineUsageViewRepo {
  def selectAll(implicit c: Connection): List[CheckConstraintRoutineUsageViewRow]
}