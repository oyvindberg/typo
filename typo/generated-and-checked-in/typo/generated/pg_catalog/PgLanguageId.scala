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

import anorm.Column
import anorm.RowParser
import anorm.SqlParser
import anorm.ToStatement

case class PgLanguageId(value: Long) extends AnyVal
object PgLanguageId {
  implicit val ordering: Ordering[PgLanguageId] = Ordering.by(_.value)
  
  implicit val toStatement: ToStatement[PgLanguageId] = implicitly[ToStatement[Long]].contramap(_.value)
  implicit val column: Column[PgLanguageId] = implicitly[Column[Long]].map(PgLanguageId.apply)
  implicit val rowParser: RowParser[PgLanguageId] = SqlParser.get[PgLanguageId]("oid")
}