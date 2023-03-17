package testdb.information_schema

import java.sql.Connection

trait PgForeignTableColumnsRepo {
  def selectAll(implicit c: Connection): List[PgForeignTableColumnsRow]
  def selectByFieldValues(fieldValues: List[PgForeignTableColumnsFieldValue[_]])(implicit c: Connection): List[PgForeignTableColumnsRow]
}
