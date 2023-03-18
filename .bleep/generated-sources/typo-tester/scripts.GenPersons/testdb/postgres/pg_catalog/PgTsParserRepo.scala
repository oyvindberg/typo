package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgTsParserRepo {
  def selectAll(implicit c: Connection): List[PgTsParserRow]
  def selectByFieldValues(fieldValues: List[PgTsParserFieldValue[_]])(implicit c: Connection): List[PgTsParserRow]
}
