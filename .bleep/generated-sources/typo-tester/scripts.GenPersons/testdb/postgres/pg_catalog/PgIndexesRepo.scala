package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgIndexesRepo {
  def selectAll(implicit c: Connection): List[PgIndexesRow]
  def selectByFieldValues(fieldValues: List[PgIndexesFieldValue[_]])(implicit c: Connection): List[PgIndexesRow]
}
