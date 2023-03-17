package testdb.pg_catalog

import java.sql.Connection

trait PgGroupRepo {
  def selectAll(implicit c: Connection): List[PgGroupRow]
  def selectByFieldValues(fieldValues: List[PgGroupFieldValue[_]])(implicit c: Connection): List[PgGroupRow]
}
