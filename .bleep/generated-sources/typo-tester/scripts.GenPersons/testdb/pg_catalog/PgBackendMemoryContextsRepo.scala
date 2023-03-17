package testdb
package pg_catalog

import java.sql.Connection

trait PgBackendMemoryContextsRepo {
  def selectAll(implicit c: Connection): List[PgBackendMemoryContextsRow]
  def selectByFieldValues(fieldValues: List[PgBackendMemoryContextsFieldValue[_]])(implicit c: Connection): List[PgBackendMemoryContextsRow]
}
