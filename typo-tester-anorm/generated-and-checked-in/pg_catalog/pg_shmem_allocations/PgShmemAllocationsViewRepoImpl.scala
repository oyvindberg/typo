/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_shmem_allocations

import anorm.SqlStringInterpolation
import java.sql.Connection

object PgShmemAllocationsViewRepoImpl extends PgShmemAllocationsViewRepo {
  override def selectAll(implicit c: Connection): List[PgShmemAllocationsViewRow] = {
    SQL"""select "name", "off", "size", allocated_size
          from pg_catalog.pg_shmem_allocations
       """.as(PgShmemAllocationsViewRow.rowParser(1).*)
  }
}