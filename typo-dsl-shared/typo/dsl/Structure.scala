package typo.dsl

/** Expresses that (tuples of) `Fields` structures can be joined.
  *
  * This also serves as the type-level connection between `Fields` and `Row`.
  */
trait Structure[Fields, Row] {
  def fields: Fields
  def columns: List[SqlExpr.FieldLikeNoHkt[?, ?]]
  def _path: List[Path]
  def withPath(path: Path): Structure[Fields, Row]

  // It's up to you to ensure that the `Row` in `field` is the same type as `row`
  def untypedGet[T, N[_]](field: SqlExpr.FieldLike[T, N, ?], row: Row): N[T]

  // It's up to you to ensure that the `Row` in `field` is the same type as `row`
  def untypedEval[TN](expr: SqlExpr.SqlExprNoHkt[TN], row: Row): TN =
    expr match {
      case expr: SqlExpr[t, n] => untypedEval[t, n](expr, row)
    }

  // It's up to you to ensure that the `Row` in `field` is the same type as `row`
  def untypedEval[T, N[_]](expr: SqlExpr[T, N], row: Row): N[T] =
    (expr: @unchecked) match {
      case like: SqlExpr.FieldLike[T, N, row] =>
        untypedGet(like, row)
      case x: SqlExpr.Const[T, N] =>
        x.value
      case x: SqlExpr.ArrayIndex[t, n1, n2] =>
        x.N
          .mapN(untypedEval(x.arr, row), untypedEval(x.idx, row)) { (arr: Array[t], idx: Int) =>
            if (idx < 0 || idx >= arr.length) None
            else Some(arr(idx))
          }
          .flatten
      case SqlExpr.Apply1(f, arg1, n) =>
        n.mapN(untypedEval(arg1, row))(f.eval)
      case SqlExpr.Apply2(f, arg1, arg2, n) =>
        n.mapN(untypedEval(arg1, row), untypedEval(arg2, row))(f.eval)
      case SqlExpr.Apply3(f, arg1, arg2, arg3, n) =>
        n.mapN(untypedEval(arg1, row), untypedEval(arg2, row), untypedEval(arg3, row))(f.eval)
      case SqlExpr.Binary(left, op, right, n) =>
        n.mapN(untypedEval(left, row), untypedEval(right, row))(op.eval)
      case SqlExpr.Underlying(expr, bijection, n) =>
        n.mapN(untypedEval(expr, row))(bijection.underlying)
      case x: SqlExpr.Coalesce[t] =>
        untypedEval(x.expr, row).getOrElse(untypedEval(x.getOrElse, row))
      case SqlExpr.In(expr, values, _, n) =>
        n.mapN(untypedEval(expr, row))(values.contains)
      case x: SqlExpr.IsNull[t] =>
        untypedEval(x.expr, row).isEmpty
      case x: SqlExpr.Not[T, N] =>
        x.N.mapN(untypedEval(x.expr, row))(t => x.B.map(t)(b => !b))
      case x: SqlExpr.ToNullable[t, n] =>
        x.N.toOpt(untypedEval(x.expr, row))
      case x: SqlExpr.RowExpr =>
        x.exprs.map(expr => untypedEval(expr, row))
      case x: SqlExpr.CompositeIn[t, Row] @unchecked /* for 2.13 */ =>
        val thisRow: Seq[?] = x.parts.map(part => untypedEval(part.field, row))
        x.tuples.exists { tuple =>
          val thatRow: Seq[?] = x.parts.map(part => part.extract(tuple))
          thisRow == thatRow
        }
    }

  final def join[Fields2, Row2](other: Structure[Fields2, Row2]): Structure[Fields ~ Fields2, Row ~ Row2] =
    new Structure.Tupled(Structure.sharedPrefix(this._path, other._path), left = this, right = other)

  final def leftJoin[Fields2, Row2](other: Structure[Fields2, Row2]): Structure[Fields ~ OuterJoined[Fields2], Row ~ Option[Row2]] =
    new Structure.LeftTupled(Structure.sharedPrefix(this._path, other._path), left = this, right = other)
}

object Structure {
  def sharedPrefix[T](left: List[T], right: List[T]): List[T] = {
    val prefix = left.zip(right).takeWhile { case (a, b) => a == b }
    prefix.map(_._1)
  }
  // some of the row types are discarded, exchanging some type-safety for a cleaner API
  implicit class FieldOps[T, N[_], R0](private val field: SqlExpr.FieldLike[T, N, R0]) extends AnyVal {
    def castRow[R1]: SqlExpr.FieldLike[T, N, R1] = field.asInstanceOf[SqlExpr.FieldLike[T, N, R1]]
  }

  trait Relation[Fields, Row] extends Structure[Fields, Row] { outer =>
    def copy(path: List[Path]): Relation[Fields, Row]

    override def withPath(newPath: Path): Relation[Fields, Row] =
      copy(path = newPath :: _path)

    override final def untypedGet[T, N[_]](field: SqlExpr.FieldLike[T, N, ?], row: Row): N[T] =
      field.castRow[Row].get(row)
  }

  private class Tupled[Fields1, Fields2, Row1, Row2](val _path: List[Path], val left: Structure[Fields1, Row1], val right: Structure[Fields2, Row2]) extends Structure[Fields1 ~ Fields2, Row1 ~ Row2] {
    override val fields: Fields1 ~ Fields2 =
      (left.fields, right.fields)

    override val columns: List[SqlExpr.FieldLikeNoHkt[?, ?]] =
      left.columns ++ right.columns

    override def untypedGet[T, N[_]](field: SqlExpr.FieldLike[T, N, ?], row: Row1 ~ Row2): N[T] =
      if (left.columns.contains(field)) left.untypedGet(field.castRow, row._1)
      else right.untypedGet(field.castRow, row._2)

    override def withPath(newPath: Path): Tupled[Fields1, Fields2, Row1, Row2] =
      new Tupled(newPath :: _path, left.withPath(newPath), right.withPath(newPath))
  }

  private class LeftTupled[Fields1, Fields2, Row1, Row2](val _path: List[Path], val left: Structure[Fields1, Row1], val right: Structure[Fields2, Row2])
      extends Structure[Fields1 ~ OuterJoined[Fields2], Row1 ~ Option[Row2]] {

    override val fields: Fields1 ~ OuterJoined[Fields2] =
      (left.fields, new OuterJoined(right.fields))

    override val columns: List[SqlExpr.FieldLikeNoHkt[?, ?]] =
      left.columns ++ right.columns

    override def untypedGet[T, N[_]](field: SqlExpr.FieldLike[T, N, ?], row: Row1 ~ Option[Row2]): N[T] =
      if (left.columns.contains(field)) left.untypedGet(field, row._1)
      else {
        val option: Option[N[T]] = row._2.map(v => right.untypedGet(field, v))
        val flattened: Option[Any] = (option: Option[?]) match {
          case Some(Some(t)) => Some(t)
          case Some(None)    => None
          case Some(t)       => Some(t)
          case None          => None
        }
        flattened.asInstanceOf[N[T]]
      }

    override def withPath(newPath: Path): LeftTupled[Fields1, Fields2, Row1, Row2] =
      new LeftTupled(newPath :: _path, left.withPath(newPath), right.withPath(newPath))
  }
}
