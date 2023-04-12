package typo
package internal

import play.api.libs.json.JsValue

case class ColumnComputed(
    pointsTo: Option[(db.RelationName, db.ColName)],
    name: sc.Ident,
    tpe: sc.Type,
    dbName: db.ColName,
    columnDefault: Option[String],
    comment: Option[String],
    jsonDescription: JsValue
) {
  def param: sc.Param = sc.Param(name, tpe)
}
