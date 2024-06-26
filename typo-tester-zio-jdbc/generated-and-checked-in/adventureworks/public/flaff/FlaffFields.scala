/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package public
package flaff

import typo.dsl.ForeignKey
import typo.dsl.Path
import typo.dsl.Required
import typo.dsl.SqlExpr
import typo.dsl.SqlExpr.CompositeIn
import typo.dsl.SqlExpr.CompositeIn.TuplePart
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.IdField
import typo.dsl.SqlExpr.OptField
import typo.dsl.Structure.Relation

trait FlaffFields {
  def code: IdField[ShortText, FlaffRow]
  def anotherCode: IdField[/* max 20 chars */ String, FlaffRow]
  def someNumber: IdField[Int, FlaffRow]
  def specifier: IdField[ShortText, FlaffRow]
  def parentspecifier: OptField[ShortText, FlaffRow]
  def fkFlaff: ForeignKey[FlaffFields, FlaffRow] =
    ForeignKey[FlaffFields, FlaffRow]("public.flaff_parent_fk", Nil)
      .withColumnPair(code, _.code)
      .withColumnPair(anotherCode, _.anotherCode)
      .withColumnPair(someNumber, _.someNumber)
      .withColumnPair(parentspecifier, _.specifier)
  def compositeIdIs(compositeId: FlaffId): SqlExpr[Boolean, Required] =
    code.isEqual(compositeId.code).and(anotherCode.isEqual(compositeId.anotherCode)).and(someNumber.isEqual(compositeId.someNumber)).and(specifier.isEqual(compositeId.specifier))
  def compositeIdIn(compositeIds: Array[FlaffId]): SqlExpr[Boolean, Required] =
    new CompositeIn(compositeIds)(TuplePart(code)(_.code), TuplePart(anotherCode)(_.anotherCode), TuplePart(someNumber)(_.someNumber), TuplePart(specifier)(_.specifier))
  
}

object FlaffFields {
  lazy val structure: Relation[FlaffFields, FlaffRow] =
    new Impl(Nil)
    
  private final class Impl(val _path: List[Path])
    extends Relation[FlaffFields, FlaffRow] {
  
    override lazy val fields: FlaffFields = new FlaffFields {
      override def code = IdField[ShortText, FlaffRow](_path, "code", None, Some("text"), x => x.code, (row, value) => row.copy(code = value))
      override def anotherCode = IdField[/* max 20 chars */ String, FlaffRow](_path, "another_code", None, None, x => x.anotherCode, (row, value) => row.copy(anotherCode = value))
      override def someNumber = IdField[Int, FlaffRow](_path, "some_number", None, Some("int4"), x => x.someNumber, (row, value) => row.copy(someNumber = value))
      override def specifier = IdField[ShortText, FlaffRow](_path, "specifier", None, Some("text"), x => x.specifier, (row, value) => row.copy(specifier = value))
      override def parentspecifier = OptField[ShortText, FlaffRow](_path, "parentspecifier", None, Some("text"), x => x.parentspecifier, (row, value) => row.copy(parentspecifier = value))
    }
  
    override lazy val columns: List[FieldLikeNoHkt[?, FlaffRow]] =
      List[FieldLikeNoHkt[?, FlaffRow]](fields.code, fields.anotherCode, fields.someNumber, fields.specifier, fields.parentspecifier)
  
    override def copy(path: List[Path]): Impl =
      new Impl(path)
  }
  
}
