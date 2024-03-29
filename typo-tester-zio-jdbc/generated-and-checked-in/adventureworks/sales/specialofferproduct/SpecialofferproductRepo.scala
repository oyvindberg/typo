/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package specialofferproduct

import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder
import zio.ZIO
import zio.jdbc.UpdateResult
import zio.jdbc.ZConnection
import zio.stream.ZStream

trait SpecialofferproductRepo {
  def delete(compositeId: SpecialofferproductId): ZIO[ZConnection, Throwable, Boolean]
  def delete: DeleteBuilder[SpecialofferproductFields, SpecialofferproductRow]
  def insert(unsaved: SpecialofferproductRow): ZIO[ZConnection, Throwable, SpecialofferproductRow]
  def insertStreaming(unsaved: ZStream[ZConnection, Throwable, SpecialofferproductRow], batchSize: Int): ZIO[ZConnection, Throwable, Long]
  def insert(unsaved: SpecialofferproductRowUnsaved): ZIO[ZConnection, Throwable, SpecialofferproductRow]
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  def insertUnsavedStreaming(unsaved: ZStream[ZConnection, Throwable, SpecialofferproductRowUnsaved], batchSize: Int): ZIO[ZConnection, Throwable, Long]
  def select: SelectBuilder[SpecialofferproductFields, SpecialofferproductRow]
  def selectAll: ZStream[ZConnection, Throwable, SpecialofferproductRow]
  def selectById(compositeId: SpecialofferproductId): ZIO[ZConnection, Throwable, Option[SpecialofferproductRow]]
  def update(row: SpecialofferproductRow): ZIO[ZConnection, Throwable, Boolean]
  def update: UpdateBuilder[SpecialofferproductFields, SpecialofferproductRow]
  def upsert(unsaved: SpecialofferproductRow): ZIO[ZConnection, Throwable, UpdateResult[SpecialofferproductRow]]
}
