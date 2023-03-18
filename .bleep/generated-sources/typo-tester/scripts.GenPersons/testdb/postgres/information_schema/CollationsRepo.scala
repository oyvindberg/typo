package testdb
package postgres
package information_schema

import java.sql.Connection

trait CollationsRepo {
  def selectAll(implicit c: Connection): List[CollationsRow]
  def selectByFieldValues(fieldValues: List[CollationsFieldValue[_]])(implicit c: Connection): List[CollationsRow]
}
