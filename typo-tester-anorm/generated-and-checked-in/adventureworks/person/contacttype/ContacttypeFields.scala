/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package contacttype

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.public.Name
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.IdField

trait ContacttypeFields[Row] {
  val contacttypeid: IdField[ContacttypeId, Row]
  val name: Field[Name, Row]
  val modifieddate: Field[TypoLocalDateTime, Row]
}
object ContacttypeFields extends ContacttypeStructure[ContacttypeRow](None, identity, (_, x) => x)
