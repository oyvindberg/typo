package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgHbaFileRulesRepo {
  def selectAll(implicit c: Connection): List[PgHbaFileRulesRow]
  def selectByFieldValues(fieldValues: List[PgHbaFileRulesFieldValue[_]])(implicit c: Connection): List[PgHbaFileRulesRow]
}
