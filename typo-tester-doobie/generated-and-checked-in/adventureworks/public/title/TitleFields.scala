/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package public
package title

import typo.dsl.Path
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.IdField
import typo.dsl.Structure.Relation

trait TitleFields {
  def code: IdField[TitleId, TitleRow]
}

object TitleFields {
  lazy val structure: Relation[TitleFields, TitleRow] =
    new Impl(Nil)
    
  private final class Impl(val _path: List[Path])
    extends Relation[TitleFields, TitleRow] {
  
    override lazy val fields: TitleFields = new TitleFields {
      override def code = IdField[TitleId, TitleRow](_path, "code", None, None, x => x.code, (row, value) => row.copy(code = value))
    }
  
    override lazy val columns: List[FieldLikeNoHkt[?, TitleRow]] =
      List[FieldLikeNoHkt[?, TitleRow]](fields.code)
  
    override def copy(path: List[Path]): Impl =
      new Impl(path)
  }
  
}
