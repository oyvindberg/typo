package typo

import sc.*

sealed trait Language {
  val BuiltIn: Map[Ident, Type.Qualified]
  def extension: String
  def renderTree(tree: Tree): String
}

object Language {
  case object Scala extends Language {

    // don't generate imports for these
    val BuiltIn: Map[Ident, Type.Qualified] =
      Set(
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
        TypesScala.Iterator,
        TypesJava.Character,
        TypesJava.Integer,
        TypesScala.Left,
        TypesScala.List,
        TypesScala.Long,
        TypesScala.Map,
        TypesScala.None,
        TypesScala.Option,
        TypesScala.Ordering,
        TypesScala.Right,
        TypesScala.Short,
        TypesScala.Some,
        TypesJava.String,
        TypesJava.StringBuilder,
        TypesScala.StringContext,
        TypesScala.Unit,
        TypesJava.Throwable
      )
        .map(x => (x.value.name, x))
        .toMap

    override def extension: String = "scala"
    override def renderTree(tree: Tree): String =
      tree match {
        case Ident(value) =>
          def isValidId(str: String) = str.head.isUnicodeIdentifierStart && str.drop(1).forall(_.isUnicodeIdentifierPart)

          def escape(str: String) = s"`$str`"

          if (isScalaKeyword(value) || !isValidId(value)) escape(value) else value
        case QIdent(value)                      => value.map(renderTree).mkString(".")
        case Param(name, tpe, Some(default))    => renderTree(name) + ": " + renderTree(tpe) + " = " + default.render(this)
        case Param(name, tpe, None)             => renderTree(name) + ": " + renderTree(tpe)
        case Params(params)                     => params.map(renderTree).mkString("(", ", ", ")")
        case StrLit(str) if str.contains(Quote) => TripleQuote + str + TripleQuote
        case StrLit(str)                        => Quote + str + Quote
        case Summon(tpe)                        => s"implicitly[${renderTree(tpe)}]"
        case tpe: Type =>
          tpe match {
            case Type.ArrayOf(value)                 => s"Array[${renderTree(value)}]"
            case Type.Abstract(value)                => renderTree(value)
            case Type.Wildcard                       => "?"
            case Type.TApply(underlying, targs)      => renderTree(underlying) + targs.map(renderTree).mkString("[", ", ", "]")
            case Type.Qualified(value)               => renderTree(value)
            case Type.Commented(underlying, comment) => s"$comment ${renderTree(underlying)}"
            case Type.ByName(underlying)             => s"=> ${renderTree(underlying)}"
            case Type.UserDefined(underlying)        => s"/* user-picked */ ${renderTree(underlying)}"
          }
        case StringInterpolate(_, prefix, content) =>
          content.render(this).lines match {
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
          val renderedBody = body.render(this)

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
          val renderedBody = body.render(this)

          if (tparams.isEmpty && params.isEmpty && implicitParams.isEmpty)
            s"val $renderedName: $renderedTpe = $renderedBody"
          else {
            val renderedTparams = if (tparams.isEmpty) "" else tparams.map(renderTree).mkString("[", ", ", "]")
            val init = s"def $renderedName$renderedTparams"
            val renderedParams =
              params match {
                case Nil            => ""
                case List(one)      => s"(${renderTree(one)})"
                case List(one, two) => s"(${renderTree(one)}, ${renderTree(two)})"
                case more =>
                  val indent = " " * (init.length + 1)
                  more.zipWithIndex.map { case (p, idx) => (if (idx == 0) "" else indent) + renderTree(p) }.mkString("(", ",\n", s"\n${" " * init.length})")
              }
            val renderedImplicitParams = if (implicitParams.isEmpty) "" else implicitParams.map(renderTree).mkString("(implicit ", ", ", ")")
            s"def $renderedName$renderedTparams$renderedParams$renderedImplicitParams: $renderedTpe = $renderedBody"
          }
        case Obj(name, members, body) =>
          if (members.isEmpty && body.isEmpty) ""
          else {
            val codeMembers: List[String] =
              body.map(_.render(this).asString).toList ++ members.sortBy(_.name).map(renderTree)

            s"""|object ${name.name.value} {
                |${codeMembers.flatMap(_.linesIterator).map("  " + _).mkString("\n")}
                |}""".stripMargin
          }
      }
  }
}
