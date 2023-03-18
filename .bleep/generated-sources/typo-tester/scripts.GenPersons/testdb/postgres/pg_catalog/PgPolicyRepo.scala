package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgPolicyRepo {
  def selectAll(implicit c: Connection): List[PgPolicyRow]
  def selectByFieldValues(fieldValues: List[PgPolicyFieldValue[_]])(implicit c: Connection): List[PgPolicyRow]
}
