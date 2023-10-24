package typo.internal.codegen

import typo.internal.ComputedDefault
import typo.sc.Type
import typo.{NonEmptyList, sc}

final case class JsonLibZioJson(pkg: sc.QIdent, default: ComputedDefault, inlineImplicits: Boolean) extends JsonLib {
  private val Decoder = sc.Type.Qualified("zio.json.JsonDecoder")
  private val Encoder = sc.Type.Qualified("zio.json.JsonEncoder")
  private val DeriveJsonDecoder = sc.Type.Qualified("zio.json.DeriveJsonDecoder")
  private val DeriveJsonEncoder = sc.Type.Qualified("zio.json.DeriveJsonEncoder")
  private val Json = sc.Type.Qualified("zio.json.ast.Json")

  private val encoderName = sc.Ident("encoder")
  private val decoderName = sc.Ident("decoder")

  /** Resolve known implicits at generation-time instead of at compile-time */
  private def lookupDecoderFor(tpe: sc.Type): sc.Code = {
    def go(tpe: sc.Type): sc.Code =
      tpe match {
        case sc.Type.BigDecimal                                            => code"$Decoder.scalaBigDecimal"
        case sc.Type.Boolean                                               => code"$Decoder.boolean"
        case sc.Type.Byte                                                  => code"$Decoder.byte"
        case sc.Type.Double                                                => code"$Decoder.double"
        case sc.Type.Float                                                 => code"$Decoder.float"
        case sc.Type.Instant                                               => code"$Decoder.instant"
        case sc.Type.Int                                                   => code"$Decoder.int"
        case sc.Type.LocalDate                                             => code"$Decoder.decodeLocalDate"
        case sc.Type.LocalDateTime                                         => code"$Decoder.localDate"
        case sc.Type.LocalTime                                             => code"$Decoder.localTime"
        case sc.Type.Long                                                  => code"$Decoder.long"
        case sc.Type.OffsetDateTime                                        => code"$Decoder.offsetDateTime"
        case sc.Type.OffsetTime                                            => code"$Decoder.offsetTime"
        case sc.Type.String                                                => code"$Decoder.string"
        case sc.Type.UUID                                                  => code"$Decoder.uuid"
        case sc.Type.TApply(sc.Type.Array, List(targ))                     => code"$Decoder.array[$targ](${go(targ)}, implicitly)"
        case sc.Type.TApply(default.Defaulted, List(targ))                 => code"${default.Defaulted}.$decoderName(${go(targ)})"
        case sc.Type.Optional(targ)                                        => code"$Decoder.option(${go(targ)})"
        case x: sc.Type.Qualified if x.value.idents.startsWith(pkg.idents) => code"$tpe.$decoderName"
        case other                                                         => code"${Decoder.of(other)}"
      }

    if (inlineImplicits) go(sc.Type.base(tpe)) else Decoder.of(tpe).code
  }

  /** Resolve known implicits at generation-time instead of at compile-time */
  def lookupEncoderFor(tpe: sc.Type): sc.Code = {
    def go(tpe: sc.Type): sc.Code =
      tpe match {
        case sc.Type.BigDecimal                                            => code"$Encoder.scalaBigDecimal"
        case sc.Type.Boolean                                               => code"$Encoder.boolean"
        case sc.Type.Byte                                                  => code"$Encoder.byte"
        case sc.Type.Double                                                => code"$Encoder.double"
        case sc.Type.Float                                                 => code"$Encoder.float"
        case sc.Type.Instant                                               => code"$Encoder.instant"
        case sc.Type.Int                                                   => code"$Encoder.int"
        case sc.Type.LocalDate                                             => code"$Encoder.localDate"
        case sc.Type.LocalDateTime                                         => code"$Encoder.localDateTime"
        case sc.Type.LocalTime                                             => code"$Encoder.localTime"
        case sc.Type.Long                                                  => code"$Encoder.long"
        case sc.Type.OffsetDateTime                                        => code"$Encoder.offsetDateTime"
        case sc.Type.OffsetTime                                            => code"$Encoder.offsetTime"
        case sc.Type.String                                                => code"$Encoder.string"
        case sc.Type.UUID                                                  => code"$Encoder.uuid"
        case sc.Type.TApply(sc.Type.Array, List(targ))                     => code"$Encoder.array[$targ](${go(targ)}, implicitly)"
        case sc.Type.TApply(default.Defaulted, List(targ))                 => code"${default.Defaulted}.$encoderName(${go(targ)})"
        case sc.Type.Optional(targ)                                        => code"$Encoder.option(${go(targ)})"
        case x: sc.Type.Qualified if x.value.idents.startsWith(pkg.idents) => code"$tpe.$encoderName"
        case other                                                         => code"${Encoder.of(other)}"
      }

    if (inlineImplicits) go(sc.Type.base(tpe)) else Encoder.of(tpe).code
  }

  override def defaultedInstance(d: ComputedDefault): List[sc.Given] = {
    val T = sc.Type.Abstract(sc.Ident("T"))
    List(
      sc.Given(
        tparams = List(T),
        name = decoderName,
        implicitParams = List(sc.Param(sc.Ident("T"), Decoder.of(T), None)),
        tpe = Decoder.of(d.Defaulted.of(T)),
        body = code"""|c => c.as[${sc.Type.String}].flatMap {
                 |    case "defaulted" => ${sc.Type.Right}(${d.UseDefault})
                 |    case _           => c.downField("provided").as[$T].map(${d.Provided}.apply)
                 |  }""".stripMargin
      ),
      sc.Given(
        tparams = List(T),
        name = encoderName,
        implicitParams = List(sc.Param(sc.Ident("T"), Encoder.of(T), None)),
        tpe = Encoder.of(d.Defaulted.of(T)),
        body = code"""|$Encoder.instance {
                 |  case ${d.Provided}(value) => $Json.Obj.apply("provided" -> ${Encoder.of(T)}.encodeJson(value))
                 |  case ${d.UseDefault}      => $Json.Str.apply("defaulted")
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
        tpe = Decoder.of(wrapperType),
        body = code"""${lookupDecoderFor(underlying)}.mapOrFail($wrapperType.apply)"""
      ),
      sc.Given(
        tparams = Nil,
        name = encoderName,
        implicitParams = Nil,
        tpe = Encoder.of(wrapperType),
        body = code"${lookupEncoderFor(underlying)}.contramap(_.value)"
      )
    )

  override def anyValInstances(wrapperType: Type.Qualified, fieldName: sc.Ident, underlying: sc.Type): List[sc.Given] =
    List(
      sc.Given(
        tparams = Nil,
        name = encoderName,
        implicitParams = Nil,
        tpe = Encoder.of(wrapperType),
        body = code"${lookupEncoderFor(underlying)}.contramap(_.$fieldName)"
      ),
      sc.Given(
        tparams = Nil,
        name = decoderName,
        implicitParams = Nil,
        tpe = Decoder.of(wrapperType),
        body = code"${lookupDecoderFor(underlying)}.map($wrapperType.apply)"
      )
    )

  override def productInstances(tpe: sc.Type, fields: NonEmptyList[JsonLib.Field]): List[sc.Given] = {
    val decoder =
      sc.Given(
        tparams = Nil,
        name = decoderName,
        implicitParams = Nil,
        tpe = Decoder.of(tpe),
        body = code"""$DeriveJsonDecoder.gen[$tpe]"""
      )

    val encoder =
      sc.Given(
        tparams = Nil,
        name = encoderName,
        implicitParams = Nil,
        tpe = Encoder.of(tpe),
        body = code"""$DeriveJsonEncoder.gen[$tpe]"""
      )

    List(decoder, encoder)
  }

  override def missingInstances: List[sc.ClassMember] = List.empty
}
