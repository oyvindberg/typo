/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_namespace

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

class PgNamespaceRepoMock(map: scala.collection.mutable.Map[PgNamespaceId, PgNamespaceRow] = scala.collection.mutable.Map.empty) extends PgNamespaceRepo {
  override def delete(oid: PgNamespaceId)(implicit c: Connection): Boolean = {
    map.remove(oid).isDefined
  }
  override def delete: DeleteBuilder[PgNamespaceFields, PgNamespaceRow] = {
    DeleteBuilderMock(DeleteParams.empty, PgNamespaceFields, map)
  }
  override def insert(unsaved: PgNamespaceRow)(implicit c: Connection): PgNamespaceRow = {
    val _ = if (map.contains(unsaved.oid))
      sys.error(s"id ${unsaved.oid} already exists")
    else
      map.put(unsaved.oid, unsaved)
    
    unsaved
  }
  override def select: SelectBuilder[PgNamespaceFields, PgNamespaceRow] = {
    SelectBuilderMock(PgNamespaceFields, () => map.values.toList, SelectParams.empty)
  }
  override def selectAll(implicit c: Connection): List[PgNamespaceRow] = {
    map.values.toList
  }
  override def selectById(oid: PgNamespaceId)(implicit c: Connection): Option[PgNamespaceRow] = {
    map.get(oid)
  }
  override def selectByIds(oids: Array[PgNamespaceId])(implicit c: Connection): List[PgNamespaceRow] = {
    oids.flatMap(map.get).toList
  }
  override def selectByUnique(nspname: String)(implicit c: Connection): Option[PgNamespaceRow] = {
    map.values.find(v => nspname == v.nspname)
  }
  override def update(row: PgNamespaceRow)(implicit c: Connection): Boolean = {
    map.get(row.oid) match {
      case Some(`row`) => false
      case Some(_) =>
        map.put(row.oid, row): @nowarn
        true
      case None => false
    }
  }
  override def update: UpdateBuilder[PgNamespaceFields, PgNamespaceRow] = {
    UpdateBuilderMock(UpdateParams.empty, PgNamespaceFields, map)
  }
  override def upsert(unsaved: PgNamespaceRow)(implicit c: Connection): PgNamespaceRow = {
    map.put(unsaved.oid, unsaved): @nowarn
    unsaved
  }
}