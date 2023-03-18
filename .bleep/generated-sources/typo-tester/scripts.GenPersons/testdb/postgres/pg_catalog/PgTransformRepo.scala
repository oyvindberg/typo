package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgTransformRepo {
  def selectAll(implicit c: Connection): List[PgTransformRow]
  def selectByFieldValues(fieldValues: List[PgTransformFieldValue[_]])(implicit c: Connection): List[PgTransformRow]
}
