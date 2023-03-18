package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgSeclabelRepo {
  def selectAll(implicit c: Connection): List[PgSeclabelRow]
  def selectById(objoidAndClassoidAndObjsubidAndProvider: PgSeclabelId)(implicit c: Connection): Option[PgSeclabelRow]
  def selectByFieldValues(fieldValues: List[PgSeclabelFieldValue[_]])(implicit c: Connection): List[PgSeclabelRow]
  def updateFieldValues(objoidAndClassoidAndObjsubidAndProvider: PgSeclabelId, fieldValues: List[PgSeclabelFieldValue[_]])(implicit c: Connection): Int
  def insert(objoidAndClassoidAndObjsubidAndProvider: PgSeclabelId, unsaved: PgSeclabelRowUnsaved)(implicit c: Connection): Unit
  def delete(objoidAndClassoidAndObjsubidAndProvider: PgSeclabelId)(implicit c: Connection): Boolean
}
