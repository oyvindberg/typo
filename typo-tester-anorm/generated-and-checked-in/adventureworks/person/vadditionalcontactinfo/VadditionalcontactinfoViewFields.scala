/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package vadditionalcontactinfo

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoUUID
import adventureworks.customtypes.TypoXml
import adventureworks.person.businessentity.BusinessentityId
import adventureworks.public.Name
import adventureworks.userdefined.FirstName
import typo.dsl.Path
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.OptField
import typo.dsl.Structure.Relation

trait VadditionalcontactinfoViewFields {
  def businessentityid: Field[BusinessentityId, VadditionalcontactinfoViewRow]
  def firstname: Field[/* user-picked */ FirstName, VadditionalcontactinfoViewRow]
  def middlename: OptField[Name, VadditionalcontactinfoViewRow]
  def lastname: Field[Name, VadditionalcontactinfoViewRow]
  def telephonenumber: OptField[TypoXml, VadditionalcontactinfoViewRow]
  def telephonespecialinstructions: OptField[String, VadditionalcontactinfoViewRow]
  def street: OptField[TypoXml, VadditionalcontactinfoViewRow]
  def city: OptField[TypoXml, VadditionalcontactinfoViewRow]
  def stateprovince: OptField[TypoXml, VadditionalcontactinfoViewRow]
  def postalcode: OptField[TypoXml, VadditionalcontactinfoViewRow]
  def countryregion: OptField[TypoXml, VadditionalcontactinfoViewRow]
  def homeaddressspecialinstructions: OptField[TypoXml, VadditionalcontactinfoViewRow]
  def emailaddress: OptField[TypoXml, VadditionalcontactinfoViewRow]
  def emailspecialinstructions: OptField[String, VadditionalcontactinfoViewRow]
  def emailtelephonenumber: OptField[TypoXml, VadditionalcontactinfoViewRow]
  def rowguid: Field[TypoUUID, VadditionalcontactinfoViewRow]
  def modifieddate: Field[TypoLocalDateTime, VadditionalcontactinfoViewRow]
}

object VadditionalcontactinfoViewFields {
  lazy val structure: Relation[VadditionalcontactinfoViewFields, VadditionalcontactinfoViewRow] =
    new Impl(Nil)
    
  private final class Impl(val _path: List[Path])
    extends Relation[VadditionalcontactinfoViewFields, VadditionalcontactinfoViewRow] {
  
    override lazy val fields: VadditionalcontactinfoViewFields = new VadditionalcontactinfoViewFields {
      override def businessentityid = Field[BusinessentityId, VadditionalcontactinfoViewRow](_path, "businessentityid", None, None, x => x.businessentityid, (row, value) => row.copy(businessentityid = value))
      override def firstname = Field[/* user-picked */ FirstName, VadditionalcontactinfoViewRow](_path, "firstname", None, None, x => x.firstname, (row, value) => row.copy(firstname = value))
      override def middlename = OptField[Name, VadditionalcontactinfoViewRow](_path, "middlename", None, None, x => x.middlename, (row, value) => row.copy(middlename = value))
      override def lastname = Field[Name, VadditionalcontactinfoViewRow](_path, "lastname", None, None, x => x.lastname, (row, value) => row.copy(lastname = value))
      override def telephonenumber = OptField[TypoXml, VadditionalcontactinfoViewRow](_path, "telephonenumber", None, None, x => x.telephonenumber, (row, value) => row.copy(telephonenumber = value))
      override def telephonespecialinstructions = OptField[String, VadditionalcontactinfoViewRow](_path, "telephonespecialinstructions", None, None, x => x.telephonespecialinstructions, (row, value) => row.copy(telephonespecialinstructions = value))
      override def street = OptField[TypoXml, VadditionalcontactinfoViewRow](_path, "street", None, None, x => x.street, (row, value) => row.copy(street = value))
      override def city = OptField[TypoXml, VadditionalcontactinfoViewRow](_path, "city", None, None, x => x.city, (row, value) => row.copy(city = value))
      override def stateprovince = OptField[TypoXml, VadditionalcontactinfoViewRow](_path, "stateprovince", None, None, x => x.stateprovince, (row, value) => row.copy(stateprovince = value))
      override def postalcode = OptField[TypoXml, VadditionalcontactinfoViewRow](_path, "postalcode", None, None, x => x.postalcode, (row, value) => row.copy(postalcode = value))
      override def countryregion = OptField[TypoXml, VadditionalcontactinfoViewRow](_path, "countryregion", None, None, x => x.countryregion, (row, value) => row.copy(countryregion = value))
      override def homeaddressspecialinstructions = OptField[TypoXml, VadditionalcontactinfoViewRow](_path, "homeaddressspecialinstructions", None, None, x => x.homeaddressspecialinstructions, (row, value) => row.copy(homeaddressspecialinstructions = value))
      override def emailaddress = OptField[TypoXml, VadditionalcontactinfoViewRow](_path, "emailaddress", None, None, x => x.emailaddress, (row, value) => row.copy(emailaddress = value))
      override def emailspecialinstructions = OptField[String, VadditionalcontactinfoViewRow](_path, "emailspecialinstructions", None, None, x => x.emailspecialinstructions, (row, value) => row.copy(emailspecialinstructions = value))
      override def emailtelephonenumber = OptField[TypoXml, VadditionalcontactinfoViewRow](_path, "emailtelephonenumber", None, None, x => x.emailtelephonenumber, (row, value) => row.copy(emailtelephonenumber = value))
      override def rowguid = Field[TypoUUID, VadditionalcontactinfoViewRow](_path, "rowguid", None, None, x => x.rowguid, (row, value) => row.copy(rowguid = value))
      override def modifieddate = Field[TypoLocalDateTime, VadditionalcontactinfoViewRow](_path, "modifieddate", Some("text"), None, x => x.modifieddate, (row, value) => row.copy(modifieddate = value))
    }
  
    override lazy val columns: List[FieldLikeNoHkt[?, VadditionalcontactinfoViewRow]] =
      List[FieldLikeNoHkt[?, VadditionalcontactinfoViewRow]](fields.businessentityid, fields.firstname, fields.middlename, fields.lastname, fields.telephonenumber, fields.telephonespecialinstructions, fields.street, fields.city, fields.stateprovince, fields.postalcode, fields.countryregion, fields.homeaddressspecialinstructions, fields.emailaddress, fields.emailspecialinstructions, fields.emailtelephonenumber, fields.rowguid, fields.modifieddate)
  
    override def copy(path: List[Path]): Impl =
      new Impl(path)
  }
  
}
