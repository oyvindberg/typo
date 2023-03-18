package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgInheritsRepo {
  def selectAll(implicit c: Connection): List[PgInheritsRow]
  def selectById(inhrelidAndInhseqno: PgInheritsId)(implicit c: Connection): Option[PgInheritsRow]
  def selectByFieldValues(fieldValues: List[PgInheritsFieldValue[_]])(implicit c: Connection): List[PgInheritsRow]
  def updateFieldValues(inhrelidAndInhseqno: PgInheritsId, fieldValues: List[PgInheritsFieldValue[_]])(implicit c: Connection): Int
  def insert(inhrelidAndInhseqno: PgInheritsId, unsaved: PgInheritsRowUnsaved)(implicit c: Connection): Unit
  def delete(inhrelidAndInhseqno: PgInheritsId)(implicit c: Connection): Boolean
}
