/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package purchasing
package vendor

import adventureworks.person.businessentity.BusinessentityId
import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class VendorRow(
  /** Points to [[person.businessentity.BusinessentityRow.businessentityid]] */
  businessentityid: BusinessentityId /* {"table_catalog":"Adventureworks","table_schema":"purchasing","table_name":"vendor","column_name":"businessentityid","ordinal_position":1,"is_nullable":"NO","data_type":"integer","numeric_precision":32,"numeric_precision_radix":2,"numeric_scale":0,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"int4","dtd_identifier":"1","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  accountnumber: String /* {"table_catalog":"Adventureworks","table_schema":"purchasing","table_name":"vendor","column_name":"accountnumber","ordinal_position":2,"is_nullable":"NO","data_type":"character varying","character_maximum_length":15,"character_octet_length":60,"domain_catalog":"Adventureworks","domain_schema":"public","domain_name":"AccountNumber","udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"varchar","dtd_identifier":"2","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  name: String /* {"table_catalog":"Adventureworks","table_schema":"purchasing","table_name":"vendor","column_name":"name","ordinal_position":3,"is_nullable":"NO","data_type":"character varying","character_maximum_length":50,"character_octet_length":200,"domain_catalog":"Adventureworks","domain_schema":"public","domain_name":"Name","udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"varchar","dtd_identifier":"3","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  creditrating: Int /* {"table_catalog":"Adventureworks","table_schema":"purchasing","table_name":"vendor","column_name":"creditrating","ordinal_position":4,"is_nullable":"NO","data_type":"smallint","numeric_precision":16,"numeric_precision_radix":2,"numeric_scale":0,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"int2","dtd_identifier":"4","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  preferredvendorstatus: Boolean /* {"table_catalog":"Adventureworks","table_schema":"purchasing","table_name":"vendor","column_name":"preferredvendorstatus","ordinal_position":5,"column_default":"true","is_nullable":"NO","data_type":"boolean","domain_catalog":"Adventureworks","domain_schema":"public","domain_name":"Flag","udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"bool","dtd_identifier":"5","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  activeflag: Boolean /* {"table_catalog":"Adventureworks","table_schema":"purchasing","table_name":"vendor","column_name":"activeflag","ordinal_position":6,"column_default":"true","is_nullable":"NO","data_type":"boolean","domain_catalog":"Adventureworks","domain_schema":"public","domain_name":"Flag","udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"bool","dtd_identifier":"6","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  purchasingwebserviceurl: Option[String] /* {"table_catalog":"Adventureworks","table_schema":"purchasing","table_name":"vendor","column_name":"purchasingwebserviceurl","ordinal_position":7,"is_nullable":"YES","data_type":"character varying","character_maximum_length":1024,"character_octet_length":4096,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"varchar","dtd_identifier":"7","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  modifieddate: LocalDateTime /* {"table_catalog":"Adventureworks","table_schema":"purchasing","table_name":"vendor","column_name":"modifieddate","ordinal_position":8,"column_default":"now()","is_nullable":"NO","data_type":"timestamp without time zone","datetime_precision":6,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"timestamp","dtd_identifier":"8","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */
)

object VendorRow {
  def rowParser(prefix: String): RowParser[VendorRow] = { row =>
    Success(
      VendorRow(
        businessentityid = row[BusinessentityId](prefix + "businessentityid"),
        accountnumber = row[String](prefix + "accountnumber"),
        name = row[String](prefix + "name"),
        creditrating = row[Int](prefix + "creditrating"),
        preferredvendorstatus = row[Boolean](prefix + "preferredvendorstatus"),
        activeflag = row[Boolean](prefix + "activeflag"),
        purchasingwebserviceurl = row[Option[String]](prefix + "purchasingwebserviceurl"),
        modifieddate = row[LocalDateTime](prefix + "modifieddate")
      )
    )
  }

  implicit val oFormat: OFormat[VendorRow] = new OFormat[VendorRow]{
    override def writes(o: VendorRow): JsObject =
      Json.obj(
        "businessentityid" -> o.businessentityid,
        "accountnumber" -> o.accountnumber,
        "name" -> o.name,
        "creditrating" -> o.creditrating,
        "preferredvendorstatus" -> o.preferredvendorstatus,
        "activeflag" -> o.activeflag,
        "purchasingwebserviceurl" -> o.purchasingwebserviceurl,
        "modifieddate" -> o.modifieddate
      )
  
    override def reads(json: JsValue): JsResult[VendorRow] = {
      JsResult.fromTry(
        Try(
          VendorRow(
            businessentityid = json.\("businessentityid").as[BusinessentityId],
            accountnumber = json.\("accountnumber").as[String],
            name = json.\("name").as[String],
            creditrating = json.\("creditrating").as[Int],
            preferredvendorstatus = json.\("preferredvendorstatus").as[Boolean],
            activeflag = json.\("activeflag").as[Boolean],
            purchasingwebserviceurl = json.\("purchasingwebserviceurl").toOption.map(_.as[String]),
            modifieddate = json.\("modifieddate").as[LocalDateTime]
          )
        )
      )
    }
  }
}