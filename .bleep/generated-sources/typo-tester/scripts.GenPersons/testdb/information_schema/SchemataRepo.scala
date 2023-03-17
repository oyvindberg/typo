package testdb
package information_schema

import java.sql.Connection

trait SchemataRepo {
  def selectAll(implicit c: Connection): List[SchemataRow]
  def selectByFieldValues(fieldValues: List[SchemataFieldValue[_]])(implicit c: Connection): List[SchemataRow]
}
