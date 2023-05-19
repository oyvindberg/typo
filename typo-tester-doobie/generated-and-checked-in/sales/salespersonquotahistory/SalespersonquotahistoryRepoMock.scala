/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salespersonquotahistory

import doobie.free.connection.ConnectionIO
import doobie.free.connection.delay
import fs2.Stream

class SalespersonquotahistoryRepoMock(toRow: Function1[SalespersonquotahistoryRowUnsaved, SalespersonquotahistoryRow],
                                      map: scala.collection.mutable.Map[SalespersonquotahistoryId, SalespersonquotahistoryRow] = scala.collection.mutable.Map.empty) extends SalespersonquotahistoryRepo {
  override def delete(compositeId: SalespersonquotahistoryId): ConnectionIO[Boolean] = {
    delay(map.remove(compositeId).isDefined)
  }
  override def insert(unsaved: SalespersonquotahistoryRow): ConnectionIO[SalespersonquotahistoryRow] = {
    delay {
      if (map.contains(unsaved.compositeId))
        sys.error(s"id ${unsaved.compositeId} already exists")
      else
        map.put(unsaved.compositeId, unsaved)
      unsaved
    }
  }
  override def insert(unsaved: SalespersonquotahistoryRowUnsaved): ConnectionIO[SalespersonquotahistoryRow] = {
    insert(toRow(unsaved))
  }
  override def selectAll: Stream[ConnectionIO, SalespersonquotahistoryRow] = {
    Stream.emits(map.values.toList)
  }
  override def selectByFieldValues(fieldValues: List[SalespersonquotahistoryFieldOrIdValue[_]]): Stream[ConnectionIO, SalespersonquotahistoryRow] = {
    Stream.emits {
      fieldValues.foldLeft(map.values) {
        case (acc, SalespersonquotahistoryFieldValue.businessentityid(value)) => acc.filter(_.businessentityid == value)
        case (acc, SalespersonquotahistoryFieldValue.quotadate(value)) => acc.filter(_.quotadate == value)
        case (acc, SalespersonquotahistoryFieldValue.salesquota(value)) => acc.filter(_.salesquota == value)
        case (acc, SalespersonquotahistoryFieldValue.rowguid(value)) => acc.filter(_.rowguid == value)
        case (acc, SalespersonquotahistoryFieldValue.modifieddate(value)) => acc.filter(_.modifieddate == value)
      }.toList
    }
  }
  override def selectById(compositeId: SalespersonquotahistoryId): ConnectionIO[Option[SalespersonquotahistoryRow]] = {
    delay(map.get(compositeId))
  }
  override def update(row: SalespersonquotahistoryRow): ConnectionIO[Boolean] = {
    delay {
      map.get(row.compositeId) match {
        case Some(`row`) => false
        case Some(_) =>
          map.put(row.compositeId, row)
          true
        case None => false
      }
    }
  }
  override def updateFieldValues(compositeId: SalespersonquotahistoryId, fieldValues: List[SalespersonquotahistoryFieldValue[_]]): ConnectionIO[Boolean] = {
    delay {
      map.get(compositeId) match {
        case Some(oldRow) =>
          val updatedRow = fieldValues.foldLeft(oldRow) {
            case (acc, SalespersonquotahistoryFieldValue.salesquota(value)) => acc.copy(salesquota = value)
            case (acc, SalespersonquotahistoryFieldValue.rowguid(value)) => acc.copy(rowguid = value)
            case (acc, SalespersonquotahistoryFieldValue.modifieddate(value)) => acc.copy(modifieddate = value)
          }
          if (updatedRow != oldRow) {
            map.put(compositeId, updatedRow)
            true
          } else {
            false
          }
        case None => false
      }
    }
  }
  override def upsert(unsaved: SalespersonquotahistoryRow): ConnectionIO[SalespersonquotahistoryRow] = {
    delay {
      map.put(unsaved.compositeId, unsaved)
      unsaved
    }
  }
}