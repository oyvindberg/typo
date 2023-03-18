package testdb
package hardcoded
package myschema

import java.sql.Connection

trait FootballClubRepo {
  def selectAll(implicit c: Connection): List[FootballClubRow]
  def selectById(id: FootballClubId)(implicit c: Connection): Option[FootballClubRow]
  def selectByIds(ids: List[FootballClubId])(implicit c: Connection): List[FootballClubRow]
  def selectByFieldValues(fieldValues: List[FootballClubFieldValue[_]])(implicit c: Connection): List[FootballClubRow]
  def updateFieldValues(id: FootballClubId, fieldValues: List[FootballClubFieldValue[_]])(implicit c: Connection): Int
  def insert(id: FootballClubId, unsaved: FootballClubRowUnsaved)(implicit c: Connection): Unit
  def delete(id: FootballClubId)(implicit c: Connection): Boolean
}
