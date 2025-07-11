/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person_dynamic

import adventureworks.public.Name
import adventureworks.userdefined.FirstName
import doobie.util.Read
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.Encoder

/** SQL file: person_dynamic.sql */
case class PersonDynamicSqlRow(
  /** Points to [[person.person.PersonRow.title]] */
  title: Option[/* max 8 chars */ String],
  /** Points to [[person.person.PersonRow.firstname]] */
  firstname: /* user-picked */ FirstName,
  /** Points to [[person.person.PersonRow.middlename]] */
  middlename: Option[Name],
  /** Points to [[person.person.PersonRow.lastname]] */
  lastname: Name
)

object PersonDynamicSqlRow {
  implicit lazy val decoder: Decoder[PersonDynamicSqlRow] = Decoder.forProduct4[PersonDynamicSqlRow, Option[/* max 8 chars */ String], /* user-picked */ FirstName, Option[Name], Name]("title", "firstname", "middlename", "lastname")(PersonDynamicSqlRow.apply)(Decoder.decodeOption(Decoder.decodeString), FirstName.decoder, Decoder.decodeOption(Name.decoder), Name.decoder)
  implicit lazy val encoder: Encoder[PersonDynamicSqlRow] = Encoder.forProduct4[PersonDynamicSqlRow, Option[/* max 8 chars */ String], /* user-picked */ FirstName, Option[Name], Name]("title", "firstname", "middlename", "lastname")(x => (x.title, x.firstname, x.middlename, x.lastname))(Encoder.encodeOption(Encoder.encodeString), FirstName.encoder, Encoder.encodeOption(Name.encoder), Name.encoder)
  implicit lazy val read: Read[PersonDynamicSqlRow] = new Read.CompositeOfInstances(Array(
    new Read.SingleOpt(Meta.StringMeta.get).asInstanceOf[Read[Any]],
      new Read.Single(/* user-picked */ FirstName.get).asInstanceOf[Read[Any]],
      new Read.SingleOpt(Name.get).asInstanceOf[Read[Any]],
      new Read.Single(Name.get).asInstanceOf[Read[Any]]
  ))(using scala.reflect.ClassTag.Any).map { arr =>
    PersonDynamicSqlRow(
      title = arr(0).asInstanceOf[Option[/* max 8 chars */ String]],
          firstname = arr(1).asInstanceOf[/* user-picked */ FirstName],
          middlename = arr(2).asInstanceOf[Option[Name]],
          lastname = arr(3).asInstanceOf[Name]
    )
  }
}
