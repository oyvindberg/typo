package testdb.information_schema

import java.sql.Connection

trait ParametersRepo {
  def selectAll(implicit c: Connection): List[ParametersRow]
  def selectByFieldValues(fieldValues: List[ParametersFieldValue[_]])(implicit c: Connection): List[ParametersRow]
}
