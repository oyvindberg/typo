/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package currencyrate

import java.sql.Connection
import scala.Function1

class CurrencyrateRepoMock(toRow: Function1[CurrencyrateRowUnsaved, CurrencyrateRow],
                           map: scala.collection.mutable.Map[CurrencyrateId, CurrencyrateRow] = scala.collection.mutable.Map.empty) extends CurrencyrateRepo {
  override def delete(currencyrateid: CurrencyrateId)(implicit c: Connection): Boolean = {
    map.remove(currencyrateid).isDefined
  }
  override def insert(unsaved: CurrencyrateRow)(implicit c: Connection): CurrencyrateRow = {
    map.put(unsaved.currencyrateid, unsaved)
    unsaved
  }
  override def insert(unsaved: CurrencyrateRowUnsaved)(implicit c: Connection): CurrencyrateRow = {
    val row = toRow(unsaved)
    map.put(row.currencyrateid, row)
    row
  }
  override def selectAll(implicit c: Connection): List[CurrencyrateRow] = {
    map.values.toList
  }
  override def selectByFieldValues(fieldValues: List[CurrencyrateFieldOrIdValue[_]])(implicit c: Connection): List[CurrencyrateRow] = {
    fieldValues.foldLeft(map.values) {
      case (acc, CurrencyrateFieldValue.currencyrateid(value)) => acc.filter(_.currencyrateid == value)
      case (acc, CurrencyrateFieldValue.currencyratedate(value)) => acc.filter(_.currencyratedate == value)
      case (acc, CurrencyrateFieldValue.fromcurrencycode(value)) => acc.filter(_.fromcurrencycode == value)
      case (acc, CurrencyrateFieldValue.tocurrencycode(value)) => acc.filter(_.tocurrencycode == value)
      case (acc, CurrencyrateFieldValue.averagerate(value)) => acc.filter(_.averagerate == value)
      case (acc, CurrencyrateFieldValue.endofdayrate(value)) => acc.filter(_.endofdayrate == value)
      case (acc, CurrencyrateFieldValue.modifieddate(value)) => acc.filter(_.modifieddate == value)
    }.toList
  }
  override def selectById(currencyrateid: CurrencyrateId)(implicit c: Connection): Option[CurrencyrateRow] = {
    map.get(currencyrateid)
  }
  override def selectByIds(currencyrateids: Array[CurrencyrateId])(implicit c: Connection): List[CurrencyrateRow] = {
    currencyrateids.flatMap(map.get).toList
  }
  override def update(row: CurrencyrateRow)(implicit c: Connection): Boolean = {
    map.get(row.currencyrateid) match {
      case Some(`row`) => false
      case Some(_) =>
        map.put(row.currencyrateid, row)
        true
      case None => false
    }
  }
  override def updateFieldValues(currencyrateid: CurrencyrateId, fieldValues: List[CurrencyrateFieldValue[_]])(implicit c: Connection): Boolean = {
    map.get(currencyrateid) match {
      case Some(oldRow) =>
        val updatedRow = fieldValues.foldLeft(oldRow) {
          case (acc, CurrencyrateFieldValue.currencyratedate(value)) => acc.copy(currencyratedate = value)
          case (acc, CurrencyrateFieldValue.fromcurrencycode(value)) => acc.copy(fromcurrencycode = value)
          case (acc, CurrencyrateFieldValue.tocurrencycode(value)) => acc.copy(tocurrencycode = value)
          case (acc, CurrencyrateFieldValue.averagerate(value)) => acc.copy(averagerate = value)
          case (acc, CurrencyrateFieldValue.endofdayrate(value)) => acc.copy(endofdayrate = value)
          case (acc, CurrencyrateFieldValue.modifieddate(value)) => acc.copy(modifieddate = value)
        }
        if (updatedRow != oldRow) {
          map.put(currencyrateid, updatedRow)
          true
        } else {
          false
        }
      case None => false
    }
  }
}