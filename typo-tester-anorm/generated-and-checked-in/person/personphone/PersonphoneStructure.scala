/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package personphone

import adventureworks.TypoLocalDateTime
import adventureworks.person.businessentity.BusinessentityId
import adventureworks.person.phonenumbertype.PhonenumbertypeId
import adventureworks.public.Phone
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.IdField
import typo.dsl.Structure.Relation

class PersonphoneStructure[Row](val prefix: Option[String], val extract: Row => PersonphoneRow, val merge: (Row, PersonphoneRow) => Row)
  extends Relation[PersonphoneFields, PersonphoneRow, Row]
    with PersonphoneFields[Row] { outer =>

  override val businessentityid = new IdField[BusinessentityId, Row](prefix, "businessentityid", None, Some("int4"))(x => extract(x).businessentityid, (row, value) => merge(row, extract(row).copy(businessentityid = value)))
  override val phonenumber = new IdField[Phone, Row](prefix, "phonenumber", None, Some(""""public".Phone"""))(x => extract(x).phonenumber, (row, value) => merge(row, extract(row).copy(phonenumber = value)))
  override val phonenumbertypeid = new IdField[PhonenumbertypeId, Row](prefix, "phonenumbertypeid", None, Some("int4"))(x => extract(x).phonenumbertypeid, (row, value) => merge(row, extract(row).copy(phonenumbertypeid = value)))
  override val modifieddate = new Field[TypoLocalDateTime, Row](prefix, "modifieddate", Some("text"), Some("timestamp"))(x => extract(x).modifieddate, (row, value) => merge(row, extract(row).copy(modifieddate = value)))

  override val columns: List[FieldLikeNoHkt[?, Row]] =
    List[FieldLikeNoHkt[?, Row]](businessentityid, phonenumber, phonenumbertypeid, modifieddate)

  override def copy[NewRow](prefix: Option[String], extract: NewRow => PersonphoneRow, merge: (NewRow, PersonphoneRow) => NewRow): PersonphoneStructure[NewRow] =
    new PersonphoneStructure(prefix, extract, merge)
}