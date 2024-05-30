package typo.internal

object quote {
  def apply(str: String) = s"'$str'"
  def double(str: String) = s"\"$str\""
}
