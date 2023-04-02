package typo

sealed trait DbLibName

object DbLibName {
  case object Anorm extends DbLibName
}
