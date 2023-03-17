package testdb
package pg_catalog

import java.sql.Connection

trait PgStatProgressVacuumRepo {
  def selectAll(implicit c: Connection): List[PgStatProgressVacuumRow]
  def selectByFieldValues(fieldValues: List[PgStatProgressVacuumFieldValue[_]])(implicit c: Connection): List[PgStatProgressVacuumRow]
}
