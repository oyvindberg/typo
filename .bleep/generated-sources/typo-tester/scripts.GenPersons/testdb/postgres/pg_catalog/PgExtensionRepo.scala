package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgExtensionRepo {
  def selectAll(implicit c: Connection): List[PgExtensionRow]
  def selectById(oid: PgExtensionId)(implicit c: Connection): Option[PgExtensionRow]
  def selectByIds(oids: List[PgExtensionId])(implicit c: Connection): List[PgExtensionRow]
  def selectByFieldValues(fieldValues: List[PgExtensionFieldValue[_]])(implicit c: Connection): List[PgExtensionRow]
  def updateFieldValues(oid: PgExtensionId, fieldValues: List[PgExtensionFieldValue[_]])(implicit c: Connection): Int
  def insert(oid: PgExtensionId, unsaved: PgExtensionRowUnsaved)(implicit c: Connection): Unit
  def delete(oid: PgExtensionId)(implicit c: Connection): Boolean
  def selectByUnique(extname: String)(implicit c: Connection): Option[PgExtensionRow]
}
