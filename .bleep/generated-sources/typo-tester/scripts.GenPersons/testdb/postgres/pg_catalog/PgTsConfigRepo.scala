package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgTsConfigRepo {
  def selectAll(implicit c: Connection): List[PgTsConfigRow]
  def selectByFieldValues(fieldValues: List[PgTsConfigFieldValue[_]])(implicit c: Connection): List[PgTsConfigRow]
}
