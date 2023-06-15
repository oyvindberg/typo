package typo
package internal
package codegen

trait JsonLib {
  def defaultedInstance(default: ComputedDefault): List[sc.Code]
  def stringEnumInstances(wrapperType: sc.Type, underlying: sc.Type, lookup: sc.Ident): List[sc.Code]
  def anyValInstances(wrapperType: sc.Type.Qualified, underlying: sc.Type): List[sc.Code]
  def customTypeInstances(exoticType: CustomType): List[sc.Code]
  def instances(tpe: sc.Type, cols: NonEmptyList[ComputedColumn]): List[sc.Code]
}
