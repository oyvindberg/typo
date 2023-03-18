package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgLanguageRepo {
  def selectAll(implicit c: Connection): List[PgLanguageRow]
  def selectByFieldValues(fieldValues: List[PgLanguageFieldValue[_]])(implicit c: Connection): List[PgLanguageRow]
}
