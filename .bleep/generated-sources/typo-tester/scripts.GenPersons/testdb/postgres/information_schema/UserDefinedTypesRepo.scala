package testdb
package postgres
package information_schema

import java.sql.Connection

trait UserDefinedTypesRepo {
  def selectAll(implicit c: Connection): List[UserDefinedTypesRow]
  def selectByFieldValues(fieldValues: List[UserDefinedTypesFieldValue[_]])(implicit c: Connection): List[UserDefinedTypesRow]
}
