package typo

case class ColumnComputed(
    pointsTo: Option[(db.RelationName, db.ColName)],
    name: sc.Ident,
    tpe: sc.Type,
    dbName: db.ColName,
    hasDefault: Boolean
) {
  def param: sc.Param = sc.Param(name, tpe)
}
