/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package check_constraint_routine_usage

import anorm.SqlStringInterpolation
import java.sql.Connection

object CheckConstraintRoutineUsageViewRepoImpl extends CheckConstraintRoutineUsageViewRepo {
  override def selectAll(implicit c: Connection): List[CheckConstraintRoutineUsageViewRow] = {
    SQL"""select "constraint_catalog", "constraint_schema", "constraint_name", specific_catalog, specific_schema, "specific_name"
          from information_schema.check_constraint_routine_usage
       """.as(CheckConstraintRoutineUsageViewRow.rowParser(1).*)
  }
}