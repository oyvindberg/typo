package testdb
package postgres
package information_schema

import java.sql.Connection

trait ElementTypesRepo {
  def selectAll(implicit c: Connection): List[ElementTypesRow]
  def selectByFieldValues(fieldValues: List[ElementTypesFieldValue[_]])(implicit c: Connection): List[ElementTypesRow]
}
