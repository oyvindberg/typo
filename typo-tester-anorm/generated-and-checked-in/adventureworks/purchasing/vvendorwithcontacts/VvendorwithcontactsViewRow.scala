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
import anorm.Column
import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.OWrites
import play.api.libs.json.Reads
import play.api.libs.json.Writes
import scala.collection.immutable.ListMap
import scala.util.Try

case class VvendorwithcontactsViewRow(
  /** Points to [[vendor.VendorRow.businessentityid]] */
  businessentityid: BusinessentityId,
  /** Points to [[vendor.VendorRow.name]] */
  name: Name,
  /** Points to [[person.contacttype.ContacttypeRow.name]] */
  contacttype: Name,
  /** Points to [[person.person.PersonRow.title]] */
  title: Option[/* max 8 chars */ String],
  /** Points to [[person.person.PersonRow.firstname]] */
  firstname: /* user-picked */ FirstName,
  /** Points to [[person.person.PersonRow.middlename]] */
  middlename: Option[Name],
  /** Points to [[person.person.PersonRow.lastname]] */
  lastname: Name,
  /** Points to [[person.person.PersonRow.suffix]] */
  suffix: Option[/* max 10 chars */ String],
  /** Points to [[person.personphone.PersonphoneRow.phonenumber]] */
  phonenumber: Option[Phone],
  /** Points to [[person.phonenumbertype.PhonenumbertypeRow.name]] */
  phonenumbertype: Option[Name],
  /** Points to [[person.emailaddress.EmailaddressRow.emailaddress]] */
  emailaddress: Option[/* max 50 chars */ String],
  /** Points to [[person.person.PersonRow.emailpromotion]] */
  emailpromotion: Int
)

object VvendorwithcontactsViewRow {
  implicit lazy val reads: Reads[VvendorwithcontactsViewRow] = Reads[VvendorwithcontactsViewRow](json => JsResult.fromTry(
      Try(
        VvendorwithcontactsViewRow(
          businessentityid = json.\("businessentityid").as(BusinessentityId.reads),
          name = json.\("name").as(Name.reads),
          contacttype = json.\("contacttype").as(Name.reads),
          title = json.\("title").toOption.map(_.as(Reads.StringReads)),
          firstname = json.\("firstname").as(FirstName.reads),
          middlename = json.\("middlename").toOption.map(_.as(Name.reads)),
          lastname = json.\("lastname").as(Name.reads),
          suffix = json.\("suffix").toOption.map(_.as(Reads.StringReads)),
          phonenumber = json.\("phonenumber").toOption.map(_.as(Phone.reads)),
          phonenumbertype = json.\("phonenumbertype").toOption.map(_.as(Name.reads)),
          emailaddress = json.\("emailaddress").toOption.map(_.as(Reads.StringReads)),
          emailpromotion = json.\("emailpromotion").as(Reads.IntReads)
        )
      )
    ),
  )
  def rowParser(idx: Int): RowParser[VvendorwithcontactsViewRow] = RowParser[VvendorwithcontactsViewRow] { row =>
    Success(
      VvendorwithcontactsViewRow(
        businessentityid = row(idx + 0)(BusinessentityId.column),
        name = row(idx + 1)(Name.column),
        contacttype = row(idx + 2)(Name.column),
        title = row(idx + 3)(Column.columnToOption(Column.columnToString)),
        firstname = row(idx + 4)(/* user-picked */ FirstName.column),
        middlename = row(idx + 5)(Column.columnToOption(Name.column)),
        lastname = row(idx + 6)(Name.column),
        suffix = row(idx + 7)(Column.columnToOption(Column.columnToString)),
        phonenumber = row(idx + 8)(Column.columnToOption(Phone.column)),
        phonenumbertype = row(idx + 9)(Column.columnToOption(Name.column)),
        emailaddress = row(idx + 10)(Column.columnToOption(Column.columnToString)),
        emailpromotion = row(idx + 11)(Column.columnToInt)
      )
    )
  }
  implicit lazy val writes: OWrites[VvendorwithcontactsViewRow] = OWrites[VvendorwithcontactsViewRow](o =>
    new JsObject(ListMap[String, JsValue](
      "businessentityid" -> BusinessentityId.writes.writes(o.businessentityid),
      "name" -> Name.writes.writes(o.name),
      "contacttype" -> Name.writes.writes(o.contacttype),
      "title" -> Writes.OptionWrites(Writes.StringWrites).writes(o.title),
      "firstname" -> FirstName.writes.writes(o.firstname),
      "middlename" -> Writes.OptionWrites(Name.writes).writes(o.middlename),
      "lastname" -> Name.writes.writes(o.lastname),
      "suffix" -> Writes.OptionWrites(Writes.StringWrites).writes(o.suffix),
      "phonenumber" -> Writes.OptionWrites(Phone.writes).writes(o.phonenumber),
      "phonenumbertype" -> Writes.OptionWrites(Name.writes).writes(o.phonenumbertype),
      "emailaddress" -> Writes.OptionWrites(Writes.StringWrites).writes(o.emailaddress),
      "emailpromotion" -> Writes.IntWrites.writes(o.emailpromotion)
    ))
  )
}