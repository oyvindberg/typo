package testdb
package postgres
package information_schema

import java.sql.Connection

trait ForeignDataWrappersRepo {
  def selectAll(implicit c: Connection): List[ForeignDataWrappersRow]
  def selectByFieldValues(fieldValues: List[ForeignDataWrappersFieldValue[_]])(implicit c: Connection): List[ForeignDataWrappersRow]
}
