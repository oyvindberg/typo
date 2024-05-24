package typo.dsl

class OuterJoined[Fields](fields: Fields) {
  def apply[T, N[_]: Nullability](f: Fields => SqlExpr[T, N]): SqlExpr[T, Option] =
    f(fields).?
}
