package testdb.information_schema

import java.sql.Connection

trait ConstraintTableUsageRepo {
  def selectAll(implicit c: Connection): List[ConstraintTableUsageRow]
  def selectByFieldValues(fieldValues: List[ConstraintTableUsageFieldValue[_]])(implicit c: Connection): List[ConstraintTableUsageRow]
}
