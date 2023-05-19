/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package productmodel

import doobie.free.connection.ConnectionIO
import doobie.free.connection.delay
import fs2.Stream

class ProductmodelRepoMock(toRow: Function1[ProductmodelRowUnsaved, ProductmodelRow],
                           map: scala.collection.mutable.Map[ProductmodelId, ProductmodelRow] = scala.collection.mutable.Map.empty) extends ProductmodelRepo {
  override def delete(productmodelid: ProductmodelId): ConnectionIO[Boolean] = {
    delay(map.remove(productmodelid).isDefined)
  }
  override def insert(unsaved: ProductmodelRow): ConnectionIO[ProductmodelRow] = {
    delay {
      if (map.contains(unsaved.productmodelid))
        sys.error(s"id ${unsaved.productmodelid} already exists")
      else
        map.put(unsaved.productmodelid, unsaved)
      unsaved
    }
  }
  override def insert(unsaved: ProductmodelRowUnsaved): ConnectionIO[ProductmodelRow] = {
    insert(toRow(unsaved))
  }
  override def selectAll: Stream[ConnectionIO, ProductmodelRow] = {
    Stream.emits(map.values.toList)
  }
  override def selectByFieldValues(fieldValues: List[ProductmodelFieldOrIdValue[_]]): Stream[ConnectionIO, ProductmodelRow] = {
    Stream.emits {
      fieldValues.foldLeft(map.values) {
        case (acc, ProductmodelFieldValue.productmodelid(value)) => acc.filter(_.productmodelid == value)
        case (acc, ProductmodelFieldValue.name(value)) => acc.filter(_.name == value)
        case (acc, ProductmodelFieldValue.catalogdescription(value)) => acc.filter(_.catalogdescription == value)
        case (acc, ProductmodelFieldValue.instructions(value)) => acc.filter(_.instructions == value)
        case (acc, ProductmodelFieldValue.rowguid(value)) => acc.filter(_.rowguid == value)
        case (acc, ProductmodelFieldValue.modifieddate(value)) => acc.filter(_.modifieddate == value)
      }.toList
    }
  }
  override def selectById(productmodelid: ProductmodelId): ConnectionIO[Option[ProductmodelRow]] = {
    delay(map.get(productmodelid))
  }
  override def selectByIds(productmodelids: Array[ProductmodelId]): Stream[ConnectionIO, ProductmodelRow] = {
    Stream.emits(productmodelids.flatMap(map.get).toList)
  }
  override def update(row: ProductmodelRow): ConnectionIO[Boolean] = {
    delay {
      map.get(row.productmodelid) match {
        case Some(`row`) => false
        case Some(_) =>
          map.put(row.productmodelid, row)
          true
        case None => false
      }
    }
  }
  override def updateFieldValues(productmodelid: ProductmodelId, fieldValues: List[ProductmodelFieldValue[_]]): ConnectionIO[Boolean] = {
    delay {
      map.get(productmodelid) match {
        case Some(oldRow) =>
          val updatedRow = fieldValues.foldLeft(oldRow) {
            case (acc, ProductmodelFieldValue.name(value)) => acc.copy(name = value)
            case (acc, ProductmodelFieldValue.catalogdescription(value)) => acc.copy(catalogdescription = value)
            case (acc, ProductmodelFieldValue.instructions(value)) => acc.copy(instructions = value)
            case (acc, ProductmodelFieldValue.rowguid(value)) => acc.copy(rowguid = value)
            case (acc, ProductmodelFieldValue.modifieddate(value)) => acc.copy(modifieddate = value)
          }
          if (updatedRow != oldRow) {
            map.put(productmodelid, updatedRow)
            true
          } else {
            false
          }
        case None => false
      }
    }
  }
  override def upsert(unsaved: ProductmodelRow): ConnectionIO[ProductmodelRow] = {
    delay {
      map.put(unsaved.productmodelid, unsaved)
      unsaved
    }
  }
}