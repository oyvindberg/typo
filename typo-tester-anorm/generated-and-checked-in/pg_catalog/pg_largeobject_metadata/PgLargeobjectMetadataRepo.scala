/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_largeobject_metadata

import java.sql.Connection

trait PgLargeobjectMetadataRepo {
  def delete(oid: PgLargeobjectMetadataId)(implicit c: Connection): Boolean
  def insert(unsaved: PgLargeobjectMetadataRow)(implicit c: Connection): PgLargeobjectMetadataRow
  def selectAll(implicit c: Connection): List[PgLargeobjectMetadataRow]
  def selectById(oid: PgLargeobjectMetadataId)(implicit c: Connection): Option[PgLargeobjectMetadataRow]
  def selectByIds(oids: Array[PgLargeobjectMetadataId])(implicit c: Connection): List[PgLargeobjectMetadataRow]
  def update(row: PgLargeobjectMetadataRow)(implicit c: Connection): Boolean
  def upsert(unsaved: PgLargeobjectMetadataRow)(implicit c: Connection): PgLargeobjectMetadataRow
}