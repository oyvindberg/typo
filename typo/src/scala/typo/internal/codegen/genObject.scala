package typo
package internal
package codegen

object genObject {
  def apply(name: sc.QIdent, members: List[sc.ClassMember]): sc.Obj =
    sc.Obj(name, members, None)

  def withBody(name: sc.QIdent, members: List[sc.ClassMember])(body: sc.Code): sc.Obj =
    sc.Obj(name, members, Some(body))
}
