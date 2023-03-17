package testdb.information_schema

import java.sql.Connection

trait ColumnOptionsRepo {
  def selectAll(implicit c: Connection): List[ColumnOptionsRow]
  def selectByFieldValues(fieldValues: List[ColumnOptionsFieldValue[_]])(implicit c: Connection): List[ColumnOptionsRow]
}
