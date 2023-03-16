package typo

trait JsonLib {
  def defaultedInstance(defaulted: sc.QIdent, provided: sc.QIdent, useDefault: sc.QIdent): List[sc.Code]
  def stringEnumInstances(wrapperType: sc.Type, underlying: sc.Type, lookup: sc.Ident): List[sc.Code]
  def anyValInstances(wrapperType: sc.Type.Qualified, underlying: sc.Type): List[sc.Code]
  def instances(tpe: sc.Type, cols: Seq[ColumnComputed]): List[sc.Code]
}
