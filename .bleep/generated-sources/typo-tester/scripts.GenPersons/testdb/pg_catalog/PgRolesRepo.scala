package testdb.pg_catalog

import java.sql.Connection

trait PgRolesRepo {
  def selectAll(implicit c: Connection): List[PgRolesRow]
  def selectByFieldValues(fieldValues: List[PgRolesFieldValue[_]])(implicit c: Connection): List[PgRolesRow]
}
