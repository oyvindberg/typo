package testdb
package information_schema

import java.sql.Connection

trait InformationSchemaCatalogNameRepo {
  def selectAll(implicit c: Connection): List[InformationSchemaCatalogNameRow]
  def selectByFieldValues(fieldValues: List[InformationSchemaCatalogNameFieldValue[_]])(implicit c: Connection): List[InformationSchemaCatalogNameRow]
}
