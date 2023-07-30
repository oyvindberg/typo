package typo
package internal
package codegen

trait JsonLib {
  def defaultedInstance(default: ComputedDefault): List[sc.Given]
  def stringEnumInstances(wrapperType: sc.Type, underlying: sc.Type, lookup: sc.Ident): List[sc.Given]
  def anyValInstances(wrapperType: sc.Type.Qualified, fieldName: sc.Ident, underlying: sc.Type): List[sc.Given]
  def productInstances(tpe: sc.Type, fields: NonEmptyList[JsonLib.Field]): List[sc.Given]
  def missingInstances: List[sc.ClassMember]

  final def customTypeInstances(ct: CustomType): List[sc.Given] =
    ct.params match {
      case NonEmptyList(param, Nil) =>
        anyValInstances(ct.typoType, param.name, param.tpe)
      case more =>
        productInstances(ct.typoType, more.map(param => JsonLib.Field(param.name, sc.StrLit(param.name.value), param.tpe)))
    }

  final def instances(tpe: sc.Type, cols: NonEmptyList[ComputedColumn]): List[sc.Given] =
    productInstances(tpe, cols.map(col => JsonLib.Field(col.name, sc.StrLit(col.dbName.value), col.tpe)))
}

object JsonLib {
  case class Field(scalaName: sc.Ident, jsonName: sc.StrLit, tpe: sc.Type)
}
