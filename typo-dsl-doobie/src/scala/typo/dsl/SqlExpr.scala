package typo.dsl

import cats.implicits.toFoldableOps
import doobie.Fragment
import doobie.implicits.toSqlInterpolator
import doobie.util.{Put, Write}

import java.util.concurrent.atomic.AtomicInteger
import scala.reflect.ClassTag

trait SqlExpr[T, N[_], R] extends SqlExpr.SqlExprNoHkt[N[T], R] {
  final def isEqual[N2[_], NC[_]](t: SqlExpr[T, N2, R])(implicit O: Ordering[T], N: Nullability2[N, N2, NC]): SqlExpr[Boolean, NC, R] =
    this === t

  final def isNotEqual[N2[_], NC[_]](t: SqlExpr[T, N2, R])(implicit O: Ordering[T], N: Nullability2[N, N2, NC]): SqlExpr[Boolean, NC, R] =
    this !== t

  final def ===[N2[_], NC[_]](t: SqlExpr[T, N2, R])(implicit O: Ordering[T], N: Nullability2[N, N2, NC]): SqlExpr[Boolean, NC, R] =
    SqlExpr.Binary(this, SqlOperator.eq, t, N)

  final def !==[N2[_], NC[_]](t: SqlExpr[T, N2, R])(implicit O: Ordering[T], N: Nullability2[N, N2, NC]): SqlExpr[Boolean, NC, R] =
    SqlExpr.Binary(this, SqlOperator.neq, t, N)

  final def >[N2[_], NC[_]](t: SqlExpr[T, N2, R])(implicit O: Ordering[T], N: Nullability2[N, N2, NC]): SqlExpr[Boolean, NC, R] =
    SqlExpr.Binary(this, SqlOperator.gt, t, N)

  final def >=[N2[_], NC[_]](t: SqlExpr[T, N2, R])(implicit O: Ordering[T], N: Nullability2[N, N2, NC]): SqlExpr[Boolean, NC, R] =
    SqlExpr.Binary(this, SqlOperator.gte, t, N)

  final def <[N2[_], NC[_]](t: SqlExpr[T, N2, R])(implicit O: Ordering[T], N: Nullability2[N, N2, NC]): SqlExpr[Boolean, NC, R] =
    SqlExpr.Binary(this, SqlOperator.lt, t, N)

  final def <=[N2[_], NC[_]](t: SqlExpr[T, N2, R])(implicit O: Ordering[T], N: Nullability2[N, N2, NC]): SqlExpr[Boolean, NC, R] =
    SqlExpr.Binary(this, SqlOperator.lte, t, N)

  final infix def or[N2[_], NC[_]](other: SqlExpr[T, N2, R])(implicit B: Bijection[T, Boolean], N: Nullability2[N, N2, NC]): SqlExpr[T, NC, R] =
    SqlExpr.Binary(this, SqlOperator.or[T], other, N)

  final infix def and[N2[_], NC[_]](other: SqlExpr[T, N2, R])(implicit B: Bijection[T, Boolean], N: Nullability2[N, N2, NC]): SqlExpr[T, NC, R] =
    SqlExpr.Binary(this, SqlOperator.and[T], other, N)

  def unary_!(implicit B: Bijection[T, Boolean], N: Nullability[N]): SqlExpr[T, N, R] =
    SqlExpr.Not(this, B, N)

  final def +[N2[_], NC[_]](t: SqlExpr[T, N2, R])(implicit Num: Numeric[T], N: Nullability2[N, N2, NC]): SqlExpr[T, NC, R] =
    plus(t)
  final def plus[N2[_], NC[_]](t: SqlExpr[T, N2, R])(implicit Num: Numeric[T], N: Nullability2[N, N2, NC]): SqlExpr[T, NC, R] =
    SqlExpr.Binary(this, SqlOperator.plus, t, N)

  final def -[N2[_], NC[_]](t: SqlExpr[T, N2, R])(implicit Num: Numeric[T], N: Nullability2[N, N2, NC]): SqlExpr[T, NC, R] =
    sub(t)
  final def sub[N2[_], NC[_]](t: SqlExpr[T, N2, R])(implicit Num: Numeric[T], N: Nullability2[N, N2, NC]): SqlExpr[T, NC, R] =
    SqlExpr.Binary(this, SqlOperator.minus, t, N)

  final def *[N2[_], NC[_]](t: SqlExpr[T, N2, R])(implicit Num: Numeric[T], N: Nullability2[N, N2, NC]): SqlExpr[T, NC, R] =
    mul(t)
  final def mul[N2[_], NC[_]](t: SqlExpr[T, N2, R])(implicit Num: Numeric[T], N: Nullability2[N, N2, NC]): SqlExpr[T, NC, R] =
    SqlExpr.Binary(this, SqlOperator.mul, t, N)

  final def underlying[TT](implicit B: Bijection[T, TT], N: Nullability[N]): SqlExpr[TT, N, R] =
    SqlExpr.Underlying(this, B, N)

  final def like(str: String)(implicit B: Bijection[T, String], N: Nullability[N]): SqlExpr[Boolean, N, R] =
    SqlExpr.Binary(this, SqlOperator.like, SqlExpr.asConstRequired(str), N.withRequired)

  final def ||[N2[_], NC[_]](other: SqlExpr[T, N2, R])(implicit B: Bijection[T, String], N: Nullability2[N, N2, NC]): SqlExpr[T, NC, R] =
    stringAppend(other)
  final def stringAppend[N2[_], NC[_]](other: SqlExpr[T, N2, R])(implicit B: Bijection[T, String], N: Nullability2[N, N2, NC]): SqlExpr[T, NC, R] =
    SqlExpr.Binary(this, SqlOperator.strAdd, other, N)

  final def lower(implicit B: Bijection[T, String], N: Nullability[N]): SqlExpr[T, N, R] =
    SqlExpr.Apply1(SqlFunction1.lower, this, N)

  final def reverse(implicit B: Bijection[T, String], N: Nullability[N]): SqlExpr[T, N, R] =
    SqlExpr.Apply1(SqlFunction1.reverse, this, N)

  final def upper(implicit B: Bijection[T, String], N: Nullability[N]): SqlExpr[T, N, R] =
    SqlExpr.Apply1(SqlFunction1.upper, this, N)

  final def strpos[N2[_], NC[_]](substring: SqlExpr[String, N2, R])(implicit B: Bijection[T, String], N: Nullability2[N, N2, NC]): SqlExpr[Int, NC, R] =
    SqlExpr.Apply2(SqlFunction2.strpos, this, substring, N)

  final def strLength(implicit B: Bijection[T, String], N: Nullability[N]): SqlExpr[Int, N, R] =
    SqlExpr.Apply1(SqlFunction1.length, this, N)

  final def substring[N2[_], N3[_], NC[_]](from: SqlExpr[Int, N2, R], count: SqlExpr[Int, N3, R])(implicit
      B: Bijection[T, String],
      N: Nullability3[N, N2, N3, NC]
  ): SqlExpr[T, NC, R] =
    SqlExpr.Apply3(SqlFunction3.substring[T](), this, from, count, N)

  final infix def in(ts: Array[T])(implicit ev: Put[Array[T]], N: Nullability[N]): SqlExpr[Boolean, N, R] =
    SqlExpr.In(this, ts, ev, N)

  final def ?(implicit N: Nullability[N]): SqlExpr[T, Option, R] = opt
  final def opt(implicit N: Nullability[N]): SqlExpr[T, Option, R] = SqlExpr.ToNullable(this, N)
}

object SqlExpr {
  trait SqlExprNoHkt[NT, R] {
    def eval(row: R): NT

    def render(counter: AtomicInteger): Fragment
  }

  sealed trait FieldLikeNoHkt[NT, Row] extends SqlExprNoHkt[NT, Row] {
    private[typo] val prefix: Option[String]
    private[typo] val name: String
    private[typo] val get: Row => NT
    private[typo] val set: (Row, NT) => Row
    private[typo] val sqlReadCast: Option[String]
    private[typo] val sqlWriteCast: Option[String]

    final def value: String = prefix.fold("")(_ + ".") + name
  }

  sealed trait FieldLikeNotIdNoHkt[NT, R] extends FieldLikeNoHkt[NT, R]

  sealed abstract class FieldLike[T, N[_], R](val prefix: Option[String], val name: String, val sqlReadCast: Option[String], val sqlWriteCast: Option[String])(
      val get: R => N[T],
      val set: (R, N[T]) => R
  ) extends SqlExpr[T, N, R]
      with FieldLikeNoHkt[N[T], R] {
    override def eval(row: R): N[T] = get(row)
    override def render(counter: AtomicInteger): Fragment = Fragment.const0(value)
  }

  sealed trait FieldLikeNotId[T, N[_], R] extends FieldLike[T, N, R] with FieldLikeNotIdNoHkt[N[T], R]

  // connect a field `name` to a type `T` for a relation `R`
  class Field[T, R](prefix: Option[String], name: String, sqlReadCast: Option[String] = None, sqlWriteCast: Option[String] = None)(get: R => T, set: (R, T) => R)
      extends FieldLike[T, Required, R](prefix, name, sqlReadCast, sqlWriteCast)(get, set)
      with FieldLikeNotId[T, Required, R]

  class IdField[T, R](prefix: Option[String], name: String, sqlReadCast: Option[String] = None, sqlWriteCast: Option[String] = None)(get: R => T, set: (R, T) => R)
      extends FieldLike[T, Required, R](prefix, name, sqlReadCast, sqlWriteCast)(get, set)

  class OptField[T, R](prefix: Option[String], name: String, sqlReadCast: Option[String] = None, sqlWriteCast: Option[String] = None)(get: R => Option[T], set: (R, Option[T]) => R)
      extends FieldLike[T, Option, R](prefix, name, sqlReadCast, sqlWriteCast)(get, set)
      with FieldLikeNotId[T, Option, R]

  case class Const[T, N[_], R](value: N[T], P: Put[T], W: Write[N[T]]) extends SqlExpr[T, N, R] {
    override def eval(row: R): N[T] =
      value

    override def render(counter: AtomicInteger): Fragment = {
      val cast = P match {
        case _: Put.Basic[_]    => Fragment.empty
        case p: Put.Advanced[_] => Fragment.const0(s"::${p.schemaTypes.head}")
      }
      fr"${W.toFragment(value)}$cast"
    }
  }
  object Const {
    trait As[T, N[_], R] {
      def apply(value: N[T]): SqlExpr.Const[T, N, R]
    }

    object As {
      implicit def as[T, N[_], R](implicit P: Put[T], W: Write[N[T]]): As[T, N, R] =
        (value: N[T]) => SqlExpr.Const(value, P, W)
    }
  }

  case class ArrayIndex[T, N1[_], N2[_], R](arr: SqlExpr[Array[T], N1, R], idx: SqlExpr[Int, N2, R], N: Nullability2[N1, N2, Option]) extends SqlExpr[T, Option, R] {
    override def eval(row: R): Option[T] = {
      N.mapN(arr.eval(row), idx.eval(row)) { (arr, idx) =>
        if (idx < 0 || idx >= arr.length) None
        else Some(arr(idx))
      }.flatten
    }

    override def render(counter: AtomicInteger): Fragment =
      fr"${arr.render(counter)}[${idx.render(counter)}]"
  }

  case class Apply1[T1, O, N[_], R](f: SqlFunction1[T1, O], arg1: SqlExpr[T1, N, R], N: Nullability[N]) extends SqlExpr[O, N, R] {
    override def eval(row: R): N[O] =
      N.mapN(arg1.eval(row))(f.eval)
    override def render(counter: AtomicInteger): Fragment =
      fr"${Fragment.const0(f.name)}(${arg1.render(counter)})"
  }

  case class Apply2[T1, T2, O, N1[_], N2[_], N[_], R](f: SqlFunction2[T1, T2, O], arg1: SqlExpr[T1, N1, R], arg2: SqlExpr[T2, N2, R], N: Nullability2[N1, N2, N]) extends SqlExpr[O, N, R] {
    override def eval(row: R): N[O] =
      N.mapN(arg1.eval(row), arg2.eval(row))(f.eval)
    override def render(counter: AtomicInteger): Fragment =
      fr"${Fragment.const0(f.name)}(${arg1.render(counter)}, ${arg2.render(counter)})"
  }

  case class Apply3[T1, T2, T3, N1[_], N2[_], N3[_], N[_], O, R](
      f: SqlFunction3[T1, T2, T3, O],
      arg1: SqlExpr[T1, N1, R],
      arg2: SqlExpr[T2, N2, R],
      arg3: SqlExpr[T3, N3, R],
      N: Nullability3[N1, N2, N3, N]
  ) extends SqlExpr[O, N, R] {
    override def eval(row: R): N[O] =
      N.mapN(arg1.eval(row), arg2.eval(row), arg3.eval(row))(f.eval)
    override def render(counter: AtomicInteger): Fragment =
      fr"${Fragment.const0(f.name)}(${arg1.render(counter)}, ${arg2.render(counter)}, ${arg3.render(counter)})"
  }

  case class Binary[T1, T2, O, N1[_], N2[_], N[_], R](left: SqlExpr[T1, N1, R], op: SqlOperator[T1, T2, O], right: SqlExpr[T2, N2, R], N: Nullability2[N1, N2, N]) extends SqlExpr[O, N, R] {
    override def eval(row: R): N[O] =
      N.mapN(left.eval(row), right.eval(row))(op.eval)
    override def render(counter: AtomicInteger): Fragment =
      fr"(${left.render(counter)} ${Fragment.const0(op.name)} ${right.render(counter)})"
  }

  case class Underlying[T, TT, N[_], R](expr: SqlExpr[T, N, R], bijection: Bijection[T, TT], N: Nullability[N]) extends SqlExpr[TT, N, R] {
    override def eval(row: R): N[TT] =
      N.mapN(expr.eval(row))(bijection.underlying)
    override def render(counter: AtomicInteger): Fragment =
      expr.render(counter)
  }

  case class Coalesce[T, R](expr: SqlExpr[T, Option, R], getOrElse: SqlExpr[T, Required, R]) extends SqlExpr[T, Required, R] {
    override def eval(row: R): T =
      expr.eval(row).getOrElse(getOrElse.eval(row))
    override def render(counter: AtomicInteger): Fragment =
      fr"coalesce(${expr.render(counter)}, ${getOrElse.render(counter)})"
  }

  case class In[T, N[_], R](expr: SqlExpr[T, N, R], values: Array[T], ev: Put[Array[T]], N: Nullability[N]) extends SqlExpr[Boolean, N, R] {
    override def eval(row: R): N[Boolean] =
      N.mapN(expr.eval(row))(values.contains)

    override def render(counter: AtomicInteger): Fragment =
      fr"${expr.render(counter)} = ANY(${Write.fromPut(ev).toFragment(values)})"
  }

  case class IsNull[T, R](expr: SqlExpr[T, Option, R]) extends SqlExpr[Boolean, Required, R] {
    override def eval(row: R): Boolean =
      expr.eval(row).isEmpty
    override def render(counter: AtomicInteger): Fragment =
      fr"${expr.render(counter)} IS NULL"
  }

  case class Not[T, N[_], R](expr: SqlExpr[T, N, R], B: Bijection[T, Boolean], N: Nullability[N]) extends SqlExpr[T, N, R] {
    override def eval(row: R): N[T] =
      N.mapN(expr.eval(row))(t => B.map(t)(b => !b))

    override def render(counter: AtomicInteger): Fragment =
      fr"NOT ${expr.render(counter)}"
  }

  case class ToNullable[T, R, N1[_]](expr: SqlExpr[T, N1, R], N: Nullability[N1]) extends SqlExpr[T, Option, R] {
    override def eval(row: R): Option[T] =
      N.toOpt(expr.eval(row))
    override def render(counter: AtomicInteger): Fragment =
      expr.render(counter)
  }

  // automatically put values in a constant expression
  implicit def asConstOpt[T, R](t: Option[T])(implicit P: Put[T]): SqlExpr.Const[T, Option, R] =
    Const(t, P, Write.fromPutOption(using P))

  implicit def asConstRequired[T, R](t: T)(implicit P: Put[T]): SqlExpr.Const[T, Required, R] =
    Const[T, Required, R](t, P, Write.fromPut(using P))

  // some syntax to construct field sort order
  implicit class SqlExprSortSyntax[T, N[_], R](private val expr: SqlExpr[T, N, R]) extends AnyVal {
    def asc(implicit O: Ordering[T], N: Nullability[N]): SortOrder[T, N, R] = SortOrder(expr, ascending = true, nullsFirst = false)
    def desc(implicit O: Ordering[T], N: Nullability[N]): SortOrder[T, N, R] = SortOrder(expr, ascending = false, nullsFirst = false)
  }

  final case class RowExpr[R](exprs: List[SqlExpr.SqlExprNoHkt[?, R]]) extends SqlExpr[List[?], Required, R] {
    override def eval(row: R): List[?] = exprs.map(_.eval(row))
    override def render(counter: AtomicInteger): Fragment = fr"(${exprs.map(_.render(counter)).intercalate(fr",")})"
  }

  implicit class SqlExprArraySyntax[T, N[_], R](private val expr: SqlExpr[Array[T], N, R]) extends AnyVal {

    /** look up an element in an array at index `idx` */
    def arrayIndex[N2[_]](idx: SqlExpr[Int, N2, R])(implicit N: Nullability2[N, N2, Option]): SqlExpr[T, Option, R] =
      SqlExpr.ArrayIndex[T, N, N2, R](expr, idx, N)

    /** concatenate two arrays */
    def arrayConcat[N2[_], NC[_]](other: SqlExpr[Array[T], N2, R])(implicit C: ClassTag[T], N: Nullability2[N, N2, NC]): SqlExpr[Array[T], NC, R] =
      SqlExpr.Binary(expr, SqlOperator.arrayConcat, other, N)

    /** does arrays have elements in common */
    def arrayOverlaps[N2[_], NC[_]](other: SqlExpr[Array[T], N2, R])(implicit N: Nullability2[N, N2, NC]): SqlExpr[Boolean, NC, R] =
      SqlExpr.Binary(expr, SqlOperator.arrayOverlaps[Array[T], T], other, N)
  }

  implicit class SqlExprOptionalSyntax[T, R](private val expr: SqlExpr[T, Option, R]) extends AnyVal {
    def isNull: SqlExpr[Boolean, Required, R] =
      SqlExpr.IsNull(expr)
    def coalesce(orElse: SqlExpr[T, Required, R]): SqlExpr[T, Required, R] =
      SqlExpr.Coalesce(expr, orElse)
  }
}
