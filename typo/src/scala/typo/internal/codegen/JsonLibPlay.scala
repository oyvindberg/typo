package typo
package internal
package codegen

object JsonLibPlay extends JsonLib {
  val Reads = sc.Type.Qualified("play.api.libs.json.Reads")
  val Writes = sc.Type.Qualified("play.api.libs.json.Writes")
  val OWrites = sc.Type.Qualified("play.api.libs.json.OWrites")
  val Json = sc.Type.Qualified("play.api.libs.json.Json")
  val JsString = sc.Type.Qualified("play.api.libs.json.JsString")
  val JsError = sc.Type.Qualified("play.api.libs.json.JsError")
  val JsSuccess = sc.Type.Qualified("play.api.libs.json.JsSuccess")
  val JsValue = sc.Type.Qualified("play.api.libs.json.JsValue")
  val JsNull = sc.Type.Qualified("play.api.libs.json.JsNull")
  val JsObject = sc.Type.Qualified("play.api.libs.json.JsObject")
  val JsResult = sc.Type.Qualified("play.api.libs.json.JsResult")

  val readsName: sc.Ident = sc.Ident("reads")
  val readsOptName = sc.Ident("readsOpt")
  val writesName: sc.Ident = sc.Ident("writes")

  def readsFor(tpe: sc.Type): sc.Code =
    sc.Summon(Reads.of(tpe)).code

  def writesFor(tpe: sc.Type): sc.Code =
    sc.Summon(Writes.of(tpe)).code

  override def defaultedInstance(d: ComputedDefault): List[sc.Given] = {
    val T = sc.Type.Abstract(sc.Ident("T"))
    val reader = sc.Given(
      tparams = List(T),
      name = readsName,
      implicitParams = List(sc.Param(sc.Ident("T"), Reads.of(T), None)),
      tpe = Reads.of(d.Defaulted.of(T)),
      body = code"""|{
                    |  case $JsString("defaulted") =>
                    |    $JsSuccess(${d.Defaulted}.${d.UseDefault})
                    |  case $JsObject(Seq(("provided", providedJson: $JsValue))) =>
                    |    $Json.fromJson[T](providedJson).map(${d.Defaulted}.${d.Provided}.apply)
                    |  case _ =>
                    |    $JsError(s"Expected `${d.Defaulted}` json object structure")
                    |}""".stripMargin
    )

    val readerOpt = sc.Given(
      tparams = List(T),
      name = readsOptName,
      implicitParams = List(sc.Param(sc.Ident("T"), Reads.of(T), None)),
      tpe = Reads.of(d.Defaulted.of(sc.Type.Option.of(T))),
      body = code"""|{
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
    )

    val writer = sc.Given(
      tparams = List(T),
      name = writesName,
      implicitParams = List(sc.Param(sc.Ident("T"), Writes.of(T), None)),
      tpe = Writes.of(d.Defaulted.of(T)),
      body = code"""|{
                    |  case ${d.Defaulted}.${d.Provided}(value) => $Json.obj("provided" -> $T.writes(value))
                    |  case ${d.Defaulted}.${d.UseDefault}      => $JsString("defaulted")
                    |}""".stripMargin
    )

    List(reader, readerOpt, writer)
  }
  override def stringEnumInstances(wrapperType: sc.Type, underlying: sc.Type, lookup: sc.Ident): List[sc.Given] =
    List(
      sc.Given(
        tparams = Nil,
        name = readsName,
        implicitParams = Nil,
        tpe = Reads.of(wrapperType),
        body = code"""|${Reads.of(wrapperType)}((value: $JsValue) =>
                      |  value.validate(${readsFor(underlying)}).flatMap { str =>
                      |    $lookup.get(str) match {
                      |      case Some(value) => $JsSuccess(value)
                      |      case None => $JsError(s"'$$str' does not match any of the following legal values: $$Names")
                      |    }
                      |  }
                      |)""".stripMargin
      ),
      sc.Given(
        tparams = Nil,
        name = writesName,
        implicitParams = Nil,
        tpe = Writes.of(wrapperType),
        body = code"${Writes.of(wrapperType)}(value => ${writesFor(underlying)}.writes(value.value))"
      )
    )

  override def productInstances(tpe: sc.Type, fields: NonEmptyList[JsonLib.Field]): List[sc.Given] =
    List(
      sc.Given(
        tparams = Nil,
        name = readsName,
        implicitParams = Nil,
        tpe = Reads.of(tpe),
        body = {
          val newFields = fields.map { f =>
            val value = f.tpe match {
              case sc.Type.Optional(of) => code"""json.\\(${f.jsonName}).toOption.map(_.as[$of])"""
              case _                    => code"""json.\\(${f.jsonName}).as[${f.tpe}]"""
            }

            code"""${f.scalaName} = $value"""
          }
          code"""|${Reads.of(tpe)}(json => $JsResult.fromTry(
                 |    ${sc.Type.Try}(
                 |      $tpe(
                 |        ${newFields.mkCode(",\n")}
                 |      )
                 |    )
                 |  ),
                 |)""".stripMargin
        }
      ),
      sc.Given(
        tparams = Nil,
        name = writesName,
        implicitParams = Nil,
        tpe = OWrites.of(tpe),
        body = {
          val newFields = fields.map { f =>
            code"""${f.jsonName} -> $Json.toJson(o.${f.scalaName})"""
          }
          code"""|${OWrites.of(tpe)}(o =>
                 |  new $JsObject(${sc.Type.ListMap.of(sc.Type.String, JsValue)}(
                 |    ${newFields.mkCode(",\n")}
                 |  ))
                 |)""".stripMargin
        }
      )
    )

  def anyValInstances(wrapperType: sc.Type.Qualified, underlying: sc.Type): List[sc.Given] =
    List(
      sc.Given(
        tparams = Nil,
        name = readsName,
        implicitParams = Nil,
        tpe = Reads.of(wrapperType),
        body = code"${readsFor(underlying)}.map(${wrapperType.value.name}.apply)"
      ),
      sc.Given(
        tparams = Nil,
        name = writesName,
        implicitParams = Nil,
        tpe = Writes.of(wrapperType),
        body = code"${writesFor(underlying)}.contramap(_.value)"
      )
    )

  override val missingInstances: List[sc.ClassMember] =
    List(
      sc.Given(
        tparams = Nil,
        name = sc.Ident("OffsetTimeReads"),
        implicitParams = Nil,
        tpe = Reads.of(sc.Type.OffsetTime),
        body = code"""|${readsFor(sc.Type.String)}.flatMapResult { str =>
                      |    try $JsSuccess(${sc.Type.OffsetTime}.parse(str)) catch {
                      |      case x: ${sc.Type.DateTimeParseException} => $JsError(s"must follow $${${sc.Type.DateTimeFormatter}.ISO_OFFSET_TIME}: $${x.getMessage}")
                      |    }
                      |  }""".stripMargin
      ),
      sc.Given(
        tparams = Nil,
        name = sc.Ident("OffsetTimeWrites"),
        implicitParams = Nil,
        tpe = Writes.of(sc.Type.OffsetTime),
        body = code"${writesFor(sc.Type.String)}.contramap(_.toString)"
      )
    )
}
