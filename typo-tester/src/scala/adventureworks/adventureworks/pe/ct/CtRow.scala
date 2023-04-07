/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pe
package ct

import adventureworks.person.contacttype.ContacttypeId
import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class CtRow(
  id: Option[Int] /* {"table_catalog":"Adventureworks","table_schema":"pe","table_name":"ct","column_name":"id","ordinal_position":1,"is_nullable":"YES","data_type":"integer","numeric_precision":32,"numeric_precision_radix":2,"numeric_scale":0,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"int4","dtd_identifier":"1","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[person.contacttype.ContacttypeRow.contacttypeid]] */
  contacttypeid: Option[ContacttypeId] /* {"table_catalog":"Adventureworks","table_schema":"pe","table_name":"ct","column_name":"contacttypeid","ordinal_position":2,"is_nullable":"YES","data_type":"integer","numeric_precision":32,"numeric_precision_radix":2,"numeric_scale":0,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"int4","dtd_identifier":"2","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[person.contacttype.ContacttypeRow.name]] */
  name: Option[String] /* {"table_catalog":"Adventureworks","table_schema":"pe","table_name":"ct","column_name":"name","ordinal_position":3,"is_nullable":"YES","data_type":"character varying","character_maximum_length":50,"character_octet_length":200,"domain_catalog":"Adventureworks","domain_schema":"public","domain_name":"Name","udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"varchar","dtd_identifier":"3","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[person.contacttype.ContacttypeRow.modifieddate]] */
  modifieddate: Option[LocalDateTime] /* {"table_catalog":"Adventureworks","table_schema":"pe","table_name":"ct","column_name":"modifieddate","ordinal_position":4,"is_nullable":"YES","data_type":"timestamp without time zone","datetime_precision":6,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"timestamp","dtd_identifier":"4","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */
)

object CtRow {
  def rowParser(prefix: String): RowParser[CtRow] = { row =>
    Success(
      CtRow(
        id = row[Option[Int]](prefix + "id"),
        contacttypeid = row[Option[ContacttypeId]](prefix + "contacttypeid"),
        name = row[Option[String]](prefix + "name"),
        modifieddate = row[Option[LocalDateTime]](prefix + "modifieddate")
      )
    )
  }

  implicit val oFormat: OFormat[CtRow] = new OFormat[CtRow]{
    override def writes(o: CtRow): JsObject =
      Json.obj(
        "id" -> o.id,
        "contacttypeid" -> o.contacttypeid,
        "name" -> o.name,
        "modifieddate" -> o.modifieddate
      )
  
    override def reads(json: JsValue): JsResult[CtRow] = {
      JsResult.fromTry(
        Try(
          CtRow(
            id = json.\("id").toOption.map(_.as[Int]),
            contacttypeid = json.\("contacttypeid").toOption.map(_.as[ContacttypeId]),
            name = json.\("name").toOption.map(_.as[String]),
            modifieddate = json.\("modifieddate").toOption.map(_.as[LocalDateTime])
          )
        )
      )
    }
  }
}