/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package humanresources
package employee

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDate
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoShort
import adventureworks.customtypes.TypoUUID
import adventureworks.person.businessentity.BusinessentityId
import adventureworks.public.Flag
import zio.json.JsonDecoder
import zio.json.JsonEncoder
import zio.json.ast.Json
import zio.json.internal.Write

/** This class corresponds to a row in table `humanresources.employee` which has not been persisted yet */
case class EmployeeRowUnsaved(
  /** Primary key for Employee records.  Foreign key to BusinessEntity.BusinessEntityID.
      Points to [[person.person.PersonRow.businessentityid]] */
  businessentityid: BusinessentityId,
  /** Unique national identification number such as a social security number. */
  nationalidnumber: /* max 15 chars */ String,
  /** Network login. */
  loginid: /* max 256 chars */ String,
  /** Work title such as Buyer or Sales Representative. */
  jobtitle: /* max 50 chars */ String,
  /** Date of birth.
      Constraint CK_Employee_BirthDate affecting columns birthdate:  (((birthdate >= '1930-01-01'::date) AND (birthdate <= (now() - '18 years'::interval)))) */
  birthdate: TypoLocalDate,
  /** M = Married, S = Single
      Constraint CK_Employee_MaritalStatus affecting columns maritalstatus:  ((upper((maritalstatus)::text) = ANY (ARRAY['M'::text, 'S'::text]))) */
  maritalstatus: /* bpchar, max 1 chars */ String,
  /** M = Male, F = Female
      Constraint CK_Employee_Gender affecting columns gender:  ((upper((gender)::text) = ANY (ARRAY['M'::text, 'F'::text]))) */
  gender: /* bpchar, max 1 chars */ String,
  /** Employee hired on this date.
      Constraint CK_Employee_HireDate affecting columns hiredate:  (((hiredate >= '1996-07-01'::date) AND (hiredate <= (now() + '1 day'::interval)))) */
  hiredate: TypoLocalDate,
  /** Default: true
      Job classification. 0 = Hourly, not exempt from collective bargaining. 1 = Salaried, exempt from collective bargaining. */
  salariedflag: Defaulted[Flag] = Defaulted.UseDefault,
  /** Default: 0
      Number of available vacation hours.
      Constraint CK_Employee_VacationHours affecting columns vacationhours:  (((vacationhours >= '-40'::integer) AND (vacationhours <= 240))) */
  vacationhours: Defaulted[TypoShort] = Defaulted.UseDefault,
  /** Default: 0
      Number of available sick leave hours.
      Constraint CK_Employee_SickLeaveHours affecting columns sickleavehours:  (((sickleavehours >= 0) AND (sickleavehours <= 120))) */
  sickleavehours: Defaulted[TypoShort] = Defaulted.UseDefault,
  /** Default: true
      0 = Inactive, 1 = Active */
  currentflag: Defaulted[Flag] = Defaulted.UseDefault,
  /** Default: uuid_generate_v1() */
  rowguid: Defaulted[TypoUUID] = Defaulted.UseDefault,
  /** Default: now() */
  modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.UseDefault,
  /** Default: '/'::character varying
      Where the employee is located in corporate hierarchy. */
  organizationnode: Defaulted[Option[String]] = Defaulted.UseDefault
) {
  def toRow(salariedflagDefault: => Flag, vacationhoursDefault: => TypoShort, sickleavehoursDefault: => TypoShort, currentflagDefault: => Flag, rowguidDefault: => TypoUUID, modifieddateDefault: => TypoLocalDateTime, organizationnodeDefault: => Option[String]): EmployeeRow =
    EmployeeRow(
      businessentityid = businessentityid,
      nationalidnumber = nationalidnumber,
      loginid = loginid,
      jobtitle = jobtitle,
      birthdate = birthdate,
      maritalstatus = maritalstatus,
      gender = gender,
      hiredate = hiredate,
      salariedflag = salariedflag match {
                       case Defaulted.UseDefault => salariedflagDefault
                       case Defaulted.Provided(value) => value
                     },
      vacationhours = vacationhours match {
                        case Defaulted.UseDefault => vacationhoursDefault
                        case Defaulted.Provided(value) => value
                      },
      sickleavehours = sickleavehours match {
                         case Defaulted.UseDefault => sickleavehoursDefault
                         case Defaulted.Provided(value) => value
                       },
      currentflag = currentflag match {
                      case Defaulted.UseDefault => currentflagDefault
                      case Defaulted.Provided(value) => value
                    },
      rowguid = rowguid match {
                  case Defaulted.UseDefault => rowguidDefault
                  case Defaulted.Provided(value) => value
                },
      modifieddate = modifieddate match {
                       case Defaulted.UseDefault => modifieddateDefault
                       case Defaulted.Provided(value) => value
                     },
      organizationnode = organizationnode match {
                           case Defaulted.UseDefault => organizationnodeDefault
                           case Defaulted.Provided(value) => value
                         }
    )
}
object EmployeeRowUnsaved {
  implicit lazy val jsonDecoder: JsonDecoder[EmployeeRowUnsaved] = JsonDecoder[Json.Obj].mapOrFail { jsonObj =>
    val businessentityid = jsonObj.get("businessentityid").toRight("Missing field 'businessentityid'").flatMap(_.as(BusinessentityId.jsonDecoder))
    val nationalidnumber = jsonObj.get("nationalidnumber").toRight("Missing field 'nationalidnumber'").flatMap(_.as(JsonDecoder.string))
    val loginid = jsonObj.get("loginid").toRight("Missing field 'loginid'").flatMap(_.as(JsonDecoder.string))
    val jobtitle = jsonObj.get("jobtitle").toRight("Missing field 'jobtitle'").flatMap(_.as(JsonDecoder.string))
    val birthdate = jsonObj.get("birthdate").toRight("Missing field 'birthdate'").flatMap(_.as(TypoLocalDate.jsonDecoder))
    val maritalstatus = jsonObj.get("maritalstatus").toRight("Missing field 'maritalstatus'").flatMap(_.as(JsonDecoder.string))
    val gender = jsonObj.get("gender").toRight("Missing field 'gender'").flatMap(_.as(JsonDecoder.string))
    val hiredate = jsonObj.get("hiredate").toRight("Missing field 'hiredate'").flatMap(_.as(TypoLocalDate.jsonDecoder))
    val salariedflag = jsonObj.get("salariedflag").toRight("Missing field 'salariedflag'").flatMap(_.as(Defaulted.jsonDecoder(Flag.jsonDecoder)))
    val vacationhours = jsonObj.get("vacationhours").toRight("Missing field 'vacationhours'").flatMap(_.as(Defaulted.jsonDecoder(TypoShort.jsonDecoder)))
    val sickleavehours = jsonObj.get("sickleavehours").toRight("Missing field 'sickleavehours'").flatMap(_.as(Defaulted.jsonDecoder(TypoShort.jsonDecoder)))
    val currentflag = jsonObj.get("currentflag").toRight("Missing field 'currentflag'").flatMap(_.as(Defaulted.jsonDecoder(Flag.jsonDecoder)))
    val rowguid = jsonObj.get("rowguid").toRight("Missing field 'rowguid'").flatMap(_.as(Defaulted.jsonDecoder(TypoUUID.jsonDecoder)))
    val modifieddate = jsonObj.get("modifieddate").toRight("Missing field 'modifieddate'").flatMap(_.as(Defaulted.jsonDecoder(TypoLocalDateTime.jsonDecoder)))
    val organizationnode = jsonObj.get("organizationnode").toRight("Missing field 'organizationnode'").flatMap(_.as(Defaulted.jsonDecoder(JsonDecoder.option(using JsonDecoder.string))))
    if (businessentityid.isRight && nationalidnumber.isRight && loginid.isRight && jobtitle.isRight && birthdate.isRight && maritalstatus.isRight && gender.isRight && hiredate.isRight && salariedflag.isRight && vacationhours.isRight && sickleavehours.isRight && currentflag.isRight && rowguid.isRight && modifieddate.isRight && organizationnode.isRight)
      Right(EmployeeRowUnsaved(businessentityid = businessentityid.toOption.get, nationalidnumber = nationalidnumber.toOption.get, loginid = loginid.toOption.get, jobtitle = jobtitle.toOption.get, birthdate = birthdate.toOption.get, maritalstatus = maritalstatus.toOption.get, gender = gender.toOption.get, hiredate = hiredate.toOption.get, salariedflag = salariedflag.toOption.get, vacationhours = vacationhours.toOption.get, sickleavehours = sickleavehours.toOption.get, currentflag = currentflag.toOption.get, rowguid = rowguid.toOption.get, modifieddate = modifieddate.toOption.get, organizationnode = organizationnode.toOption.get))
    else Left(List[Either[String, Any]](businessentityid, nationalidnumber, loginid, jobtitle, birthdate, maritalstatus, gender, hiredate, salariedflag, vacationhours, sickleavehours, currentflag, rowguid, modifieddate, organizationnode).flatMap(_.left.toOption).mkString(", "))
  }
  implicit lazy val jsonEncoder: JsonEncoder[EmployeeRowUnsaved] = new JsonEncoder[EmployeeRowUnsaved] {
    override def unsafeEncode(a: EmployeeRowUnsaved, indent: Option[Int], out: Write): Unit = {
      out.write("{")
      out.write(""""businessentityid":""")
      BusinessentityId.jsonEncoder.unsafeEncode(a.businessentityid, indent, out)
      out.write(",")
      out.write(""""nationalidnumber":""")
      JsonEncoder.string.unsafeEncode(a.nationalidnumber, indent, out)
      out.write(",")
      out.write(""""loginid":""")
      JsonEncoder.string.unsafeEncode(a.loginid, indent, out)
      out.write(",")
      out.write(""""jobtitle":""")
      JsonEncoder.string.unsafeEncode(a.jobtitle, indent, out)
      out.write(",")
      out.write(""""birthdate":""")
      TypoLocalDate.jsonEncoder.unsafeEncode(a.birthdate, indent, out)
      out.write(",")
      out.write(""""maritalstatus":""")
      JsonEncoder.string.unsafeEncode(a.maritalstatus, indent, out)
      out.write(",")
      out.write(""""gender":""")
      JsonEncoder.string.unsafeEncode(a.gender, indent, out)
      out.write(",")
      out.write(""""hiredate":""")
      TypoLocalDate.jsonEncoder.unsafeEncode(a.hiredate, indent, out)
      out.write(",")
      out.write(""""salariedflag":""")
      Defaulted.jsonEncoder(Flag.jsonEncoder).unsafeEncode(a.salariedflag, indent, out)
      out.write(",")
      out.write(""""vacationhours":""")
      Defaulted.jsonEncoder(TypoShort.jsonEncoder).unsafeEncode(a.vacationhours, indent, out)
      out.write(",")
      out.write(""""sickleavehours":""")
      Defaulted.jsonEncoder(TypoShort.jsonEncoder).unsafeEncode(a.sickleavehours, indent, out)
      out.write(",")
      out.write(""""currentflag":""")
      Defaulted.jsonEncoder(Flag.jsonEncoder).unsafeEncode(a.currentflag, indent, out)
      out.write(",")
      out.write(""""rowguid":""")
      Defaulted.jsonEncoder(TypoUUID.jsonEncoder).unsafeEncode(a.rowguid, indent, out)
      out.write(",")
      out.write(""""modifieddate":""")
      Defaulted.jsonEncoder(TypoLocalDateTime.jsonEncoder).unsafeEncode(a.modifieddate, indent, out)
      out.write(",")
      out.write(""""organizationnode":""")
      Defaulted.jsonEncoder(JsonEncoder.option(using JsonEncoder.string)).unsafeEncode(a.organizationnode, indent, out)
      out.write("}")
    }
  }
  implicit lazy val text: Text[EmployeeRowUnsaved] = Text.instance[EmployeeRowUnsaved]{ (row, sb) =>
    BusinessentityId.text.unsafeEncode(row.businessentityid, sb)
    sb.append(Text.DELIMETER)
    Text.stringInstance.unsafeEncode(row.nationalidnumber, sb)
    sb.append(Text.DELIMETER)
    Text.stringInstance.unsafeEncode(row.loginid, sb)
    sb.append(Text.DELIMETER)
    Text.stringInstance.unsafeEncode(row.jobtitle, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDate.text.unsafeEncode(row.birthdate, sb)
    sb.append(Text.DELIMETER)
    Text.stringInstance.unsafeEncode(row.maritalstatus, sb)
    sb.append(Text.DELIMETER)
    Text.stringInstance.unsafeEncode(row.gender, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDate.text.unsafeEncode(row.hiredate, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(Flag.text).unsafeEncode(row.salariedflag, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(TypoShort.text).unsafeEncode(row.vacationhours, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(TypoShort.text).unsafeEncode(row.sickleavehours, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(Flag.text).unsafeEncode(row.currentflag, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(TypoUUID.text).unsafeEncode(row.rowguid, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(TypoLocalDateTime.text).unsafeEncode(row.modifieddate, sb)
    sb.append(Text.DELIMETER)
    Defaulted.text(Text.option(Text.stringInstance)).unsafeEncode(row.organizationnode, sb)
  }
}
