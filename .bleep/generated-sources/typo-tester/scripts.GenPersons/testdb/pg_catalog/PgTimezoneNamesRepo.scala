package testdb.pg_catalog

import java.sql.Connection

trait PgTimezoneNamesRepo {
  def selectAll(implicit c: Connection): List[PgTimezoneNamesRow]
  def selectByFieldValues(fieldValues: List[PgTimezoneNamesFieldValue[_]])(implicit c: Connection): List[PgTimezoneNamesRow]
}
