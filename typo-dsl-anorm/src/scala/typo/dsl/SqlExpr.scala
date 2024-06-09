package typo.dsl

import anorm.{NamedParameter, ParameterMetaData, ParameterValue, ToParameterValue}
import typo.dsl.Fragment.FragmentStringInterpolator

import java.util.concurrent.atomic.AtomicInteger
import scala.reflect.ClassTag

sealed trait SqlExpr[T, N[_]] extends SqlExpr.SqlExprNoHkt[N[T]] {
  final def isEqual[N2[_], NC[_]](t: SqlExpr[T, N2])(implicit O: Ordering[T], N: Nullability2[N, N2, NC]): SqlExpr[Boolean, NC] =
    this === t

  final def isNotEqual[N2[_], NC[_]](t: SqlExpr[T, N2])(implicit O: Ordering[T], N: Nullability2[N, N2, NC]): SqlExpr[Boolean, NC] =
    this !== t

  final def ===[N2[_], NC[_]](t: SqlExpr[T, N2])(implicit O: Ordering[T], N: Nullability2[N, N2, NC]): SqlExpr[Boolean, NC] =
    SqlExpr.Binary(this, SqlOperator.eq, t, N)

  final def !==[N2[_], NC[_]](t: SqlExpr[T, N2])(implicit O: Ordering[T], N: Nullability2[N, N2, NC]): SqlExpr[Boolean, NC] =
    SqlExpr.Binary(this, SqlOperator.neq, t, N)

  final def >[N2[_], NC[_]](t: SqlExpr[T, N2])(implicit O: Ordering[T], N: Nullability2[N, N2, NC]): SqlExpr[Boolean, NC] =
    SqlExpr.Binary(this, SqlOperator.gt, t, N)

  final def >=[N2[_], NC[_]](t: SqlExpr[T, N2])(implicit O: Ordering[T], N: Nullability2[N, N2, NC]): SqlExpr[Boolean, NC] =
    SqlExpr.Binary(this, SqlOperator.gte, t, N)

  final def <[N2[_], NC[_]](t: SqlExpr[T, N2])(implicit O: Ordering[T], N: Nullability2[N, N2, NC]): SqlExpr[Boolean, NC] =
    SqlExpr.Binary(this, SqlOperator.lt, t, N)

  final def <=[N2[_], NC[_]](t: SqlExpr[T, N2])(implicit O: Ordering[T], N: Nullability2[N, N2, NC]): SqlExpr[Boolean, NC] =
    SqlExpr.Binary(this, SqlOperator.lte, t, N)

  final infix def or[N2[_], NC[_]](other: SqlExpr[T, N2])(implicit B: Bijection[T, Boolean], N: Nullability2[N, N2, NC]): SqlExpr[T, NC] =
    SqlExpr.Binary(this, SqlOperator.or[T], other, N)

  final infix def and[N2[_], NC[_]](other: SqlExpr[T, N2])(implicit B: Bijection[T, Boolean], N: Nullability2[N, N2, NC]): SqlExpr[T, NC] =
    SqlExpr.Binary(this, SqlOperator.and[T], other, N)

  def unary_!(implicit B: Bijection[T, Boolean], N: Nullability[N]): SqlExpr[T, N] =
    SqlExpr.Not(this, B, N)

  final def +[N2[_], NC[_]](t: SqlExpr[T, N2])(implicit Num: Numeric[T], N: Nullability2[N, N2, NC]): SqlExpr[T, NC] =
    plus(t)
  final def plus[N2[_], NC[_]](t: SqlExpr[T, N2])(implicit Num: Numeric[T], N: Nullability2[N, N2, NC]): SqlExpr[T, NC] =
    SqlExpr.Binary(this, SqlOperator.plus, t, N)

  final def -[N2[_], NC[_]](t: SqlExpr[T, N2])(implicit Num: Numeric[T], N: Nullability2[N, N2, NC]): SqlExpr[T, NC] =
    sub(t)
  final def sub[N2[_], NC[_]](t: SqlExpr[T, N2])(implicit Num: Numeric[T], N: Nullability2[N, N2, NC]): SqlExpr[T, NC] =
    SqlExpr.Binary(this, SqlOperator.minus, t, N)

  final def *[N2[_], NC[_]](t: SqlExpr[T, N2])(implicit Num: Numeric[T], N: Nullability2[N, N2, NC]): SqlExpr[T, NC] =
    mul(t)
  final def mul[N2[_], NC[_]](t: SqlExpr[T, N2])(implicit Num: Numeric[T], N: Nullability2[N, N2, NC]): SqlExpr[T, NC] =
    SqlExpr.Binary(this, SqlOperator.mul, t, N)

  final def underlying[TT](implicit B: Bijection[T, TT], N: Nullability[N]): SqlExpr[TT, N] =
    SqlExpr.Underlying(this, B, N)

  final def like(str: String)(implicit B: Bijection[T, String], N: Nullability[N]): SqlExpr[Boolean, N] =
    SqlExpr.Binary(this, SqlOperator.like, SqlExpr.asConstRequired(str), N.withRequired)

  final def ||[N2[_], NC[_]](other: SqlExpr[T, N2])(implicit B: Bijection[T, String], N: Nullability2[N, N2, NC]): SqlExpr[T, NC] =
    stringAppend(other)
  final def stringAppend[N2[_], NC[_]](other: SqlExpr[T, N2])(implicit B: Bijection[T, String], N: Nullability2[N, N2, NC]): SqlExpr[T, NC] =
    SqlExpr.Binary(this, SqlOperator.strAdd, other, N)

  final def lower(implicit B: Bijection[T, String], N: Nullability[N]): SqlExpr[T, N] =
    SqlExpr.Apply1(SqlFunction1.lower, this, N)

  final def reverse(implicit B: Bijection[T, String], N: Nullability[N]): SqlExpr[T, N] =
    SqlExpr.Apply1(SqlFunction1.reverse, this, N)

  final def upper(implicit B: Bijection[T, String], N: Nullability[N]): SqlExpr[T, N] =
    SqlExpr.Apply1(SqlFunction1.upper, this, N)

  final def strpos[N2[_], NC[_]](substring: SqlExpr[String, N2])(implicit B: Bijection[T, String], N: Nullability2[N, N2, NC]): SqlExpr[Int, NC] =
    SqlExpr.Apply2(SqlFunction2.strpos, this, substring, N)

  final def strLength(implicit B: Bijection[T, String], N: Nullability[N]): SqlExpr[Int, N] =
    SqlExpr.Apply1(SqlFunction1.length, this, N)

  final def substring[N2[_], N3[_], NC[_]](from: SqlExpr[Int, N2], count: SqlExpr[Int, N3])(implicit
      B: Bijection[T, String],
      N: Nullability3[N, N2, N3, NC]
  ): SqlExpr[T, NC] =
    SqlExpr.Apply3(SqlFunction3.substring[T](), this, from, count, N)

  final infix def in(ts: Array[T])(implicit ev: ToParameterValue[Array[T]], N: Nullability[N]): SqlExpr[Boolean, N] =
    SqlExpr.In(this, ts, ev, N)

  final def ?(implicit N: Nullability[N]): SqlExpr[T, Option] = opt
  final def opt(implicit N: Nullability[N]): SqlExpr[T, Option] = SqlExpr.ToNullable(this, N)
}

object SqlExpr {
  sealed trait SqlExprNoHkt[NT] {
    def render(ctx: RenderCtx, counter: AtomicInteger): Fragment
  }

  sealed trait FieldLikeNoHkt[NT, Row] extends SqlExprNoHkt[NT] with Product {
    val path: List[Path]
    val name: String
    val get: Row => NT
    val set: (Row, NT) => Row
    val sqlReadCast: Option[String]
    val sqlWriteCast: Option[String]

    final def value(ctx: RenderCtx): String = ctx.alias.get(path).fold("")(_ + ".") + name
    final def render(ctx: RenderCtx, counter: AtomicInteger): Fragment = Fragment(value(ctx))
  }

  sealed trait FieldLikeNotIdNoHkt[NT, R] extends FieldLikeNoHkt[NT, R]

  sealed trait FieldLike[T, N[_], R] extends SqlExpr[T, N] with FieldLikeNoHkt[N[T], R] with Product {
    val path: List[Path]
    val name: String
    val sqlReadCast: Option[String]
    val sqlWriteCast: Option[String]
    val get: R => N[T]
    val set: (R, N[T]) => R
  }

  sealed trait FieldLikeNotId[T, N[_], R] extends FieldLike[T, N, R] with FieldLikeNotIdNoHkt[N[T], R]

  // connect a field `name` to a type `T` for a relation `R`
  case class Field[T, R](path: List[Path], name: String, sqlReadCast: Option[String], sqlWriteCast: Option[String], get: R => T, set: (R, T) => R) extends FieldLikeNotId[T, Required, R]

  case class IdField[T, R](path: List[Path], name: String, sqlReadCast: Option[String], sqlWriteCast: Option[String], get: R => T, set: (R, T) => R) extends FieldLike[T, Required, R]

  case class OptField[T, R](path: List[Path], name: String, sqlReadCast: Option[String], sqlWriteCast: Option[String], get: R => Option[T], set: (R, Option[T]) => R)
      extends FieldLikeNotId[T, Option, R]

  case class Const[T, N[_]](value: N[T], T: ToParameterValue[N[T]], P: ParameterMetaData[T]) extends SqlExpr[T, N] {
    override def render(ctx: RenderCtx, counter: AtomicInteger): Fragment = {
      val paramName = s"param${counter.incrementAndGet()}"
      Fragment(List(NamedParameter(paramName, T(value))), s"{$paramName}::${P.sqlType}")
    }
  }

  object Const {
    trait As[T, N[_]] {
      def apply(value: N[T]): SqlExpr.Const[T, N]
    }

    object As {
      implicit def as[T, N[_]](implicit T: ToParameterValue[N[T]], P: ParameterMetaData[T]): As[T, N] =
        (value: N[T]) => SqlExpr.Const(value, T, P)
    }
  }

  case class ArrayIndex[T, N1[_], N2[_]](arr: SqlExpr[Array[T], N1], idx: SqlExpr[Int, N2], N: Nullability2[N1, N2, Option]) extends SqlExpr[T, Option] {
    override def render(ctx: RenderCtx, counter: AtomicInteger): Fragment =
      frag"${arr.render(ctx, counter)}[${idx.render(ctx, counter)}]"
  }

  case class Apply1[T1, O, N[_]](f: SqlFunction1[T1, O], arg1: SqlExpr[T1, N], N: Nullability[N]) extends SqlExpr[O, N] {
    override def render(ctx: RenderCtx, counter: AtomicInteger): Fragment =
      frag"${Fragment(f.name)}(${arg1.render(ctx, counter)})"
  }

  case class Apply2[T1, T2, O, N1[_], N2[_], N[_]](f: SqlFunction2[T1, T2, O], arg1: SqlExpr[T1, N1], arg2: SqlExpr[T2, N2], N: Nullability2[N1, N2, N]) extends SqlExpr[O, N] {
    override def render(ctx: RenderCtx, counter: AtomicInteger): Fragment =
      frag"${Fragment(f.name)}(${arg1.render(ctx, counter)}, ${arg2.render(ctx, counter)})"
  }

  case class Apply3[T1, T2, T3, N1[_], N2[_], N3[_], N[_], O](
      f: SqlFunction3[T1, T2, T3, O],
      arg1: SqlExpr[T1, N1],
      arg2: SqlExpr[T2, N2],
      arg3: SqlExpr[T3, N3],
      N: Nullability3[N1, N2, N3, N]
  ) extends SqlExpr[O, N] {
    override def render(ctx: RenderCtx, counter: AtomicInteger): Fragment =
      frag"${Fragment(f.name)}(${arg1.render(ctx, counter)}, ${arg2.render(ctx, counter)}, ${arg3.render(ctx, counter)})"
  }

  case class Binary[T1, T2, O, N1[_], N2[_], N[_]](left: SqlExpr[T1, N1], op: SqlOperator[T1, T2, O], right: SqlExpr[T2, N2], N: Nullability2[N1, N2, N]) extends SqlExpr[O, N] {
    override def render(ctx: RenderCtx, counter: AtomicInteger): Fragment =
      frag"(${left.render(ctx, counter)} ${Fragment(op.name)} ${right.render(ctx, counter)})"
  }

  case class Underlying[T, TT, N[_]](expr: SqlExpr[T, N], bijection: Bijection[T, TT], N: Nullability[N]) extends SqlExpr[TT, N] {
    override def render(ctx: RenderCtx, counter: AtomicInteger): Fragment =
      expr.render(ctx, counter)
  }

  case class Coalesce[T](expr: SqlExpr[T, Option], getOrElse: SqlExpr[T, Required]) extends SqlExpr[T, Required] {
    override def render(ctx: RenderCtx, counter: AtomicInteger): Fragment =
      frag"coalesce(${expr.render(ctx, counter)}, ${getOrElse.render(ctx, counter)})"
  }

  case class In[T, N[_]](expr: SqlExpr[T, N], values: Array[T], ev: ToParameterValue[Array[T]], N: Nullability[N]) extends SqlExpr[Boolean, N] {
    override def render(ctx: RenderCtx, counter: AtomicInteger): Fragment = {
      val paramName = s"param${counter.incrementAndGet()}"
      val np = NamedParameter(paramName, ParameterValue.from(values)(ev))
      frag"${expr.render(ctx, counter)} = ANY({${Fragment(List(np), paramName)}})"
    }
  }

  case class CompositeIn[Tuple, Row](tuples: Array[Tuple])(val parts: CompositeIn.TuplePart[Tuple, ?, Row]*) extends SqlExpr[Boolean, Required] {
    override def render(ctx: RenderCtx, counter: AtomicInteger): Fragment = {
      val fieldNames: Seq[Fragment] =
        parts.map(part => frag"${part.field.render(ctx, counter)}")

      val unnests: Seq[Fragment] =
        parts.map { case part: CompositeIn.TuplePart[Tuple, t, Row] =>
          val partExpr: Const[Array[t], Required] = part.asConst(tuples.map(part.extract)(using part.CT))
          frag"unnest(${partExpr.render(ctx, counter)})"
        }

      frag"(${fieldNames.mkFragment(", ")}) in (select ${unnests.mkFragment(", ")})"
    }
  }

  object CompositeIn {
    case class TuplePart[Tuple, T, Row](field: FieldLike[T, Required, Row])(val extract: Tuple => T)(implicit val asConst: Const.As[Array[T], Required], val CT: ClassTag[T])
  }

  case class RowExpr(exprs: List[SqlExpr.SqlExprNoHkt[?]]) extends SqlExpr[List[?], Required] {
    override def render(ctx: RenderCtx, counter: AtomicInteger): Fragment =
      frag"(" ++ exprs.map(_.render(ctx, counter)).mkFragment(",") ++ frag")"
  }

  final case class IsNull[T](expr: SqlExpr[T, Option]) extends SqlExpr[Boolean, Required] {
    override def render(ctx: RenderCtx, counter: AtomicInteger): Fragment =
      frag"${expr.render(ctx, counter)} IS NULL"
  }

  case class Not[T, N[_]](expr: SqlExpr[T, N], B: Bijection[T, Boolean], N: Nullability[N]) extends SqlExpr[T, N] {
    override def render(ctx: RenderCtx, counter: AtomicInteger): Fragment =
      frag"NOT ${expr.render(ctx, counter)}"
  }

  case class ToNullable[T, N[_]](expr: SqlExpr[T, N], N: Nullability[N]) extends SqlExpr[T, Option] {
    override def render(ctx: RenderCtx, counter: AtomicInteger): Fragment =
      expr.render(ctx, counter)
  }

  // automatically put values in a constant expression
  implicit def asConstOpt[T, R](t: Option[T])(implicit T: ToParameterValue[Option[T]], P: ParameterMetaData[T]): SqlExpr.Const[T, Option] =
    Const(t, T, P)

  implicit def asConstRequired[T, R](t: T)(implicit T: ToParameterValue[T], P: ParameterMetaData[T]): SqlExpr.Const[T, Required] =
    Const[T, Required](t, T, P)

  // some syntax to construct field sort order
  implicit class SqlExprSortSyntax[T, N[_]](private val expr: SqlExpr[T, N]) extends AnyVal {
    def asc(implicit O: Ordering[T], N: Nullability[N]): SortOrder[T, N] = SortOrder(expr, ascending = true, nullsFirst = false)
    def desc(implicit O: Ordering[T], N: Nullability[N]): SortOrder[T, N] = SortOrder(expr, ascending = false, nullsFirst = false)
  }

  implicit class SqlExprArraySyntax[T, N[_]](private val expr: SqlExpr[Array[T], N]) extends AnyVal {

    /** look up an element in an array at index `idx` */
    def arrayIndex[N2[_]](idx: SqlExpr[Int, N2])(implicit N: Nullability2[N, N2, Option]): SqlExpr[T, Option] =
      SqlExpr.ArrayIndex[T, N, N2](expr, idx, N)

    /** concatenate two arrays */
    def arrayConcat[N2[_], NC[_]](other: SqlExpr[Array[T], N2])(implicit C: ClassTag[T], N: Nullability2[N, N2, NC]): SqlExpr[Array[T], NC] =
      SqlExpr.Binary(expr, SqlOperator.arrayConcat, other, N)

    /** does arrays have elements in common */
    def arrayOverlaps[N2[_], NC[_]](other: SqlExpr[Array[T], N2])(implicit N: Nullability2[N, N2, NC]): SqlExpr[Boolean, NC] =
      SqlExpr.Binary(expr, SqlOperator.arrayOverlaps[Array[T], T], other, N)
  }

  implicit class SqlExprOptionalSyntax[T](private val expr: SqlExpr[T, Option]) extends AnyVal {
    def isNull: SqlExpr[Boolean, Required] =
      SqlExpr.IsNull(expr)
    def coalesce(orElse: SqlExpr[T, Required]): SqlExpr[T, Required] =
      SqlExpr.Coalesce(expr, orElse)
  }
}
