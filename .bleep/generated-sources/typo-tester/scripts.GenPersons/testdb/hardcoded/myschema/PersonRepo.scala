package testdb
package hardcoded
package myschema

import java.sql.Connection

trait PersonRepo {
  def selectAll(implicit c: Connection): List[PersonRow]
  def selectById(id: PersonId)(implicit c: Connection): Option[PersonRow]
  def selectByIds(ids: List[PersonId])(implicit c: Connection): List[PersonRow]
  def selectByFieldValues(fieldValues: List[PersonFieldValue[_]])(implicit c: Connection): List[PersonRow]
  def updateFieldValues(id: PersonId, fieldValues: List[PersonFieldValue[_]])(implicit c: Connection): Int
  def insert(unsaved: PersonRowUnsaved)(implicit c: Connection): PersonId
  def delete(id: PersonId)(implicit c: Connection): Boolean
}
