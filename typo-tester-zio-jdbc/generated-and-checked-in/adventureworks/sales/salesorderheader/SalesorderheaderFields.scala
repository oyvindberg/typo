/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salesorderheader

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoShort
import adventureworks.customtypes.TypoUUID
import adventureworks.person.address.AddressId
import adventureworks.person.businessentity.BusinessentityId
import adventureworks.public.AccountNumber
import adventureworks.public.Flag
import adventureworks.public.OrderNumber
import adventureworks.purchasing.shipmethod.ShipmethodId
import adventureworks.sales.currencyrate.CurrencyrateId
import adventureworks.sales.customer.CustomerId
import adventureworks.sales.salesterritory.SalesterritoryId
import adventureworks.userdefined.CustomCreditcardId
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.IdField
import typo.dsl.SqlExpr.OptField
import typo.dsl.Structure.Relation

trait SalesorderheaderFields[Row] {
  val salesorderid: IdField[SalesorderheaderId, Row]
  val revisionnumber: Field[TypoShort, Row]
  val orderdate: Field[TypoLocalDateTime, Row]
  val duedate: Field[TypoLocalDateTime, Row]
  val shipdate: OptField[TypoLocalDateTime, Row]
  val status: Field[TypoShort, Row]
  val onlineorderflag: Field[Flag, Row]
  val purchaseordernumber: OptField[OrderNumber, Row]
  val accountnumber: OptField[AccountNumber, Row]
  val customerid: Field[CustomerId, Row]
  val salespersonid: OptField[BusinessentityId, Row]
  val territoryid: OptField[SalesterritoryId, Row]
  val billtoaddressid: Field[AddressId, Row]
  val shiptoaddressid: Field[AddressId, Row]
  val shipmethodid: Field[ShipmethodId, Row]
  val creditcardid: OptField[/* user-picked */ CustomCreditcardId, Row]
  val creditcardapprovalcode: OptField[/* max 15 chars */ String, Row]
  val currencyrateid: OptField[CurrencyrateId, Row]
  val subtotal: Field[BigDecimal, Row]
  val taxamt: Field[BigDecimal, Row]
  val freight: Field[BigDecimal, Row]
  val totaldue: OptField[BigDecimal, Row]
  val comment: OptField[/* max 128 chars */ String, Row]
  val rowguid: Field[TypoUUID, Row]
  val modifieddate: Field[TypoLocalDateTime, Row]
}

object SalesorderheaderFields {
  val structure: Relation[SalesorderheaderFields, SalesorderheaderRow, SalesorderheaderRow] = 
    new Impl(None, identity, (_, x) => x)
    
  private final class Impl[Row](val prefix: Option[String], val extract: Row => SalesorderheaderRow, val merge: (Row, SalesorderheaderRow) => Row)
    extends Relation[SalesorderheaderFields, SalesorderheaderRow, Row] { 
  
    override val fields: SalesorderheaderFields[Row] = new SalesorderheaderFields[Row] {
      override val salesorderid = new IdField[SalesorderheaderId, Row](prefix, "salesorderid", None, Some("int4"))(x => extract(x).salesorderid, (row, value) => merge(row, extract(row).copy(salesorderid = value)))
      override val revisionnumber = new Field[TypoShort, Row](prefix, "revisionnumber", None, Some("int2"))(x => extract(x).revisionnumber, (row, value) => merge(row, extract(row).copy(revisionnumber = value)))
      override val orderdate = new Field[TypoLocalDateTime, Row](prefix, "orderdate", Some("text"), Some("timestamp"))(x => extract(x).orderdate, (row, value) => merge(row, extract(row).copy(orderdate = value)))
      override val duedate = new Field[TypoLocalDateTime, Row](prefix, "duedate", Some("text"), Some("timestamp"))(x => extract(x).duedate, (row, value) => merge(row, extract(row).copy(duedate = value)))
      override val shipdate = new OptField[TypoLocalDateTime, Row](prefix, "shipdate", Some("text"), Some("timestamp"))(x => extract(x).shipdate, (row, value) => merge(row, extract(row).copy(shipdate = value)))
      override val status = new Field[TypoShort, Row](prefix, "status", None, Some("int2"))(x => extract(x).status, (row, value) => merge(row, extract(row).copy(status = value)))
      override val onlineorderflag = new Field[Flag, Row](prefix, "onlineorderflag", None, Some("bool"))(x => extract(x).onlineorderflag, (row, value) => merge(row, extract(row).copy(onlineorderflag = value)))
      override val purchaseordernumber = new OptField[OrderNumber, Row](prefix, "purchaseordernumber", None, Some("varchar"))(x => extract(x).purchaseordernumber, (row, value) => merge(row, extract(row).copy(purchaseordernumber = value)))
      override val accountnumber = new OptField[AccountNumber, Row](prefix, "accountnumber", None, Some("varchar"))(x => extract(x).accountnumber, (row, value) => merge(row, extract(row).copy(accountnumber = value)))
      override val customerid = new Field[CustomerId, Row](prefix, "customerid", None, Some("int4"))(x => extract(x).customerid, (row, value) => merge(row, extract(row).copy(customerid = value)))
      override val salespersonid = new OptField[BusinessentityId, Row](prefix, "salespersonid", None, Some("int4"))(x => extract(x).salespersonid, (row, value) => merge(row, extract(row).copy(salespersonid = value)))
      override val territoryid = new OptField[SalesterritoryId, Row](prefix, "territoryid", None, Some("int4"))(x => extract(x).territoryid, (row, value) => merge(row, extract(row).copy(territoryid = value)))
      override val billtoaddressid = new Field[AddressId, Row](prefix, "billtoaddressid", None, Some("int4"))(x => extract(x).billtoaddressid, (row, value) => merge(row, extract(row).copy(billtoaddressid = value)))
      override val shiptoaddressid = new Field[AddressId, Row](prefix, "shiptoaddressid", None, Some("int4"))(x => extract(x).shiptoaddressid, (row, value) => merge(row, extract(row).copy(shiptoaddressid = value)))
      override val shipmethodid = new Field[ShipmethodId, Row](prefix, "shipmethodid", None, Some("int4"))(x => extract(x).shipmethodid, (row, value) => merge(row, extract(row).copy(shipmethodid = value)))
      override val creditcardid = new OptField[/* user-picked */ CustomCreditcardId, Row](prefix, "creditcardid", None, Some("int4"))(x => extract(x).creditcardid, (row, value) => merge(row, extract(row).copy(creditcardid = value)))
      override val creditcardapprovalcode = new OptField[/* max 15 chars */ String, Row](prefix, "creditcardapprovalcode", None, None)(x => extract(x).creditcardapprovalcode, (row, value) => merge(row, extract(row).copy(creditcardapprovalcode = value)))
      override val currencyrateid = new OptField[CurrencyrateId, Row](prefix, "currencyrateid", None, Some("int4"))(x => extract(x).currencyrateid, (row, value) => merge(row, extract(row).copy(currencyrateid = value)))
      override val subtotal = new Field[BigDecimal, Row](prefix, "subtotal", None, Some("numeric"))(x => extract(x).subtotal, (row, value) => merge(row, extract(row).copy(subtotal = value)))
      override val taxamt = new Field[BigDecimal, Row](prefix, "taxamt", None, Some("numeric"))(x => extract(x).taxamt, (row, value) => merge(row, extract(row).copy(taxamt = value)))
      override val freight = new Field[BigDecimal, Row](prefix, "freight", None, Some("numeric"))(x => extract(x).freight, (row, value) => merge(row, extract(row).copy(freight = value)))
      override val totaldue = new OptField[BigDecimal, Row](prefix, "totaldue", None, Some("numeric"))(x => extract(x).totaldue, (row, value) => merge(row, extract(row).copy(totaldue = value)))
      override val comment = new OptField[/* max 128 chars */ String, Row](prefix, "comment", None, None)(x => extract(x).comment, (row, value) => merge(row, extract(row).copy(comment = value)))
      override val rowguid = new Field[TypoUUID, Row](prefix, "rowguid", None, Some("uuid"))(x => extract(x).rowguid, (row, value) => merge(row, extract(row).copy(rowguid = value)))
      override val modifieddate = new Field[TypoLocalDateTime, Row](prefix, "modifieddate", Some("text"), Some("timestamp"))(x => extract(x).modifieddate, (row, value) => merge(row, extract(row).copy(modifieddate = value)))
    }
  
    override val columns: List[FieldLikeNoHkt[?, Row]] =
      List[FieldLikeNoHkt[?, Row]](fields.salesorderid, fields.revisionnumber, fields.orderdate, fields.duedate, fields.shipdate, fields.status, fields.onlineorderflag, fields.purchaseordernumber, fields.accountnumber, fields.customerid, fields.salespersonid, fields.territoryid, fields.billtoaddressid, fields.shiptoaddressid, fields.shipmethodid, fields.creditcardid, fields.creditcardapprovalcode, fields.currencyrateid, fields.subtotal, fields.taxamt, fields.freight, fields.totaldue, fields.comment, fields.rowguid, fields.modifieddate)
  
    override def copy[NewRow](prefix: Option[String], extract: NewRow => SalesorderheaderRow, merge: (NewRow, SalesorderheaderRow) => NewRow): Impl[NewRow] =
      new Impl(prefix, extract, merge)
  }
  
}