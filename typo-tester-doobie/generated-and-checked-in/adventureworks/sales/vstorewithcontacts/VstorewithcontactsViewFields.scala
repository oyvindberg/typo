/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package vstorewithcontacts

import adventureworks.person.businessentity.BusinessentityId
import adventureworks.public.Name
import adventureworks.public.Phone
import adventureworks.userdefined.FirstName
import typo.dsl.Path
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.OptField
import typo.dsl.Structure.Relation

trait VstorewithcontactsViewFields {
  def businessentityid: Field[BusinessentityId, VstorewithcontactsViewRow]
  def name: Field[Name, VstorewithcontactsViewRow]
  def contacttype: Field[Name, VstorewithcontactsViewRow]
  def title: OptField[/* max 8 chars */ String, VstorewithcontactsViewRow]
  def firstname: Field[/* user-picked */ FirstName, VstorewithcontactsViewRow]
  def middlename: OptField[Name, VstorewithcontactsViewRow]
  def lastname: Field[Name, VstorewithcontactsViewRow]
  def suffix: OptField[/* max 10 chars */ String, VstorewithcontactsViewRow]
  def phonenumber: OptField[Phone, VstorewithcontactsViewRow]
  def phonenumbertype: OptField[Name, VstorewithcontactsViewRow]
  def emailaddress: OptField[/* max 50 chars */ String, VstorewithcontactsViewRow]
  def emailpromotion: Field[Int, VstorewithcontactsViewRow]
}

object VstorewithcontactsViewFields {
  lazy val structure: Relation[VstorewithcontactsViewFields, VstorewithcontactsViewRow] =
    new Impl(Nil)
    
  private final class Impl(val _path: List[Path])
    extends Relation[VstorewithcontactsViewFields, VstorewithcontactsViewRow] {
  
    override lazy val fields: VstorewithcontactsViewFields = new VstorewithcontactsViewFields {
      override def businessentityid = Field[BusinessentityId, VstorewithcontactsViewRow](_path, "businessentityid", None, None, x => x.businessentityid, (row, value) => row.copy(businessentityid = value))
      override def name = Field[Name, VstorewithcontactsViewRow](_path, "name", None, None, x => x.name, (row, value) => row.copy(name = value))
      override def contacttype = Field[Name, VstorewithcontactsViewRow](_path, "contacttype", None, None, x => x.contacttype, (row, value) => row.copy(contacttype = value))
      override def title = OptField[/* max 8 chars */ String, VstorewithcontactsViewRow](_path, "title", None, None, x => x.title, (row, value) => row.copy(title = value))
      override def firstname = Field[/* user-picked */ FirstName, VstorewithcontactsViewRow](_path, "firstname", None, None, x => x.firstname, (row, value) => row.copy(firstname = value))
      override def middlename = OptField[Name, VstorewithcontactsViewRow](_path, "middlename", None, None, x => x.middlename, (row, value) => row.copy(middlename = value))
      override def lastname = Field[Name, VstorewithcontactsViewRow](_path, "lastname", None, None, x => x.lastname, (row, value) => row.copy(lastname = value))
      override def suffix = OptField[/* max 10 chars */ String, VstorewithcontactsViewRow](_path, "suffix", None, None, x => x.suffix, (row, value) => row.copy(suffix = value))
      override def phonenumber = OptField[Phone, VstorewithcontactsViewRow](_path, "phonenumber", None, None, x => x.phonenumber, (row, value) => row.copy(phonenumber = value))
      override def phonenumbertype = OptField[Name, VstorewithcontactsViewRow](_path, "phonenumbertype", None, None, x => x.phonenumbertype, (row, value) => row.copy(phonenumbertype = value))
      override def emailaddress = OptField[/* max 50 chars */ String, VstorewithcontactsViewRow](_path, "emailaddress", None, None, x => x.emailaddress, (row, value) => row.copy(emailaddress = value))
      override def emailpromotion = Field[Int, VstorewithcontactsViewRow](_path, "emailpromotion", None, None, x => x.emailpromotion, (row, value) => row.copy(emailpromotion = value))
    }
  
    override lazy val columns: List[FieldLikeNoHkt[?, VstorewithcontactsViewRow]] =
      List[FieldLikeNoHkt[?, VstorewithcontactsViewRow]](fields.businessentityid, fields.name, fields.contacttype, fields.title, fields.firstname, fields.middlename, fields.lastname, fields.suffix, fields.phonenumber, fields.phonenumbertype, fields.emailaddress, fields.emailpromotion)
  
    override def copy(path: List[Path]): Impl =
      new Impl(path)
  }
  
}
