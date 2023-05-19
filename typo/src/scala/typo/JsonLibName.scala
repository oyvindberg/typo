package typo

sealed trait JsonLibName

object JsonLibName {
  case object Circe extends JsonLibName
  case object PlayJson extends JsonLibName
  case object None extends JsonLibName
}
