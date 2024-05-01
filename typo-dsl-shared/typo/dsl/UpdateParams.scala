package typo.dsl

case class UpdateParams[Fields, Row](
    where: List[Fields => SqlExpr[Boolean, Required]],
    setters: List[UpdateParams.Setter[Fields, ?, Row]]
) {
  def where(v: Fields => SqlExpr[Boolean, Required]): UpdateParams[Fields, Row] =
    copy(where = where :+ v)

  def set[NT](col: Fields => SqlExpr.FieldLikeNotIdNoHkt[NT, Row], value: Fields => SqlExpr.SqlExprNoHkt[NT]): UpdateParams[Fields, Row] =
    copy(setters = setters :+ UpdateParams.Setter(col, value))
}

object UpdateParams {
  case class Setter[Fields, NT, Row](col: Fields => SqlExpr.FieldLikeNotIdNoHkt[NT, Row], value: Fields => SqlExpr.SqlExprNoHkt[NT])

  def empty[Fields, Row]: UpdateParams[Fields, Row] =
    UpdateParams[Fields, Row](List.empty, List.empty)
}
