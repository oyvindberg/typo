package testdb
package postgres
package information_schema

import java.sql.Connection

trait ColumnDomainUsageRepo {
  def selectAll(implicit c: Connection): List[ColumnDomainUsageRow]
  def selectByFieldValues(fieldValues: List[ColumnDomainUsageFieldValue[_]])(implicit c: Connection): List[ColumnDomainUsageRow]
}
