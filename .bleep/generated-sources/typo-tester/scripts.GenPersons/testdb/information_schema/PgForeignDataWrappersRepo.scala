package testdb
package information_schema

import java.sql.Connection

trait PgForeignDataWrappersRepo {
  def selectAll(implicit c: Connection): List[PgForeignDataWrappersRow]
  def selectByFieldValues(fieldValues: List[PgForeignDataWrappersFieldValue[_]])(implicit c: Connection): List[PgForeignDataWrappersRow]
}
