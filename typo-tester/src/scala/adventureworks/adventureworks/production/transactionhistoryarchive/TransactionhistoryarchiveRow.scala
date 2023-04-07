/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package production
package transactionhistoryarchive

import anorm.RowParser
import anorm.Success
import java.time.LocalDateTime
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class TransactionhistoryarchiveRow(
  transactionid: TransactionhistoryarchiveId /* {"table_catalog":"Adventureworks","table_schema":"production","table_name":"transactionhistoryarchive","column_name":"transactionid","ordinal_position":1,"is_nullable":"NO","data_type":"integer","numeric_precision":32,"numeric_precision_radix":2,"numeric_scale":0,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"int4","dtd_identifier":"1","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  productid: Int /* {"table_catalog":"Adventureworks","table_schema":"production","table_name":"transactionhistoryarchive","column_name":"productid","ordinal_position":2,"is_nullable":"NO","data_type":"integer","numeric_precision":32,"numeric_precision_radix":2,"numeric_scale":0,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"int4","dtd_identifier":"2","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  referenceorderid: Int /* {"table_catalog":"Adventureworks","table_schema":"production","table_name":"transactionhistoryarchive","column_name":"referenceorderid","ordinal_position":3,"is_nullable":"NO","data_type":"integer","numeric_precision":32,"numeric_precision_radix":2,"numeric_scale":0,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"int4","dtd_identifier":"3","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  referenceorderlineid: Int /* {"table_catalog":"Adventureworks","table_schema":"production","table_name":"transactionhistoryarchive","column_name":"referenceorderlineid","ordinal_position":4,"column_default":"0","is_nullable":"NO","data_type":"integer","numeric_precision":32,"numeric_precision_radix":2,"numeric_scale":0,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"int4","dtd_identifier":"4","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  transactiondate: LocalDateTime /* {"table_catalog":"Adventureworks","table_schema":"production","table_name":"transactionhistoryarchive","column_name":"transactiondate","ordinal_position":5,"column_default":"now()","is_nullable":"NO","data_type":"timestamp without time zone","datetime_precision":6,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"timestamp","dtd_identifier":"5","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  transactiontype: String /* {"table_catalog":"Adventureworks","table_schema":"production","table_name":"transactionhistoryarchive","column_name":"transactiontype","ordinal_position":6,"is_nullable":"NO","data_type":"character","character_maximum_length":1,"character_octet_length":4,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"bpchar","dtd_identifier":"6","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  quantity: Int /* {"table_catalog":"Adventureworks","table_schema":"production","table_name":"transactionhistoryarchive","column_name":"quantity","ordinal_position":7,"is_nullable":"NO","data_type":"integer","numeric_precision":32,"numeric_precision_radix":2,"numeric_scale":0,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"int4","dtd_identifier":"7","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  actualcost: BigDecimal /* {"table_catalog":"Adventureworks","table_schema":"production","table_name":"transactionhistoryarchive","column_name":"actualcost","ordinal_position":8,"is_nullable":"NO","data_type":"numeric","numeric_precision_radix":10,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"numeric","dtd_identifier":"8","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  modifieddate: LocalDateTime /* {"table_catalog":"Adventureworks","table_schema":"production","table_name":"transactionhistoryarchive","column_name":"modifieddate","ordinal_position":9,"column_default":"now()","is_nullable":"NO","data_type":"timestamp without time zone","datetime_precision":6,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"timestamp","dtd_identifier":"9","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */
)

object TransactionhistoryarchiveRow {
  def rowParser(prefix: String): RowParser[TransactionhistoryarchiveRow] = { row =>
    Success(
      TransactionhistoryarchiveRow(
        transactionid = row[TransactionhistoryarchiveId](prefix + "transactionid"),
        productid = row[Int](prefix + "productid"),
        referenceorderid = row[Int](prefix + "referenceorderid"),
        referenceorderlineid = row[Int](prefix + "referenceorderlineid"),
        transactiondate = row[LocalDateTime](prefix + "transactiondate"),
        transactiontype = row[String](prefix + "transactiontype"),
        quantity = row[Int](prefix + "quantity"),
        actualcost = row[BigDecimal](prefix + "actualcost"),
        modifieddate = row[LocalDateTime](prefix + "modifieddate")
      )
    )
  }

  implicit val oFormat: OFormat[TransactionhistoryarchiveRow] = new OFormat[TransactionhistoryarchiveRow]{
    override def writes(o: TransactionhistoryarchiveRow): JsObject =
      Json.obj(
        "transactionid" -> o.transactionid,
        "productid" -> o.productid,
        "referenceorderid" -> o.referenceorderid,
        "referenceorderlineid" -> o.referenceorderlineid,
        "transactiondate" -> o.transactiondate,
        "transactiontype" -> o.transactiontype,
        "quantity" -> o.quantity,
        "actualcost" -> o.actualcost,
        "modifieddate" -> o.modifieddate
      )
  
    override def reads(json: JsValue): JsResult[TransactionhistoryarchiveRow] = {
      JsResult.fromTry(
        Try(
          TransactionhistoryarchiveRow(
            transactionid = json.\("transactionid").as[TransactionhistoryarchiveId],
            productid = json.\("productid").as[Int],
            referenceorderid = json.\("referenceorderid").as[Int],
            referenceorderlineid = json.\("referenceorderlineid").as[Int],
            transactiondate = json.\("transactiondate").as[LocalDateTime],
            transactiontype = json.\("transactiontype").as[String],
            quantity = json.\("quantity").as[Int],
            actualcost = json.\("actualcost").as[BigDecimal],
            modifieddate = json.\("modifieddate").as[LocalDateTime]
          )
        )
      )
    }
  }
}