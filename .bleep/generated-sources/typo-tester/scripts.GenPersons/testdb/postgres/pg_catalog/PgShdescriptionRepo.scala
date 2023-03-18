package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgShdescriptionRepo {
  def selectAll(implicit c: Connection): List[PgShdescriptionRow]
  def selectById(compositeId: PgShdescriptionId)(implicit c: Connection): Option[PgShdescriptionRow]
  def selectByFieldValues(fieldValues: List[PgShdescriptionFieldValue[_]])(implicit c: Connection): List[PgShdescriptionRow]
  def updateFieldValues(compositeId: PgShdescriptionId, fieldValues: List[PgShdescriptionFieldValue[_]])(implicit c: Connection): Int
  def insert(compositeId: PgShdescriptionId, unsaved: PgShdescriptionRowUnsaved)(implicit c: Connection): Unit
  def delete(compositeId: PgShdescriptionId)(implicit c: Connection): Boolean
}
