package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgInitPrivsRepo {
  def selectAll(implicit c: Connection): List[PgInitPrivsRow]
  def selectById(objoidAndClassoidAndObjsubid: PgInitPrivsId)(implicit c: Connection): Option[PgInitPrivsRow]
  def selectByFieldValues(fieldValues: List[PgInitPrivsFieldValue[_]])(implicit c: Connection): List[PgInitPrivsRow]
  def updateFieldValues(objoidAndClassoidAndObjsubid: PgInitPrivsId, fieldValues: List[PgInitPrivsFieldValue[_]])(implicit c: Connection): Int
  def insert(objoidAndClassoidAndObjsubid: PgInitPrivsId, unsaved: PgInitPrivsRowUnsaved)(implicit c: Connection): Unit
  def delete(objoidAndClassoidAndObjsubid: PgInitPrivsId)(implicit c: Connection): Boolean
}
