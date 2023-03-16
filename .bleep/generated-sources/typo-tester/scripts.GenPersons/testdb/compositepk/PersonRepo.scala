package testdb.compositepk

import java.sql.Connection

trait PersonRepo {
  def selectAll(implicit c: Connection): List[PersonRow]
  def selectById(oneAndTwo: PersonId)(implicit c: Connection): Option[PersonRow]
  def selectByFieldValues(fieldValues: List[PersonFieldValue[_]])(implicit c: Connection): List[PersonRow]
  def updateFieldValues(oneAndTwo: PersonId, fieldValues: List[PersonFieldValue[_]])(implicit c: Connection): Int
  def insert(unsaved: PersonRowUnsaved)(implicit c: Connection): PersonId
  def delete(oneAndTwo: PersonId)(implicit c: Connection): Boolean
}
