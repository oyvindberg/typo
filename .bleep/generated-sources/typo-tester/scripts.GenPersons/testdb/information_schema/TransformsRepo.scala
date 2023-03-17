package testdb.information_schema

import java.sql.Connection

trait TransformsRepo {
  def selectAll(implicit c: Connection): List[TransformsRow]
  def selectByFieldValues(fieldValues: List[TransformsFieldValue[_]])(implicit c: Connection): List[TransformsRow]
}
