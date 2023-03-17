package testdb.information_schema

import java.sql.Connection

trait ForeignServerOptionsRepo {
  def selectAll(implicit c: Connection): List[ForeignServerOptionsRow]
  def selectByFieldValues(fieldValues: List[ForeignServerOptionsFieldValue[_]])(implicit c: Connection): List[ForeignServerOptionsRow]
}
