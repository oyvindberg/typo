package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgTriggerRepo {
  def selectAll(implicit c: Connection): List[PgTriggerRow]
  def selectById(oid: PgTriggerId)(implicit c: Connection): Option[PgTriggerRow]
  def selectByIds(oids: List[PgTriggerId])(implicit c: Connection): List[PgTriggerRow]
  def selectByFieldValues(fieldValues: List[PgTriggerFieldValue[_]])(implicit c: Connection): List[PgTriggerRow]
  def updateFieldValues(oid: PgTriggerId, fieldValues: List[PgTriggerFieldValue[_]])(implicit c: Connection): Int
  def insert(oid: PgTriggerId, unsaved: PgTriggerRowUnsaved)(implicit c: Connection): Unit
  def delete(oid: PgTriggerId)(implicit c: Connection): Boolean
  def selectByUnique(tgrelid: Long, tgname: String)(implicit c: Connection): Option[PgTriggerRow]
}
