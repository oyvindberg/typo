package testdb
package postgres
package information_schema

import java.sql.Connection

trait DomainConstraintsRepo {
  def selectAll(implicit c: Connection): List[DomainConstraintsRow]
  def selectByFieldValues(fieldValues: List[DomainConstraintsFieldValue[_]])(implicit c: Connection): List[DomainConstraintsRow]
}
