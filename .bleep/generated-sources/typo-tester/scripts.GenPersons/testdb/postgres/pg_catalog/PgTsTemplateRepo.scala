package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgTsTemplateRepo {
  def selectAll(implicit c: Connection): List[PgTsTemplateRow]
  def selectByFieldValues(fieldValues: List[PgTsTemplateFieldValue[_]])(implicit c: Connection): List[PgTsTemplateRow]
}
