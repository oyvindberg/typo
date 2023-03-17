package testdb.information_schema

import java.sql.Connection

trait ForeignTableOptionsRepo {
  def selectAll(implicit c: Connection): List[ForeignTableOptionsRow]
  def selectByFieldValues(fieldValues: List[ForeignTableOptionsFieldValue[_]])(implicit c: Connection): List[ForeignTableOptionsRow]
}
