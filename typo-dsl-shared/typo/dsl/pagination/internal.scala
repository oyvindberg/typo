package typo.dsl.pagination

object internal {
  // avoid cats dependency
  implicit class ListOps[T](private val list: List[T]) extends AnyVal {
    def traverse[U](f: T => Either[String, U]): Either[String, List[U]] = {
      val it = list.iterator
      val result = List.newBuilder[U]
      while (it.hasNext) {
        f(it.next()) match {
          case Left(e)  => return Left(e)
          case Right(u) => result += u
        }
      }
      Right(result.result())
    }
  }
}
