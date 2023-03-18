package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgAttributeRepo {
  def selectAll(implicit c: Connection): List[PgAttributeRow]
  def selectByFieldValues(fieldValues: List[PgAttributeFieldValue[_]])(implicit c: Connection): List[PgAttributeRow]
}
