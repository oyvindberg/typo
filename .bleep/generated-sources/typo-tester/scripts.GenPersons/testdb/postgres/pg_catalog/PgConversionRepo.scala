package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgConversionRepo {
  def selectAll(implicit c: Connection): List[PgConversionRow]
  def selectByFieldValues(fieldValues: List[PgConversionFieldValue[_]])(implicit c: Connection): List[PgConversionRow]
}
