/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pe
package pnt

import adventureworks.TypoLocalDateTime
import adventureworks.person.phonenumbertype.PhonenumbertypeId
import adventureworks.public.Name
import typo.dsl.SqlExpr.OptField

trait PntViewFields[Row] {
  val id: OptField[Int, Row]
  val phonenumbertypeid: OptField[PhonenumbertypeId, Row]
  val name: OptField[Name, Row]
  val modifieddate: OptField[TypoLocalDateTime, Row]
}
object PntViewFields extends PntViewStructure[PntViewRow](None, identity, (_, x) => x)
