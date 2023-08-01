package typo.dsl

import scala.reflect.ClassTag

case class SqlOperator[I1, I2, O](name: String, eval: (I1, I2) => O)

object SqlOperator {
  def or[T](implicit B: Bijection[T, Boolean]) =
    new SqlOperator[T, T, T]("OR", (i1, i2) => B.map(i1)(b1 => b1 || B.underlying(i2)))
  def and[T](implicit B: Bijection[T, Boolean]) =
    new SqlOperator[T, T, T]("AND", (i1, i2) => B.map(i1)(b1 => b1 && B.underlying(i2)))
  def eq[T](implicit O: Ordering[T]) =
    new SqlOperator[T, T, Boolean]("=", O.equiv)
  def neq[T](implicit O: Ordering[T]) =
    new SqlOperator[T, T, Boolean]("!=", (i1, i2) => !O.equiv(i1, i2))
  def gt[T](implicit N: Ordering[T]) =
    new SqlOperator[T, T, Boolean](">", N.gt)
  def gte[T](implicit N: Ordering[T]) =
    new SqlOperator[T, T, Boolean](">=", N.gteq)
  def lt[T](implicit N: Ordering[T]) =
    new SqlOperator[T, T, Boolean]("<", N.lt)
  def lte[T](implicit N: Ordering[T]) =
    new SqlOperator[T, T, Boolean]("<=", N.lteq)
  def plus[T](implicit N: Numeric[T]) =
    new SqlOperator[T, T, T]("+", N.plus)
  def minus[T](implicit N: Numeric[T]) =
    new SqlOperator[T, T, T]("-", N.minus)
  def mul[T](implicit N: Numeric[T]) =
    new SqlOperator[T, T, T]("*", N.times)
  def like[T](implicit B: Bijection[T, String]) =
    new SqlOperator[T, String, Boolean]("LIKE", (i1, i2) => i2.contains(B.underlying(i1)))
  def strAdd[T](implicit B: Bijection[T, String]) =
    new SqlOperator[T, T, T]("||", (i1, i2) => B.map(i1)(_ + B.underlying(i2)))
  def arrayConcat[T, U: ClassTag](implicit B: Bijection[T, Array[U]]) =
    new SqlOperator[T, T, T]("||", (i1, i2) => B.map(i1)(_ ++ B.underlying(i2)))
  def arrayOverlaps[T, U](implicit B: Bijection[T, Array[U]]) =
    new SqlOperator[T, T, Boolean]("&&", (i1, i2) => B.underlying(i1).exists(B.underlying(i2).toSet))
}
