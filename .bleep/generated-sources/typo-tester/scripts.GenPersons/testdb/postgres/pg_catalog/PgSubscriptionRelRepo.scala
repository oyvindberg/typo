package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgSubscriptionRelRepo {
  def selectAll(implicit c: Connection): List[PgSubscriptionRelRow]
  def selectById(srrelidAndSrsubid: PgSubscriptionRelId)(implicit c: Connection): Option[PgSubscriptionRelRow]
  def selectByFieldValues(fieldValues: List[PgSubscriptionRelFieldValue[_]])(implicit c: Connection): List[PgSubscriptionRelRow]
  def updateFieldValues(srrelidAndSrsubid: PgSubscriptionRelId, fieldValues: List[PgSubscriptionRelFieldValue[_]])(implicit c: Connection): Int
  def insert(srrelidAndSrsubid: PgSubscriptionRelId, unsaved: PgSubscriptionRelRowUnsaved)(implicit c: Connection): Unit
  def delete(srrelidAndSrsubid: PgSubscriptionRelId)(implicit c: Connection): Boolean
}
