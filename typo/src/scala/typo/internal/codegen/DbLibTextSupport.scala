package typo
package internal
package codegen

class DbLibTextSupport(pkg: sc.QIdent, inlineImplicits: Boolean, externalText: Option[sc.Type.Qualified], default: ComputedDefault) {
  // name of type class instance
  val textName = sc.Ident("text")
  // configurable default value used in CSV file. this must match between what the generated COPY statement and the `Text` instance says
  val DefaultValue = "__DEFAULT_VALUE__"
  // may refer to the qualified name of `Text` typeclass in doobie, or the one we generate ourselves for other libraries
  val Text = externalText.getOrElse(sc.Type.Qualified(pkg / sc.Ident("Text")))
  // boilerplate for streaming insert we generate for non-doobie libraries
  val streamingInsert = sc.Type.Qualified(pkg / sc.Ident("streamingInsert"))

  /** Resolve known implicits at generation-time instead of at compile-time */
  def lookupTextFor(tpe: sc.Type): sc.Code =
    if (!inlineImplicits) Text.of(tpe).code
    else
      sc.Type.base(tpe) match {
        case sc.Type.BigDecimal                                            => code"$Text.bigDecimalInstance"
        case sc.Type.Boolean                                               => code"$Text.booleanInstance"
        case sc.Type.Double                                                => code"$Text.doubleInstance"
        case sc.Type.Float                                                 => code"$Text.floatInstance"
        case sc.Type.Int                                                   => code"$Text.intInstance"
        case sc.Type.Long                                                  => code"$Text.longInstance"
        case sc.Type.String                                                => code"$Text.stringInstance"
        case sc.Type.TApply(sc.Type.Array, List(sc.Type.Byte))             => code"$Text.byteArrayInstance"
        case sc.Type.Optional(targ)                                        => code"$Text.option(${lookupTextFor(targ)})"
        case sc.Type.TApply(default.Defaulted, List(targ))                 => code"${default.Defaulted}.$textName(${lookupTextFor(targ)})"
        case x: sc.Type.Qualified if x.value.idents.startsWith(pkg.idents) => code"$tpe.$textName"
        case sc.Type.TApply(sc.Type.Array, List(targ: sc.Type.Qualified)) if targ.value.idents.startsWith(pkg.idents) =>
          code"$Text.iterableInstance[${sc.Type.Array}, $targ](${lookupTextFor(targ)}, implicitly)"
        case other => code"${Text.of(other)}"
      }

  val defaultedInstance: sc.Given = {
    val T = sc.Type.Abstract(sc.Ident("T"))
    val textofT = sc.Ident("t")
    sc.Given(
      tparams = List(T),
      name = textName,
      implicitParams = List(sc.Param(textofT, Text.of(T), None)),
      tpe = Text.of(default.Defaulted.of(T)),
      body = code"""|$Text.instance {
               |  case (${default.Defaulted}.${default.Provided}(value), sb) => $textofT.unsafeEncode(value, sb)
               |  case (${default.Defaulted}.${default.UseDefault}, sb) =>
               |    sb.append("$DefaultValue")
               |    ()
               |}""".stripMargin
    )
  }

  def anyValInstance(wrapperType: sc.Type, underlying: sc.Type): sc.Given =
    sc.Given(
      tparams = Nil,
      name = textName,
      implicitParams = Nil,
      tpe = Text.of(wrapperType),
      body = {
        val underlyingText = lookupTextFor(underlying)
        val v = sc.Ident("v")
        code"""|new ${Text.of(wrapperType)} {
               |  override def unsafeEncode($v: $wrapperType, sb: ${sc.Type.StringBuilder}) = $underlyingText.unsafeEncode($v.value, sb)
               |  override def unsafeArrayEncode($v: $wrapperType, sb: ${sc.Type.StringBuilder}) = $underlyingText.unsafeArrayEncode($v.value, sb)
               |}""".stripMargin
      }
    )

  def rowInstance(tpe: sc.Type, cols: NonEmptyList[ComputedColumn]): sc.Given = {
    val row = sc.Ident("row")
    val sb = sc.Ident("sb")
    val textCols = cols.map { col => code"${lookupTextFor(col.tpe)}.unsafeEncode($row.${col.name}, $sb)" }
    val body =
      code"""|$Text.instance[$tpe]{ ($row, $sb) =>
             |  ${textCols.mkCode(code"\n$sb.append($Text.DELIMETER)\n")}
             |}""".stripMargin
    sc.Given(tparams = Nil, name = textName, implicitParams = Nil, tpe = Text.of(tpe), body = body)
  }

  def customTypeInstance(ct: CustomType): sc.Given = {
    sc.Given(
      tparams = Nil,
      name = textName,
      implicitParams = Nil,
      tpe = Text.of(ct.typoType),
      body = {
        val underlying = lookupTextFor(ct.toText.textType)
        val v = sc.Ident("v")
        code"""|new ${Text.of(ct.typoType)} {
               |  override def unsafeEncode($v: ${ct.typoType}, sb: ${sc.Type.StringBuilder}) = $underlying.unsafeEncode(${ct.toText.toTextType(v)}, sb)
               |  override def unsafeArrayEncode($v: ${ct.typoType}, sb: ${sc.Type.StringBuilder}) = $underlying.unsafeArrayEncode(${ct.toText.toTextType(v)}, sb)
               |}""".stripMargin
      }
    )
  }
}
