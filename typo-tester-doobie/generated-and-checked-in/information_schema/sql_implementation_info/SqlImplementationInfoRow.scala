/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package sql_implementation_info

import adventureworks.information_schema.CardinalNumber
import adventureworks.information_schema.CharacterData
import doobie.Get
import doobie.Read
import doobie.enumerated.Nullability
import io.circe.Decoder
import io.circe.Encoder
import io.circe.HCursor
import io.circe.Json
import java.sql.ResultSet

case class SqlImplementationInfoRow(
  implementationInfoId: Option[CharacterData],
  implementationInfoName: Option[CharacterData],
  integerValue: Option[CardinalNumber],
  characterValue: Option[CharacterData],
  comments: Option[CharacterData]
)

object SqlImplementationInfoRow {
  implicit val decoder: Decoder[SqlImplementationInfoRow] =
    (c: HCursor) =>
      for {
        implementationInfoId <- c.downField("implementation_info_id").as[Option[CharacterData]]
        implementationInfoName <- c.downField("implementation_info_name").as[Option[CharacterData]]
        integerValue <- c.downField("integer_value").as[Option[CardinalNumber]]
        characterValue <- c.downField("character_value").as[Option[CharacterData]]
        comments <- c.downField("comments").as[Option[CharacterData]]
      } yield SqlImplementationInfoRow(implementationInfoId, implementationInfoName, integerValue, characterValue, comments)
  implicit val encoder: Encoder[SqlImplementationInfoRow] = {
    import io.circe.syntax._
    row =>
      Json.obj(
        "implementation_info_id" := row.implementationInfoId,
        "implementation_info_name" := row.implementationInfoName,
        "integer_value" := row.integerValue,
        "character_value" := row.characterValue,
        "comments" := row.comments
      )}
  implicit val read: Read[SqlImplementationInfoRow] =
    new Read[SqlImplementationInfoRow](
      gets = List(
        (Get[CharacterData], Nullability.Nullable),
        (Get[CharacterData], Nullability.Nullable),
        (Get[CardinalNumber], Nullability.Nullable),
        (Get[CharacterData], Nullability.Nullable),
        (Get[CharacterData], Nullability.Nullable)
      ),
      unsafeGet = (rs: ResultSet, i: Int) => SqlImplementationInfoRow(
        implementationInfoId = Get[CharacterData].unsafeGetNullable(rs, i + 0),
        implementationInfoName = Get[CharacterData].unsafeGetNullable(rs, i + 1),
        integerValue = Get[CardinalNumber].unsafeGetNullable(rs, i + 2),
        characterValue = Get[CharacterData].unsafeGetNullable(rs, i + 3),
        comments = Get[CharacterData].unsafeGetNullable(rs, i + 4)
      )
    )
  

}