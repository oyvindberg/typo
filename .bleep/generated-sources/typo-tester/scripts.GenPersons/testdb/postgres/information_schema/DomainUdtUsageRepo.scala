package testdb
package postgres
package information_schema

import java.sql.Connection

trait DomainUdtUsageRepo {
  def selectAll(implicit c: Connection): List[DomainUdtUsageRow]
  def selectByFieldValues(fieldValues: List[DomainUdtUsageFieldValue[_]])(implicit c: Connection): List[DomainUdtUsageRow]
}
