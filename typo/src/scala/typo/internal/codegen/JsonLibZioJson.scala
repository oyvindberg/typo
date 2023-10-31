package typo.internal.codegen

import typo.internal.ComputedDefault
import typo.sc.Type
import typo.{NonEmptyList, sc}

final case class JsonLibZioJson(pkg: sc.QIdent, default: ComputedDefault, inlineImplicits: Boolean) extends JsonLib {
  private val JsonDecoder = sc.Type.Qualified("zio.json.JsonDecoder")
  private val JsonEncoder = sc.Type.Qualified("zio.json.JsonEncoder")
  private val Write = sc.Type.Qualified("zio.json.internal.Write")
  private val JsonError = sc.Type.Qualified("zio.json.JsonError")
  private val RetractReader = sc.Type.Qualified("zio.json.internal.RetractReader")
  private val Success = sc.Type.Qualified("scala.util.Success")
  private val Json = sc.Type.Qualified("zio.json.ast.Json")

  private val encoderName = sc.Ident("jsonEncoder")
  private val decoderName = sc.Ident("jsonDecoder")

  /** Resolve known implicits at generation-time instead of at compile-time */
  private def lookupDecoderFor(tpe: sc.Type): sc.Code = {
    def go(tpe: sc.Type): sc.Code =
      tpe match {
        case sc.Type.BigDecimal                                            => code"$JsonDecoder.scalaBigDecimal"
        case sc.Type.Boolean                                               => code"$JsonDecoder.boolean"
        case sc.Type.Byte                                                  => code"$JsonDecoder.byte"
        case sc.Type.Double                                                => code"$JsonDecoder.double"
        case sc.Type.Float                                                 => code"$JsonDecoder.float"
        case sc.Type.Instant                                               => code"$JsonDecoder.instant"
        case sc.Type.Int                                                   => code"$JsonDecoder.int"
        case sc.Type.LocalDate                                             => code"$JsonDecoder.localDate"
        case sc.Type.LocalDateTime                                         => code"$JsonDecoder.localDateTime"
        case sc.Type.LocalTime                                             => code"$JsonDecoder.localTime"
        case sc.Type.Long                                                  => code"$JsonDecoder.long"
        case sc.Type.OffsetDateTime                                        => code"$JsonDecoder.offsetDateTime"
        case sc.Type.OffsetTime                                            => code"$JsonDecoder.offsetTime"
        case sc.Type.String                                                => code"$JsonDecoder.string"
        case sc.Type.UUID                                                  => code"$JsonDecoder.uuid"
        case sc.Type.TApply(sc.Type.Array, List(targ))                     => code"$JsonDecoder.array[$targ](${go(targ)}, implicitly)"
        case sc.Type.TApply(default.Defaulted, List(targ))                 => code"${default.Defaulted}.$decoderName(${go(targ)})"
        case sc.Type.Optional(targ)                                        => code"$JsonDecoder.option(${go(targ)})"
        case x: sc.Type.Qualified if x.value.idents.startsWith(pkg.idents) => code"$tpe.$decoderName"
        case other                                                         => code"${JsonDecoder.of(other)}"
      }

    if (inlineImplicits) go(sc.Type.base(tpe)) else JsonDecoder.of(tpe).code
  }

  /** Resolve known implicits at generation-time instead of at compile-time */
  def lookupEncoderFor(tpe: sc.Type): sc.Code = {
    def go(tpe: sc.Type): sc.Code =
      tpe match {
        case sc.Type.BigDecimal                                            => code"$JsonEncoder.scalaBigDecimal"
        case sc.Type.Boolean                                               => code"$JsonEncoder.boolean"
        case sc.Type.Byte                                                  => code"$JsonEncoder.byte"
        case sc.Type.Double                                                => code"$JsonEncoder.double"
        case sc.Type.Float                                                 => code"$JsonEncoder.float"
        case sc.Type.Instant                                               => code"$JsonEncoder.instant"
        case sc.Type.Int                                                   => code"$JsonEncoder.int"
        case sc.Type.LocalDate                                             => code"$JsonEncoder.localDate"
        case sc.Type.LocalDateTime                                         => code"$JsonEncoder.localDateTime"
        case sc.Type.LocalTime                                             => code"$JsonEncoder.localTime"
        case sc.Type.Long                                                  => code"$JsonEncoder.long"
        case sc.Type.OffsetDateTime                                        => code"$JsonEncoder.offsetDateTime"
        case sc.Type.OffsetTime                                            => code"$JsonEncoder.offsetTime"
        case sc.Type.String                                                => code"$JsonEncoder.string"
        case sc.Type.UUID                                                  => code"$JsonEncoder.uuid"
        case sc.Type.TApply(sc.Type.Array, List(targ))                     => code"$JsonEncoder.array[$targ](${go(targ)}, implicitly)"
        case sc.Type.TApply(default.Defaulted, List(targ))                 => code"${default.Defaulted}.$encoderName(${go(targ)})"
        case sc.Type.Optional(targ)                                        => code"$JsonEncoder.option(${go(targ)})"
        case x: sc.Type.Qualified if x.value.idents.startsWith(pkg.idents) => code"$tpe.$encoderName"
        case other                                                         => code"${JsonEncoder.of(other)}"
      }

    if (inlineImplicits) go(sc.Type.base(tpe)) else JsonEncoder.of(tpe).code
  }

  override def defaultedInstance(d: ComputedDefault): List[sc.Given] = {
    val T = sc.Type.Abstract(sc.Ident("T"))
    List(
      sc.Given(
        tparams = List(T),
        name = decoderName,
        implicitParams = List(sc.Param(sc.Ident("T"), JsonDecoder.of(T), None)),
        tpe = JsonDecoder.of(d.Defaulted.of(T)),
        body = code"""|new ${JsonDecoder.of(d.Defaulted.of(T))} {
                      |  override def unsafeDecode(trace: ${sc.Type.List.of(JsonError)}, in: $RetractReader): ${d.Defaulted.of(T)} =
                      |    ${sc.Type.Try}($JsonDecoder.string.unsafeDecode(trace, in)) match {
                      |      case $Success("defaulted") => UseDefault
                      |      case _ => Provided(T.unsafeDecode(trace, in))
                      |    }
                      |  }""".stripMargin
      ),
      sc.Given(
        tparams = List(T),
        name = encoderName,
        implicitParams = List(sc.Param(sc.Ident("T"), JsonEncoder.of(T), None)),
        tpe = JsonEncoder.of(d.Defaulted.of(T)),
        body = code"""|new ${JsonEncoder.of(d.Defaulted.of(T))} {
                 |  override def unsafeEncode(a: ${d.Defaulted.of(T)}, indent: ${sc.Type.Option.of(sc.Type.Int)}, out: $Write): Unit =
                 |    a match {
                 |      case ${d.Provided}(value) =>
                 |        out.write("{")
                 |        out.write("\\"provided\\":")
                 |        ${sc.Ident("T")}.unsafeEncode(value, None, out)
                 |        out.write("}")
                 |      case ${d.UseDefault} => out.write("\\"defaulted\\"")
                 |    }
                 |}""".stripMargin
      )
    )
  }

  override def stringEnumInstances(wrapperType: sc.Type, underlying: sc.Type): List[sc.Given] =
    List(
      sc.Given(
        tparams = Nil,
        name = decoderName,
        implicitParams = Nil,
        tpe = JsonDecoder.of(wrapperType),
        body = code"""${lookupDecoderFor(underlying)}.mapOrFail($wrapperType.apply)"""
      ),
      sc.Given(
        tparams = Nil,
        name = encoderName,
        implicitParams = Nil,
        tpe = JsonEncoder.of(wrapperType),
        body = code"${lookupEncoderFor(underlying)}.contramap(_.value)"
      )
    )

  override def anyValInstances(wrapperType: Type.Qualified, fieldName: sc.Ident, underlying: sc.Type): List[sc.Given] =
    List(
      sc.Given(
        tparams = Nil,
        name = encoderName,
        implicitParams = Nil,
        tpe = JsonEncoder.of(wrapperType),
        body = code"${lookupEncoderFor(underlying)}.contramap(_.$fieldName)"
      ),
      sc.Given(
        tparams = Nil,
        name = decoderName,
        implicitParams = Nil,
        tpe = JsonDecoder.of(wrapperType),
        body = code"${lookupDecoderFor(underlying)}.map($wrapperType.apply)"
      )
    )

  override def productInstances(tpe: sc.Type, fields: NonEmptyList[JsonLib.Field]): List[sc.Given] = {
    val decoder =
      sc.Given(
        tparams = Nil,
        name = decoderName,
        implicitParams = Nil,
        tpe = JsonDecoder.of(tpe),
        body = {
          val params =
            fields.map(f =>
              f.tpe match {
                case sc.Type.Optional(_) =>
                  code"""${f.scalaName} <- jsonObj.get(${f.jsonName}).forEach(_.as[${f.tpe}]).map(_.flatten)"""
                case _ =>
                  code"""${f.scalaName} <- jsonObj.get(${f.jsonName}).forEach(_.as[${f.tpe}]).flatMap(_.toRight(\"\"\"Missing field ${f.jsonName}\"\"\"))"""
              }
            )

          code"""|$JsonDecoder[$Json.Obj].mapOrFail { jsonObj =>
                 |  import zio.prelude.ForEachOps
                 |
                 |  for {
                 |    ${params.mkCode("\n")}
                 |  } yield $tpe(${fields.map(v => code"${v.scalaName} = ${v.scalaName}").mkCode(", ")})
                 |}""".stripMargin
        }
      )

    val encoder =
      sc.Given(
        tparams = Nil,
        name = encoderName,
        implicitParams = Nil,
        tpe = JsonEncoder.of(tpe),
        body = {
          val params =
            fields.map(f =>
              code"""|out.write(\"\"\"${f.jsonName}:\"\"\")
                     |JsonEncoder[${f.tpe}].unsafeEncode(a.${f.scalaName}, indent, out)""".stripMargin
            )

          code"""|new $JsonEncoder[$tpe] {
                 |  override def unsafeEncode(a: $tpe, indent: Option[Int], out: $Write): Unit = {
                 |    out.write("{")
                 |    ${params.mkCode(code"\nout.write(\",\")\n")}
                 |    out.write("}")
                 |  }
                 |}""".stripMargin
        })

    List(decoder, encoder)
  }

  override def missingInstances: List[sc.ClassMember] = List.empty
}
