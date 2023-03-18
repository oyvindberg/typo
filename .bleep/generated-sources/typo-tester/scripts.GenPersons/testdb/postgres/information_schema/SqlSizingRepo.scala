package testdb
package postgres
package information_schema

import java.sql.Connection

trait SqlSizingRepo {
  def selectAll(implicit c: Connection): List[SqlSizingRow]
  def selectByFieldValues(fieldValues: List[SqlSizingFieldValue[_]])(implicit c: Connection): List[SqlSizingRow]
}
