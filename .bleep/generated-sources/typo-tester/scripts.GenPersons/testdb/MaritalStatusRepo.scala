package testdb

import java.sql.Connection

trait MaritalStatusRepo {
  def selectAll(implicit c: Connection): List[MaritalStatusRow]
  def selectById(id: MaritalStatusId)(implicit c: Connection): Option[MaritalStatusRow]
  def selectByIds(ids: List[MaritalStatusId])(implicit c: Connection): List[MaritalStatusRow]
  def selectByFieldValues(fieldValues: List[MaritalStatusFieldValue[_]])(implicit c: Connection): List[MaritalStatusRow]
  def updateFieldValues(id: MaritalStatusId, fieldValues: List[MaritalStatusFieldValue[_]])(implicit c: Connection): Int
  def delete(id: MaritalStatusId)(implicit c: Connection): Boolean
}
