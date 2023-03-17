package testdb
package information_schema

import java.sql.Connection

trait ViewTableUsageRepo {
  def selectAll(implicit c: Connection): List[ViewTableUsageRow]
  def selectByFieldValues(fieldValues: List[ViewTableUsageFieldValue[_]])(implicit c: Connection): List[ViewTableUsageRow]
}
