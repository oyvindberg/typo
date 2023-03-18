package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgRewriteRepo {
  def selectAll(implicit c: Connection): List[PgRewriteRow]
  def selectById(oid: PgRewriteId)(implicit c: Connection): Option[PgRewriteRow]
  def selectByIds(oids: List[PgRewriteId])(implicit c: Connection): List[PgRewriteRow]
  def selectByFieldValues(fieldValues: List[PgRewriteFieldValue[_]])(implicit c: Connection): List[PgRewriteRow]
  def updateFieldValues(oid: PgRewriteId, fieldValues: List[PgRewriteFieldValue[_]])(implicit c: Connection): Int
  def insert(oid: PgRewriteId, unsaved: PgRewriteRowUnsaved)(implicit c: Connection): Unit
  def delete(oid: PgRewriteId)(implicit c: Connection): Boolean
  def selectByUnique(evClass: Long, rulename: String)(implicit c: Connection): Option[PgRewriteRow]
}
