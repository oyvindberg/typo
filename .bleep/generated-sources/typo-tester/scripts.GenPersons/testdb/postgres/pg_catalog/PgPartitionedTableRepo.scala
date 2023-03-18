package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgPartitionedTableRepo {
  def selectAll(implicit c: Connection): List[PgPartitionedTableRow]
  def selectByFieldValues(fieldValues: List[PgPartitionedTableFieldValue[_]])(implicit c: Connection): List[PgPartitionedTableRow]
}
