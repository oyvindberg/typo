package testdb
package postgres
package public

import java.sql.Connection

trait FooRepo {
  def selectAll(implicit c: Connection): List[FooRow]
  def selectByFieldValues(fieldValues: List[FooFieldValue[_]])(implicit c: Connection): List[FooRow]
}
