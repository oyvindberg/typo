package testdb
package pg_catalog

import java.sql.Connection

trait PgConfigRepo {
  def selectAll(implicit c: Connection): List[PgConfigRow]
  def selectByFieldValues(fieldValues: List[PgConfigFieldValue[_]])(implicit c: Connection): List[PgConfigRow]
}
