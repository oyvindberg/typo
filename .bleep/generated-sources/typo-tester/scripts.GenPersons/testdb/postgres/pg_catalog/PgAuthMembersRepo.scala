package testdb
package postgres
package pg_catalog

import java.sql.Connection

trait PgAuthMembersRepo {
  def selectAll(implicit c: Connection): List[PgAuthMembersRow]
  def selectById(roleidAndMember: PgAuthMembersId)(implicit c: Connection): Option[PgAuthMembersRow]
  def selectByFieldValues(fieldValues: List[PgAuthMembersFieldValue[_]])(implicit c: Connection): List[PgAuthMembersRow]
  def updateFieldValues(roleidAndMember: PgAuthMembersId, fieldValues: List[PgAuthMembersFieldValue[_]])(implicit c: Connection): Int
  def insert(roleidAndMember: PgAuthMembersId, unsaved: PgAuthMembersRowUnsaved)(implicit c: Connection): Unit
  def delete(roleidAndMember: PgAuthMembersId)(implicit c: Connection): Boolean
  def selectByUnique(member: Long, roleid: Long)(implicit c: Connection): Option[PgAuthMembersRow]
}
