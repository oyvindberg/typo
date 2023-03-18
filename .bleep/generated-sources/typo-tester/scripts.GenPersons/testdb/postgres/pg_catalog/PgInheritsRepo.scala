package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgInheritsRepo {
  def selectAll(implicit c: Connection): List[PgInheritsRow]
  def selectByFieldValues(fieldValues: List[PgInheritsFieldValue[_]])(implicit c: Connection): List[PgInheritsRow]
}
