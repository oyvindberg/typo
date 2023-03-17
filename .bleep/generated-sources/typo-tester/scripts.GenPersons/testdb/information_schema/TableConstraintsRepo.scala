package testdb.information_schema

import java.sql.Connection

trait TableConstraintsRepo {
  def selectAll(implicit c: Connection): List[TableConstraintsRow]
  def selectByFieldValues(fieldValues: List[TableConstraintsFieldValue[_]])(implicit c: Connection): List[TableConstraintsRow]
}
