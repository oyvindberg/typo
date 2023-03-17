package testdb.information_schema

import java.sql.Connection

trait ColumnColumnUsageRepo {
  def selectAll(implicit c: Connection): List[ColumnColumnUsageRow]
  def selectByFieldValues(fieldValues: List[ColumnColumnUsageFieldValue[_]])(implicit c: Connection): List[ColumnColumnUsageRow]
}
