package typo.dsl

import scala.annotation.implicitNotFound

/** Abstract over [[Required]] and [[Option]]. This is used to abstract over the nullability of a field.
  */
trait Nullability[N1[_]] { outer =>
  def toOpt[T1](t1: N1[T1]): Option[T1]
  def mapN[T1, R](t1: N1[T1])(f: T1 => R): N1[R]

  final def withRequired: Nullability2[N1, Required, N1] =
    new Nullability2[N1, Required, N1] {
      override def mapN[T1, T2, R](t1: N1[T1], t2: Required[T2])(f: (T1, T2) => R): N1[R] = outer.mapN(t1)(f(_, t2))
    }

  final def withOption: Nullability2[N1, Option, Option] =
    new Nullability2[N1, Option, Option] {
      override def mapN[T1, T2, R](t1: N1[T1], t2: Option[T2])(f: (T1, T2) => R): Option[R] =
        for {
          t1 <- outer.toOpt(t1)
          t2 <- t2
        } yield (f(t1, t2))
    }
}

object Nullability {
  def apply[N1[_]](implicit ev: Nullability[N1]): Nullability[N1] = ev

  implicit val required: Nullability[Required] =
    new Nullability[Required] {
      override def toOpt[T1](t1: Required[T1]): Option[T1] = Some(t1)
      override def mapN[T1, R](t1: T1)(f: T1 => R): R = f(t1)
    }

  implicit val opt: Nullability[Option] =
    new Nullability[Option] {
      override def toOpt[T1](t1: Option[T1]): Option[T1] = t1
      override def mapN[T1, R](t1: Option[T1])(f: T1 => R): Option[R] = t1.map(f)
    }
}

/** This binary variant is used to compute the nullability of a field that is the result of a binary operator or function.
  * @tparam N1
  *   nullability of first argument
  * @tparam N2
  *   nullability of second argument
  * @tparam Next
  *   nullability of applying arguments to function
  */
@implicitNotFound(
  "Cannot compute nullability of ${Next} from ${N1} and ${N2}. If you're in a context where an a non-nullable sql expression is expected, you can use `expr.coalesce(...)` to provide a fallback value"
)
trait Nullability2[N1[_], N2[_], Next[_]] {
  def mapN[T1, T2, R](t1: N1[T1], t2: N2[T2])(f: (T1, T2) => R): Next[R]
}

trait Nullability2Lower {
  implicit def opt[N1[_]: Nullability, N2[_]: Nullability]: Nullability2[N1, N2, Option] =
    new Nullability2[N1, N2, Option] {
      override def mapN[T1, T2, R](t1: N1[T1], t2: N2[T2])(f: (T1, T2) => R): Option[R] = {
        for {
          t1 <- Nullability[N1].toOpt(t1)
          t2 <- Nullability[N2].toOpt(t2)
        } yield f(t1, t2)
      }
    }
}

object Nullability2 extends Nullability2Lower {
  implicit val required: Nullability2[Required, Required, Required] =
    new Nullability2[Required, Required, Required] {
      override def mapN[T1, T2, R](t1: T1, t2: T2)(f: (T1, T2) => R): R = f(t1, t2)
    }
}

/** This trinary variant is used to compute the nullability of a field that is the result of a function.
  * @tparam N1
  *   nullability of first argument
  * @tparam N2
  *   nullability of second argument
  * @tparam N3
  *   nullability of third argument
  * @tparam Next
  *   nullability of applying arguments to function
  */
@implicitNotFound(
  "Cannot compute nullability of ${Next} from ${N1}, ${N2} and ${N3}. If you're in a context where an a non-nullable sql expression is expected, you can use `expr.coalesce(...)` to provide a fallback value. You can use `expr.?` to go the other direction"
)
trait Nullability3[N1[_], N2[_], N3[_], Next[_]] {
  def mapN[T1, T2, T3, R](t1: N1[T1], t2: N2[T2], t3: N3[T3])(f: (T1, T2, T3) => R): Next[R]
}

trait Nullability3Lower {
  implicit def opt[N1[_]: Nullability, N2[_]: Nullability, N3[_]: Nullability]: Nullability3[N1, N2, N3, Option] =
    new Nullability3[N1, N2, N3, Option] {
      override def mapN[T1, T2, T3, R](t1: N1[T1], t2: N2[T2], t3: N3[T3])(f: (T1, T2, T3) => R): Option[R] = {
        for {
          t1 <- Nullability[N1].toOpt(t1)
          t2 <- Nullability[N2].toOpt(t2)
          t3 <- Nullability[N3].toOpt(t3)
        } yield f(t1, t2, t3)
      }
    }
}

object Nullability3 extends Nullability3Lower {
  implicit val required: Nullability3[Required, Required, Required, Required] =
    new Nullability3[Required, Required, Required, Required] {
      override def mapN[T1, T2, T3, R](t1: T1, t2: T2, t3: T3)(f: (T1, T2, T3) => R): R = f(t1, t2, t3)
    }
}
