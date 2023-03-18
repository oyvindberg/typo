package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgAttrdefRepo {
  def selectAll(implicit c: Connection): List[PgAttrdefRow]
  def selectByFieldValues(fieldValues: List[PgAttrdefFieldValue[_]])(implicit c: Connection): List[PgAttrdefRow]
}
