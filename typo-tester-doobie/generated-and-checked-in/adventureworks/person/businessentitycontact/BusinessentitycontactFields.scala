/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package businessentitycontact

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.person.businessentity.BusinessentityId
import adventureworks.person.contacttype.ContacttypeId
import java.util.UUID
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.IdField

trait BusinessentitycontactFields[Row] {
  val businessentityid: IdField[BusinessentityId, Row]
  val personid: IdField[BusinessentityId, Row]
  val contacttypeid: IdField[ContacttypeId, Row]
  val rowguid: Field[UUID, Row]
  val modifieddate: Field[TypoLocalDateTime, Row]
}
object BusinessentitycontactFields extends BusinessentitycontactStructure[BusinessentitycontactRow](None, identity, (_, x) => x)
