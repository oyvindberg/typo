package testdb
package information_schema

import java.sql.Connection

trait SequencesRepo {
  def selectAll(implicit c: Connection): List[SequencesRow]
  def selectByFieldValues(fieldValues: List[SequencesFieldValue[_]])(implicit c: Connection): List[SequencesRow]
}
