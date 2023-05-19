/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package phonenumbertype

import adventureworks.Defaulted
import adventureworks.public.Name
import io.circe.Decoder
import io.circe.Encoder
import io.circe.HCursor
import io.circe.Json
import java.time.LocalDateTime

/** This class corresponds to a row in table `person.phonenumbertype` which has not been persisted yet */
case class PhonenumbertypeRowUnsaved(
  /** Name of the telephone number type */
  name: Name,
  /** Default: nextval('person.phonenumbertype_phonenumbertypeid_seq'::regclass)
      Primary key for telephone number type records. */
  phonenumbertypeid: Defaulted[PhonenumbertypeId] = Defaulted.UseDefault,
  /** Default: now() */
  modifieddate: Defaulted[LocalDateTime] = Defaulted.UseDefault
) {
  def toRow(phonenumbertypeidDefault: => PhonenumbertypeId, modifieddateDefault: => LocalDateTime): PhonenumbertypeRow =
    PhonenumbertypeRow(
      name = name,
      phonenumbertypeid = phonenumbertypeid match {
                            case Defaulted.UseDefault => phonenumbertypeidDefault
                            case Defaulted.Provided(value) => value
                          },
      modifieddate = modifieddate match {
                       case Defaulted.UseDefault => modifieddateDefault
                       case Defaulted.Provided(value) => value
                     }
    )
}
object PhonenumbertypeRowUnsaved {
  implicit val decoder: Decoder[PhonenumbertypeRowUnsaved] =
    (c: HCursor) =>
      for {
        name <- c.downField("name").as[Name]
        phonenumbertypeid <- c.downField("phonenumbertypeid").as[Defaulted[PhonenumbertypeId]]
        modifieddate <- c.downField("modifieddate").as[Defaulted[LocalDateTime]]
      } yield PhonenumbertypeRowUnsaved(name, phonenumbertypeid, modifieddate)
  implicit val encoder: Encoder[PhonenumbertypeRowUnsaved] = {
    import io.circe.syntax._
    row =>
      Json.obj(
        "name" := row.name,
        "phonenumbertypeid" := row.phonenumbertypeid,
        "modifieddate" := row.modifieddate
      )}
}