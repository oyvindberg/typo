/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productmodelproductdescriptionculture

import java.sql.Connection
import scala.annotation.nowarn
import typo.dsl.DeleteBuilder
import typo.dsl.DeleteBuilder.DeleteBuilderMock
import typo.dsl.DeleteParams
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderMock
import typo.dsl.SelectParams
import typo.dsl.UpdateBuilder
import typo.dsl.UpdateBuilder.UpdateBuilderMock
import typo.dsl.UpdateParams

class ProductmodelproductdescriptioncultureRepoMock(toRow: Function1[ProductmodelproductdescriptioncultureRowUnsaved, ProductmodelproductdescriptioncultureRow],
                                                    map: scala.collection.mutable.Map[ProductmodelproductdescriptioncultureId, ProductmodelproductdescriptioncultureRow] = scala.collection.mutable.Map.empty) extends ProductmodelproductdescriptioncultureRepo {
  override def delete(compositeId: ProductmodelproductdescriptioncultureId)(implicit c: Connection): Boolean = {
    map.remove(compositeId).isDefined
  }
  override def delete: DeleteBuilder[ProductmodelproductdescriptioncultureFields, ProductmodelproductdescriptioncultureRow] = {
    DeleteBuilderMock(DeleteParams.empty, ProductmodelproductdescriptioncultureFields.structure.fields, map)
  }
  override def insert(unsaved: ProductmodelproductdescriptioncultureRow)(implicit c: Connection): ProductmodelproductdescriptioncultureRow = {
    val _ = if (map.contains(unsaved.compositeId))
      sys.error(s"id ${unsaved.compositeId} already exists")
    else
      map.put(unsaved.compositeId, unsaved)
    
    unsaved
  }
  override def insertStreaming(unsaved: Iterator[ProductmodelproductdescriptioncultureRow], batchSize: Int)(implicit c: Connection): Long = {
    unsaved.foreach { row =>
      map += (row.compositeId -> row)
    }
    unsaved.size.toLong
  }
  override def insert(unsaved: ProductmodelproductdescriptioncultureRowUnsaved)(implicit c: Connection): ProductmodelproductdescriptioncultureRow = {
    insert(toRow(unsaved))
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: Iterator[ProductmodelproductdescriptioncultureRowUnsaved], batchSize: Int)(implicit c: Connection): Long = {
    unsaved.foreach { unsavedRow =>
      val row = toRow(unsavedRow)
      map += (row.compositeId -> row)
    }
    unsaved.size.toLong
  }
  override def select: SelectBuilder[ProductmodelproductdescriptioncultureFields, ProductmodelproductdescriptioncultureRow] = {
    SelectBuilderMock(ProductmodelproductdescriptioncultureFields.structure, () => map.values.toList, SelectParams.empty)
  }
  override def selectAll(implicit c: Connection): List[ProductmodelproductdescriptioncultureRow] = {
    map.values.toList
  }
  override def selectById(compositeId: ProductmodelproductdescriptioncultureId)(implicit c: Connection): Option[ProductmodelproductdescriptioncultureRow] = {
    map.get(compositeId)
  }
  override def update(row: ProductmodelproductdescriptioncultureRow)(implicit c: Connection): Boolean = {
    map.get(row.compositeId) match {
      case Some(`row`) => false
      case Some(_) =>
        map.put(row.compositeId, row): @nowarn
        true
      case None => false
    }
  }
  override def update: UpdateBuilder[ProductmodelproductdescriptioncultureFields, ProductmodelproductdescriptioncultureRow] = {
    UpdateBuilderMock(UpdateParams.empty, ProductmodelproductdescriptioncultureFields.structure.fields, map)
  }
  override def upsert(unsaved: ProductmodelproductdescriptioncultureRow)(implicit c: Connection): ProductmodelproductdescriptioncultureRow = {
    map.put(unsaved.compositeId, unsaved): @nowarn
    unsaved
  }
}
