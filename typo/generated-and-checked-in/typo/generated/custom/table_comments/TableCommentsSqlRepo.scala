/**
 * File has been automatically generated by `typo` for internal use.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 *
 * (If you're developing `typo` and want to change it: run `bleep generate-sources`)
 */
package typo
package generated
package custom
package table_comments

import java.sql.Connection

trait TableCommentsSqlRepo {
  def apply()(implicit c: Connection): List[TableCommentsSqlRow]
}
