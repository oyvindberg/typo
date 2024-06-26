/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package sohsr

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.sales.salesorderheader.SalesorderheaderId
import adventureworks.sales.salesreason.SalesreasonId
import typo.dsl.Path
import typo.dsl.SqlExpr.Field
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.Structure.Relation

trait SohsrViewFields {
  def salesorderid: Field[SalesorderheaderId, SohsrViewRow]
  def salesreasonid: Field[SalesreasonId, SohsrViewRow]
  def modifieddate: Field[TypoLocalDateTime, SohsrViewRow]
}

object SohsrViewFields {
  lazy val structure: Relation[SohsrViewFields, SohsrViewRow] =
    new Impl(Nil)
    
  private final class Impl(val _path: List[Path])
    extends Relation[SohsrViewFields, SohsrViewRow] {
  
    override lazy val fields: SohsrViewFields = new SohsrViewFields {
      override def salesorderid = Field[SalesorderheaderId, SohsrViewRow](_path, "salesorderid", None, None, x => x.salesorderid, (row, value) => row.copy(salesorderid = value))
      override def salesreasonid = Field[SalesreasonId, SohsrViewRow](_path, "salesreasonid", None, None, x => x.salesreasonid, (row, value) => row.copy(salesreasonid = value))
      override def modifieddate = Field[TypoLocalDateTime, SohsrViewRow](_path, "modifieddate", Some("text"), None, x => x.modifieddate, (row, value) => row.copy(modifieddate = value))
    }
  
    override lazy val columns: List[FieldLikeNoHkt[?, SohsrViewRow]] =
      List[FieldLikeNoHkt[?, SohsrViewRow]](fields.salesorderid, fields.salesreasonid, fields.modifieddate)
  
    override def copy(path: List[Path]): Impl =
      new Impl(path)
  }
  
}
