package testdb
package postgres
package information_schema

import java.sql.Connection

trait PgForeignServersRepo {
  def selectAll(implicit c: Connection): List[PgForeignServersRow]
  def selectByFieldValues(fieldValues: List[PgForeignServersFieldValue[_]])(implicit c: Connection): List[PgForeignServersRow]
}
