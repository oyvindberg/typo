package testdb.information_schema

import java.sql.Connection

trait ColumnUdtUsageRepo {
  def selectAll(implicit c: Connection): List[ColumnUdtUsageRow]
  def selectByFieldValues(fieldValues: List[ColumnUdtUsageFieldValue[_]])(implicit c: Connection): List[ColumnUdtUsageRow]
}
