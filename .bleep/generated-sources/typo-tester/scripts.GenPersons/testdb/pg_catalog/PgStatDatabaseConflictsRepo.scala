package testdb.pg_catalog

import java.sql.Connection

trait PgStatDatabaseConflictsRepo {
  def selectAll(implicit c: Connection): List[PgStatDatabaseConflictsRow]
  def selectByFieldValues(fieldValues: List[PgStatDatabaseConflictsFieldValue[_]])(implicit c: Connection): List[PgStatDatabaseConflictsRow]
}
