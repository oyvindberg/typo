package typo

import play.api.libs.json.JsValue

case class ColumnComputed(
    pointsTo: Option[(db.RelationName, db.ColName)],
    name: sc.Ident,
    tpe: sc.Type,
    dbName: db.ColName,
    hasDefault: Boolean,
    jsonDescription: JsValue
) {
  def param: sc.Param = sc.Param(name, tpe)
}
