package testdb
package pg_catalog

import java.sql.Connection

trait PgStatSslRepo {
  def selectAll(implicit c: Connection): List[PgStatSslRow]
  def selectByFieldValues(fieldValues: List[PgStatSslFieldValue[_]])(implicit c: Connection): List[PgStatSslRow]
}
