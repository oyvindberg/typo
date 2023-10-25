package typo

/** Simplified model of the scala language.
  *
  * The generated code is stored in the `Code` data structure. For full flexibility, some parts are stored as text and other parts in trees. Most notably *all type and term references* which need an
  * import to work should be in a tree.
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
    def apply(str: String): QIdent =
      sc.QIdent(str.split('.').toList.map(Ident.apply))
    def of(idents: Ident*): QIdent =
      QIdent(idents.toList)
  }
  case class Param(name: Ident, tpe: Type, default: Option[sc.Code]) extends Tree
  case class Params(params: List[Param]) extends Tree

  case class StrLit(str: String) extends Tree {
    def prefixed(prefix: String) = StrLit(prefix + str)
  }

  case class StringInterpolate(`import`: sc.Type, prefix: sc.Ident, content: sc.Code) extends Tree

  sealed trait ClassMember extends Tree {
    def name: sc.Ident
  }

  case class Summon(tpe: Type) extends Tree

  case class Given(
      tparams: List[Type.Abstract],
      name: sc.Ident,
      implicitParams: List[Param],
      tpe: sc.Type,
      body: sc.Code
  ) extends ClassMember

  case class Value(
      tparams: List[Type.Abstract],
      name: sc.Ident,
      params: List[Param],
      implicitParams: List[Param],
      tpe: sc.Type,
      body: sc.Code
  ) extends ClassMember

  case class Obj(
      name: sc.QIdent,
      members: List[ClassMember],
      body: Option[sc.Code]
  ) extends Tree

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
    case class ByName(underlying: Type) extends Type

    object Qualified {
      implicit val ordering: Ordering[Qualified] = scala.Ordering.by(renderTree)

      def apply(str: String): Qualified =
        Qualified(QIdent(str))
      def apply(value: Ident): Qualified =
        Qualified(QIdent(scala.List(value)))
    }

    val JavaBigDecimal = Qualified("java.math.BigDecimal")
    val JavaCharacter = Qualified("java.lang.Character")
    val JavaBoolean = Qualified("java.lang.Boolean")
    val JavaDouble = Qualified("java.lang.Double")
    val JavaInteger = Qualified("java.lang.Integer")
    val JavaLong = Qualified("java.lang.Long")
    val JavaFloat = Qualified("java.lang.Float")
    val JavaShort = Qualified("java.lang.Short")

    val String = Qualified("java.lang.String")
    val Connection = Qualified("java.sql.Connection")
    val PreparedStatement = Qualified("java.sql.PreparedStatement")
    val ResultSet = sc.Type.Qualified("java.sql.ResultSet")
    val JavaSqlTime = Qualified("java.sql.Time")
    val Types = Qualified("java.sql.Types")
    val Instant = Qualified("java.time.Instant")
    val LocalDate = Qualified("java.time.LocalDate")
    val LocalDateTime = Qualified("java.time.LocalDateTime")
    val LocalTime = Qualified("java.time.LocalTime")
    val OffsetDateTime = Qualified("java.time.OffsetDateTime")
    val OffsetTime = Qualified("java.time.OffsetTime")
    val ZoneOffset = Qualified("java.time.ZoneOffset")
    val DateTimeParseException = Qualified("java.time.format.DateTimeParseException")
    val JavaHashMap = Qualified("java.util.HashMap")
    val JavaMap = Qualified("java.util.Map")
    val UUID = Qualified("java.util.UUID")
    val PgArray = Qualified("org.postgresql.jdbc.PgArray")
    val PGbox = Qualified("org.postgresql.geometric.PGbox")
    val PGcircle = Qualified("org.postgresql.geometric.PGcircle")
    val PGline = Qualified("org.postgresql.geometric.PGline")
    val PGlseg = Qualified("org.postgresql.geometric.PGlseg")
    val PGpath = Qualified("org.postgresql.geometric.PGpath")
    val PGpoint = Qualified("org.postgresql.geometric.PGpoint")
    val PGpolygon = Qualified("org.postgresql.geometric.PGpolygon")
    val PgSQLXML = Qualified("org.postgresql.jdbc.PgSQLXML")
    val PGInterval = Qualified("org.postgresql.util.PGInterval")
    val PGmoney = Qualified("org.postgresql.util.PGmoney")
    val PGobject = Qualified("org.postgresql.util.PGobject")
    val Any = Qualified("scala.Any")
    val AnyRef = Qualified("scala.AnyRef")
    val AnyVal = Qualified("scala.AnyVal")
    val Array = Qualified("scala.Array")
    val Boolean = Qualified("scala.Boolean")
    val Byte = Qualified("scala.Byte")
    val Char = Qualified("scala.Char")
    val Double = Qualified("scala.Double")
    val Either = Qualified("scala.Either")
    val Float = Qualified("scala.Float")
    val Function1 = Qualified("scala.Function1")
    val Int = Qualified("scala.Int")
    val Left = Qualified("scala.Left")
    val List = Qualified("scala.List")
    val Long = Qualified("scala.Long")
    val None = Qualified("scala.None")
    val Option = Qualified("scala.Option")
    val Random = Qualified("scala.util.Random")
    val Right = Qualified("scala.Right")
    val Short = Qualified("scala.Short")
    val Some = Qualified("scala.Some")
    val StringContext = Qualified("scala.StringContext")
    val Unit = Qualified("scala.Unit")
    val Map = Qualified("scala.collection.immutable.Map")
    val ListMap = Qualified("scala.collection.immutable.ListMap")
    val mutableMap = Qualified("scala.collection.mutable.Map")
    val BigDecimal = Qualified("scala.math.BigDecimal")
    val Ordering = Qualified("scala.math.Ordering")
    val Try = Qualified("scala.util.Try")
    val DateTimeFormatter = Qualified("java.time.format.DateTimeFormatter")
    val DateTimeFormatterBuilder = Qualified("java.time.format.DateTimeFormatterBuilder")
    val ChronoField = Qualified("java.time.temporal.ChronoField")
    val ChronoUnit = Qualified("java.time.temporal.ChronoUnit")
    val Numeric = Qualified("scala.math.Numeric")
    val nowarn = Qualified("scala.annotation.nowarn")
    val Throwable = sc.Type.Qualified("java.lang.Throwable")

    object dsl {
      val Bijection = Qualified("typo.dsl.Bijection")
      val DeleteBuilder = Qualified("typo.dsl.DeleteBuilder")
      val DeleteBuilderMock = Qualified("typo.dsl.DeleteBuilder.DeleteBuilderMock")
      val DeleteParams = Qualified("typo.dsl.DeleteParams")
      val Field = Qualified("typo.dsl.SqlExpr.Field")
      val FieldLikeNoHkt = Qualified("typo.dsl.SqlExpr.FieldLikeNoHkt")
      val FieldValue = Qualified("typo.dsl.FieldValue")
      val IdField = Qualified("typo.dsl.SqlExpr.IdField")
      val OptField = Qualified("typo.dsl.SqlExpr.OptField")
      val SelectBuilder = Qualified("typo.dsl.SelectBuilder")
      val SelectBuilderMock = Qualified("typo.dsl.SelectBuilderMock")
      val SelectBuilderSql = Qualified("typo.dsl.SelectBuilderSql")
      val SelectParams = Qualified("typo.dsl.SelectParams")
      val StructureRelation = Qualified("typo.dsl.Structure.Relation")
      val UpdatedValue = Qualified("typo.dsl.UpdatedValue")
      val UpdateBuilder = Qualified("typo.dsl.UpdateBuilder")
      val UpdateBuilderMock = Qualified("typo.dsl.UpdateBuilder.UpdateBuilderMock")
      val UpdateParams = Qualified("typo.dsl.UpdateParams")
    }

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
        Function1,
        Int,
        JavaCharacter,
        JavaInteger,
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
        Unit,
        Throwable
      )
        .map(x => (x.value.name, x))
        .toMap

    /* also in scala 2.12 */
    val HasOrdering: Set[Type] =
      Set(BigDecimal, Boolean, Byte, Double, Float, Int, Long, Short, String)

    object Optional {
      def unapply(tpe: sc.Type): Option[sc.Type] = tpe match {
        case Wildcard                        => scala.None
        case TApply(Option, scala.List(one)) => scala.Some(one)
        case TApply(underlying, _)           => unapply(underlying)
        case Qualified(_)                    => scala.None
        case Abstract(_)                     => scala.None
        case Commented(underlying, _)        => unapply(underlying)
        case ByName(underlying)              => unapply(underlying)
        case UserDefined(underlying)         => unapply(underlying)
      }
    }

    def boxedType(tpe: sc.Type): Option[Qualified] =
      tpe match {
        case Int                      => scala.Some(JavaInteger)
        case Long                     => scala.Some(JavaLong)
        case Float                    => scala.Some(Qualified("java.lang.Float"))
        case Double                   => scala.Some(JavaDouble)
        case Boolean                  => scala.Some(JavaBoolean)
        case Short                    => scala.Some(Qualified("java.lang.Short"))
        case Byte                     => scala.Some(Qualified("java.lang.Byte"))
        case Char                     => scala.Some(JavaCharacter)
        case Commented(underlying, _) => boxedType(underlying)
        case ByName(underlying)       => boxedType(underlying)
        case UserDefined(underlying)  => boxedType(underlying)
        case _                        => scala.None
      }

    def containsUserDefined(tpe: sc.Type): Boolean = tpe match {
      case Wildcard                  => false
      case TApply(underlying, targs) => containsUserDefined(underlying) || targs.exists(containsUserDefined)
      case Qualified(_)              => false
      case Abstract(_)               => false
      case Commented(underlying, _)  => containsUserDefined(underlying)
      case ByName(underlying)        => containsUserDefined(underlying)
      case UserDefined(_)            => true
    }

    def base(tpe: sc.Type): sc.Type = tpe match {
      case Wildcard                  => tpe
      case TApply(underlying, targs) => TApply(base(underlying), targs.map(base))
      case Qualified(_)              => tpe
      case Abstract(_)               => tpe
      case Commented(underlying, _)  => base(underlying)
      case ByName(underlying)        => base(underlying)
      case UserDefined(tpe)          => base(tpe)
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

  val Quote = '"'.toString
  val TripleQuote = Quote * 3

  def renderTree(tree: Tree): String =
    tree match {
      case Ident(value) =>
        def isValidId(str: String) = str.head.isUnicodeIdentifierStart && str.drop(1).forall(_.isUnicodeIdentifierPart)
        def escape(str: String) = s"`$str`"
        if (isScalaKeyword(value) || !isValidId(value)) escape(value) else value
      case QIdent(value)                      => value.map(renderTree).mkString(".")
      case Param(name, tpe, Some(default))    => renderTree(name) + ": " + renderTree(tpe) + " = " + default.render
      case Param(name, tpe, None)             => renderTree(name) + ": " + renderTree(tpe)
      case Params(params)                     => params.map(renderTree).mkString("(", ", ", ")")
      case StrLit(str) if str.contains(Quote) => TripleQuote + str + TripleQuote
      case StrLit(str)                        => Quote + str + Quote
      case Summon(tpe)                        => s"implicitly[${renderTree(tpe)}]"
      case tpe: Type =>
        tpe match {
          case Type.Abstract(value)                => renderTree(value)
          case Type.Wildcard                       => "?"
          case Type.TApply(underlying, targs)      => renderTree(underlying) + targs.map(renderTree).mkString("[", ", ", "]")
          case Type.Qualified(value)               => renderTree(value)
          case Type.Commented(underlying, comment) => s"$comment ${renderTree(underlying)}"
          case Type.ByName(underlying)             => s"=> ${renderTree(underlying)}"
          case Type.UserDefined(underlying)        => s"/* user-picked */ ${renderTree(underlying)}"
        }
      case StringInterpolate(_, prefix, content) =>
        content.render.lines match {
          case Array(one) if one.contains(Quote) =>
            s"${renderTree(prefix)}$TripleQuote$one$TripleQuote"
          case Array(one) =>
            s"${renderTree(prefix)}$Quote$one$Quote"
          case more =>
            val renderedPrefix = renderTree(prefix)
            val pre = s"$renderedPrefix$TripleQuote"
            val ret = more.iterator.zipWithIndex.map {
              case (line, n) if n == 0 => pre + line
              // if line is last
              case (line, n) if n == more.length - 1 =>
                // if its empty align triple quotes with previous
                if (line.isEmpty) s"${" " * renderedPrefix.length}$TripleQuote"
                // or just align like the rest and put it at the end
                else s"${" " * pre.length}$line$TripleQuote"
              case (line, _) => s"${" " * pre.length}$line"
            }.mkString
            ret
        }
      case Given(tparams, name, implicitParams, tpe, body) =>
        val renderedName = renderTree(name)
        val renderedTpe = renderTree(tpe)
        val renderedBody = body.render

        if (tparams.isEmpty && implicitParams.isEmpty)
          s"implicit lazy val $renderedName: $renderedTpe = $renderedBody"
        else {
          val renderedTparams = if (tparams.isEmpty) "" else tparams.map(renderTree).mkString("[", ", ", "]")
          val renderedImplicitParams = if (implicitParams.isEmpty) "" else implicitParams.map(renderTree).mkString("(implicit ", ", ", ")")
          s"implicit def $renderedName$renderedTparams$renderedImplicitParams: $renderedTpe = $renderedBody"
        }
      case Value(tparams, name, params, implicitParams, tpe, body) =>
        val renderedName = renderTree(name)
        val renderedTpe = renderTree(tpe)
        val renderedBody = body.render

        if (tparams.isEmpty && params.isEmpty && implicitParams.isEmpty)
          s"val $renderedName: $renderedTpe = $renderedBody"
        else {
          val renderedTparams = if (tparams.isEmpty) "" else tparams.map(renderTree).mkString("[", ", ", "]")
          val renderedParams = if (params.isEmpty) "" else params.map(renderTree).mkString("(", ", ", ")")
          val renderedImplicitParams = if (implicitParams.isEmpty) "" else implicitParams.map(renderTree).mkString("(implicit ", ", ", ")")
          s"def $renderedName$renderedTparams$renderedParams$renderedImplicitParams: $renderedTpe = $renderedBody"
        }
      case Obj(name, members, body) =>
        if (members.isEmpty && body.isEmpty) ""
        else {
          val codeMembers: List[String] =
            body.map(_.render.asString).toList ++ members.sortBy(_.name).map(renderTree)

          s"""|object ${name.name.value} {
              |${codeMembers.flatMap(_.linesIterator).map("  " + _).mkString("\n")}
              |}""".stripMargin
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
    lazy val render: Lines =
      this match {
        case Code.Interpolated(parts, args) =>
          val lines = Array.newBuilder[String]
          val currentLine = new StringBuilder()

          def consume(str: Lines, indent: Int): Unit =
            // @unchecked because scala 2 and 3 disagree on exhaustivity
            (str.lines: @unchecked) match {
              case Array() => ()
              case Array(one) if one.endsWith("\n") =>
                currentLine.append(one)
                lines += currentLine.result()
                currentLine.clear()
              case Array(one) =>
                currentLine.append(one)
                ()
              case Array(first, rest*) =>
                currentLine.append(first)
                lines += currentLine.result()
                currentLine.clear()
                val indentedRest = rest.map(str => (" " * indent) + str)
                if (indentedRest.lastOption.exists(_.endsWith("\n"))) {
                  lines ++= indentedRest
                } else {
                  lines ++= indentedRest.init
                  currentLine.append(indentedRest.last)
                  ()
                }
            }

          // do the string interpolation
          parts.iterator.zipWithIndex.foreach { case (str, n) =>
            if (n > 0) {
              val rendered = args(n - 1).render
              // consider the current indentation level when interpolating in multiline strings
              consume(rendered, indent = currentLine.length)
            }
            val escaped = StringContext.processEscapes(str)
            consume(Lines(escaped), indent = 0)
          }
          // commit last line
          lines += currentLine.result()
          // recombine lines back into one string
          Lines(lines.result())
        case Code.Combined(codes) => codes.iterator.map(_.render).reduceOption(_ ++ _).getOrElse(Lines.Empty)
        case Code.Str(str)        => Lines(str)
        case Code.Tree(tree)      => Lines(renderTree(tree))
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

  case class Lines(lines: Array[String]) {
    def ++(other: Lines): Lines =
      if (this == Lines.Empty) other
      else if (other == Lines.Empty) this
      else if (lines.last.endsWith("\n")) new Lines(lines ++ other.lines)
      else {
        val newArray = lines.init.iterator ++ Iterator(lines.last + other.lines.head) ++ other.lines.iterator.drop(1)
        new Lines(newArray.toArray)
      }

    def asString: String = lines.mkString
    override def toString: String = asString
  }

  object Lines {
    val Empty = new Lines(Array())
    def apply(str: String): Lines = if (str.isEmpty) Empty else new Lines(str.linesWithSeparators.toArray)
  }

  object Code {
    val Empty: Code = Str("")
    case class Interpolated(parts: Seq[String], args: Seq[Code]) extends Code
    case class Combined(codes: List[Code]) extends Code
    case class Str(value: String) extends Code
    case class Tree(value: sc.Tree) extends Code
  }

  // `s"..." interpolator
  def s(content: sc.Code) =
    sc.StringInterpolate(Type.StringContext, sc.Ident("s"), content)
}
