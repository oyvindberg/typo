package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgSequenceRepo {
  def selectAll(implicit c: Connection): List[PgSequenceRow]
  def selectByFieldValues(fieldValues: List[PgSequenceFieldValue[_]])(implicit c: Connection): List[PgSequenceRow]
}
