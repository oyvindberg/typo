package typo.dsl

trait PgObjetMapper[T] {
  def unmap(pgObject: Any): T
}
object PgObjetMapper {
  def apply[T](implicit ev: PgObjetMapper[T]): PgObjetMapper[T] = ev

  def fromFunction[T](f: Any => T): PgObjetMapper[T] = (pgObject: Any) => f(pgObject)
}
