package typo
package internal
package codegen

case class JsonLibCirce(pkg: sc.QIdent, default: ComputedDefault, inlineImplicits: Boolean) extends JsonLib {
  val Decoder = sc.Type.Qualified("io.circe.Decoder")
  val Encoder = sc.Type.Qualified("io.circe.Encoder")
  val HCursor = sc.Type.Qualified("io.circe.HCursor")
  val Json = sc.Type.Qualified("io.circe.Json")
  val DecodingFailure = sc.Type.Qualified("io.circe.DecodingFailure")

  val encoderName = sc.Ident("encoder")
  val decoderName = sc.Ident("decoder")

  /** Resolve known implicits at generation-time instead of at compile-time */
  def lookupDecoderFor(tpe: sc.Type): sc.Code = {
    def go(tpe: sc.Type): sc.Code =
      tpe match {
        case sc.Type.BigDecimal                                            => code"$Decoder.decodeBigDecimal"
        case sc.Type.Boolean                                               => code"$Decoder.decodeBoolean"
        case sc.Type.Byte                                                  => code"$Decoder.decodeByte"
        case sc.Type.Double                                                => code"$Decoder.decodeDouble"
        case sc.Type.Float                                                 => code"$Decoder.decodeFloat"
        case sc.Type.Int                                                   => code"$Decoder.decodeInt"
        case sc.Type.LocalDate                                             => code"$Decoder.decodeLocalDate"
        case sc.Type.LocalDateTime                                         => code"$Decoder.decodeLocalDateTime"
        case sc.Type.LocalTime                                             => code"$Decoder.decodeLocalTime"
        case sc.Type.Long                                                  => code"$Decoder.decodeLong"
        case sc.Type.OffsetDateTime                                        => code"$Decoder.decodeOffsetDateTime"
        case sc.Type.OffsetTime                                            => code"$Decoder.decodeOffsetTime"
        case sc.Type.String                                                => code"$Decoder.decodeString"
        case sc.Type.UUID                                                  => code"$Decoder.decodeUUID"
        case sc.Type.TApply(sc.Type.Array, List(targ))                     => code"$Decoder.decodeArray[$targ](${go(targ)}, implicitly)"
        case sc.Type.TApply(default.Defaulted, List(targ))                 => code"${default.Defaulted}.$decoderName(${go(targ)})"
        case sc.Type.Optional(targ)                                        => code"$Decoder.decodeOption(${go(targ)})"
        case x: sc.Type.Qualified if x.value.idents.startsWith(pkg.idents) => code"$tpe.$decoderName"
        case other                                                         => code"${Decoder.of(other)}"
      }

    if (inlineImplicits) go(sc.Type.base(tpe)) else Decoder.of(tpe).code
  }

  /** Resolve known implicits at generation-time instead of at compile-time */
  def lookupEncoderFor(tpe: sc.Type): sc.Code = {
    def go(tpe: sc.Type): sc.Code =
      tpe match {
        case sc.Type.BigDecimal                                            => code"$Encoder.encodeBigDecimal"
        case sc.Type.Boolean                                               => code"$Encoder.encodeBoolean"
        case sc.Type.Byte                                                  => code"$Encoder.encodeByte"
        case sc.Type.Double                                                => code"$Encoder.encodeDouble"
        case sc.Type.Float                                                 => code"$Encoder.encodeFloat"
        case sc.Type.Int                                                   => code"$Encoder.encodeInt"
        case sc.Type.LocalDate                                             => code"$Encoder.encodeLocalDate"
        case sc.Type.LocalDateTime                                         => code"$Encoder.encodeLocalDateTime"
        case sc.Type.LocalTime                                             => code"$Encoder.encodeLocalTime"
        case sc.Type.Long                                                  => code"$Encoder.encodeLong"
        case sc.Type.OffsetDateTime                                        => code"$Encoder.encodeOffsetDateTime"
        case sc.Type.OffsetTime                                            => code"$Encoder.encodeOffsetTime"
        case sc.Type.String                                                => code"$Encoder.encodeString"
        case sc.Type.UUID                                                  => code"$Encoder.encodeUUID"
        case sc.Type.TApply(sc.Type.Array, List(targ))                     => code"$Encoder.encodeIterable[$targ, ${sc.Type.Array}](${go(targ)}, implicitly)"
        case sc.Type.TApply(default.Defaulted, List(targ))                 => code"${default.Defaulted}.$encoderName(${go(targ)})"
        case sc.Type.Optional(targ)                                        => code"$Encoder.encodeOption(${go(targ)})"
        case x: sc.Type.Qualified if x.value.idents.startsWith(pkg.idents) => code"$tpe.$encoderName"
        case other                                                         => code"${Encoder.of(other)}"
      }

    if (inlineImplicits) go(sc.Type.base(tpe)) else Encoder.of(tpe).code
  }

  def anyValInstances(wrapperType: sc.Type.Qualified, fieldName: sc.Ident, underlying: sc.Type): List[sc.Given] =
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
                 |  case ${d.Provided}(value) => $Json.obj("provided" -> ${Encoder.of(T)}.apply(value))
                 |  case ${d.UseDefault}      => $Json.fromString("defaulted")
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
        body = code"""${Decoder.of(underlying)}.emap($wrapperType.apply)"""
      ),
      sc.Given(
        tparams = Nil,
        name = encoderName,
        implicitParams = Nil,
        tpe = Encoder.of(wrapperType),
        body = code"${Encoder.of(underlying)}.contramap(_.value)"
      )
    )

  def productInstances(tpe: sc.Type, fields: NonEmptyList[JsonLib.Field]): List[sc.Given] = {
    val decoder =
      fields.length match {
        case n if n < 23 =>
          val fieldNames = fields.map(_.jsonName.code).mkCode(", ")
          val instances = fields.map(f => lookupDecoderFor(f.tpe)).mkCode(", ")

          sc.Given(
            tparams = Nil,
            name = decoderName,
            implicitParams = Nil,
            tpe = Decoder.of(tpe),
            body = code"""$Decoder.forProduct$n[$tpe, ${fields.map(_.tpe.code).mkCode(", ")}]($fieldNames)($tpe.apply)($instances)"""
          )
        case _ =>
          val cases = fields.map { f =>
            code"""${f.scalaName} = orThrow(c.get(${f.jsonName})(${lookupDecoderFor(f.tpe)}))"""
          }
          sc.Given(
            tparams = Nil,
            name = decoderName,
            implicitParams = Nil,
            tpe = Decoder.of(tpe),
            body = code"""|$Decoder.instanceTry[$tpe]((c: $HCursor) =>
                          |  ${sc.Type.Try} {
                          |    def orThrow[R](either: ${sc.Type.Either}[$DecodingFailure, R]): R = either match {
                          |      case Left(err) => throw err
                          |      case Right(r)  => r
                          |    }
                          |    $tpe(
                          |      ${cases.mkCode(",\n")}
                          |    )
                          |  }
                          |)""".stripMargin
          )

      }

    val encoder = {
      fields.length match {
        case n if n < 23 =>
          val fieldNames = fields.map(_.jsonName.code).mkCode(", ")
          val f = code"x => (${fields.map(f => code"x.${f.scalaName}").mkCode(", ")})"
          val instances = fields.map(f => lookupEncoderFor(f.tpe)).mkCode(", ")
          sc.Given(
            tparams = Nil,
            name = encoderName,
            implicitParams = Nil,
            tpe = Encoder.of(tpe),
            body = code"""$Encoder.forProduct$n[$tpe, ${fields.map(_.tpe.code).mkCode(", ")}]($fieldNames)($f)($instances)"""
          )

        case _ =>
          val cases = fields.map { f =>
            code"""${f.jsonName} -> ${lookupEncoderFor(f.tpe)}.apply(row.${f.scalaName})"""
          }
          sc.Given(
            tparams = Nil,
            name = encoderName,
            implicitParams = Nil,
            tpe = Encoder.of(tpe),
            body = code"""|${Encoder.of(tpe)}(row =>
                          |  $Json.obj(
                          |    ${cases.mkCode(",\n")}
                          |  )
                          |)""".stripMargin
          )

      }
    }

    List(decoder, encoder)
  }

  override def missingInstances: List[sc.ClassMember] = Nil
}
