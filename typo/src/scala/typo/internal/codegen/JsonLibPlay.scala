package typo
package internal
package codegen

object JsonLibPlay extends JsonLib {
  def Format(t: sc.Type) = sc.Type.TApply(sc.Type.Qualified("play.api.libs.json.Format"), List(t))
  val ReadsName = sc.Type.Qualified("play.api.libs.json.Reads")
  def Reads(t: sc.Type) = sc.Type.TApply(ReadsName, List(t))
  val WritesName = "play.api.libs.json.Writes"
  def Writes(t: sc.Type) = sc.Type.TApply(sc.Type.Qualified(WritesName), List(t))
  val Json = sc.Type.Qualified("play.api.libs.json.Json")
  def OFormat(t: sc.Type) = sc.Type.TApply(sc.Type.Qualified("play.api.libs.json.OFormat"), List(t))
  val JsString = sc.Type.Qualified("play.api.libs.json.JsString")
  val JsError = sc.Type.Qualified("play.api.libs.json.JsError")
  val JsSuccess = sc.Type.Qualified("play.api.libs.json.JsSuccess")
  val JsValue = sc.Type.Qualified("play.api.libs.json.JsValue")
  val JsNull = sc.Type.Qualified("play.api.libs.json.JsNull")
  val JsObject = sc.Type.Qualified("play.api.libs.json.JsObject")
  val JsResult = sc.Type.Qualified("play.api.libs.json.JsResult")

  override def defaultedInstance(d: ComputedDefault): List[sc.Code] = {
    val T = sc.Type.Abstract(sc.Ident("T"))
    val defaultOfT = d.Defaulted.of(T)
    val defaultOfOptT = d.Defaulted.of(sc.Type.Option.of(T))
    val reader =
      code"""|implicit def reads[$T: $ReadsName]: ${Reads(defaultOfT)} = {
             |  case $JsString("defaulted") =>
             |    $JsSuccess(${d.Defaulted}.${d.UseDefault})
             |  case $JsObject(Seq(("provided", providedJson: $JsValue))) =>
             |    $Json.fromJson[T](providedJson).map(${d.Defaulted}.${d.Provided}.apply)
             |  case _ =>
             |    $JsError(s"Expected `${d.Defaulted}` json object structure")
             |}
             |""".stripMargin
    val readerOpt =
      code"""|implicit def readsOpt[$T: $ReadsName]: ${Reads(defaultOfOptT)} = {
             |  case $JsString("defaulted") =>
             |    $JsSuccess(${d.Defaulted}.${d.UseDefault})
             |  case $JsObject(Seq(("provided", $JsNull))) =>
             |    $JsSuccess(${d.Defaulted}.${d.Provided}(${sc.Type.None}))
             |  case $JsObject(Seq(("provided", providedJson: $JsValue))) =>
             |    $Json.fromJson[T](providedJson).map(x => ${d.Defaulted}.${d.Provided}(${sc.Type.Some}(x)))
             |  case _ =>
             |    $JsError(s"Expected `${d.Defaulted}` json object structure")
             |}
             |""".stripMargin

    val writer =
      code"""|implicit def writes[$T: $WritesName]: ${Writes(defaultOfT)} = {
             |  case ${d.Defaulted}.${d.Provided}(value) => $Json.obj("provided" -> implicitly[${Writes(T)}].writes(value))
             |  case ${d.Defaulted}.${d.UseDefault}      => $JsString("defaulted")
             |}
             |""".stripMargin

    List(reader, readerOpt, writer)
  }
  override def stringEnumInstances(wrapperType: sc.Type, underlying: sc.Type, lookup: sc.Ident): List[sc.Code] = {
    val reader =
      code"""|implicit val reads: ${Reads(wrapperType)} = (value: $JsValue) =>
             |  value.validate[${sc.Type.String}].flatMap { str =>
             |    $lookup.get(str) match {
             |      case Some(value) => $JsSuccess(value)
             |      case None => $JsError(s"'$$str' does not match any of the following legal values: $$Names")
             |    }
             |  }
             |""".stripMargin
    val writer = code"""implicit val writes: ${Writes(wrapperType)} = value => implicitly[${Writes(underlying)}].writes(value.value)"""

    List(reader, writer)
  }

  override def instances(tpe: sc.Type, cols: NonEmptyList[ComputedColumn]): List[sc.Code] = {
    def as(col: ComputedColumn): sc.Code =
      col.tpe match {
        case sc.Type.Optional(of) => code"""json.\\(${sc.StrLit(col.dbName.value)}).toOption.map(_.as[$of])"""
        case _                    => code"""json.\\(${sc.StrLit(col.dbName.value)}).as[${col.tpe}]"""
      }

    List(
      code"""|implicit val oFormat: ${OFormat(tpe)} = new ${OFormat(tpe)}{
             |  override def writes(o: $tpe): $JsObject =
             |    $Json.obj(
             |      ${cols.map(col => code"""${sc.StrLit(col.dbName.value)} -> o.${col.name}""").mkCode(",\n")}
             |    )
             |
             |  override def reads(json: $JsValue): ${JsResult.of(tpe)} = {
             |    $JsResult.fromTry(
             |      ${sc.Type.Try}(
             |        $tpe(
             |          ${cols.map(col => code"""${col.name} = ${as(col)}""").mkCode(",\n")}
             |        )
             |      )
             |    )
             |  }
             |}"""
    )
  }

  def anyValInstances(wrapperType: sc.Type.Qualified, underlying: sc.Type): List[sc.Code] =
    List(
      code"""implicit val format: ${Format(wrapperType)} = implicitly[${Format(underlying)}].bimap(${wrapperType.value.name}.apply, _.value)"""
    )

  override def customTypeInstances(ct: CustomType): List[sc.Code] = {
    val tpe = ct.typoType
    def as(param: sc.Param): sc.Code =
      param.tpe match {
        case sc.Type.Optional(of) => code"""json.\\(${sc.StrLit(param.name.value)}).toOption.map(_.as[$of])"""
        case _                    => code"""json.\\(${sc.StrLit(param.name.value)}).as[${param.tpe}]"""
      }

    val format =
      code"""|implicit val oFormat: ${OFormat(tpe)} = new ${OFormat(tpe)}{
             |  override def writes(o: $tpe): $JsObject =
             |    $Json.obj(
             |      ${ct.params.map(param => code"""${sc.StrLit(param.name.value)} -> o.${param.name}""").mkCode(",\n")}
             |    )
             |
             |  override def reads(json: $JsValue): ${JsResult.of(tpe)} = {
             |    $JsResult.fromTry(
             |      ${sc.Type.Try}(
             |        $tpe(
             |          ${ct.params.map(param => code"""${param.name} = ${as(param)}""").mkCode(",\n")}
             |        )
             |      )
             |    )
             |  }
             |}"""

    List(format)
  }
}
