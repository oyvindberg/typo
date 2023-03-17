package testdb
package information_schema

import java.sql.Connection

trait RoutinesRepo {
  def selectAll(implicit c: Connection): List[RoutinesRow]
  def selectByFieldValues(fieldValues: List[RoutinesFieldValue[_]])(implicit c: Connection): List[RoutinesRow]
}
