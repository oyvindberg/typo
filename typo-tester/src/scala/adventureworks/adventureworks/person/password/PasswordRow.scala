/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package password

import adventureworks.person.businessentity.BusinessentityId
import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import java.util.UUID
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class PasswordRow(
  /** Points to [[person.PersonRow.businessentityid]] */
  businessentityid: BusinessentityId /* {"table_catalog":"Adventureworks","table_schema":"person","table_name":"password","column_name":"businessentityid","ordinal_position":1,"is_nullable":"NO","data_type":"integer","numeric_precision":32,"numeric_precision_radix":2,"numeric_scale":0,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"int4","dtd_identifier":"1","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  passwordhash: String /* {"table_catalog":"Adventureworks","table_schema":"person","table_name":"password","column_name":"passwordhash","ordinal_position":2,"is_nullable":"NO","data_type":"character varying","character_maximum_length":128,"character_octet_length":512,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"varchar","dtd_identifier":"2","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  passwordsalt: String /* {"table_catalog":"Adventureworks","table_schema":"person","table_name":"password","column_name":"passwordsalt","ordinal_position":3,"is_nullable":"NO","data_type":"character varying","character_maximum_length":10,"character_octet_length":40,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"varchar","dtd_identifier":"3","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  rowguid: UUID /* {"table_catalog":"Adventureworks","table_schema":"person","table_name":"password","column_name":"rowguid","ordinal_position":4,"column_default":"uuid_generate_v1()","is_nullable":"NO","data_type":"uuid","udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"uuid","dtd_identifier":"4","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  modifieddate: LocalDateTime /* {"table_catalog":"Adventureworks","table_schema":"person","table_name":"password","column_name":"modifieddate","ordinal_position":5,"column_default":"now()","is_nullable":"NO","data_type":"timestamp without time zone","datetime_precision":6,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"timestamp","dtd_identifier":"5","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */
)

object PasswordRow {
  def rowParser(prefix: String): RowParser[PasswordRow] = { row =>
    Success(
      PasswordRow(
        businessentityid = row[BusinessentityId](prefix + "businessentityid"),
        passwordhash = row[String](prefix + "passwordhash"),
        passwordsalt = row[String](prefix + "passwordsalt"),
        rowguid = row[UUID](prefix + "rowguid"),
        modifieddate = row[LocalDateTime](prefix + "modifieddate")
      )
    )
  }

  implicit val oFormat: OFormat[PasswordRow] = new OFormat[PasswordRow]{
    override def writes(o: PasswordRow): JsObject =
      Json.obj(
        "businessentityid" -> o.businessentityid,
        "passwordhash" -> o.passwordhash,
        "passwordsalt" -> o.passwordsalt,
        "rowguid" -> o.rowguid,
        "modifieddate" -> o.modifieddate
      )
  
    override def reads(json: JsValue): JsResult[PasswordRow] = {
      JsResult.fromTry(
        Try(
          PasswordRow(
            businessentityid = json.\("businessentityid").as[BusinessentityId],
            passwordhash = json.\("passwordhash").as[String],
            passwordsalt = json.\("passwordsalt").as[String],
            rowguid = json.\("rowguid").as[UUID],
            modifieddate = json.\("modifieddate").as[LocalDateTime]
          )
        )
      )
    }
  }
}