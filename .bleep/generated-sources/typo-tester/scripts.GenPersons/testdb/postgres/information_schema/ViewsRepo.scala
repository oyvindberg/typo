package testdb
package postgres
package information_schema

import java.sql.Connection

trait ViewsRepo {
  def selectAll(implicit c: Connection): List[ViewsRow]
  def selectByFieldValues(fieldValues: List[ViewsFieldValue[_]])(implicit c: Connection): List[ViewsRow]
}
