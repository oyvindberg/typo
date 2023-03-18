package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgShdescriptionRepo {
  def selectAll(implicit c: Connection): List[PgShdescriptionRow]
  def selectById(objoidAndClassoid: PgShdescriptionId)(implicit c: Connection): Option[PgShdescriptionRow]
  def selectByFieldValues(fieldValues: List[PgShdescriptionFieldValue[_]])(implicit c: Connection): List[PgShdescriptionRow]
  def updateFieldValues(objoidAndClassoid: PgShdescriptionId, fieldValues: List[PgShdescriptionFieldValue[_]])(implicit c: Connection): Int
  def insert(objoidAndClassoid: PgShdescriptionId, unsaved: PgShdescriptionRowUnsaved)(implicit c: Connection): Unit
  def delete(objoidAndClassoid: PgShdescriptionId)(implicit c: Connection): Boolean
}
