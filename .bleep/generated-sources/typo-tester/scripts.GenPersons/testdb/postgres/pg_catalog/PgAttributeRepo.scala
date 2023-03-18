package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgAttributeRepo {
  def selectAll(implicit c: Connection): List[PgAttributeRow]
  def selectById(attrelidAndAttnum: PgAttributeId)(implicit c: Connection): Option[PgAttributeRow]
  def selectByFieldValues(fieldValues: List[PgAttributeFieldValue[_]])(implicit c: Connection): List[PgAttributeRow]
  def updateFieldValues(attrelidAndAttnum: PgAttributeId, fieldValues: List[PgAttributeFieldValue[_]])(implicit c: Connection): Int
  def insert(attrelidAndAttnum: PgAttributeId, unsaved: PgAttributeRowUnsaved)(implicit c: Connection): Unit
  def delete(attrelidAndAttnum: PgAttributeId)(implicit c: Connection): Boolean
  def selectByUnique(attrelid: Long, attname: String)(implicit c: Connection): Option[PgAttributeRow]
}
