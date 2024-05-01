package typo.dsl

case class DeleteParams[Fields](where: List[Fields => SqlExpr[Boolean, Required]]) {
  def where(v: Fields => SqlExpr[Boolean, Required]): DeleteParams[Fields] =
    copy(where = where :+ v)
}

object DeleteParams {
  def empty[Fields]: DeleteParams[Fields] =
    DeleteParams[Fields](List.empty)
}
