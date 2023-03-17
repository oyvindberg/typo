package testdb
package information_schema

import java.sql.Connection

trait ForeignServersRepo {
  def selectAll(implicit c: Connection): List[ForeignServersRow]
  def selectByFieldValues(fieldValues: List[ForeignServersFieldValue[_]])(implicit c: Connection): List[ForeignServersRow]
}
