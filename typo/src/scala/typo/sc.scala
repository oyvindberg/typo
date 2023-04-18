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
    def /(newIdents: List[Ident]): QIdent = QIdent(idents ++ newIdents)
    def name = idents.last
  }
  object QIdent {
    def of(idents: Ident*): QIdent = QIdent(idents.toList)
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
    case class Qualified(value: QIdent) extends Type {
      def name = value.name
    }
    case class Abstract(value: Ident) extends Type
    case class Commented(underlying: Type, comment: String) extends Type
    case class UserDefined(underlying: Type) extends Type

    object Qualified {
      implicit val ordering: Ordering[Qualified] = scala.Ordering.by(renderTree)

      def apply(value: String): Qualified =
        Qualified(QIdent(value.split('.').toList.map(Ident.apply)))
      def apply(value: Ident): Qualified =
        Qualified(QIdent(scala.List(value)))
    }

    val ZonedDateTime = sc.Type.Qualified("java.time.ZonedDateTime")
    val LocalDate = sc.Type.Qualified("java.time.LocalDate")
    val LocalDateTime = sc.Type.Qualified("java.time.LocalDateTime")
    val LocalTime = sc.Type.Qualified("java.time.LocalTime")
    val JavaTime = sc.Type.Qualified("java.sql.Time")
    val AnyVal = sc.Type.Qualified("scala.AnyVal")
    val Any = sc.Type.Qualified("scala.Any")
    val AnyRef = sc.Type.Qualified("scala.AnyRef")
    val BigDecimal = sc.Type.Qualified("scala.math.BigDecimal")
    val Byte = sc.Type.Qualified("scala.Byte")
    val Char = sc.Type.Qualified("scala.Char")
    val JavaCharacter = sc.Type.Qualified("java.lang.Character")
    val Short = sc.Type.Qualified("scala.Short")
    val JavaShort = sc.Type.Qualified("java.lang.Short")
    val JavaByte = sc.Type.Qualified("java.lang.Byte")
    val Array = sc.Type.Qualified("scala.Array")
    val Ordering = sc.Type.Qualified("scala.math.Ordering")
    val Connection = sc.Type.Qualified("java.sql.Connection")
    val PreparedStatement = sc.Type.Qualified("java.sql.PreparedStatement")
    val Types = sc.Type.Qualified("java.sql.Types")
    val Unit = Qualified("scala.Unit")
    val Int = Qualified("scala.Int")
    val JavaInteger = Qualified("java.lang.Integer")
    val Double = Qualified("scala.Double")
    val JavaDouble = Qualified("java.lang.Double")
    val UUID = Qualified("java.util.UUID")
    val Long = Qualified("scala.Long")
    val JavaLong = Qualified("java.lang.Long")
    val String = Qualified("java.lang.String")
    val Float = Qualified("scala.Float")
    val JavaFloat = Qualified("java.lang.Float")
    val Boolean = Qualified("scala.Boolean")
    val JavaBoolean = Qualified("java.lang.Boolean")
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
    val BuiltIn: Map[Ident, Type.Qualified] =
      Set(
        Any,
        AnyRef,
        AnyVal,
        Array,
        BigDecimal,
        Boolean,
        Byte,
        Double,
        Either,
        Float,
        Int,
        Left,
        List,
        Long,
        Map,
        None,
        Option,
        Ordering,
        Right,
        Short,
        Some,
        String,
        StringContext,
        Unit
      )
        .map(x => (x.value.name, x))
        .toMap

    object Optional {
      def unapply(tpe: sc.Type): Option[sc.Type] = tpe match {
        case Wildcard                        => scala.None
        case TApply(Option, scala.List(one)) => scala.Some(one)
        case TApply(underlying, _)           => unapply(underlying)
        case Qualified(_)                    => scala.None
        case Abstract(_)                     => scala.None
        case Commented(underlying, _)        => unapply(underlying)
        case UserDefined(underlying)         => unapply(underlying)
      }
    }

    def boxedType(tpe: sc.Type): Option[Qualified] =
      tpe match {
        case Int                      => scala.Some(JavaInteger)
        case Long                     => scala.Some(JavaLong)
        case Float                    => scala.Some(JavaFloat)
        case Double                   => scala.Some(JavaDouble)
        case Boolean                  => scala.Some(JavaBoolean)
        case Short                    => scala.Some(JavaShort)
        case Byte                     => scala.Some(JavaByte)
        case Char                     => scala.Some(JavaCharacter)
        case Commented(underlying, _) => boxedType(underlying)
        case UserDefined(underlying)  => boxedType(underlying)
        case _                        => scala.None
      }

    def containsUserDefined(tpe: sc.Type): Boolean = tpe match {
      case Wildcard                  => false
      case TApply(underlying, targs) => containsUserDefined(underlying) || targs.exists(containsUserDefined)
      case Qualified(_)              => false
      case Abstract(_)               => false
      case Commented(underlying, _)  => containsUserDefined(underlying)
      case UserDefined(_)            => true
    }
  }

  val isScalaKeyword: Set[String] =
    Set(
      "abstract",
      "case",
      "class",
      "catch",
      "def",
      "do",
      "else",
      "enum",
      "extends",
      "export",
      "extension",
      "false",
      "final",
      "finally",
      "for",
      "forSome",
      "given",
      "if",
      "implicit",
      "import",
      "inline",
      "lazy",
      "macro",
      "match",
      "new",
      "null",
      "object",
      "override",
      "package",
      "private",
      "protected",
      "return",
      "sealed",
      "super",
      "then",
      "this",
      "throw",
      "trait",
      "true",
      "try",
      "type",
      "using",
      "val",
      "var",
      "with",
      "while",
      "yield",
      ".",
      "_",
      ":",
      "=",
      "=>",
      "<-",
      "<:",
      "<%",
      ">:",
      "#",
      "@"
    )

  def renderTree(tree: Tree): String = {
    tree match {
      case Ident(value) =>
        def isValidId(str: String) = str.head.isUnicodeIdentifierStart && str.drop(1).forall(_.isUnicodeIdentifierPart)
        def escape(str: String) = s"`$str`"
        if (isScalaKeyword(value) || !isValidId(value)) escape(value) else value
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
          case Type.UserDefined(underlying)        => s"/* user-picked */ ${renderTree(underlying)}"
        }
      case StringInterpolate(_, prefix, content) =>
        val Quote = '"'.toString
        s"${renderTree(prefix)}${Quote * 3}${content.render}${Quote * 3}"
    }
  }

  case class File(tpe: Type.Qualified, contents: Code, secondaryTypes: List[Type.Qualified]) {
    val name: Ident = tpe.value.name
    val pkg = QIdent(tpe.value.idents.dropRight(1))
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
        case Code.Interpolated(parts, args) =>
          Code.Interpolated(parts.map(_.stripMargin), args.map(_.stripMargin))
      }

    // render tree as a string in such a way that newlines inside interpolated strings preserves outer indentation
    lazy val render: String =
      this match {
        case Code.Interpolated(parts, args) =>
          val lines = List.newBuilder[String]
          var currentLine = ""

          def consume(str: String, indent: Int): Unit = {
            // @unchecked because scala 2 and 3 disagree on exhaustivity
            (str.linesWithSeparators.toList: @unchecked) match {
              case Nil => ()
              case List(one) if one.endsWith("\n") =>
                lines += (currentLine + one)
                currentLine = ""
              case List(one) =>
                currentLine += one
              case List(first, rest*) =>
                lines += (currentLine + first)
                val indentedRest = rest.toList.map(str => (" " * indent) + str)
                if (indentedRest.lastOption.exists(_.endsWith("\n"))) {
                  lines ++= indentedRest
                  currentLine = ""
                } else {
                  lines ++= indentedRest.init
                  currentLine = indentedRest.last
                }
            }
          }
          // do the string interpolation
          parts.zipWithIndex.foreach { case (str, n) =>
            if (n > 0) {
              val rendered = args(n - 1).render
              // consider the current indentation level when interpolating in multiline strings
              consume(rendered, indent = currentLine.length)
            }
            val escaped = StringContext.processEscapes(str)
            consume(escaped, indent = 0)
          }
          // commit last line
          lines += currentLine
          // recombine lines back into one string
          lines.result().mkString
        case Code.Combined(codes) => codes.map(_.render).mkString
        case Code.Str(str)        => str
        case Code.Tree(tree)      => renderTree(tree)
      }

    def mapTrees(f: Tree => Tree): Code =
      this match {
        case Code.Interpolated(parts, args) => Code.Interpolated(parts, args.map(_.mapTrees(f)))
        case Code.Combined(codes)           => Code.Combined(codes.map(_.mapTrees(f)))
        case str @ Code.Str(_)              => str
        case Code.Tree(tree)                => Code.Tree(f(tree))
      }

    def ++(other: Code): Code = Code.Combined(List(this, other))
  }

  object Code {
    val Empty: Code = Str("")
    implicit val ordering: Ordering[Code] = Ordering.by(_.render)
    case class Interpolated(parts: Seq[String], args: Seq[Code]) extends Code
    case class Combined(codes: List[Code]) extends Code
    case class Str(value: String) extends Code
    case class Tree(value: sc.Tree) extends Code
  }

  // `s"..." interpolator
  def s(content: sc.Code) =
    sc.StringInterpolate(Type.StringContext, sc.Ident("s"), content)
}
