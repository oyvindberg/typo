package testdb
package information_schema

import java.sql.Connection

trait AttributesRepo {
  def selectAll(implicit c: Connection): List[AttributesRow]
  def selectByFieldValues(fieldValues: List[AttributesFieldValue[_]])(implicit c: Connection): List[AttributesRow]
}
