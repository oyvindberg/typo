package typo

import typo.Show.*
import typo.db.*

import java.nio.file.{Files, Path}

object s {
  case class Ident(value: String) extends AnyVal {
    def appended(suffix: String) = new Ident(value + suffix)
  }

  object Ident {
    implicit val shows: Show[Ident] = _.value

    private def camelCase(name: ColName): Ident =
      Ident(
        name.value
          .split('_')
          .zipWithIndex
          .map {
            case (s, 0) => s
            case (s, _) => s.capitalize
          }
          .mkString("")
      )

    def titleCase(name: String): Ident =
      Ident(name.split('_').map(_.capitalize).mkString(""))

    def field(name: ColName): Ident =
      camelCase(name)

    def id(name: TableName): Ident =
      titleCase(name.value).appended("Id")

    def repo(name: TableName): Ident =
      titleCase(name.value).appended("Repo")

    def repoImpl(name: TableName): Ident =
      titleCase(name.value).appended("RepoImpl")

    def repoMock(name: TableName): Ident =
      titleCase(name.value).appended("RepoMock")

    def row(name: TableName): Ident =
      titleCase(name.value).appended("Row")

    def rowUnsaved(name: TableName): Ident =
      titleCase(name.value).appended("RowUnsaved")

    def fieldValue(name: TableName): Ident =
      titleCase(name.value).appended("FieldValue")

    def `enum`(name: EnumName): Ident =
      titleCase(name.value).appended("Enum")

    def enumValue(name: String): Ident = Ident(name)
  }

  case class QIdent(value: List[Ident]) extends AnyVal {
    def /(ident: Ident): QIdent = QIdent(value :+ ident)
  }

  object QIdent {
    implicit val shows: Show[QIdent] = _.value.map(_.show).mkString(".")
  }

  case class Param(name: Ident, tpe: Type)

  object Param {
    implicit val shows: Show[Param] = { case Param(name, tpe) =>
      show"$name: $tpe"
    }
  }

  case class StrLit(str: String)

  object StrLit {
    implicit val shows: Show[StrLit] = { case StrLit(str) =>
      '"'.toString + str + '"'.toString
    }
  }

  sealed trait Type

  object Type {
    case object Wildcard extends Type
    case object Int extends Type
    case object Long extends Type
    case object String extends Type
    case object Boolean extends Type
    case class Option(underlying: Type) extends Type
    case class List(underlying: Type) extends Type
    case class TApply(underlying: Type, targs: scala.List[Type]) extends Type
    case class Qualified(value: QIdent) extends Type

    object Qualified {
      def apply(value: Ident): Qualified =
        Qualified(QIdent(scala.List(value)))
    }

    implicit val shows: Show[Type] = {
      case Wildcard                  => "_"
      case Int                       => "Int"
      case Long                      => "Long"
      case String                    => "String"
      case Boolean                   => "Boolean"
      case Option(underlying)        => show"Option[$underlying]"
      case List(underlying)          => show"List[$underlying]"
      case Qualified(qname)          => qname.show
      case TApply(underlying, targs) => show"$underlying[${targs.map(shows.show).mkString(", ")}]"
    }
  }
}
