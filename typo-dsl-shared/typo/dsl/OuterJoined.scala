package typo.dsl

class OuterJoined[Fields[_], Row](fields: Fields[Row]) {
  def apply[T, N[_]: Nullability](f: Fields[Row] => SqlExpr[T, N, Row]): SqlExpr[T, Option, Row] =
    f(fields).?
}
