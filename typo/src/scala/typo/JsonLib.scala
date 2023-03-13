package typo

import typo.sc.syntax.CodeInterpolator

trait JsonLib {
  def defaultedInstance(defaulted: sc.QIdent, provided: sc.QIdent, useDefault: sc.QIdent): List[sc.Code]
  def stringEnumInstances(wrapperType: sc.Type, underlying: sc.Type, lookup: sc.Ident): List[sc.Code]
  def anyValInstances(wrapperType: sc.Type.Qualified, underlying: sc.Type): List[sc.Code]
  def instances(tpe: sc.Type, scalaFields: Seq[(sc.Ident, sc.Type, db.Col)]): List[sc.Code]
}

object JsonLib {
  object PlayJson extends JsonLib {
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
    val JsObject = sc.Type.Qualified("play.api.libs.json.JsObject")

    override def defaultedInstance(defaulted: sc.QIdent, provided: sc.QIdent, useDefault: sc.QIdent): List[sc.Code] = {
      val T = sc.Type.Abstract(sc.Ident("T"))
      val defaultOfT = sc.Type.TApply(sc.Type.Qualified(defaulted), List(T))
      val reader = code"""implicit def reads[$T: $ReadsName]: ${Reads(defaultOfT)} = {
            |    case $JsString("defaulted") =>
            |      $JsSuccess($useDefault)
            |    case $JsObject(Seq(("provided", providedJson: $JsValue))) =>
            |      $Json.fromJson[T](providedJson).map($provided.apply)
            |    case _ =>
            |      $JsError(s"Expected `$defaulted` json object structure")
            |  }
            |""".stripMargin

      val writer = code"""implicit def writes[$T: $WritesName]: ${Writes(defaultOfT)} = {
            |    case $provided(value) => $Json.obj("provided" -> implicitly[${Writes(T)}].writes(value))
            |    case $useDefault      => $JsString("defaulted")
            |  }
            |""".stripMargin
      List(
        reader,
        writer
      )
    }
    override def stringEnumInstances(wrapperType: sc.Type, underlying: sc.Type, lookup: sc.Ident): List[sc.Code] = {
      val reader = code"""implicit val reads: ${Reads(wrapperType)} = (value: $JsValue) =>
                         |    value.validate[${sc.Type.String}].flatMap { str =>
                         |      $lookup.get(str) match {
                         |        case Some(value) => $JsSuccess(value)
                         |        case None => $JsError(s"'$$str' does not match any of the following legal values: $$Names")
                         |      }
                         |    }
                         |""".stripMargin
      val writer = code"""implicit val writes: ${Writes(wrapperType)} = value => implicitly[${Writes(underlying)}].writes(value.value)"""

      List(reader, writer)
    }

    override def instances(tpe: sc.Type, scalaFields: Seq[(sc.Ident, sc.Type, db.Col)]): List[sc.Code] =
      List(
        code"""implicit val oFormat: ${OFormat(tpe)} = $Json.format"""
      )

    def anyValInstances(wrapperType: sc.Type.Qualified, underlying: sc.Type): List[sc.Code] =
      List(
        code"""implicit val format: ${Format(wrapperType)} = implicitly[${Format(underlying)}].bimap(${wrapperType.value.last}.apply, _.value)"""
      )
  }
}
