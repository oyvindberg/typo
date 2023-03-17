package testdb
package pg_catalog

import java.sql.Connection

trait PgPoliciesRepo {
  def selectAll(implicit c: Connection): List[PgPoliciesRow]
  def selectByFieldValues(fieldValues: List[PgPoliciesFieldValue[_]])(implicit c: Connection): List[PgPoliciesRow]
}
