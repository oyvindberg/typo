package typo

import typo.db.*
import typo.sc.syntax.CodeInterpolator

import java.nio.file.{Files, Path}
import scala.collection.immutable.SortedSet

/** Simplified model of the scala language.
  *
  * The generated code is stored in the `Code` data structure. For full flexibility, some parts are stored as text and other parts in trees. Most notably *all
  * type and term references* which need an import to work should be in a tree.
  *
  * You'll mainly use this module with the `code"..."` interpolator.
  *
  * (It should rather be called `scala`, but let's avoid that name clash)
  */
object sc {
  sealed trait Tree

  case class Ident(value: String) extends Tree {
    def appended(suffix: String) = new Ident(value + suffix)
  }

  object Ident {
    implicit val ordering: Ordering[Ident] = Ordering.by(_.value)

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

  case class QIdent(idents: List[Ident]) extends Tree {
    require(idents.nonEmpty)
    def /(ident: Ident): QIdent = QIdent(idents :+ ident)
    def last = idents.last
  }
  object QIdent {
    implicit val ordering: Ordering[QIdent] = Ordering.by(renderTree)
  }
  case class Param(name: Ident, tpe: Type) extends Tree

  case class StrLit(str: String) extends Tree
  case class StringInterpolate(`import`: sc.Type, prefix: sc.Ident, content: sc.Code) extends Tree

  sealed trait Type extends Tree

  object Type {
    case object Wildcard extends Type
    case class TApply(underlying: Type, targs: List[Type]) extends Type
    case class Qualified(value: QIdent) extends Type

    object Qualified {
      def apply(value: String): Qualified =
        Qualified(QIdent(value.split('.').toList.map(Ident.apply)))
      def apply(value: Ident): Qualified =
        Qualified(QIdent(scala.List(value)))
    }

    val AnyVal = sc.Type.Qualified("scala.AnyVal")
    val OrderingName = sc.Type.Qualified("scala.math.Ordering")
    def Ordering(t: sc.Type) = sc.Type.TApply(OrderingName, scala.List(t))
    val Connection = sc.Type.Qualified("java.sql.Connection")
    val Unit = Qualified("scala.Unit")
    val Int = Qualified("scala.Int")
    val Long = Qualified("scala.Long")
    val String = Qualified("java.lang.String")
    val Boolean = Qualified("scala.Boolean")
    val OptionName = Qualified("scala.Option")
    val ListName = Qualified("scala.List")
    val MapName = Qualified("scala.Map")

    // don't generate imports for these
    val BuiltIn: Map[Ident, QIdent] =
      Set(AnyVal, OrderingName, Unit, Int, Long, String, Boolean, OptionName, ListName, MapName)
        .map(x => (x.value.last, x.value))
        .toMap

    def Option(underlying: Type): Type = TApply(OptionName, scala.List(underlying))
    def List(underlying: Type): Type = TApply(ListName, scala.List(underlying))
    def Map(key: Type, value: Type): Type = TApply(MapName, scala.List(key, value))
  }

  def renderTree(tree: Tree): String = {
    tree match {
      case Ident(value)     => value
      case QIdent(value)    => value.map(renderTree).mkString(".")
      case Param(name, tpe) => renderTree(name) + ": " + renderTree(tpe)
      case StrLit(str)      => '"'.toString + str + '"'.toString
      case tpe: Type =>
        tpe match {
          case Type.Wildcard                  => "_"
          case Type.TApply(underlying, targs) => renderTree(underlying) + targs.map(renderTree).mkString("[", ", ", "]")
          case Type.Qualified(value)          => renderTree(value)
        }
      case StringInterpolate(_, prefix, content) =>
        val Quote = '"'.toString
        s"${renderTree(prefix)}${Quote * 3}${content.render}${Quote * 3}"
    }
  }

  case class File(tpe: Type.Qualified, contents: Code) {
    def name: Ident = tpe.value.last
  }

  @FunctionalInterface
  trait ToCode[T] {
    def toCode(t: T): Code
  }

  object ToCode {
    def apply[T: ToCode]: ToCode[T] = implicitly

    implicit def tree[T <: Tree]: ToCode[T] = Code.Tree.apply
    implicit val str: ToCode[String] = Code.Str.apply
    implicit val code: ToCode[Code] = identity
  }

  object syntax {
    implicit class ToCodeOps[T](private val t: T) extends AnyVal {
      def code(implicit toCode: ToCode[T]): Code = toCode.toCode(t)
    }

    implicit final class CodeInterpolator(private val sc: StringContext) extends AnyVal {
      def code(args: Code*): Code = {
        val fragments = List.newBuilder[Code]
        sc.parts.zipWithIndex.foreach { case (str, n) =>
          if (n > 0) fragments += args(n - 1)
          fragments += Code.Str(StringContext.processEscapes(str))
        }
        Code.Combined(fragments.result())
      }
    }
  }

  /** Semi-structured generated code. We keep all `Tree`s as long as possible so we can write imports based on what is used
    */
  sealed trait Code {
    override def toString: String = sys.error("stringifying code too early loses structure")

    def stripMargin: Code =
      this match {
        case Code.Combined(codes) => Code.Combined(codes.map(_.stripMargin))
        case Code.Str(value)      => Code.Str(value.stripMargin)
        case tree @ Code.Tree(_)  => tree
      }

    def render: String =
      this match {
        case Code.Combined(codes) => codes.map(_.render).mkString
        case Code.Str(str)        => str
        case Code.Tree(tree)      => renderTree(tree)
      }

    def mapTrees(f: Tree => Tree): Code =
      this match {
        case Code.Combined(codes) => Code.Combined(codes.map(_.mapTrees(f)))
        case str @ Code.Str(_)    => str
        case Code.Tree(tree)      => Code.Tree(f(tree))
      }

    def ++(other: Code): Code = Code.Combined(List(this, other))
  }

  object Code {
    case class Combined(codes: Iterable[Code]) extends Code
    case class Str(value: String) extends Code
    case class Tree(value: sc.Tree) extends Code

    // magnet pattern
    implicit def toCode[T: ToCode](x: T): Code = ToCode[T].toCode(x)

    implicit class CodeOps[I[t] <: Iterable[t], C <: Code](private val codes: I[C]) extends AnyVal {
      def mkCode(sep: Code): Code = {
        val interspersed = codes.zipWithIndex.map {
          case (c, 0) => c
          case (c, _) => Combined(List(sep, c))
        }
        Combined(interspersed)
      }
    }
  }
}
