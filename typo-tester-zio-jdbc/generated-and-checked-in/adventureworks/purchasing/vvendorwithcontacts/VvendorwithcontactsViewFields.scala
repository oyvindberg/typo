/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package purchasing
package vvendorwithcontacts

import adventureworks.person.businessentity.BusinessentityId
import adventureworks.public.Name
import adventureworks.public.Phone
import adventureworks.userdefined.FirstName
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.OptField
import typo.dsl.Structure.Relation

trait VvendorwithcontactsViewFields[Row] {
  val businessentityid: Field[BusinessentityId, Row]
  val name: Field[Name, Row]
  val contacttype: Field[Name, Row]
  val title: OptField[/* max 8 chars */ String, Row]
  val firstname: Field[/* user-picked */ FirstName, Row]
  val middlename: OptField[Name, Row]
  val lastname: Field[Name, Row]
  val suffix: OptField[/* max 10 chars */ String, Row]
  val phonenumber: OptField[Phone, Row]
  val phonenumbertype: OptField[Name, Row]
  val emailaddress: OptField[/* max 50 chars */ String, Row]
  val emailpromotion: Field[Int, Row]
}

object VvendorwithcontactsViewFields {
  val structure: Relation[VvendorwithcontactsViewFields, VvendorwithcontactsViewRow, VvendorwithcontactsViewRow] = 
    new Impl(None, identity, (_, x) => x)
    
  private final class Impl[Row](val prefix: Option[String], val extract: Row => VvendorwithcontactsViewRow, val merge: (Row, VvendorwithcontactsViewRow) => Row)
    extends Relation[VvendorwithcontactsViewFields, VvendorwithcontactsViewRow, Row] { 
  
    override val fields: VvendorwithcontactsViewFields[Row] = new VvendorwithcontactsViewFields[Row] {
      override val businessentityid = new Field[BusinessentityId, Row](prefix, "businessentityid", None, None)(x => extract(x).businessentityid, (row, value) => merge(row, extract(row).copy(businessentityid = value)))
      override val name = new Field[Name, Row](prefix, "name", None, None)(x => extract(x).name, (row, value) => merge(row, extract(row).copy(name = value)))
      override val contacttype = new Field[Name, Row](prefix, "contacttype", None, None)(x => extract(x).contacttype, (row, value) => merge(row, extract(row).copy(contacttype = value)))
      override val title = new OptField[/* max 8 chars */ String, Row](prefix, "title", None, None)(x => extract(x).title, (row, value) => merge(row, extract(row).copy(title = value)))
      override val firstname = new Field[/* user-picked */ FirstName, Row](prefix, "firstname", None, None)(x => extract(x).firstname, (row, value) => merge(row, extract(row).copy(firstname = value)))
      override val middlename = new OptField[Name, Row](prefix, "middlename", None, None)(x => extract(x).middlename, (row, value) => merge(row, extract(row).copy(middlename = value)))
      override val lastname = new Field[Name, Row](prefix, "lastname", None, None)(x => extract(x).lastname, (row, value) => merge(row, extract(row).copy(lastname = value)))
      override val suffix = new OptField[/* max 10 chars */ String, Row](prefix, "suffix", None, None)(x => extract(x).suffix, (row, value) => merge(row, extract(row).copy(suffix = value)))
      override val phonenumber = new OptField[Phone, Row](prefix, "phonenumber", None, None)(x => extract(x).phonenumber, (row, value) => merge(row, extract(row).copy(phonenumber = value)))
      override val phonenumbertype = new OptField[Name, Row](prefix, "phonenumbertype", None, None)(x => extract(x).phonenumbertype, (row, value) => merge(row, extract(row).copy(phonenumbertype = value)))
      override val emailaddress = new OptField[/* max 50 chars */ String, Row](prefix, "emailaddress", None, None)(x => extract(x).emailaddress, (row, value) => merge(row, extract(row).copy(emailaddress = value)))
      override val emailpromotion = new Field[Int, Row](prefix, "emailpromotion", None, None)(x => extract(x).emailpromotion, (row, value) => merge(row, extract(row).copy(emailpromotion = value)))
    }
  
    override val columns: List[FieldLikeNoHkt[?, Row]] =
      List[FieldLikeNoHkt[?, Row]](fields.businessentityid, fields.name, fields.contacttype, fields.title, fields.firstname, fields.middlename, fields.lastname, fields.suffix, fields.phonenumber, fields.phonenumbertype, fields.emailaddress, fields.emailpromotion)
  
    override def copy[NewRow](prefix: Option[String], extract: NewRow => VvendorwithcontactsViewRow, merge: (NewRow, VvendorwithcontactsViewRow) => NewRow): Impl[NewRow] =
      new Impl(prefix, extract, merge)
  }
  
}