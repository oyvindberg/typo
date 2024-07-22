package typo
package internal

case class ComputedColumn(
    pointsTo: List[(Source.Relation, db.ColName)],
    name: sc.Ident,
    tpe: sc.Type,
    dbCol: db.Col
) {
  def dbName = dbCol.name
  def param: sc.Param = sc.Param(name, tpe)
}
