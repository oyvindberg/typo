package typo
package internal
package codegen

object JsonLibCirce extends JsonLib {
  val Decoder = sc.Type.Qualified("io.circe.Decoder")
  val Encoder = sc.Type.Qualified("io.circe.Encoder")
  val HCursor = sc.Type.Qualified("io.circe.HCursor")
  val Json = sc.Type.Qualified("io.circe.Json")
  val DecodingFailure = sc.Type.Qualified("io.circe.DecodingFailure")

  val encoderName = sc.Ident("encoder")
  val decoderName = sc.Ident("decoder")

  def decoderFor(tpe: sc.Type): sc.Code =
    Decoder.of(tpe).code

  def encoderFor(tpe: sc.Type): sc.Code =
    Encoder.of(tpe).code

  def anyValInstances(wrapperType: sc.Type.Qualified, underlying: sc.Type): List[sc.Given] =
    List(
      sc.Given(
        tparams = Nil,
        name = encoderName,
        implicitParams = Nil,
        tpe = Encoder.of(wrapperType),
        body = code"${encoderFor(underlying)}.contramap(_.value)"
      ),
      sc.Given(
        tparams = Nil,
        name = decoderName,
        implicitParams = Nil,
        tpe = Decoder.of(wrapperType),
        body = code"${decoderFor(underlying)}.map($wrapperType.apply)"
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

  override def stringEnumInstances(wrapperType: sc.Type, underlying: sc.Type, lookup: sc.Ident): List[sc.Given] =
    List(
      sc.Given(
        tparams = Nil,
        name = decoderName,
        implicitParams = Nil,
        tpe = Decoder.of(wrapperType),
        body = code"""${Decoder.of(underlying)}.emap(str => $lookup.get(str).toRight(s"'$$str' does not match any of the following legal values: $$Names"))"""
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

          sc.Given(
            tparams = Nil,
            name = decoderName,
            implicitParams = Nil,
            tpe = Decoder.of(tpe),
            body = code"""$Decoder.forProduct$n[$tpe, ${fields.map(_.tpe.code).mkCode(", ")}]($fieldNames)($tpe.apply)"""
          )
        case _ =>
          val cases = fields.map { f =>
            code"""${f.scalaName} = orThrow(c.get(${f.jsonName})(${decoderFor(f.tpe)}))"""
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
          sc.Given(
            tparams = Nil,
            name = encoderName,
            implicitParams = Nil,
            tpe = Encoder.of(tpe),
            body = code"""$Encoder.forProduct$n[$tpe, ${fields.map(_.tpe.code).mkCode(", ")}]($fieldNames)($f)"""
          )

        case _ =>
          val cases = fields.map { f =>
            code"""${f.jsonName} -> ${encoderFor(f.tpe)}.apply(row.${f.scalaName})"""
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
