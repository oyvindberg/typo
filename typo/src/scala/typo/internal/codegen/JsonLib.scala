package typo
package internal
package codegen

trait JsonLib {
  def defaultedInstance(defaulted: sc.Type.Qualified, provided: sc.Ident, useDefault: sc.Ident): List[sc.Code]
  def stringEnumInstances(wrapperType: sc.Type, underlying: sc.Type, lookup: sc.Ident): List[sc.Code]
  def anyValInstances(wrapperType: sc.Type.Qualified, underlying: sc.Type): List[sc.Code]
  def instances(tpe: sc.Type, cols: NonEmptyList[ComputedColumn]): List[sc.Code]
  def missingInstances: List[sc.Code]
}

object JsonLib {
  object None extends JsonLib {
    def defaultedInstance(defaulted: sc.Type.Qualified, provided: sc.Ident, useDefault: sc.Ident): List[sc.Code] = Nil
    def stringEnumInstances(wrapperType: sc.Type, underlying: sc.Type, lookup: sc.Ident): List[sc.Code] = Nil
    def anyValInstances(wrapperType: sc.Type.Qualified, underlying: sc.Type): List[sc.Code] = Nil
    def instances(tpe: sc.Type, cols: NonEmptyList[ComputedColumn]): List[sc.Code] = Nil
    def missingInstances: List[sc.Code] = Nil
  }
}
