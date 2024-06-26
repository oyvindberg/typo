/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package wr

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoShort
import adventureworks.production.location.LocationId
import adventureworks.production.workorder.WorkorderId
import typo.dsl.Path
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.OptField
import typo.dsl.Structure.Relation

trait WrViewFields {
  def id: Field[WorkorderId, WrViewRow]
  def workorderid: Field[WorkorderId, WrViewRow]
  def productid: Field[Int, WrViewRow]
  def operationsequence: Field[TypoShort, WrViewRow]
  def locationid: Field[LocationId, WrViewRow]
  def scheduledstartdate: Field[TypoLocalDateTime, WrViewRow]
  def scheduledenddate: Field[TypoLocalDateTime, WrViewRow]
  def actualstartdate: OptField[TypoLocalDateTime, WrViewRow]
  def actualenddate: OptField[TypoLocalDateTime, WrViewRow]
  def actualresourcehrs: OptField[BigDecimal, WrViewRow]
  def plannedcost: Field[BigDecimal, WrViewRow]
  def actualcost: OptField[BigDecimal, WrViewRow]
  def modifieddate: Field[TypoLocalDateTime, WrViewRow]
}

object WrViewFields {
  lazy val structure: Relation[WrViewFields, WrViewRow] =
    new Impl(Nil)
    
  private final class Impl(val _path: List[Path])
    extends Relation[WrViewFields, WrViewRow] {
  
    override lazy val fields: WrViewFields = new WrViewFields {
      override def id = Field[WorkorderId, WrViewRow](_path, "id", None, None, x => x.id, (row, value) => row.copy(id = value))
      override def workorderid = Field[WorkorderId, WrViewRow](_path, "workorderid", None, None, x => x.workorderid, (row, value) => row.copy(workorderid = value))
      override def productid = Field[Int, WrViewRow](_path, "productid", None, None, x => x.productid, (row, value) => row.copy(productid = value))
      override def operationsequence = Field[TypoShort, WrViewRow](_path, "operationsequence", None, None, x => x.operationsequence, (row, value) => row.copy(operationsequence = value))
      override def locationid = Field[LocationId, WrViewRow](_path, "locationid", None, None, x => x.locationid, (row, value) => row.copy(locationid = value))
      override def scheduledstartdate = Field[TypoLocalDateTime, WrViewRow](_path, "scheduledstartdate", Some("text"), None, x => x.scheduledstartdate, (row, value) => row.copy(scheduledstartdate = value))
      override def scheduledenddate = Field[TypoLocalDateTime, WrViewRow](_path, "scheduledenddate", Some("text"), None, x => x.scheduledenddate, (row, value) => row.copy(scheduledenddate = value))
      override def actualstartdate = OptField[TypoLocalDateTime, WrViewRow](_path, "actualstartdate", Some("text"), None, x => x.actualstartdate, (row, value) => row.copy(actualstartdate = value))
      override def actualenddate = OptField[TypoLocalDateTime, WrViewRow](_path, "actualenddate", Some("text"), None, x => x.actualenddate, (row, value) => row.copy(actualenddate = value))
      override def actualresourcehrs = OptField[BigDecimal, WrViewRow](_path, "actualresourcehrs", None, None, x => x.actualresourcehrs, (row, value) => row.copy(actualresourcehrs = value))
      override def plannedcost = Field[BigDecimal, WrViewRow](_path, "plannedcost", None, None, x => x.plannedcost, (row, value) => row.copy(plannedcost = value))
      override def actualcost = OptField[BigDecimal, WrViewRow](_path, "actualcost", None, None, x => x.actualcost, (row, value) => row.copy(actualcost = value))
      override def modifieddate = Field[TypoLocalDateTime, WrViewRow](_path, "modifieddate", Some("text"), None, x => x.modifieddate, (row, value) => row.copy(modifieddate = value))
    }
  
    override lazy val columns: List[FieldLikeNoHkt[?, WrViewRow]] =
      List[FieldLikeNoHkt[?, WrViewRow]](fields.id, fields.workorderid, fields.productid, fields.operationsequence, fields.locationid, fields.scheduledstartdate, fields.scheduledenddate, fields.actualstartdate, fields.actualenddate, fields.actualresourcehrs, fields.plannedcost, fields.actualcost, fields.modifieddate)
  
    override def copy(path: List[Path]): Impl =
      new Impl(path)
  }
  
}
