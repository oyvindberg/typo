package testdb
package postgres
package information_schema

import java.sql.Connection

trait SqlFeaturesRepo {
  def selectAll(implicit c: Connection): List[SqlFeaturesRow]
  def selectByFieldValues(fieldValues: List[SqlFeaturesFieldValue[_]])(implicit c: Connection): List[SqlFeaturesRow]
}
