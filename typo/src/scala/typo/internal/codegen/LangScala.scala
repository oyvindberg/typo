package typo
package internal
package codegen
import typo.sc.Type

case object LangScala extends Lang {
  override val BigDecimal: sc.Type = TypesScala.BigDecimal
  override val Boolean: sc.Type = TypesScala.Boolean
  override val Byte: sc.Type = TypesScala.Byte
  override val Double: sc.Type = TypesScala.Double
  override val Float: sc.Type = TypesScala.Float
  override val Function1: Type = TypesScala.Function1
  override val Int: sc.Type = TypesScala.Int
  override val ListType: sc.Type = TypesScala.List
  override val Long: sc.Type = TypesScala.Long
  override val MapType: sc.Type = TypesScala.Map
  override val MapImpl: sc.Type = TypesScala.Map
  override val Short: sc.Type = TypesScala.Short

  override def docLink(cls: sc.QIdent, value: sc.Ident): String =
    s"Points to [[${cls.dotName}.${value.value}]]"

  object Optional extends OptionalSupport {
    val tpe: Type = TypesScala.Option
  }

  // don't generate imports for these
  override val BuiltIn: Map[sc.Ident, sc.Type.Qualified] =
    Set(
      TypesJava.Character,
      TypesJava.Integer,
      TypesJava.String,
      TypesJava.StringBuilder,
      TypesJava.Throwable,
      TypesScala.Any,
      TypesScala.AnyRef,
      TypesScala.AnyVal,
      TypesScala.Array,
      TypesScala.BigDecimal,
      TypesScala.Boolean,
      TypesScala.Byte,
      TypesScala.Double,
      TypesScala.Either,
      TypesScala.Float,
      TypesScala.Function1,
      TypesScala.Int,
      TypesScala.Iterable,
      TypesScala.Iterator,
      TypesScala.Left,
      TypesScala.List,
      TypesScala.Long,
      TypesScala.Map,
      TypesScala.Nil,
      TypesScala.None,
      TypesScala.Numeric,
      TypesScala.Option,
      TypesScala.Right,
      TypesScala.Short,
      TypesScala.Some,
      TypesScala.StringContext,
      TypesScala.Unit
    )
      .map(x => (x.value.name, x))
      .toMap

  override def extension: String = "scala"

  val Quote = '"'.toString
  val TripleQuote = Quote * 3
  val breakAfter = 3

  override def renderTree(tree: sc.Tree): sc.Code =
    tree match {
      case sc.Ident(value) =>
        def isValidId(str: String) =
          str.head.isUnicodeIdentifierStart && str.drop(1).forall(_.isUnicodeIdentifierPart)

        def escape(str: String) = s"`$str`"

        if (isKeyword(value) || !isValidId(value)) escape(value) else value
      case sc.QIdent(value)               => value.map(renderTree).mkCode(".")
      case p: sc.Param                    => renderParam(p, false)
      case sc.ApplyFunction1(target, arg) => code"$target($arg)"
      case sc.ApplyByName(target)         => code"$target"
      case sc.ApplyNullary(target)        => code"$target"
      case sc.New(target, args) =>
        if (args.length > breakAfter)
          code"""|new $target(
                 |  ${args.map(renderTree).mkCode(",\n")}
                 |)""".stripMargin
        else code"new $target(${args.map(renderTree).mkCode(", ")})"
      case sc.Arg.Pos(value)                     => value
      case sc.Arg.Named(name, value)             => code"$name = $value"
      case sc.StrLit(str) if str.contains(Quote) => TripleQuote + str + TripleQuote
      case sc.StrLit(str)                        => Quote + str + Quote
      case sc.Summon(tpe)                        => code"implicitly[$tpe]"
      case tpe: sc.Type =>
        tpe match {
          case sc.Type.ArrayOf(value)                 => code"Array[$value]"
          case sc.Type.Abstract(value)                => value.code
          case sc.Type.Wildcard                       => code"?"
          case sc.Type.TApply(underlying, targs)      => code"$underlying[${targs.map(renderTree).mkCode(", ")}]"
          case sc.Type.Qualified(value)               => value.code
          case sc.Type.Commented(underlying, comment) => code"$comment $underlying"
          case sc.Type.ByName(underlying)             => code"=> $underlying"
          case sc.Type.UserDefined(underlying)        => code"/* user-picked */ $underlying"
        }
      case sc.StringInterpolate(_, prefix, content) =>
        content.render(this).lines match {
          case Array(one) if one.contains(Quote) =>
            code"$prefix$TripleQuote$one$TripleQuote"
          case Array(one) =>
            code"$prefix$Quote$one$Quote"
          case more =>
            val renderedPrefix = renderTree(prefix).render(this).asString
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
      case sc.Given(tparams, name, implicitParams, tpe, body) =>
        if (tparams.isEmpty && implicitParams.isEmpty)
          withBody(code"implicit lazy val $name: $tpe", Some(body))
        else {
          withBody(code"implicit def $name${renderTparams(tparams)}${renderImplicitParams(implicitParams)}: $tpe", Some(body))
        }
      case sc.Value(name, tpe, body) =>
        withBody(code"val $name: $tpe", body)
      case sc.Method(comments, tparams, name, params, implicitParams, tpe, body) =>
        withBody(
          renderWithParams(
            prefix = code"${renderComments(comments).getOrElse(sc.Code.Empty)}def $name${renderTparams(tparams)}",
            params = params,
            implicitParams = implicitParams,
            isVal = false,
            forceParenthesis = false
          ) ++ code": $tpe",
          body
        )
      case enm: sc.Enum =>
        val members = enm.members.map { case (name, value) =>
          name -> code"case object $name extends ${enm.tpe.name}(${sc.StrLit(value)})"
        }
        val str = sc.Ident("str")
        val obj =
          code"""|object ${enm.tpe.value} {
                 |  ${enm.instances.map(_.code).mkCode("\n")}
                 |  def apply($str: ${TypesJava.String}): ${TypesScala.Either.of(TypesJava.String, enm.tpe)} =
                 |    ByName.get($str).toRight(s"'$$str' does not match any of the following legal values: $$Names")
                 |  def force($str: ${TypesJava.String}): ${enm.tpe} =
                 |    apply($str) match {
                 |      case ${TypesScala.Left}(msg) => sys.error(msg)
                 |      case ${TypesScala.Right}(value) => value
                 |    }
                 |  ${members.map { case (_, definition) => definition }.mkCode("\n")}
                 |  val All: ${TypesScala.List.of(enm.tpe)} = ${TypesScala.List}(${members.map { case (ident, _) => ident.code }.mkCode(", ")})
                 |  val Names: ${TypesJava.String} = All.map(_.value).mkString(", ")
                 |  val ByName: ${TypesScala.Map.of(TypesJava.String, enm.tpe)} = All.map(x => (x.value, x)).toMap
                 |  }
            """.stripMargin

        code"""|${renderComments(enm.comments).getOrElse(sc.Code.Empty)}
                 |sealed abstract class ${enm.tpe.name}(val value: ${TypesJava.String})
                 |
                 |$obj
                 |""".stripMargin

      case sum: sc.Adt.Sum =>
        List[Option[sc.Code]](
          renderComments(sum.comments),
          Some(code"sealed trait "),
          Some(sum.name.name.value),
          sum.tparams match {
            case Nil      => None
            case nonEmpty => Some(renderTparams(nonEmpty))
          },
          sum.implements.headOption.map(extends_ => code" extends $extends_"),
          sum.implements.drop(1) match {
            case Nil      => None
            case nonEmpty => Some(nonEmpty.map(x => code" with $x").mkCode(" "))
          },
          sum.members match {
            case Nil => None
            case nonEmpty =>
              Some(
                code"""| {
                       |  ${nonEmpty.map(renderTree).mkCode("\n")}
                       |}""".stripMargin
              )
          },
          sum.staticMembers.sortBy(_.name).map(renderTree) ++ sum.flattenedSubtypes.sortBy(_.name.name).map(renderTree) match {
            case Nil => None
            case nonEmpty =>
              Some(code"""|
                          |
                          |object ${sum.name.name.value} {
                          |  ${nonEmpty.mkCode("\n")}
                          |}""".stripMargin)
          }
        ).flatten.mkCode("")
      case cls: sc.Adt.Record =>
        val (extends_, implements) = (cls.isWrapper, cls.`extends`.toList ++ cls.implements) match {
          case (true, types) => (Some(TypesScala.AnyVal), types)
          case (_, types)    => (types.headOption, types.drop(1))
        }
        val prefix = List[Option[sc.Code]](
          renderComments(cls.comments),
          Some(code"case class "),
          Some(cls.name.name.value),
          cls.tparams match {
            case Nil      => None
            case nonEmpty => Some(renderTparams(nonEmpty))
          }
        ).flatten.mkCode("")

        List[Option[sc.Code]](
          Some(
            renderWithParams(
              prefix = prefix,
              params = cls.params,
              implicitParams = cls.implicitParams,
              isVal = false,
              forceParenthesis = true
            )
          ),
          extends_.map(x => code" extends $x"),
          implements match {
            case Nil      => None
            case nonEmpty => Some(nonEmpty.map(x => code" with $x").mkCode(" "))
          },
          cls.members match {
            case Nil => None
            case nonEmpty =>
              Some(
                code"""| {
                       |  ${nonEmpty.map(renderTree).mkCode("\n")}
                       |}""".stripMargin
              )
          },
          cls.staticMembers
            .sortBy(_.name)
            .map(renderTree) match {
            case Nil => None
            case nonEmpty =>
              Some(code"""|
                          |
                          |object ${cls.name.name.value} {
                          |  ${nonEmpty.mkCode("\n")}
                          |}""".stripMargin)
          }
        ).flatten.mkCode("")
      case cls: sc.Class =>
        val (extends_, implements) = {
          val types = cls.`extends`.toList ++ cls.implements
          (types.headOption, types.drop(1))
        }

        val prefix = List[Option[sc.Code]](
          renderComments(cls.comments),
          cls.classType match {
            case sc.ClassType.Class     => Some(code"class ")
            case sc.ClassType.Interface => Some(code"trait ")
          },
          Some(cls.name.name.value),
          cls.tparams match {
            case Nil      => None
            case nonEmpty => Some(renderTparams(nonEmpty))
          }
        ).flatten.mkCode("")

        List[Option[sc.Code]](
          Some(
            renderWithParams(
              prefix = prefix,
              params = cls.params,
              implicitParams = cls.implicitParams,
              isVal = true,
              forceParenthesis = false
            )
          ),
          extends_.map(x => code" extends $x"),
          implements match {
            case Nil      => None
            case nonEmpty => Some(nonEmpty.map(x => code" with $x").mkCode(" "))
          },
          cls.members match {
            case Nil => None
            case nonEmpty =>
              Some(
                code"""| {
                       |  ${nonEmpty.map(renderTree).mkCode("\n")}
                       |}""".stripMargin
              )
          },
          cls.staticMembers
            .sortBy(_.name)
            .map(renderTree) match {
            case Nil => None
            case nonEmpty =>
              Some(code"""|
                          |
                          |object ${cls.name.name.value} {
                          |  ${nonEmpty.mkCode("\n")}
                          |}""".stripMargin)
          }
        ).flatten.mkCode("")
    }

  def renderParam(p: sc.Param, isVal: Boolean): sc.Code = {
    val prefix = if (isVal) "val " else ""
    val renderedDefault = p.default match {
      case Some(default) => " = " + default.render(this)
      case None          => ""
    }
    code"${renderComments(p.comments).getOrElse(sc.Code.Empty)}$prefix${p.name}: ${p.tpe}$renderedDefault"
  }

  def renderWithParams(prefix: sc.Code, params: List[sc.Param], implicitParams: List[sc.Param], isVal: Boolean, forceParenthesis: Boolean): sc.Code = {
    val base =
      if (params.length + implicitParams.length > breakAfter)
        code"""|$prefix(
               |  ${params.map(p => renderParam(p, isVal)).mkCode(",\n")}
               |)""".stripMargin
      else if (params.nonEmpty || forceParenthesis) code"$prefix(${params.map(p => renderParam(p, isVal)).mkCode(", ")})"
      else prefix
    code"$base${renderImplicitParams(implicitParams)}"
  }

  def renderTparams(tparams: List[Type.Abstract]) =
    if (tparams.isEmpty) sc.Code.Empty else code"[${tparams.map(renderTree).mkCode(", ")}]"

  def renderImplicitParams(implicitParams: List[sc.Param]) =
    if (implicitParams.isEmpty) sc.Code.Empty else code"(implicit ${implicitParams.map(renderTree).mkCode(", ")})"

  def renderComments(comments: sc.Comments): Option[sc.Code] = {
    comments.lines match {
      case Nil => None
      case title :: Nil =>
        Some(code"""/** $title */\n""")
      case title :: rest =>
        Some(code"""|/** $title
              |${rest.flatMap(_.linesIterator).map(line => s"  * $line").mkString("\n")}
              |  */\n""".stripMargin)
    }
  }

  def withBody(init: sc.Code, body: Option[sc.Code]) = {
    body match {
      case None => init
      case Some(body) =>
        val renderedBody = body.render(this)
        if (renderedBody.lines.length == 1)
          code"$init = ${renderedBody.asString}"
        else
          code"""|$init = {
                 |  ${renderedBody.asString}
                 |}""".stripMargin
    }
  }

  override val isKeyword: Set[String] =
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

}
