package typo

case class ColumnComputed(name: sc.Ident, tpe: sc.Type, dbName: db.ColName, hasDefault: Boolean) {
  def param: sc.Param = sc.Param(name, tpe)
}
