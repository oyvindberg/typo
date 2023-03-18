package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgLargeobjectMetadataRepo {
  def selectAll(implicit c: Connection): List[PgLargeobjectMetadataRow]
  def selectByFieldValues(fieldValues: List[PgLargeobjectMetadataFieldValue[_]])(implicit c: Connection): List[PgLargeobjectMetadataRow]
}
