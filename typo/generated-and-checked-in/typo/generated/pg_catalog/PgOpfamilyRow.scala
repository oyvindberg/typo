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

import anorm.RowParser
import anorm.Success

case class PgOpfamilyRow(
  oid: PgOpfamilyId,
  opfmethod: Long,
  opfname: String,
  opfnamespace: Long,
  opfowner: Long
)

object PgOpfamilyRow {
  implicit val rowParser: RowParser[PgOpfamilyRow] = { row =>
    Success(
      PgOpfamilyRow(
        oid = row[PgOpfamilyId]("oid"),
        opfmethod = row[Long]("opfmethod"),
        opfname = row[String]("opfname"),
        opfnamespace = row[Long]("opfnamespace"),
        opfowner = row[Long]("opfowner")
      )
    )
  }

  
}