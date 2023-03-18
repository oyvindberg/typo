package testdb
package postgres
package information_schema

import java.sql.Connection

trait SqlImplementationInfoRepo {
  def selectAll(implicit c: Connection): List[SqlImplementationInfoRow]
  def selectByFieldValues(fieldValues: List[SqlImplementationInfoFieldValue[_]])(implicit c: Connection): List[SqlImplementationInfoRow]
}
