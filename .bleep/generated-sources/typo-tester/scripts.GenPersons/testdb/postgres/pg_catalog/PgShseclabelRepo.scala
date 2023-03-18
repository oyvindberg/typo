package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgShseclabelRepo {
  def selectAll(implicit c: Connection): List[PgShseclabelRow]
  def selectById(compositeId: PgShseclabelId)(implicit c: Connection): Option[PgShseclabelRow]
  def selectByFieldValues(fieldValues: List[PgShseclabelFieldValue[_]])(implicit c: Connection): List[PgShseclabelRow]
  def updateFieldValues(compositeId: PgShseclabelId, fieldValues: List[PgShseclabelFieldValue[_]])(implicit c: Connection): Int
  def insert(compositeId: PgShseclabelId, unsaved: PgShseclabelRowUnsaved)(implicit c: Connection): Unit
  def delete(compositeId: PgShseclabelId)(implicit c: Connection): Boolean
}
