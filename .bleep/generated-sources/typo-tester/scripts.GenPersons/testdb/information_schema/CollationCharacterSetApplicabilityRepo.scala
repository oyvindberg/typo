package testdb.information_schema

import java.sql.Connection

trait CollationCharacterSetApplicabilityRepo {
  def selectAll(implicit c: Connection): List[CollationCharacterSetApplicabilityRow]
  def selectByFieldValues(fieldValues: List[CollationCharacterSetApplicabilityFieldValue[_]])(implicit c: Connection): List[CollationCharacterSetApplicabilityRow]
}
