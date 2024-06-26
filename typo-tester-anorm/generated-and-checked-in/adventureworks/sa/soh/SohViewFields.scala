/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package soh

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
import adventureworks.sales.salesorderheader.SalesorderheaderId
import adventureworks.sales.salesterritory.SalesterritoryId
import adventureworks.userdefined.CustomCreditcardId
import typo.dsl.Path
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.OptField
import typo.dsl.Structure.Relation

trait SohViewFields {
  def id: Field[SalesorderheaderId, SohViewRow]
  def salesorderid: Field[SalesorderheaderId, SohViewRow]
  def revisionnumber: Field[TypoShort, SohViewRow]
  def orderdate: Field[TypoLocalDateTime, SohViewRow]
  def duedate: Field[TypoLocalDateTime, SohViewRow]
  def shipdate: OptField[TypoLocalDateTime, SohViewRow]
  def status: Field[TypoShort, SohViewRow]
  def onlineorderflag: Field[Flag, SohViewRow]
  def purchaseordernumber: OptField[OrderNumber, SohViewRow]
  def accountnumber: OptField[AccountNumber, SohViewRow]
  def customerid: Field[CustomerId, SohViewRow]
  def salespersonid: OptField[BusinessentityId, SohViewRow]
  def territoryid: OptField[SalesterritoryId, SohViewRow]
  def billtoaddressid: Field[AddressId, SohViewRow]
  def shiptoaddressid: Field[AddressId, SohViewRow]
  def shipmethodid: Field[ShipmethodId, SohViewRow]
  def creditcardid: OptField[/* user-picked */ CustomCreditcardId, SohViewRow]
  def creditcardapprovalcode: OptField[/* max 15 chars */ String, SohViewRow]
  def currencyrateid: OptField[CurrencyrateId, SohViewRow]
  def subtotal: Field[BigDecimal, SohViewRow]
  def taxamt: Field[BigDecimal, SohViewRow]
  def freight: Field[BigDecimal, SohViewRow]
  def totaldue: OptField[BigDecimal, SohViewRow]
  def comment: OptField[/* max 128 chars */ String, SohViewRow]
  def rowguid: Field[TypoUUID, SohViewRow]
  def modifieddate: Field[TypoLocalDateTime, SohViewRow]
}

object SohViewFields {
  lazy val structure: Relation[SohViewFields, SohViewRow] =
    new Impl(Nil)
    
  private final class Impl(val _path: List[Path])
    extends Relation[SohViewFields, SohViewRow] {
  
    override lazy val fields: SohViewFields = new SohViewFields {
      override def id = Field[SalesorderheaderId, SohViewRow](_path, "id", None, None, x => x.id, (row, value) => row.copy(id = value))
      override def salesorderid = Field[SalesorderheaderId, SohViewRow](_path, "salesorderid", None, None, x => x.salesorderid, (row, value) => row.copy(salesorderid = value))
      override def revisionnumber = Field[TypoShort, SohViewRow](_path, "revisionnumber", None, None, x => x.revisionnumber, (row, value) => row.copy(revisionnumber = value))
      override def orderdate = Field[TypoLocalDateTime, SohViewRow](_path, "orderdate", Some("text"), None, x => x.orderdate, (row, value) => row.copy(orderdate = value))
      override def duedate = Field[TypoLocalDateTime, SohViewRow](_path, "duedate", Some("text"), None, x => x.duedate, (row, value) => row.copy(duedate = value))
      override def shipdate = OptField[TypoLocalDateTime, SohViewRow](_path, "shipdate", Some("text"), None, x => x.shipdate, (row, value) => row.copy(shipdate = value))
      override def status = Field[TypoShort, SohViewRow](_path, "status", None, None, x => x.status, (row, value) => row.copy(status = value))
      override def onlineorderflag = Field[Flag, SohViewRow](_path, "onlineorderflag", None, None, x => x.onlineorderflag, (row, value) => row.copy(onlineorderflag = value))
      override def purchaseordernumber = OptField[OrderNumber, SohViewRow](_path, "purchaseordernumber", None, None, x => x.purchaseordernumber, (row, value) => row.copy(purchaseordernumber = value))
      override def accountnumber = OptField[AccountNumber, SohViewRow](_path, "accountnumber", None, None, x => x.accountnumber, (row, value) => row.copy(accountnumber = value))
      override def customerid = Field[CustomerId, SohViewRow](_path, "customerid", None, None, x => x.customerid, (row, value) => row.copy(customerid = value))
      override def salespersonid = OptField[BusinessentityId, SohViewRow](_path, "salespersonid", None, None, x => x.salespersonid, (row, value) => row.copy(salespersonid = value))
      override def territoryid = OptField[SalesterritoryId, SohViewRow](_path, "territoryid", None, None, x => x.territoryid, (row, value) => row.copy(territoryid = value))
      override def billtoaddressid = Field[AddressId, SohViewRow](_path, "billtoaddressid", None, None, x => x.billtoaddressid, (row, value) => row.copy(billtoaddressid = value))
      override def shiptoaddressid = Field[AddressId, SohViewRow](_path, "shiptoaddressid", None, None, x => x.shiptoaddressid, (row, value) => row.copy(shiptoaddressid = value))
      override def shipmethodid = Field[ShipmethodId, SohViewRow](_path, "shipmethodid", None, None, x => x.shipmethodid, (row, value) => row.copy(shipmethodid = value))
      override def creditcardid = OptField[/* user-picked */ CustomCreditcardId, SohViewRow](_path, "creditcardid", None, None, x => x.creditcardid, (row, value) => row.copy(creditcardid = value))
      override def creditcardapprovalcode = OptField[/* max 15 chars */ String, SohViewRow](_path, "creditcardapprovalcode", None, None, x => x.creditcardapprovalcode, (row, value) => row.copy(creditcardapprovalcode = value))
      override def currencyrateid = OptField[CurrencyrateId, SohViewRow](_path, "currencyrateid", None, None, x => x.currencyrateid, (row, value) => row.copy(currencyrateid = value))
      override def subtotal = Field[BigDecimal, SohViewRow](_path, "subtotal", None, None, x => x.subtotal, (row, value) => row.copy(subtotal = value))
      override def taxamt = Field[BigDecimal, SohViewRow](_path, "taxamt", None, None, x => x.taxamt, (row, value) => row.copy(taxamt = value))
      override def freight = Field[BigDecimal, SohViewRow](_path, "freight", None, None, x => x.freight, (row, value) => row.copy(freight = value))
      override def totaldue = OptField[BigDecimal, SohViewRow](_path, "totaldue", None, None, x => x.totaldue, (row, value) => row.copy(totaldue = value))
      override def comment = OptField[/* max 128 chars */ String, SohViewRow](_path, "comment", None, None, x => x.comment, (row, value) => row.copy(comment = value))
      override def rowguid = Field[TypoUUID, SohViewRow](_path, "rowguid", None, None, x => x.rowguid, (row, value) => row.copy(rowguid = value))
      override def modifieddate = Field[TypoLocalDateTime, SohViewRow](_path, "modifieddate", Some("text"), None, x => x.modifieddate, (row, value) => row.copy(modifieddate = value))
    }
  
    override lazy val columns: List[FieldLikeNoHkt[?, SohViewRow]] =
      List[FieldLikeNoHkt[?, SohViewRow]](fields.id, fields.salesorderid, fields.revisionnumber, fields.orderdate, fields.duedate, fields.shipdate, fields.status, fields.onlineorderflag, fields.purchaseordernumber, fields.accountnumber, fields.customerid, fields.salespersonid, fields.territoryid, fields.billtoaddressid, fields.shiptoaddressid, fields.shipmethodid, fields.creditcardid, fields.creditcardapprovalcode, fields.currencyrateid, fields.subtotal, fields.taxamt, fields.freight, fields.totaldue, fields.comment, fields.rowguid, fields.modifieddate)
  
    override def copy(path: List[Path]): Impl =
      new Impl(path)
  }
  
}
