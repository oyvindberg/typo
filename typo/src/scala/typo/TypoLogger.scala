package typo

trait TypoLogger {
  def warn(str: String): Unit
  def info(str: String): Unit
}

object TypoLogger {
  object Console extends TypoLogger {
    override def warn(str: String): Unit = System.err.println(s"typo: $str")
    override def info(str: String): Unit = System.out.println(s"typo: $str")
  }

  object Noop extends TypoLogger {
    override def warn(str: String): Unit = ()
    override def info(str: String): Unit = ()
  }
}
