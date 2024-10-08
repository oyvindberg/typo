/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package public
package title_domain

import typo.dsl.Path
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.IdField
import typo.dsl.Structure.Relation

trait TitleDomainFields {
  def code: IdField[TitleDomainId, TitleDomainRow]
}

object TitleDomainFields {
  lazy val structure: Relation[TitleDomainFields, TitleDomainRow] =
    new Impl(Nil)
    
  private final class Impl(val _path: List[Path])
    extends Relation[TitleDomainFields, TitleDomainRow] {
  
    override lazy val fields: TitleDomainFields = new TitleDomainFields {
      override def code = IdField[TitleDomainId, TitleDomainRow](_path, "code", None, Some("text"), x => x.code, (row, value) => row.copy(code = value))
    }
  
    override lazy val columns: List[FieldLikeNoHkt[?, TitleDomainRow]] =
      List[FieldLikeNoHkt[?, TitleDomainRow]](fields.code)
  
    override def copy(path: List[Path]): Impl =
      new Impl(path)
  }
  
}
