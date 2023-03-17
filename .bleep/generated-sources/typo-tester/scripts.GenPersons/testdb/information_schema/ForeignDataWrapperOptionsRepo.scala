package testdb.information_schema

import java.sql.Connection

trait ForeignDataWrapperOptionsRepo {
  def selectAll(implicit c: Connection): List[ForeignDataWrapperOptionsRow]
  def selectByFieldValues(fieldValues: List[ForeignDataWrapperOptionsFieldValue[_]])(implicit c: Connection): List[ForeignDataWrapperOptionsRow]
}
