/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_opfamily

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

class PgOpfamilyRepoMock(map: scala.collection.mutable.Map[PgOpfamilyId, PgOpfamilyRow] = scala.collection.mutable.Map.empty) extends PgOpfamilyRepo {
  override def delete(oid: PgOpfamilyId)(implicit c: Connection): Boolean = {
    map.remove(oid).isDefined
  }
  override def delete: DeleteBuilder[PgOpfamilyFields, PgOpfamilyRow] = {
    DeleteBuilderMock(DeleteParams.empty, PgOpfamilyFields, map)
  }
  override def insert(unsaved: PgOpfamilyRow)(implicit c: Connection): PgOpfamilyRow = {
    val _ = if (map.contains(unsaved.oid))
      sys.error(s"id ${unsaved.oid} already exists")
    else
      map.put(unsaved.oid, unsaved)
    
    unsaved
  }
  override def select: SelectBuilder[PgOpfamilyFields, PgOpfamilyRow] = {
    SelectBuilderMock(PgOpfamilyFields, () => map.values.toList, SelectParams.empty)
  }
  override def selectAll(implicit c: Connection): List[PgOpfamilyRow] = {
    map.values.toList
  }
  override def selectById(oid: PgOpfamilyId)(implicit c: Connection): Option[PgOpfamilyRow] = {
    map.get(oid)
  }
  override def selectByIds(oids: Array[PgOpfamilyId])(implicit c: Connection): List[PgOpfamilyRow] = {
    oids.flatMap(map.get).toList
  }
  override def selectByUnique(opfmethod: /* oid */ Long, opfname: String, opfnamespace: /* oid */ Long)(implicit c: Connection): Option[PgOpfamilyRow] = {
    map.values.find(v => opfmethod == v.opfmethod && opfname == v.opfname && opfnamespace == v.opfnamespace)
  }
  override def update(row: PgOpfamilyRow)(implicit c: Connection): Boolean = {
    map.get(row.oid) match {
      case Some(`row`) => false
      case Some(_) =>
        map.put(row.oid, row): @nowarn
        true
      case None => false
    }
  }
  override def update: UpdateBuilder[PgOpfamilyFields, PgOpfamilyRow] = {
    UpdateBuilderMock(UpdateParams.empty, PgOpfamilyFields, map)
  }
  override def upsert(unsaved: PgOpfamilyRow)(implicit c: Connection): PgOpfamilyRow = {
    map.put(unsaved.oid, unsaved): @nowarn
    unsaved
  }
}