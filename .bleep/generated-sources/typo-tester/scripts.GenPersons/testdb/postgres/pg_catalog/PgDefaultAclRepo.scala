package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgDefaultAclRepo {
  def selectAll(implicit c: Connection): List[PgDefaultAclRow]
  def selectByFieldValues(fieldValues: List[PgDefaultAclFieldValue[_]])(implicit c: Connection): List[PgDefaultAclRow]
}
