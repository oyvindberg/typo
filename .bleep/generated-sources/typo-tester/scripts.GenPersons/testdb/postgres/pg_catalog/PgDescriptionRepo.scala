package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgDescriptionRepo {
  def selectAll(implicit c: Connection): List[PgDescriptionRow]
  def selectById(objoidAndClassoidAndObjsubid: PgDescriptionId)(implicit c: Connection): Option[PgDescriptionRow]
  def selectByFieldValues(fieldValues: List[PgDescriptionFieldValue[_]])(implicit c: Connection): List[PgDescriptionRow]
  def updateFieldValues(objoidAndClassoidAndObjsubid: PgDescriptionId, fieldValues: List[PgDescriptionFieldValue[_]])(implicit c: Connection): Int
  def insert(objoidAndClassoidAndObjsubid: PgDescriptionId, unsaved: PgDescriptionRowUnsaved)(implicit c: Connection): Unit
  def delete(objoidAndClassoidAndObjsubid: PgDescriptionId)(implicit c: Connection): Boolean
}
