package testdb.information_schema

import java.sql.Connection

trait DataTypePrivilegesRepo {
  def selectAll(implicit c: Connection): List[DataTypePrivilegesRow]
  def selectByFieldValues(fieldValues: List[DataTypePrivilegesFieldValue[_]])(implicit c: Connection): List[DataTypePrivilegesRow]
}
