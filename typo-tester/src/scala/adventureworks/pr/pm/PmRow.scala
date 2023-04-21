/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package pm

import adventureworks.production.productmodel.ProductmodelId
import adventureworks.public.Name
import java.time.LocalDateTime
import java.util.UUID
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PmRow(
  id: Option[Int],
  /** Points to [[production.productmodel.ProductmodelRow.productmodelid]] */
  productmodelid: Option[ProductmodelId],
  /** Points to [[production.productmodel.ProductmodelRow.name]] */
  name: Option[Name],
  /** Points to [[production.productmodel.ProductmodelRow.catalogdescription]] */
  catalogdescription: Option[/* xml */ String],
  /** Points to [[production.productmodel.ProductmodelRow.instructions]] */
  instructions: Option[/* xml */ String],
  /** Points to [[production.productmodel.ProductmodelRow.rowguid]] */
  rowguid: Option[UUID],
  /** Points to [[production.productmodel.ProductmodelRow.modifieddate]] */
  modifieddate: Option[LocalDateTime]
)

object PmRow {
  implicit val oFormat: OFormat[PmRow] = new OFormat[PmRow]{
    override def writes(o: PmRow): JsObject =
      Json.obj(
        "id" -> o.id,
        "productmodelid" -> o.productmodelid,
        "name" -> o.name,
        "catalogdescription" -> o.catalogdescription,
        "instructions" -> o.instructions,
        "rowguid" -> o.rowguid,
        "modifieddate" -> o.modifieddate
      )
  
    override def reads(json: JsValue): JsResult[PmRow] = {
      JsResult.fromTry(
        Try(
          PmRow(
            id = json.\("id").toOption.map(_.as[Int]),
            productmodelid = json.\("productmodelid").toOption.map(_.as[ProductmodelId]),
            name = json.\("name").toOption.map(_.as[Name]),
            catalogdescription = json.\("catalogdescription").toOption.map(_.as[/* xml */ String]),
            instructions = json.\("instructions").toOption.map(_.as[/* xml */ String]),
            rowguid = json.\("rowguid").toOption.map(_.as[UUID]),
            modifieddate = json.\("modifieddate").toOption.map(_.as[LocalDateTime])
          )
        )
      )
    }
  }
}