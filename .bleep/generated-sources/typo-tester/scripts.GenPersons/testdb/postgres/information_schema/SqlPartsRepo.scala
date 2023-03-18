package testdb
package postgres
package information_schema

import java.sql.Connection

trait SqlPartsRepo {
  def selectAll(implicit c: Connection): List[SqlPartsRow]
  def selectByFieldValues(fieldValues: List[SqlPartsFieldValue[_]])(implicit c: Connection): List[SqlPartsRow]
}
