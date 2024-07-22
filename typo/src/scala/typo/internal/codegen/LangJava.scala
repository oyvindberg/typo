package typo
package internal
package codegen

import typo.sc.Type

case object LangJava extends Lang {
  override val BigDecimal: sc.Type = TypesJava.BigDecimal
  override val Boolean: sc.Type = TypesJava.Boolean
  override val Byte: sc.Type = TypesJava.Byte
  override val Double: sc.Type = TypesJava.Double
  override val Float: sc.Type = TypesJava.Float
  override val Function1: Type = TypesJava.Function
  override val Int: sc.Type = TypesJava.Integer
  override val ListType: sc.Type = TypesJava.List
  override val Long: sc.Type = TypesJava.Long
  override val MapType: sc.Type = TypesJava.Map
  override val MapImpl: sc.Type = TypesJava.HashMap
  override val Short: sc.Type = TypesJava.Short

  def docLink(cls: sc.QIdent, value: sc.Ident): String =
    s"Points to {@link ${cls.dotName}.${value.value}}"

  override object Optional extends OptionalSupport {
    val tpe: Type = TypesJava.Optional
  }
  // don't generate imports for these
  override val BuiltIn: Map[sc.Ident, sc.Type.Qualified] =
    Set(
      TypesJava.Boolean,
      TypesJava.Byte,
      TypesJava.Character,
      TypesJava.Double,
      TypesJava.Float,
      TypesJava.Integer,
      TypesJava.Long,
      TypesJava.Short,
      TypesJava.String,
      TypesJava.Throwable
    )
      .map(x => (x.value.name, x))
      .toMap

  override def extension: String = "java"

  val Quote = '"'.toString

  case class Ctx(publicImplied: Boolean, staticImplied: Boolean) {
    def withPublic: Ctx = copy(publicImplied = true)
    def withStatic: Ctx = copy(staticImplied = true)
    def publicStatic = (publicImplied, staticImplied) match {
      case (true, true)   => ""
      case (false, true)  => "public "
      case (true, false)  => "static "
      case (false, false) => "static static"
    }
    def public = if (publicImplied) "public " else ""
  }
  object Ctx {
    val Empty = Ctx(publicImplied = false, staticImplied = false)
  }

  override def renderTree(tree: sc.Tree): sc.Code =
    tree match {
      case sc.Ident(value) =>
        val filtered = value.zipWithIndex.collect {
          case (c, 0) if c.isUnicodeIdentifierStart => c
          case (c, _) if c.isUnicodeIdentifierPart  => c
        }.mkString
        val filtered2 = if (filtered.forall(_.isDigit)) "_" + filtered else filtered
        if (isKeyword(filtered2)) s"${filtered2}_" else filtered2
      case sc.New(target, args)           => code"new $target(${args.map(renderTree).mkCode(", ")})"
      case sc.ApplyFunction1(target, arg) => code"$target.apply($arg)"
      case sc.ApplyByName(target)         => code"$target.get()"
      case sc.ApplyNullary(target)        => code"$target()"
      case sc.Arg.Pos(value)              => value
      case sc.Arg.Named(_, value)         => value
      case sc.QIdent(value)               => value.map(renderTree).mkCode(".")
      case sc.Param(cs, name, tpe, _) =>
        code"${renderComments(cs).getOrElse(sc.Code.Empty)}$tpe $name"
      case sc.StrLit(str) if str.contains(Quote) => Quote + str.replace(Quote, "\\\"") + Quote
      case sc.StrLit(str)                        => Quote + str + Quote
      case sc.Summon(_)                          => sys.error("java doesn't support `summon`")
      case tpe: sc.Type =>
        tpe match {
          case sc.Type.ArrayOf(value)                 => code"$value[]"
          case sc.Type.Abstract(value)                => value.code
          case sc.Type.Wildcard                       => code"?"
          case sc.Type.TApply(underlying, targs)      => code"$underlying<${targs.map(renderTree).mkCode(", ")}>"
          case sc.Type.Qualified(value)               => value.code
          case sc.Type.Commented(underlying, comment) => code"$comment $underlying"
          case sc.Type.ByName(underlying)             => renderTree(TypesJava.Supplier.of(underlying))
          case sc.Type.UserDefined(underlying)        => code"/* user-picked */ $underlying"
        }
      case sc.StringInterpolate(_, _, _) =>
        sys.error("Java doesn't support string interpolation (yet)")
      case sc.Given(tparams, name, implicitParams, tpe, body) =>
        if (tparams.isEmpty && implicitParams.isEmpty)
          code"public $tpe $name = $body"
        else {
          withBody(code"public ${renderTparams(tparams)} $tpe $name${renderParams(implicitParams)}", Some(body))
        }
      case sc.Value(name, tpe, None) =>
        code"$tpe $name"
      case sc.Value(name, tpe, Some(body)) =>
        code"$tpe $name = $body;"
      case sc.Method(comments, tparams, name, params, implicitParams, tpe, body) =>
        withBody(
          code"public ${renderComments(comments).getOrElse(sc.Code.Empty)}${renderTparams(tparams)}$tpe $name${renderParams(params ++ implicitParams)}",
          body
        )
      case enm: sc.Enum =>
        code"""|public enum ${enm.tpe.name} {
               |    ${enm.members.map { case (name, value) => code"$name(${sc.StrLit(value)})" }.mkCode(",\n")};
               |    final ${TypesJava.String} value;
               |
               |    ${enm.tpe.name}(${TypesJava.String} value) {
               |        this.value = value;
               |    }
               |
               |    public static final ${TypesJava.List.of(enm.tpe)} All = ${TypesJava.List}.of(${enm.members.map { case (name, _) => name.code }.mkCode(", ")});
               |    public static final ${TypesJava.String} Names = All.stream().map(x -> x.value).collect(${TypesJava.Collectors}.joining(", "));
               |    public static final ${TypesJava.Map.of(TypesJava.String, enm.tpe)} ByName = All.stream().collect(${TypesJava.Collectors}.toMap(n -> n.value, n -> n));
               |
               |    ${enm.instances.map(_.code).mkCode("\n")}
               |
               |    public static ${enm.tpe.name} force(${TypesJava.String} str) {
               |        if (ByName.containsKey(str)) {
               |            return ByName.get(str);
               |        } else {
               |            throw new RuntimeException("'" + str + "' does not match any of the following legal values: " + Names);
               |        }
               |    }
               |}
               |""".stripMargin
      case cls: sc.Adt.Record =>
        val body: List[sc.Code] =
          cls.staticMembers.sortBy(_.name).map(x => code"static ${renderTree(x)}") ++ cls.members.sortBy(_.name).map(renderTree)

        List[Option[sc.Code]](
          renderComments(cls.comments),
          Some(code"public record "),
          Some(cls.name.name.value),
          cls.tparams match {
            case Nil      => None
            case nonEmpty => Some(renderTparams(nonEmpty))
          },
          Some(renderParams(cls.params)),
          cls.`extends`.map(x => code" extends $x"),
          cls.implements match {
            case Nil      => None
            case nonEmpty => Some(nonEmpty.map(x => code" implements $x").mkCode(" "))
          },
          Some(code"""| {
                      |  ${body.map(_ ++ code";").mkCode("\n")}
                      |}""".stripMargin)
        ).flatten.mkCode("")
      case sum: sc.Adt.Sum =>
        val body: List[sc.Code] =
          List(
            sum.flattenedSubtypes.sortBy(_.name).map(renderTree),
            sum.staticMembers.sortBy(_.name).map(x => code"static ${renderTree(x)}"),
            sum.members.sortBy(_.name).map(renderTree)
          ).flatten

        List[Option[sc.Code]](
          renderComments(sum.comments),
          Some(code"public sealed interface "),
          Some(sum.name.name.value),
          sum.tparams match {
            case Nil      => None
            case nonEmpty => Some(renderTparams(nonEmpty))
          },
          sum.implements match {
            case Nil      => None
            case nonEmpty => Some(nonEmpty.map(x => code" extends $x").mkCode(" "))
          },
          Some(code"""| {
                      |  ${body.map(_ ++ code";").mkCode("\n")}
                      |}""".stripMargin)
        ).flatten.mkCode("")
      case cls: sc.Class =>
        val body: List[sc.Code] =
          cls.staticMembers.sortBy(_.name).map(x => code"static ${renderTree(x)}") ++ cls.members.sortBy(_.name).map(renderTree)

        List[Option[sc.Code]](
          renderComments(cls.comments),
          Some(code"public "),
          cls.classType match {
            case sc.ClassType.Class     => Some(code"class ")
            case sc.ClassType.Interface => Some(code"interface ")
          },
          Some(cls.name.name.value),
          cls.tparams match {
            case Nil      => None
            case nonEmpty => Some(renderTparams(nonEmpty))
          },
          cls.params match {
            case Nil      => None
            case nonEmpty => Some(renderParams(nonEmpty))
          },
          cls.`extends`.map(x => code" extends $x"),
          cls.implements match {
            case Nil      => None
            case nonEmpty => Some(nonEmpty.map(x => code" implements $x").mkCode(" "))
          },
          Some(code"""| {
                      |  ${body.map(_ ++ code";").mkCode("\n")}
                      |}""".stripMargin)
        ).flatten.mkCode("")
    }

  def renderParams(params: List[sc.Param]): sc.Code = {
    params match {
      case Nil            => code"()"
      case List(one)      => code"($one)"
      case List(one, two) => code"($one, $two)"
      case more =>
        code"""|(
               |  ${more.map(renderTree).mkCode(",\n")}
               |)""".stripMargin
    }
  }
  def renderTparams(tparams: List[Type.Abstract]) =
    if (tparams.isEmpty) sc.Code.Empty else code"<${tparams.map(renderTree).mkCode(", ")}>"

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
        code"""|$init {
               |  return $body;
               |}""".stripMargin
    }
  }

  val isKeyword: Set[String] =
    Set(
      "abstract",
      "assert",
      "boolean",
      "break",
      "byte",
      "case",
      "catch",
      "char",
      "class",
      "const",
      "continue",
      "default",
      "do",
      "double",
      "else",
      "enum",
      "extends",
      "final",
      "finally",
      "float",
      "for",
      "goto",
      "if",
      "implements",
      "import",
      "instanceof",
      "int",
      "interface",
      "long",
      "native",
      "new",
      "package",
      "private",
      "protected",
      "public",
      "return",
      "short",
      "static",
      "strictfp",
      "super",
      "switch",
      "synchronized",
      "this",
      "throw",
      "throws",
      "transient",
      "try",
      "void",
      "volatile",
      "while",
      "true",
      "false",
      "null"
    )

}
