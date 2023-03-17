package testdb
package pg_catalog

import java.sql.Connection

trait PgRulesRepo {
  def selectAll(implicit c: Connection): List[PgRulesRow]
  def selectByFieldValues(fieldValues: List[PgRulesFieldValue[_]])(implicit c: Connection): List[PgRulesRow]
}
