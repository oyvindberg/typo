package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgInitPrivsRepo {
  def selectAll(implicit c: Connection): List[PgInitPrivsRow]
  def selectById(compositeId: PgInitPrivsId)(implicit c: Connection): Option[PgInitPrivsRow]
  def selectByFieldValues(fieldValues: List[PgInitPrivsFieldValue[_]])(implicit c: Connection): List[PgInitPrivsRow]
  def updateFieldValues(compositeId: PgInitPrivsId, fieldValues: List[PgInitPrivsFieldValue[_]])(implicit c: Connection): Int
  def insert(compositeId: PgInitPrivsId, unsaved: PgInitPrivsRowUnsaved)(implicit c: Connection): Unit
  def delete(compositeId: PgInitPrivsId)(implicit c: Connection): Boolean
}
