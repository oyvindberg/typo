/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pg_catalog
package pg_attrdef

import adventureworks.customtypes.TypoShort
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

class PgAttrdefRepoMock(map: scala.collection.mutable.Map[PgAttrdefId, PgAttrdefRow] = scala.collection.mutable.Map.empty) extends PgAttrdefRepo {
  override def delete(oid: PgAttrdefId)(implicit c: Connection): Boolean = {
    map.remove(oid).isDefined
  }
  override def delete: DeleteBuilder[PgAttrdefFields, PgAttrdefRow] = {
    DeleteBuilderMock(DeleteParams.empty, PgAttrdefFields, map)
  }
  override def insert(unsaved: PgAttrdefRow)(implicit c: Connection): PgAttrdefRow = {
    val _ = if (map.contains(unsaved.oid))
      sys.error(s"id ${unsaved.oid} already exists")
    else
      map.put(unsaved.oid, unsaved)
    
    unsaved
  }
  override def select: SelectBuilder[PgAttrdefFields, PgAttrdefRow] = {
    SelectBuilderMock(PgAttrdefFields, () => map.values.toList, SelectParams.empty)
  }
  override def selectAll(implicit c: Connection): List[PgAttrdefRow] = {
    map.values.toList
  }
  override def selectById(oid: PgAttrdefId)(implicit c: Connection): Option[PgAttrdefRow] = {
    map.get(oid)
  }
  override def selectByIds(oids: Array[PgAttrdefId])(implicit c: Connection): List[PgAttrdefRow] = {
    oids.flatMap(map.get).toList
  }
  override def selectByUnique(adrelid: /* oid */ Long, adnum: TypoShort)(implicit c: Connection): Option[PgAttrdefRow] = {
    map.values.find(v => adrelid == v.adrelid && adnum == v.adnum)
  }
  override def update(row: PgAttrdefRow)(implicit c: Connection): Boolean = {
    map.get(row.oid) match {
      case Some(`row`) => false
      case Some(_) =>
        map.put(row.oid, row): @nowarn
        true
      case None => false
    }
  }
  override def update: UpdateBuilder[PgAttrdefFields, PgAttrdefRow] = {
    UpdateBuilderMock(UpdateParams.empty, PgAttrdefFields, map)
  }
  override def upsert(unsaved: PgAttrdefRow)(implicit c: Connection): PgAttrdefRow = {
    map.put(unsaved.oid, unsaved): @nowarn
    unsaved
  }
}