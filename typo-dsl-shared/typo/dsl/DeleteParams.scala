package typo.dsl

case class DeleteParams[Fields[_], Row](
    where: List[Fields[Row] => SqlExpr[Boolean, Required, Row]]
) {
  def where(v: Fields[Row] => SqlExpr[Boolean, Required, Row]): DeleteParams[Fields, Row] =
    copy(where = where :+ v)
}

object DeleteParams {
  def empty[Fields[_], Row]: DeleteParams[Fields, Row] =
    DeleteParams[Fields, Row](List.empty)

  def applyParams[Fields[_], Row](fields: Fields[Row], rows: List[Row], params: DeleteParams[Fields, Row]): List[Row] = {
    val filtered = params.where.foldLeft(rows) { (acc, where) => acc.filter(o => where(fields).eval(o)) }
    filtered
  }
}
