package testdb
package information_schema

import java.sql.Connection

trait DomainsRepo {
  def selectAll(implicit c: Connection): List[DomainsRow]
  def selectByFieldValues(fieldValues: List[DomainsFieldValue[_]])(implicit c: Connection): List[DomainsRow]
}
