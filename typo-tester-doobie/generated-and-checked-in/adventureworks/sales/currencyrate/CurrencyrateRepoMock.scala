/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package currencyrate

import doobie.free.connection.ConnectionIO
import doobie.free.connection.delay
import fs2.Stream
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

class CurrencyrateRepoMock(toRow: Function1[CurrencyrateRowUnsaved, CurrencyrateRow],
                           map: scala.collection.mutable.Map[CurrencyrateId, CurrencyrateRow] = scala.collection.mutable.Map.empty) extends CurrencyrateRepo {
  override def delete: DeleteBuilder[CurrencyrateFields, CurrencyrateRow] = {
    DeleteBuilderMock(DeleteParams.empty, CurrencyrateFields.structure, map)
  }
  override def deleteById(currencyrateid: CurrencyrateId): ConnectionIO[Boolean] = {
    delay(map.remove(currencyrateid).isDefined)
  }
  override def deleteByIds(currencyrateids: Array[CurrencyrateId]): ConnectionIO[Int] = {
    delay(currencyrateids.map(id => map.remove(id)).count(_.isDefined))
  }
  override def insert(unsaved: CurrencyrateRow): ConnectionIO[CurrencyrateRow] = {
    delay {
      val _ = if (map.contains(unsaved.currencyrateid))
        sys.error(s"id ${unsaved.currencyrateid} already exists")
      else
        map.put(unsaved.currencyrateid, unsaved)
    
      unsaved
    }
  }
  override def insert(unsaved: CurrencyrateRowUnsaved): ConnectionIO[CurrencyrateRow] = {
    insert(toRow(unsaved))
  }
  override def insertStreaming(unsaved: Stream[ConnectionIO, CurrencyrateRow], batchSize: Int = 10000): ConnectionIO[Long] = {
    unsaved.compile.toList.map { rows =>
      var num = 0L
      rows.foreach { row =>
        map += (row.currencyrateid -> row)
        num += 1
      }
      num
    }
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: Stream[ConnectionIO, CurrencyrateRowUnsaved], batchSize: Int = 10000): ConnectionIO[Long] = {
    unsaved.compile.toList.map { unsavedRows =>
      var num = 0L
      unsavedRows.foreach { unsavedRow =>
        val row = toRow(unsavedRow)
        map += (row.currencyrateid -> row)
        num += 1
      }
      num
    }
  }
  override def select: SelectBuilder[CurrencyrateFields, CurrencyrateRow] = {
    SelectBuilderMock(CurrencyrateFields.structure, delay(map.values.toList), SelectParams.empty)
  }
  override def selectAll: Stream[ConnectionIO, CurrencyrateRow] = {
    Stream.emits(map.values.toList)
  }
  override def selectById(currencyrateid: CurrencyrateId): ConnectionIO[Option[CurrencyrateRow]] = {
    delay(map.get(currencyrateid))
  }
  override def selectByIds(currencyrateids: Array[CurrencyrateId]): Stream[ConnectionIO, CurrencyrateRow] = {
    Stream.emits(currencyrateids.flatMap(map.get).toList)
  }
  override def selectByIdsTracked(currencyrateids: Array[CurrencyrateId]): ConnectionIO[Map[CurrencyrateId, CurrencyrateRow]] = {
    selectByIds(currencyrateids).compile.toList.map { rows =>
      val byId = rows.view.map(x => (x.currencyrateid, x)).toMap
      currencyrateids.view.flatMap(id => byId.get(id).map(x => (id, x))).toMap
    }
  }
  override def update: UpdateBuilder[CurrencyrateFields, CurrencyrateRow] = {
    UpdateBuilderMock(UpdateParams.empty, CurrencyrateFields.structure, map)
  }
  override def update(row: CurrencyrateRow): ConnectionIO[Option[CurrencyrateRow]] = {
    delay {
      map.get(row.currencyrateid).map { _ =>
        map.put(row.currencyrateid, row): @nowarn
        row
      }
    }
  }
  override def upsert(unsaved: CurrencyrateRow): ConnectionIO[CurrencyrateRow] = {
    delay {
      map.put(unsaved.currencyrateid, unsaved): @nowarn
      unsaved
    }
  }
  override def upsertBatch(unsaved: List[CurrencyrateRow]): Stream[ConnectionIO, CurrencyrateRow] = {
    Stream.emits {
      unsaved.map { row =>
        map += (row.currencyrateid -> row)
        row
      }
    }
  }
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  override def upsertStreaming(unsaved: Stream[ConnectionIO, CurrencyrateRow], batchSize: Int = 10000): ConnectionIO[Int] = {
    unsaved.compile.toList.map { rows =>
      var num = 0
      rows.foreach { row =>
        map += (row.currencyrateid -> row)
        num += 1
      }
      num
    }
  }
}
