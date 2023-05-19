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
    val encoder = code"""|implicit val encoder: ${Encoder.of(wrapperType)} =
                         |  ${Encoder.of(underlying)}.contramap(_.value)""".stripMargin

    List(decoder, encoder)
  }

  override def instances(tpe: sc.Type, cols: NonEmptyList[ComputedColumn]): List[sc.Code] = {
    val cases = cols.map { col =>
      code"""${col.name} <- c.downField("${col.dbName.value}").as[${col.tpe}]"""
    }
    val decoder = code"""|implicit val decoder: ${Decoder.of(tpe)} =
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

  override def missingInstances: List[sc.Code] = {
    val postgresTypes = PostgresTypes.all.flatMap { case (_, tpe) =>
      List(
        code"""|implicit val ${tpe.value.name.value}Encoder: ${Encoder.of(tpe)} =
             |  ${Encoder.of(sc.Type.String)}.contramap(_.getValue)""".stripMargin,
        code"""|implicit val ${tpe.value.name.value}Decoder: ${Decoder.of(tpe)} =
               |  ${Decoder.of(sc.Type.String)}.map(str => $tpe(str))""".stripMargin
      )
    }
    val PgSQLXML = {
      val tpe = sc.Type.PgSQLXML
      List(
        code"""|implicit val PgSQLXMLEncoder: ${Encoder.of(tpe)} =
               |  ${Encoder.of(sc.Type.String)}.contramap(_.getString)""".stripMargin,
        code"""|implicit val PgSQLXMLDecoder: ${Decoder.of(tpe)} =
               |  ${Decoder.of(sc.Type.String)}.map(str => $tpe(null, str))""".stripMargin
      )
    }

    val hstore = {
      val javaMap = sc.Type.JavaMap.of(sc.Type.String, sc.Type.String)
      val scalaMap = sc.Type.Map.of(sc.Type.String, sc.Type.String)

      List(
        code"""|implicit val hstoreEncoder: ${Encoder.of(javaMap)} = {
               |  // on 2.12 and getting an error here? add dependency: org.scala-lang.modules::scala-collection-compat
               |  import scala.jdk.CollectionConverters._
               |  implicitly[${Encoder.of(scalaMap)}].contramap(_.asScala.toMap)
               |}""".stripMargin,
        code"""|implicit val hstoreDecoder: ${Decoder.of(javaMap)} = {
              |  // on 2.12 and getting an error here? add dependency: org.scala-lang.modules::scala-collection-compat
              |  import scala.jdk.CollectionConverters._
              |  implicitly[${Decoder.of(scalaMap)}].map(_.asJava)
              |}""".stripMargin
      )
    }

    val pgObjectFormat = List(
      code"""|implicit val pgObjectEncoder: ${Encoder.of(sc.Type.PGobject)} =
             |  $Encoder.forProduct2 ("type", "value") (x => (x.getType, x.getValue) )""".stripMargin,
      code"""|implicit val pgObjectDecoder: ${Decoder.of(sc.Type.PGobject)} =
             |  $Decoder.forProduct2[${sc.Type.PGobject}, ${sc.Type.String}, ${sc.Type.String}] ("type", "value") {case (tpe, value) =>
             |    val obj = new ${sc.Type.PGobject}
             |    obj.setType (tpe)
             |    obj.setValue (value)
             |    obj
             |  }""".stripMargin
    )

    postgresTypes ++ PgSQLXML ++ hstore ++ pgObjectFormat
  }
}
