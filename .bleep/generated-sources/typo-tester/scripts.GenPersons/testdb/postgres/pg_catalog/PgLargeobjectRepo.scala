package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgLargeobjectRepo {
  def selectAll(implicit c: Connection): List[PgLargeobjectRow]
  def selectById(loidAndPageno: PgLargeobjectId)(implicit c: Connection): Option[PgLargeobjectRow]
  def selectByFieldValues(fieldValues: List[PgLargeobjectFieldValue[_]])(implicit c: Connection): List[PgLargeobjectRow]
  def updateFieldValues(loidAndPageno: PgLargeobjectId, fieldValues: List[PgLargeobjectFieldValue[_]])(implicit c: Connection): Int
  def insert(loidAndPageno: PgLargeobjectId, unsaved: PgLargeobjectRowUnsaved)(implicit c: Connection): Unit
  def delete(loidAndPageno: PgLargeobjectId)(implicit c: Connection): Boolean
}
