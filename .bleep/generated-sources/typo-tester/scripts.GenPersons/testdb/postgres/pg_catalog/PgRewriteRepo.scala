package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgRewriteRepo {
  def selectAll(implicit c: Connection): List[PgRewriteRow]
  def selectByFieldValues(fieldValues: List[PgRewriteFieldValue[_]])(implicit c: Connection): List[PgRewriteRow]
}
