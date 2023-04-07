/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package soh

import adventureworks.person.address.AddressId
import adventureworks.person.businessentity.BusinessentityId
import adventureworks.purchasing.shipmethod.ShipmethodId
import adventureworks.sales.creditcard.CreditcardId
import adventureworks.sales.currencyrate.CurrencyrateId
import adventureworks.sales.customer.CustomerId
import adventureworks.sales.salesorderheader.SalesorderheaderId
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

case class SohRow(
  id: Option[Int] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"soh","column_name":"id","ordinal_position":1,"is_nullable":"YES","data_type":"integer","numeric_precision":32,"numeric_precision_radix":2,"numeric_scale":0,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"int4","dtd_identifier":"1","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[sales.salesorderheader.SalesorderheaderRow.salesorderid]] */
  salesorderid: Option[SalesorderheaderId] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"soh","column_name":"salesorderid","ordinal_position":2,"is_nullable":"YES","data_type":"integer","numeric_precision":32,"numeric_precision_radix":2,"numeric_scale":0,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"int4","dtd_identifier":"2","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[sales.salesorderheader.SalesorderheaderRow.revisionnumber]] */
  revisionnumber: Option[Int] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"soh","column_name":"revisionnumber","ordinal_position":3,"is_nullable":"YES","data_type":"smallint","numeric_precision":16,"numeric_precision_radix":2,"numeric_scale":0,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"int2","dtd_identifier":"3","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[sales.salesorderheader.SalesorderheaderRow.orderdate]] */
  orderdate: Option[LocalDateTime] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"soh","column_name":"orderdate","ordinal_position":4,"is_nullable":"YES","data_type":"timestamp without time zone","datetime_precision":6,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"timestamp","dtd_identifier":"4","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[sales.salesorderheader.SalesorderheaderRow.duedate]] */
  duedate: Option[LocalDateTime] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"soh","column_name":"duedate","ordinal_position":5,"is_nullable":"YES","data_type":"timestamp without time zone","datetime_precision":6,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"timestamp","dtd_identifier":"5","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[sales.salesorderheader.SalesorderheaderRow.shipdate]] */
  shipdate: Option[LocalDateTime] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"soh","column_name":"shipdate","ordinal_position":6,"is_nullable":"YES","data_type":"timestamp without time zone","datetime_precision":6,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"timestamp","dtd_identifier":"6","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[sales.salesorderheader.SalesorderheaderRow.status]] */
  status: Option[Int] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"soh","column_name":"status","ordinal_position":7,"is_nullable":"YES","data_type":"smallint","numeric_precision":16,"numeric_precision_radix":2,"numeric_scale":0,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"int2","dtd_identifier":"7","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[sales.salesorderheader.SalesorderheaderRow.onlineorderflag]] */
  onlineorderflag: Boolean /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"soh","column_name":"onlineorderflag","ordinal_position":8,"is_nullable":"NO","data_type":"boolean","domain_catalog":"Adventureworks","domain_schema":"public","domain_name":"Flag","udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"bool","dtd_identifier":"8","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[sales.salesorderheader.SalesorderheaderRow.purchaseordernumber]] */
  purchaseordernumber: Option[String] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"soh","column_name":"purchaseordernumber","ordinal_position":9,"is_nullable":"YES","data_type":"character varying","character_maximum_length":25,"character_octet_length":100,"domain_catalog":"Adventureworks","domain_schema":"public","domain_name":"OrderNumber","udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"varchar","dtd_identifier":"9","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[sales.salesorderheader.SalesorderheaderRow.accountnumber]] */
  accountnumber: Option[String] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"soh","column_name":"accountnumber","ordinal_position":10,"is_nullable":"YES","data_type":"character varying","character_maximum_length":15,"character_octet_length":60,"domain_catalog":"Adventureworks","domain_schema":"public","domain_name":"AccountNumber","udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"varchar","dtd_identifier":"10","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[sales.salesorderheader.SalesorderheaderRow.customerid]] */
  customerid: Option[CustomerId] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"soh","column_name":"customerid","ordinal_position":11,"is_nullable":"YES","data_type":"integer","numeric_precision":32,"numeric_precision_radix":2,"numeric_scale":0,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"int4","dtd_identifier":"11","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[sales.salesorderheader.SalesorderheaderRow.salespersonid]] */
  salespersonid: Option[BusinessentityId] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"soh","column_name":"salespersonid","ordinal_position":12,"is_nullable":"YES","data_type":"integer","numeric_precision":32,"numeric_precision_radix":2,"numeric_scale":0,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"int4","dtd_identifier":"12","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[sales.salesorderheader.SalesorderheaderRow.territoryid]] */
  territoryid: Option[SalesterritoryId] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"soh","column_name":"territoryid","ordinal_position":13,"is_nullable":"YES","data_type":"integer","numeric_precision":32,"numeric_precision_radix":2,"numeric_scale":0,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"int4","dtd_identifier":"13","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[sales.salesorderheader.SalesorderheaderRow.billtoaddressid]] */
  billtoaddressid: Option[AddressId] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"soh","column_name":"billtoaddressid","ordinal_position":14,"is_nullable":"YES","data_type":"integer","numeric_precision":32,"numeric_precision_radix":2,"numeric_scale":0,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"int4","dtd_identifier":"14","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[sales.salesorderheader.SalesorderheaderRow.shiptoaddressid]] */
  shiptoaddressid: Option[AddressId] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"soh","column_name":"shiptoaddressid","ordinal_position":15,"is_nullable":"YES","data_type":"integer","numeric_precision":32,"numeric_precision_radix":2,"numeric_scale":0,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"int4","dtd_identifier":"15","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[sales.salesorderheader.SalesorderheaderRow.shipmethodid]] */
  shipmethodid: Option[ShipmethodId] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"soh","column_name":"shipmethodid","ordinal_position":16,"is_nullable":"YES","data_type":"integer","numeric_precision":32,"numeric_precision_radix":2,"numeric_scale":0,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"int4","dtd_identifier":"16","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[sales.salesorderheader.SalesorderheaderRow.creditcardid]] */
  creditcardid: Option[CreditcardId] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"soh","column_name":"creditcardid","ordinal_position":17,"is_nullable":"YES","data_type":"integer","numeric_precision":32,"numeric_precision_radix":2,"numeric_scale":0,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"int4","dtd_identifier":"17","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[sales.salesorderheader.SalesorderheaderRow.creditcardapprovalcode]] */
  creditcardapprovalcode: Option[String] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"soh","column_name":"creditcardapprovalcode","ordinal_position":18,"is_nullable":"YES","data_type":"character varying","character_maximum_length":15,"character_octet_length":60,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"varchar","dtd_identifier":"18","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[sales.salesorderheader.SalesorderheaderRow.currencyrateid]] */
  currencyrateid: Option[CurrencyrateId] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"soh","column_name":"currencyrateid","ordinal_position":19,"is_nullable":"YES","data_type":"integer","numeric_precision":32,"numeric_precision_radix":2,"numeric_scale":0,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"int4","dtd_identifier":"19","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[sales.salesorderheader.SalesorderheaderRow.subtotal]] */
  subtotal: Option[BigDecimal] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"soh","column_name":"subtotal","ordinal_position":20,"is_nullable":"YES","data_type":"numeric","numeric_precision_radix":10,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"numeric","dtd_identifier":"20","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[sales.salesorderheader.SalesorderheaderRow.taxamt]] */
  taxamt: Option[BigDecimal] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"soh","column_name":"taxamt","ordinal_position":21,"is_nullable":"YES","data_type":"numeric","numeric_precision_radix":10,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"numeric","dtd_identifier":"21","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[sales.salesorderheader.SalesorderheaderRow.freight]] */
  freight: Option[BigDecimal] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"soh","column_name":"freight","ordinal_position":22,"is_nullable":"YES","data_type":"numeric","numeric_precision_radix":10,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"numeric","dtd_identifier":"22","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[sales.salesorderheader.SalesorderheaderRow.totaldue]] */
  totaldue: Option[BigDecimal] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"soh","column_name":"totaldue","ordinal_position":23,"is_nullable":"YES","data_type":"numeric","numeric_precision_radix":10,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"numeric","dtd_identifier":"23","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[sales.salesorderheader.SalesorderheaderRow.comment]] */
  comment: Option[String] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"soh","column_name":"comment","ordinal_position":24,"is_nullable":"YES","data_type":"character varying","character_maximum_length":128,"character_octet_length":512,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"varchar","dtd_identifier":"24","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[sales.salesorderheader.SalesorderheaderRow.rowguid]] */
  rowguid: Option[UUID] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"soh","column_name":"rowguid","ordinal_position":25,"is_nullable":"YES","data_type":"uuid","udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"uuid","dtd_identifier":"25","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */,
  /** Points to [[sales.salesorderheader.SalesorderheaderRow.modifieddate]] */
  modifieddate: Option[LocalDateTime] /* {"table_catalog":"Adventureworks","table_schema":"sa","table_name":"soh","column_name":"modifieddate","ordinal_position":26,"is_nullable":"YES","data_type":"timestamp without time zone","datetime_precision":6,"udt_catalog":"Adventureworks","udt_schema":"pg_catalog","udt_name":"timestamp","dtd_identifier":"26","is_self_referencing":"NO","is_identity":"NO","identity_cycle":"NO","is_generated":"NEVER","is_updatable":"YES"} */
)

object SohRow {
  def rowParser(prefix: String): RowParser[SohRow] = { row =>
    Success(
      SohRow(
        id = row[Option[Int]](prefix + "id"),
        salesorderid = row[Option[SalesorderheaderId]](prefix + "salesorderid"),
        revisionnumber = row[Option[Int]](prefix + "revisionnumber"),
        orderdate = row[Option[LocalDateTime]](prefix + "orderdate"),
        duedate = row[Option[LocalDateTime]](prefix + "duedate"),
        shipdate = row[Option[LocalDateTime]](prefix + "shipdate"),
        status = row[Option[Int]](prefix + "status"),
        onlineorderflag = row[Boolean](prefix + "onlineorderflag"),
        purchaseordernumber = row[Option[String]](prefix + "purchaseordernumber"),
        accountnumber = row[Option[String]](prefix + "accountnumber"),
        customerid = row[Option[CustomerId]](prefix + "customerid"),
        salespersonid = row[Option[BusinessentityId]](prefix + "salespersonid"),
        territoryid = row[Option[SalesterritoryId]](prefix + "territoryid"),
        billtoaddressid = row[Option[AddressId]](prefix + "billtoaddressid"),
        shiptoaddressid = row[Option[AddressId]](prefix + "shiptoaddressid"),
        shipmethodid = row[Option[ShipmethodId]](prefix + "shipmethodid"),
        creditcardid = row[Option[CreditcardId]](prefix + "creditcardid"),
        creditcardapprovalcode = row[Option[String]](prefix + "creditcardapprovalcode"),
        currencyrateid = row[Option[CurrencyrateId]](prefix + "currencyrateid"),
        subtotal = row[Option[BigDecimal]](prefix + "subtotal"),
        taxamt = row[Option[BigDecimal]](prefix + "taxamt"),
        freight = row[Option[BigDecimal]](prefix + "freight"),
        totaldue = row[Option[BigDecimal]](prefix + "totaldue"),
        comment = row[Option[String]](prefix + "comment"),
        rowguid = row[Option[UUID]](prefix + "rowguid"),
        modifieddate = row[Option[LocalDateTime]](prefix + "modifieddate")
      )
    )
  }

  implicit val oFormat: OFormat[SohRow] = new OFormat[SohRow]{
    override def writes(o: SohRow): JsObject =
      Json.obj(
        "id" -> o.id,
        "salesorderid" -> o.salesorderid,
        "revisionnumber" -> o.revisionnumber,
        "orderdate" -> o.orderdate,
        "duedate" -> o.duedate,
        "shipdate" -> o.shipdate,
        "status" -> o.status,
        "onlineorderflag" -> o.onlineorderflag,
        "purchaseordernumber" -> o.purchaseordernumber,
        "accountnumber" -> o.accountnumber,
        "customerid" -> o.customerid,
        "salespersonid" -> o.salespersonid,
        "territoryid" -> o.territoryid,
        "billtoaddressid" -> o.billtoaddressid,
        "shiptoaddressid" -> o.shiptoaddressid,
        "shipmethodid" -> o.shipmethodid,
        "creditcardid" -> o.creditcardid,
        "creditcardapprovalcode" -> o.creditcardapprovalcode,
        "currencyrateid" -> o.currencyrateid,
        "subtotal" -> o.subtotal,
        "taxamt" -> o.taxamt,
        "freight" -> o.freight,
        "totaldue" -> o.totaldue,
        "comment" -> o.comment,
        "rowguid" -> o.rowguid,
        "modifieddate" -> o.modifieddate
      )
  
    override def reads(json: JsValue): JsResult[SohRow] = {
      JsResult.fromTry(
        Try(
          SohRow(
            id = json.\("id").toOption.map(_.as[Int]),
            salesorderid = json.\("salesorderid").toOption.map(_.as[SalesorderheaderId]),
            revisionnumber = json.\("revisionnumber").toOption.map(_.as[Int]),
            orderdate = json.\("orderdate").toOption.map(_.as[LocalDateTime]),
            duedate = json.\("duedate").toOption.map(_.as[LocalDateTime]),
            shipdate = json.\("shipdate").toOption.map(_.as[LocalDateTime]),
            status = json.\("status").toOption.map(_.as[Int]),
            onlineorderflag = json.\("onlineorderflag").as[Boolean],
            purchaseordernumber = json.\("purchaseordernumber").toOption.map(_.as[String]),
            accountnumber = json.\("accountnumber").toOption.map(_.as[String]),
            customerid = json.\("customerid").toOption.map(_.as[CustomerId]),
            salespersonid = json.\("salespersonid").toOption.map(_.as[BusinessentityId]),
            territoryid = json.\("territoryid").toOption.map(_.as[SalesterritoryId]),
            billtoaddressid = json.\("billtoaddressid").toOption.map(_.as[AddressId]),
            shiptoaddressid = json.\("shiptoaddressid").toOption.map(_.as[AddressId]),
            shipmethodid = json.\("shipmethodid").toOption.map(_.as[ShipmethodId]),
            creditcardid = json.\("creditcardid").toOption.map(_.as[CreditcardId]),
            creditcardapprovalcode = json.\("creditcardapprovalcode").toOption.map(_.as[String]),
            currencyrateid = json.\("currencyrateid").toOption.map(_.as[CurrencyrateId]),
            subtotal = json.\("subtotal").toOption.map(_.as[BigDecimal]),
            taxamt = json.\("taxamt").toOption.map(_.as[BigDecimal]),
            freight = json.\("freight").toOption.map(_.as[BigDecimal]),
            totaldue = json.\("totaldue").toOption.map(_.as[BigDecimal]),
            comment = json.\("comment").toOption.map(_.as[String]),
            rowguid = json.\("rowguid").toOption.map(_.as[UUID]),
            modifieddate = json.\("modifieddate").toOption.map(_.as[LocalDateTime])
          )
        )
      )
    }
  }
}