package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgEnumRepo {
  def selectAll(implicit c: Connection): List[PgEnumRow]
  def selectByFieldValues(fieldValues: List[PgEnumFieldValue[_]])(implicit c: Connection): List[PgEnumRow]
}
