/**
 * File has been automatically generated by `typo` for internal use.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 *
 * (If you're developing `typo` and want to change it: run `bleep generate-sources)
 */
package typo
package generated
package pg_catalog

import java.sql.Connection

trait PgStatArchiverRepo {
  def selectAll(implicit c: Connection): List[PgStatArchiverRow]
  def selectByFieldValues(fieldValues: List[PgStatArchiverFieldValue[_]])(implicit c: Connection): List[PgStatArchiverRow]
}