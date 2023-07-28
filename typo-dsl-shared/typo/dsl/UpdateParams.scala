package typo.dsl

case class UpdateParams[Fields[_], Row](
    where: List[Fields[Row] => SqlExpr[Boolean, Required, Row]],
    setters: List[UpdateParams.Setter[Fields, Row, ?]]
) {
  def where(v: Fields[Row] => SqlExpr[Boolean, Required, Row]): UpdateParams[Fields, Row] =
    copy(where = where :+ v)

  def set[NT](col: Fields[Row] => SqlExpr.FieldLikeNotIdNoHkt[NT, Row], value: Fields[Row] => SqlExpr.SqlExprNoHkt[NT, Row]): UpdateParams[Fields, Row] =
    copy(setters = setters :+ UpdateParams.Setter(col, value))
}

object UpdateParams {
  case class Setter[Fields[_], Row, NT](col: Fields[Row] => SqlExpr.FieldLikeNotIdNoHkt[NT, Row], value: Fields[Row] => SqlExpr.SqlExprNoHkt[NT, Row]) {
    def transform(fields: Fields[Row], row: Row): Row = {
      val field: SqlExpr.FieldLikeNotIdNoHkt[NT, Row] = col(fields)
      val newValue: NT = value(fields).eval(row)
      field.set(row, newValue)
    }
  }

  def empty[Fields[_], Row]: UpdateParams[Fields, Row] =
    UpdateParams[Fields, Row](List.empty, List.empty)

  def applyParams[Fields[_], Row](fields: Fields[Row], rows: List[Row], params: UpdateParams[Fields, Row]): List[Row] = {
    val filtered = params.where.foldLeft(rows) { (acc, where) => acc.filter(o => where(fields).eval(o)) }
    filtered
  }
}
