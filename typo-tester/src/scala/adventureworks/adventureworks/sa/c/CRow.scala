/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package c

import adventureworks.person.businessentity.BusinessentityId
import adventureworks.sales.customer.CustomerId
import adventureworks.sales.salesterritory.SalesterritoryId
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

case class CRow(
  id: Option[Int] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"c","column_name":"id","ordinal_position":1,"is_nullable":"YES","data_type":"integer","numeric_precision":32,"numeric_precision_radix":2,"numeric_scale":0,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"int4","dtd_identifier":"1","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[sales.customer.CustomerRow.customerid]] */
  customerid: Option[CustomerId] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"c","column_name":"customerid","ordinal_position":2,"is_nullable":"YES","data_type":"integer","numeric_precision":32,"numeric_precision_radix":2,"numeric_scale":0,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"int4","dtd_identifier":"2","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[sales.customer.CustomerRow.personid]] */
  personid: Option[BusinessentityId] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"c","column_name":"personid","ordinal_position":3,"is_nullable":"YES","data_type":"integer","numeric_precision":32,"numeric_precision_radix":2,"numeric_scale":0,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"int4","dtd_identifier":"3","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[sales.customer.CustomerRow.storeid]] */
  storeid: Option[BusinessentityId] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"c","column_name":"storeid","ordinal_position":4,"is_nullable":"YES","data_type":"integer","numeric_precision":32,"numeric_precision_radix":2,"numeric_scale":0,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"int4","dtd_identifier":"4","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[sales.customer.CustomerRow.territoryid]] */
  territoryid: Option[SalesterritoryId] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"c","column_name":"territoryid","ordinal_position":5,"is_nullable":"YES","data_type":"integer","numeric_precision":32,"numeric_precision_radix":2,"numeric_scale":0,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"int4","dtd_identifier":"5","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[sales.customer.CustomerRow.rowguid]] */
  rowguid: Option[UUID] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"c","column_name":"rowguid","ordinal_position":6,"is_nullable":"YES","data_type":"uuid","udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"uuid","dtd_identifier":"6","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[sales.customer.CustomerRow.modifieddate]] */
  modifieddate: Option[LocalDateTime] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"c","column_name":"modifieddate","ordinal_position":7,"is_nullable":"YES","data_type":"timestamp without time zone","datetime_precision":6,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"timestamp","dtd_identifier":"7","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */
)

object CRow {
  def rowParser(prefix: String): RowParser[CRow] = { row =>
    Success(
      CRow(
        id = row[Option[Int]](prefix + "id"),
        customerid = row[Option[CustomerId]](prefix + "customerid"),
        personid = row[Option[BusinessentityId]](prefix + "personid"),
        storeid = row[Option[BusinessentityId]](prefix + "storeid"),
        territoryid = row[Option[SalesterritoryId]](prefix + "territoryid"),
        rowguid = row[Option[UUID]](prefix + "rowguid"),
        modifieddate = row[Option[LocalDateTime]](prefix + "modifieddate")
      )
    )
  }

  implicit val oFormat: OFormat[CRow] = new OFormat[CRow]{
    override def writes(o: CRow): JsObject =
      Json.obj(
        "id" -> o.id,
        "customerid" -> o.customerid,
        "personid" -> o.personid,
        "storeid" -> o.storeid,
        "territoryid" -> o.territoryid,
        "rowguid" -> o.rowguid,
        "modifieddate" -> o.modifieddate
      )
  
    override def reads(json: JsValue): JsResult[CRow] = {
      JsResult.fromTry(
        Try(
          CRow(
            id = json.\("id").toOption.map(_.as[Int]),
            customerid = json.\("customerid").toOption.map(_.as[CustomerId]),
            personid = json.\("personid").toOption.map(_.as[BusinessentityId]),
            storeid = json.\("storeid").toOption.map(_.as[BusinessentityId]),
            territoryid = json.\("territoryid").toOption.map(_.as[SalesterritoryId]),
            rowguid = json.\("rowguid").toOption.map(_.as[UUID]),
            modifieddate = json.\("modifieddate").toOption.map(_.as[LocalDateTime])
          )
        )
      )
    }
  }
}