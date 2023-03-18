package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgTypeRepo {
  def selectAll(implicit c: Connection): List[PgTypeRow]
  def selectById(oid: PgTypeId)(implicit c: Connection): Option[PgTypeRow]
  def selectByIds(oids: List[PgTypeId])(implicit c: Connection): List[PgTypeRow]
  def selectByFieldValues(fieldValues: List[PgTypeFieldValue[_]])(implicit c: Connection): List[PgTypeRow]
  def updateFieldValues(oid: PgTypeId, fieldValues: List[PgTypeFieldValue[_]])(implicit c: Connection): Int
  def insert(oid: PgTypeId, unsaved: PgTypeRowUnsaved)(implicit c: Connection): Unit
  def delete(oid: PgTypeId)(implicit c: Connection): Boolean
  def selectByUnique(typname: String, typnamespace: Long)(implicit c: Connection): Option[PgTypeRow]
}
