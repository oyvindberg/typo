package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgAggregateRepo {
  def selectAll(implicit c: Connection): List[PgAggregateRow]
  def selectByFieldValues(fieldValues: List[PgAggregateFieldValue[_]])(implicit c: Connection): List[PgAggregateRow]
}
