package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgShadowRepo {
  def selectAll(implicit c: Connection): List[PgShadowRow]
  def selectByFieldValues(fieldValues: List[PgShadowFieldValue[_]])(implicit c: Connection): List[PgShadowRow]
}
