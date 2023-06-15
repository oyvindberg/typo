package typo
package internal
package codegen

object JsonLibCirce extends JsonLib {
  val Decoder = sc.Type.Qualified("io.circe.Decoder")
  val Encoder = sc.Type.Qualified("io.circe.Encoder")
  val HCursor = sc.Type.Qualified("io.circe.HCursor")
  val Json = sc.Type.Qualified("io.circe.Json")

  override def defaultedInstance(d: ComputedDefault): List[sc.Code] = {
    val T = sc.Type.Abstract(sc.Ident("T"))
    val decoder =
      code"""|implicit def decoder[$T: $Decoder]: ${Decoder.of(d.Defaulted.of(T))} = c =>
             |  c.as[${sc.Type.String}].flatMap {
             |    case "defaulted" => ${sc.Type.Right}(${d.UseDefault})
             |    case _           => c.downField("provided").as[$T].map(${d.Provided}.apply)
             |  }""".stripMargin

    val encoder =
      code"""|implicit def encoder[$T: $Encoder]: ${Encoder.of(d.Defaulted.of(T))} =
             |  $Encoder.instance {
             |    case ${d.Provided}(value) => $Json.obj("provided" -> ${Encoder.of(T)}.apply(value))
             |    case ${d.UseDefault}      => $Json.fromString("defaulted")
             |  }""".stripMargin

    List(decoder, encoder)
  }

  override def stringEnumInstances(wrapperType: sc.Type, underlying: sc.Type, lookup: sc.Ident): List[sc.Code] = {
    val decoder =
      code"""|implicit val decoder: ${Decoder.of(wrapperType)} =
             |  ${Decoder.of(
              underlying
            )}.emap(str => $lookup.get(str).toRight(s"'$$str' does not match any of the following legal values: $$Names"))""".stripMargin
    val encoder =
      code"""|implicit val encoder: ${Encoder.of(wrapperType)} =
             |  ${Encoder.of(underlying)}.contramap(_.value)""".stripMargin

    List(decoder, encoder)
  }

  override def instances(tpe: sc.Type, cols: NonEmptyList[ComputedColumn]): List[sc.Code] = {
    val cases = cols.map { col =>
      code"""${col.name} <- c.downField("${col.dbName.value}").as[${col.tpe}]"""
    }
    val decoder =
      code"""|implicit val decoder: ${Decoder.of(tpe)} =
             |  (c: $HCursor) =>
             |    for {
             |      ${cases.mkCode("\n")}
             |    } yield $tpe(${cols.map(_.name.code).mkCode(", ")})""".stripMargin

    val encoder =
      code"""|implicit val encoder: ${Encoder.of(tpe)} = {
             |  import io.circe.syntax._
             |  row =>
             |    $Json.obj(
             |      ${cols.map(col => code""""${col.dbName.value}" := row.${col.name}""").mkCode(",\n")}
             |    )}""".stripMargin

    List(decoder, encoder)
  }

  def anyValInstances(wrapperType: sc.Type.Qualified, underlying: sc.Type): List[sc.Code] =
    List(
      code"""|implicit val encoder: ${Encoder.of(wrapperType)} =
             |  ${Encoder.of(underlying)}.contramap(_.value)""".stripMargin,
      code"""|implicit val decoder: ${Decoder.of(wrapperType)} =
             |  ${Decoder.of(underlying)}.map($wrapperType(_))""".stripMargin
    )

  override def customTypeInstances(ct: CustomType): List[sc.Code] = {
    val cases = ct.params.map { param =>
      code"""${param.name} <- c.downField("${param.name.value}").as[${param.tpe}]"""
    }
    val decoder =
      code"""|implicit val decoder: ${Decoder.of(ct.typoType)} =
             |  (c: $HCursor) =>
             |    for {
             |      ${cases.mkCode("\n")}
             |    } yield ${ct.typoType}(${ct.params.map(_.name.code).mkCode(", ")})""".stripMargin

    val encoder =
      code"""|implicit val encoder: ${Encoder.of(ct.typoType)} = {
             |  import io.circe.syntax._
             |  row =>
             |    $Json.obj(
             |      ${ct.params.map(param => code""""${param.name.value}" := row.${param.name}""").mkCode(",\n")}
             |    )}""".stripMargin

    List(decoder, encoder)

  }
}
