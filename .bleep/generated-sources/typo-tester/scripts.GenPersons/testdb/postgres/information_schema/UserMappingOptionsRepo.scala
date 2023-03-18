package testdb
package postgres
package information_schema

import java.sql.Connection

trait UserMappingOptionsRepo {
  def selectAll(implicit c: Connection): List[UserMappingOptionsRow]
  def selectByFieldValues(fieldValues: List[UserMappingOptionsFieldValue[_]])(implicit c: Connection): List[UserMappingOptionsRow]
}
