/**
 * File has been automatically generated by `typo` for internal use.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 *
 * (If you're developing `typo` and want to change it: run `bleep generate-sources)
 */
package typo
package generated
package pg_catalog

import java.sql.Connection

trait PgForeignDataWrapperRepo {
  def selectAll(implicit c: Connection): List[PgForeignDataWrapperRow]
  def selectById(oid: PgForeignDataWrapperId)(implicit c: Connection): Option[PgForeignDataWrapperRow]
  def selectByIds(oids: List[PgForeignDataWrapperId])(implicit c: Connection): List[PgForeignDataWrapperRow]
  def selectByFieldValues(fieldValues: List[PgForeignDataWrapperFieldValue[_]])(implicit c: Connection): List[PgForeignDataWrapperRow]
  def updateFieldValues(oid: PgForeignDataWrapperId, fieldValues: List[PgForeignDataWrapperFieldValue[_]])(implicit c: Connection): Int
  def insert(oid: PgForeignDataWrapperId, unsaved: PgForeignDataWrapperRowUnsaved)(implicit c: Connection): Unit
  def delete(oid: PgForeignDataWrapperId)(implicit c: Connection): Boolean
  def selectByUnique(fdwname: String)(implicit c: Connection): Option[PgForeignDataWrapperRow]
}