/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package sql_parts

import adventureworks.information_schema.CharacterData
import adventureworks.information_schema.YesOrNo
import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class SqlPartsRow(
  featureId: Option[CharacterData],
  featureName: Option[CharacterData],
  isSupported: Option[YesOrNo],
  isVerifiedBy: Option[CharacterData],
  comments: Option[CharacterData]
)

object SqlPartsRow {
  def rowParser(idx: Int): RowParser[SqlPartsRow] =
    RowParser[SqlPartsRow] { row =>
      Success(
        SqlPartsRow(
          featureId = row[Option[CharacterData]](idx + 0),
          featureName = row[Option[CharacterData]](idx + 1),
          isSupported = row[Option[YesOrNo]](idx + 2),
          isVerifiedBy = row[Option[CharacterData]](idx + 3),
          comments = row[Option[CharacterData]](idx + 4)
        )
      )
    }
  implicit val oFormat: OFormat[SqlPartsRow] = new OFormat[SqlPartsRow]{
    override def writes(o: SqlPartsRow): JsObject =
      Json.obj(
        "feature_id" -> o.featureId,
        "feature_name" -> o.featureName,
        "is_supported" -> o.isSupported,
        "is_verified_by" -> o.isVerifiedBy,
        "comments" -> o.comments
      )
  
    override def reads(json: JsValue): JsResult[SqlPartsRow] = {
      JsResult.fromTry(
        Try(
          SqlPartsRow(
            featureId = json.\("feature_id").toOption.map(_.as[CharacterData]),
            featureName = json.\("feature_name").toOption.map(_.as[CharacterData]),
            isSupported = json.\("is_supported").toOption.map(_.as[YesOrNo]),
            isVerifiedBy = json.\("is_verified_by").toOption.map(_.as[CharacterData]),
            comments = json.\("comments").toOption.map(_.as[CharacterData])
          )
        )
      )
    }
  }
}