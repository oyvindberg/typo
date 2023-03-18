package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgTsConfigMapRepo {
  def selectAll(implicit c: Connection): List[PgTsConfigMapRow]
  def selectById(compositeId: PgTsConfigMapId)(implicit c: Connection): Option[PgTsConfigMapRow]
  def selectByFieldValues(fieldValues: List[PgTsConfigMapFieldValue[_]])(implicit c: Connection): List[PgTsConfigMapRow]
  def updateFieldValues(compositeId: PgTsConfigMapId, fieldValues: List[PgTsConfigMapFieldValue[_]])(implicit c: Connection): Int
  def insert(compositeId: PgTsConfigMapId, unsaved: PgTsConfigMapRowUnsaved)(implicit c: Connection): Unit
  def delete(compositeId: PgTsConfigMapId)(implicit c: Connection): Boolean
}
