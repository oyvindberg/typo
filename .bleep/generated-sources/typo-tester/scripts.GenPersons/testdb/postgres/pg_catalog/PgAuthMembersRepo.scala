package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgAuthMembersRepo {
  def selectAll(implicit c: Connection): List[PgAuthMembersRow]
  def selectByFieldValues(fieldValues: List[PgAuthMembersFieldValue[_]])(implicit c: Connection): List[PgAuthMembersRow]
}
