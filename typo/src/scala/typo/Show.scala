package typo

// inlined from cats, this is better for our purposes because it doesn't have instances like for `List`
@FunctionalInterface
trait Show[T] {
  def show(t: T): String
}

object Show {
  def apply[T: Show]: Show[T] = implicitly

  implicit final class ShowInterpolator(private val sc: StringContext) extends AnyVal {
    def show(args: Shown*): String = sc.s(args.map(_.value): _*)
  }

  implicit class ShowOps[T](private val t: T) extends AnyVal {
    def show(implicit show: Show[T]): String = show.show(t)
  }

  implicit val showString: Show[String] = identity[String]
  implicit val showInt: Show[Int] = _.toString

  // magnet pattern
  case class Shown(value: String)
  object Shown {
    implicit def mat[T: Show](x: T): Shown = Shown(Show[T].show(x))
  }
}
