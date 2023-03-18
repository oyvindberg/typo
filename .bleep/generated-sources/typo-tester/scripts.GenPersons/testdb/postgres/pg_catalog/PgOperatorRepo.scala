package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgOperatorRepo {
  def selectAll(implicit c: Connection): List[PgOperatorRow]
  def selectByFieldValues(fieldValues: List[PgOperatorFieldValue[_]])(implicit c: Connection): List[PgOperatorRow]
}
