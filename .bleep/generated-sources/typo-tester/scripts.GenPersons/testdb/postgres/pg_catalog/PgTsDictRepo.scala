package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgTsDictRepo {
  def selectAll(implicit c: Connection): List[PgTsDictRow]
  def selectByFieldValues(fieldValues: List[PgTsDictFieldValue[_]])(implicit c: Connection): List[PgTsDictRow]
}
