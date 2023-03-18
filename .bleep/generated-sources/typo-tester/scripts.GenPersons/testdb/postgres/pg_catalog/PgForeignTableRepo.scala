package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgForeignTableRepo {
  def selectAll(implicit c: Connection): List[PgForeignTableRow]
  def selectById(ftrelid: PgForeignTableId)(implicit c: Connection): Option[PgForeignTableRow]
  def selectByIds(ftrelids: List[PgForeignTableId])(implicit c: Connection): List[PgForeignTableRow]
  def selectByFieldValues(fieldValues: List[PgForeignTableFieldValue[_]])(implicit c: Connection): List[PgForeignTableRow]
  def updateFieldValues(ftrelid: PgForeignTableId, fieldValues: List[PgForeignTableFieldValue[_]])(implicit c: Connection): Int
  def insert(ftrelid: PgForeignTableId, unsaved: PgForeignTableRowUnsaved)(implicit c: Connection): Unit
  def delete(ftrelid: PgForeignTableId)(implicit c: Connection): Boolean
}
