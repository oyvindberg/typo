package typo

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
  }

  case class QIdent(idents: List[Ident]) extends Tree {
    require(idents.nonEmpty)
    def /(ident: Ident): QIdent = QIdent(idents :+ ident)
    def name = idents.last
  }
  object QIdent {
    implicit val ordering: Ordering[QIdent] = Ordering.by(renderTree)
  }
  case class Param(name: Ident, tpe: Type) extends Tree

  case class StrLit(str: String) extends Tree
  case class StringInterpolate(`import`: sc.Type, prefix: sc.Ident, content: sc.Code) extends Tree

  sealed trait Type extends Tree {
    def of(targs: Type*): Type = Type.TApply(this, targs.toList)
    def withComment(str: String): Type = Type.Commented(this, s"/* $str */")
  }

  object Type {
    case object Wildcard extends Type
    case class TApply(underlying: Type, targs: List[Type]) extends Type
    case class Qualified(value: QIdent) extends Type
    case class Abstract(value: Ident) extends Type
    case class Commented(underlying: Type, comment: String) extends Type

    object Qualified {
      def apply(value: String): Qualified =
        Qualified(QIdent(value.split('.').toList.map(Ident.apply)))
      def apply(value: Ident): Qualified =
        Qualified(QIdent(scala.List(value)))
    }

    val ZonedDateTime = sc.Type.Qualified("java.time.ZonedDateTime")
    val LocalDate = sc.Type.Qualified("java.time.LocalDate")
    val LocalDateTime = sc.Type.Qualified("java.time.LocalDateTime")
    val LocalTime = sc.Type.Qualified("java.time.LocalTime")
    val AnyVal = sc.Type.Qualified("scala.AnyVal")
    val Any = sc.Type.Qualified("scala.Any")
    val BigDecimal = sc.Type.Qualified("scala.math.BigDecimal")
    val Byte = sc.Type.Qualified("scala.Byte")
    val Short = sc.Type.Qualified("scala.Short")
    val Array = sc.Type.Qualified("scala.Array")
    val Ordering = sc.Type.Qualified("scala.math.Ordering")
    val Connection = sc.Type.Qualified("java.sql.Connection")
    val PreparedStatement = sc.Type.Qualified("java.sql.PreparedStatement")
    val Types = sc.Type.Qualified("java.sql.Types")
    val Unit = Qualified("scala.Unit")
    val Int = Qualified("scala.Int")
    val Double = Qualified("scala.Double")
    val UUID = Qualified("java.util.UUID")
    val Long = Qualified("scala.Long")
    val String = Qualified("java.lang.String")
    val Float = Qualified("scala.Float")
    val Boolean = Qualified("scala.Boolean")
    val Option = Qualified("scala.Option")
    val None = Qualified("scala.None")
    val Some = Qualified("scala.Some")
    val List = Qualified("scala.List")
    val Map = Qualified("scala.collection.immutable.Map")
    val Either = Qualified("scala.Either")
    val Left = Qualified("scala.Left")
    val Right = Qualified("scala.Right")
    val Try = Qualified("scala.util.Try")
    val JavaMap = Qualified("java.util.Map")
    val MapHasAsJava = Qualified("scala.jdk.CollectionConverters.MapHasAsJava")
    val MapHasAsScala = Qualified("scala.jdk.CollectionConverters.MapHasAsScala")
    val StringContext = Qualified("java.StringContext")
    val PGobject = Qualified("org.postgresql.util.PGobject")
    val PGbox = Qualified("org.postgresql.geometric.PGbox")
    val PGcircle = Qualified("org.postgresql.geometric.PGcircle")
    val PGline = Qualified("org.postgresql.geometric.PGline")
    val PGlseg = Qualified("org.postgresql.geometric.PGlseg")
    val PGpath = Qualified("org.postgresql.geometric.PGpath")
    val PGpoint = Qualified("org.postgresql.geometric.PGpoint")
    val PGpolygon = Qualified("org.postgresql.geometric.PGpolygon")
    val PGInterval = Qualified("org.postgresql.util.PGInterval")
    val PGmoney = Qualified("org.postgresql.util.PGmoney")

    // don't generate imports for these
    val BuiltIn: Map[Ident, QIdent] =
      // format: off
      Set(Any, AnyVal, Float, Array, Short, Byte, Double, Ordering, Unit, Int, Long, String, Boolean, Option, List, Map, None, Some, Either, Left, Right, StringContext)
        // format: on
        .map(x => (x.value.name, x.value))
        .toMap

    object Optional {
      def unapply(tpe: sc.Type): Option[sc.Type] = tpe match {
        case Wildcard                        => scala.None
        case TApply(Option, scala.List(one)) => scala.Some(one)
        case TApply(underlying, _)           => unapply(underlying)
        case Qualified(_)                    => scala.None
        case Abstract(_)                     => scala.None
        case Commented(underlying, _)        => unapply(underlying)
      }
    }
  }

  def renderTree(tree: Tree): String = {
    tree match {
      case Ident(value) =>
        def isValidId(str: String) = str.head.isUnicodeIdentifierStart && str.drop(1).forall(_.isUnicodeIdentifierPart)
        def escape(str: String) = s"`$str`"
        if (value == "type" || !isValidId(value)) escape(value) else value
      case QIdent(value)    => value.map(renderTree).mkString(".")
      case Param(name, tpe) => renderTree(name) + ": " + renderTree(tpe)
      case StrLit(str)      => '"'.toString + str + '"'.toString
      case tpe: Type =>
        tpe match {
          case Type.Abstract(value)                => renderTree(value)
          case Type.Wildcard                       => "_"
          case Type.TApply(underlying, targs)      => renderTree(underlying) + targs.map(renderTree).mkString("[", ", ", "]")
          case Type.Qualified(value)               => renderTree(value)
          case Type.Commented(underlying, comment) => s"$comment ${renderTree(underlying)}"
        }
      case StringInterpolate(_, prefix, content) =>
        val Quote = '"'.toString
        s"${renderTree(prefix)}${Quote * 3}${content.render}${Quote * 3}"
    }
  }

  case class File(tpe: Type.Qualified, contents: Code) {
    val name: Ident = tpe.value.name
    val pkg = QIdent(tpe.value.idents.dropRight(1))
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
    implicit val tableName: ToCode[db.RelationName] = x => s"${x.schema}.${x.name}"
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

  // `s"..." interpolator
  def s(content: sc.Code) =
    sc.StringInterpolate(Type.StringContext, sc.Ident("s"), content)
}
