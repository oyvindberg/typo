package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgConstraintRepo {
  def selectAll(implicit c: Connection): List[PgConstraintRow]
  def selectByFieldValues(fieldValues: List[PgConstraintFieldValue[_]])(implicit c: Connection): List[PgConstraintRow]
}
