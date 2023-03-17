package testdb
package information_schema

import java.sql.Connection

trait UserMappingsRepo {
  def selectAll(implicit c: Connection): List[UserMappingsRow]
  def selectByFieldValues(fieldValues: List[UserMappingsFieldValue[_]])(implicit c: Connection): List[UserMappingsRow]
}
