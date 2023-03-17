package testdb.pg_catalog

import java.sql.Connection

trait PgCursorsRepo {
  def selectAll(implicit c: Connection): List[PgCursorsRow]
  def selectByFieldValues(fieldValues: List[PgCursorsFieldValue[_]])(implicit c: Connection): List[PgCursorsRow]
}
