package testdb
package hardcoded
package compositepk

import java.sql.Connection

trait PersonRepo {
  def selectAll(implicit c: Connection): List[PersonRow]
  def selectById(compositeId: PersonId)(implicit c: Connection): Option[PersonRow]
  def selectByFieldValues(fieldValues: List[PersonFieldValue[_]])(implicit c: Connection): List[PersonRow]
  def updateFieldValues(compositeId: PersonId, fieldValues: List[PersonFieldValue[_]])(implicit c: Connection): Int
  def insert(unsaved: PersonRowUnsaved)(implicit c: Connection): PersonId
  def delete(compositeId: PersonId)(implicit c: Connection): Boolean
}
