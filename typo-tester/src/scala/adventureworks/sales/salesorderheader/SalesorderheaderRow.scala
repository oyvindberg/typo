/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salesorderheader

import adventureworks.person.address.AddressId
import adventureworks.person.businessentity.BusinessentityId
import adventureworks.public.AccountNumber
import adventureworks.public.Flag
import adventureworks.public.OrderNumber
import adventureworks.purchasing.shipmethod.ShipmethodId
import adventureworks.sales.creditcard.CreditcardId
import adventureworks.sales.currencyrate.CurrencyrateId
import adventureworks.sales.customer.CustomerId
import adventureworks.sales.salesterritory.SalesterritoryId
import java.time.LocalDateTime
import java.util.UUID
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class SalesorderheaderRow(
  /** Primary key. */
  salesorderid: SalesorderheaderId,
  /** Incremental number to track changes to the sales order over time. */
  revisionnumber: Int,
  /** Dates the sales order was created. */
  orderdate: LocalDateTime,
  /** Date the order is due to the customer. */
  duedate: LocalDateTime,
  /** Date the order was shipped to the customer. */
  shipdate: Option[LocalDateTime],
  /** Order current status. 1 = In process; 2 = Approved; 3 = Backordered; 4 = Rejected; 5 = Shipped; 6 = Cancelled */
  status: Int,
  /** 0 = Order placed by sales person. 1 = Order placed online by customer. */
  onlineorderflag: Flag,
  /** Customer purchase order number reference. */
  purchaseordernumber: Option[OrderNumber],
  /** Financial accounting number reference. */
  accountnumber: Option[AccountNumber],
  /** Customer identification number. Foreign key to Customer.BusinessEntityID.
      Points to [[customer.CustomerRow.customerid]] */
  customerid: CustomerId,
  /** Sales person who created the sales order. Foreign key to SalesPerson.BusinessEntityID.
      Points to [[salesperson.SalespersonRow.businessentityid]] */
  salespersonid: Option[BusinessentityId],
  /** Territory in which the sale was made. Foreign key to SalesTerritory.SalesTerritoryID.
      Points to [[salesterritory.SalesterritoryRow.territoryid]] */
  territoryid: Option[SalesterritoryId],
  /** Customer billing address. Foreign key to Address.AddressID.
      Points to [[person.address.AddressRow.addressid]] */
  billtoaddressid: AddressId,
  /** Customer shipping address. Foreign key to Address.AddressID.
      Points to [[person.address.AddressRow.addressid]] */
  shiptoaddressid: AddressId,
  /** Shipping method. Foreign key to ShipMethod.ShipMethodID.
      Points to [[purchasing.shipmethod.ShipmethodRow.shipmethodid]] */
  shipmethodid: ShipmethodId,
  /** Credit card identification number. Foreign key to CreditCard.CreditCardID.
      Points to [[creditcard.CreditcardRow.creditcardid]] */
  creditcardid: Option[CreditcardId],
  /** Approval code provided by the credit card company. */
  creditcardapprovalcode: Option[String],
  /** Currency exchange rate used. Foreign key to CurrencyRate.CurrencyRateID.
      Points to [[currencyrate.CurrencyrateRow.currencyrateid]] */
  currencyrateid: Option[CurrencyrateId],
  /** Sales subtotal. Computed as SUM(SalesOrderDetail.LineTotal)for the appropriate SalesOrderID. */
  subtotal: BigDecimal,
  /** Tax amount. */
  taxamt: BigDecimal,
  /** Shipping cost. */
  freight: BigDecimal,
  /** Total due from customer. Computed as Subtotal + TaxAmt + Freight. */
  totaldue: Option[BigDecimal],
  /** Sales representative comments. */
  comment: Option[String],
  rowguid: UUID,
  modifieddate: LocalDateTime
)

object SalesorderheaderRow {
  implicit val oFormat: OFormat[SalesorderheaderRow] = new OFormat[SalesorderheaderRow]{
    override def writes(o: SalesorderheaderRow): JsObject =
      Json.obj(
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
  
    override def reads(json: JsValue): JsResult[SalesorderheaderRow] = {
      JsResult.fromTry(
        Try(
          SalesorderheaderRow(
            salesorderid = json.\("salesorderid").as[SalesorderheaderId],
            revisionnumber = json.\("revisionnumber").as[Int],
            orderdate = json.\("orderdate").as[LocalDateTime],
            duedate = json.\("duedate").as[LocalDateTime],
            shipdate = json.\("shipdate").toOption.map(_.as[LocalDateTime]),
            status = json.\("status").as[Int],
            onlineorderflag = json.\("onlineorderflag").as[Flag],
            purchaseordernumber = json.\("purchaseordernumber").toOption.map(_.as[OrderNumber]),
            accountnumber = json.\("accountnumber").toOption.map(_.as[AccountNumber]),
            customerid = json.\("customerid").as[CustomerId],
            salespersonid = json.\("salespersonid").toOption.map(_.as[BusinessentityId]),
            territoryid = json.\("territoryid").toOption.map(_.as[SalesterritoryId]),
            billtoaddressid = json.\("billtoaddressid").as[AddressId],
            shiptoaddressid = json.\("shiptoaddressid").as[AddressId],
            shipmethodid = json.\("shipmethodid").as[ShipmethodId],
            creditcardid = json.\("creditcardid").toOption.map(_.as[CreditcardId]),
            creditcardapprovalcode = json.\("creditcardapprovalcode").toOption.map(_.as[String]),
            currencyrateid = json.\("currencyrateid").toOption.map(_.as[CurrencyrateId]),
            subtotal = json.\("subtotal").as[BigDecimal],
            taxamt = json.\("taxamt").as[BigDecimal],
            freight = json.\("freight").as[BigDecimal],
            totaldue = json.\("totaldue").toOption.map(_.as[BigDecimal]),
            comment = json.\("comment").toOption.map(_.as[String]),
            rowguid = json.\("rowguid").as[UUID],
            modifieddate = json.\("modifieddate").as[LocalDateTime]
          )
        )
      )
    }
  }
}