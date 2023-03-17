package testdb.information_schema

import java.sql.Connection

trait CheckConstraintsRepo {
  def selectAll(implicit c: Connection): List[CheckConstraintsRow]
  def selectByFieldValues(fieldValues: List[CheckConstraintsFieldValue[_]])(implicit c: Connection): List[CheckConstraintsRow]
}
