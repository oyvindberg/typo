package testdb.pg_catalog

import java.sql.Connection

trait PgShmemAllocationsRepo {
  def selectAll(implicit c: Connection): List[PgShmemAllocationsRow]
  def selectByFieldValues(fieldValues: List[PgShmemAllocationsFieldValue[_]])(implicit c: Connection): List[PgShmemAllocationsRow]
}
