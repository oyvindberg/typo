/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package public
package pgtest

import adventureworks.customtypes.TypoBox
import adventureworks.customtypes.TypoBytea
import adventureworks.customtypes.TypoCircle
import adventureworks.customtypes.TypoHStore
import adventureworks.customtypes.TypoInet
import adventureworks.customtypes.TypoInstant
import adventureworks.customtypes.TypoInt2Vector
import adventureworks.customtypes.TypoInterval
import adventureworks.customtypes.TypoJson
import adventureworks.customtypes.TypoJsonb
import adventureworks.customtypes.TypoLine
import adventureworks.customtypes.TypoLineSegment
import adventureworks.customtypes.TypoLocalDate
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoLocalTime
import adventureworks.customtypes.TypoMoney
import adventureworks.customtypes.TypoOffsetTime
import adventureworks.customtypes.TypoPath
import adventureworks.customtypes.TypoPoint
import adventureworks.customtypes.TypoPolygon
import adventureworks.customtypes.TypoShort
import adventureworks.customtypes.TypoUUID
import adventureworks.customtypes.TypoVector
import adventureworks.customtypes.TypoXml
import adventureworks.public.Mydomain
import adventureworks.public.Myenum
import doobie.enumerated.Nullability
import doobie.postgres.Text
import doobie.util.Read
import doobie.util.meta.Meta
import io.circe.Decoder
import io.circe.DecodingFailure
import io.circe.Encoder
import io.circe.HCursor
import io.circe.Json
import java.sql.ResultSet
import scala.util.Try

case class PgtestRow(
  bool: Boolean,
  box: TypoBox,
  bpchar: /* bpchar, max 3 chars */ String,
  bytea: TypoBytea,
  char: /* bpchar, max 1 chars */ String,
  circle: TypoCircle,
  date: TypoLocalDate,
  float4: Float,
  float8: Double,
  hstore: TypoHStore,
  inet: TypoInet,
  int2: TypoShort,
  int2vector: TypoInt2Vector,
  int4: Int,
  int8: Long,
  interval: TypoInterval,
  json: TypoJson,
  jsonb: TypoJsonb,
  line: TypoLine,
  lseg: TypoLineSegment,
  money: TypoMoney,
  mydomain: Mydomain,
  myenum: Myenum,
  name: String,
  numeric: BigDecimal,
  path: TypoPath,
  point: TypoPoint,
  polygon: TypoPolygon,
  text: String,
  time: TypoLocalTime,
  timestamp: TypoLocalDateTime,
  timestampz: TypoInstant,
  timez: TypoOffsetTime,
  uuid: TypoUUID,
  varchar: String,
  vector: TypoVector,
  xml: TypoXml,
  boxes: Array[TypoBox],
  bpchares: Array[/* bpchar */ String],
  chares: Array[/* bpchar */ String],
  circlees: Array[TypoCircle],
  datees: Array[TypoLocalDate],
  float4es: Array[Float],
  float8es: Array[Double],
  inetes: Array[TypoInet],
  int2es: Array[TypoShort],
  int2vectores: Array[TypoInt2Vector],
  int4es: Array[Int],
  int8es: Array[Long],
  intervales: Array[TypoInterval],
  jsones: Array[TypoJson],
  jsonbes: Array[TypoJsonb],
  linees: Array[TypoLine],
  lseges: Array[TypoLineSegment],
  moneyes: Array[TypoMoney],
  myenumes: Array[Myenum],
  namees: Array[String],
  numerices: Array[BigDecimal],
  pathes: Array[TypoPath],
  pointes: Array[TypoPoint],
  polygones: Array[TypoPolygon],
  textes: Array[String],
  timees: Array[TypoLocalTime],
  timestampes: Array[TypoLocalDateTime],
  timestampzes: Array[TypoInstant],
  timezes: Array[TypoOffsetTime],
  uuides: Array[TypoUUID],
  varchares: Array[String],
  xmles: Array[TypoXml]
)

object PgtestRow {
  implicit lazy val decoder: Decoder[PgtestRow] = Decoder.instanceTry[PgtestRow]((c: HCursor) =>
    Try {
      def orThrow[R](either: Either[DecodingFailure, R]): R = either match {
        case Left(err) => throw err
        case Right(r)  => r
      }
      PgtestRow(
        bool = orThrow(c.get("bool")(Decoder.decodeBoolean)),
        box = orThrow(c.get("box")(TypoBox.decoder)),
        bpchar = orThrow(c.get("bpchar")(Decoder.decodeString)),
        bytea = orThrow(c.get("bytea")(TypoBytea.decoder)),
        char = orThrow(c.get("char")(Decoder.decodeString)),
        circle = orThrow(c.get("circle")(TypoCircle.decoder)),
        date = orThrow(c.get("date")(TypoLocalDate.decoder)),
        float4 = orThrow(c.get("float4")(Decoder.decodeFloat)),
        float8 = orThrow(c.get("float8")(Decoder.decodeDouble)),
        hstore = orThrow(c.get("hstore")(TypoHStore.decoder)),
        inet = orThrow(c.get("inet")(TypoInet.decoder)),
        int2 = orThrow(c.get("int2")(TypoShort.decoder)),
        int2vector = orThrow(c.get("int2vector")(TypoInt2Vector.decoder)),
        int4 = orThrow(c.get("int4")(Decoder.decodeInt)),
        int8 = orThrow(c.get("int8")(Decoder.decodeLong)),
        interval = orThrow(c.get("interval")(TypoInterval.decoder)),
        json = orThrow(c.get("json")(TypoJson.decoder)),
        jsonb = orThrow(c.get("jsonb")(TypoJsonb.decoder)),
        line = orThrow(c.get("line")(TypoLine.decoder)),
        lseg = orThrow(c.get("lseg")(TypoLineSegment.decoder)),
        money = orThrow(c.get("money")(TypoMoney.decoder)),
        mydomain = orThrow(c.get("mydomain")(Mydomain.decoder)),
        myenum = orThrow(c.get("myenum")(Myenum.decoder)),
        name = orThrow(c.get("name")(Decoder.decodeString)),
        numeric = orThrow(c.get("numeric")(Decoder.decodeBigDecimal)),
        path = orThrow(c.get("path")(TypoPath.decoder)),
        point = orThrow(c.get("point")(TypoPoint.decoder)),
        polygon = orThrow(c.get("polygon")(TypoPolygon.decoder)),
        text = orThrow(c.get("text")(Decoder.decodeString)),
        time = orThrow(c.get("time")(TypoLocalTime.decoder)),
        timestamp = orThrow(c.get("timestamp")(TypoLocalDateTime.decoder)),
        timestampz = orThrow(c.get("timestampz")(TypoInstant.decoder)),
        timez = orThrow(c.get("timez")(TypoOffsetTime.decoder)),
        uuid = orThrow(c.get("uuid")(TypoUUID.decoder)),
        varchar = orThrow(c.get("varchar")(Decoder.decodeString)),
        vector = orThrow(c.get("vector")(TypoVector.decoder)),
        xml = orThrow(c.get("xml")(TypoXml.decoder)),
        boxes = orThrow(c.get("boxes")(Decoder.decodeArray[TypoBox](TypoBox.decoder, implicitly))),
        bpchares = orThrow(c.get("bpchares")(Decoder.decodeArray[String](Decoder.decodeString, implicitly))),
        chares = orThrow(c.get("chares")(Decoder.decodeArray[String](Decoder.decodeString, implicitly))),
        circlees = orThrow(c.get("circlees")(Decoder.decodeArray[TypoCircle](TypoCircle.decoder, implicitly))),
        datees = orThrow(c.get("datees")(Decoder.decodeArray[TypoLocalDate](TypoLocalDate.decoder, implicitly))),
        float4es = orThrow(c.get("float4es")(Decoder.decodeArray[Float](Decoder.decodeFloat, implicitly))),
        float8es = orThrow(c.get("float8es")(Decoder.decodeArray[Double](Decoder.decodeDouble, implicitly))),
        inetes = orThrow(c.get("inetes")(Decoder.decodeArray[TypoInet](TypoInet.decoder, implicitly))),
        int2es = orThrow(c.get("int2es")(Decoder.decodeArray[TypoShort](TypoShort.decoder, implicitly))),
        int2vectores = orThrow(c.get("int2vectores")(Decoder.decodeArray[TypoInt2Vector](TypoInt2Vector.decoder, implicitly))),
        int4es = orThrow(c.get("int4es")(Decoder.decodeArray[Int](Decoder.decodeInt, implicitly))),
        int8es = orThrow(c.get("int8es")(Decoder.decodeArray[Long](Decoder.decodeLong, implicitly))),
        intervales = orThrow(c.get("intervales")(Decoder.decodeArray[TypoInterval](TypoInterval.decoder, implicitly))),
        jsones = orThrow(c.get("jsones")(Decoder.decodeArray[TypoJson](TypoJson.decoder, implicitly))),
        jsonbes = orThrow(c.get("jsonbes")(Decoder.decodeArray[TypoJsonb](TypoJsonb.decoder, implicitly))),
        linees = orThrow(c.get("linees")(Decoder.decodeArray[TypoLine](TypoLine.decoder, implicitly))),
        lseges = orThrow(c.get("lseges")(Decoder.decodeArray[TypoLineSegment](TypoLineSegment.decoder, implicitly))),
        moneyes = orThrow(c.get("moneyes")(Decoder.decodeArray[TypoMoney](TypoMoney.decoder, implicitly))),
        myenumes = orThrow(c.get("myenumes")(Decoder.decodeArray[Myenum](Myenum.decoder, implicitly))),
        namees = orThrow(c.get("namees")(Decoder.decodeArray[String](Decoder.decodeString, implicitly))),
        numerices = orThrow(c.get("numerices")(Decoder.decodeArray[BigDecimal](Decoder.decodeBigDecimal, implicitly))),
        pathes = orThrow(c.get("pathes")(Decoder.decodeArray[TypoPath](TypoPath.decoder, implicitly))),
        pointes = orThrow(c.get("pointes")(Decoder.decodeArray[TypoPoint](TypoPoint.decoder, implicitly))),
        polygones = orThrow(c.get("polygones")(Decoder.decodeArray[TypoPolygon](TypoPolygon.decoder, implicitly))),
        textes = orThrow(c.get("textes")(Decoder.decodeArray[String](Decoder.decodeString, implicitly))),
        timees = orThrow(c.get("timees")(Decoder.decodeArray[TypoLocalTime](TypoLocalTime.decoder, implicitly))),
        timestampes = orThrow(c.get("timestampes")(Decoder.decodeArray[TypoLocalDateTime](TypoLocalDateTime.decoder, implicitly))),
        timestampzes = orThrow(c.get("timestampzes")(Decoder.decodeArray[TypoInstant](TypoInstant.decoder, implicitly))),
        timezes = orThrow(c.get("timezes")(Decoder.decodeArray[TypoOffsetTime](TypoOffsetTime.decoder, implicitly))),
        uuides = orThrow(c.get("uuides")(Decoder.decodeArray[TypoUUID](TypoUUID.decoder, implicitly))),
        varchares = orThrow(c.get("varchares")(Decoder.decodeArray[String](Decoder.decodeString, implicitly))),
        xmles = orThrow(c.get("xmles")(Decoder.decodeArray[TypoXml](TypoXml.decoder, implicitly)))
      )
    }
  )
  implicit lazy val encoder: Encoder[PgtestRow] = Encoder[PgtestRow](row =>
    Json.obj(
      "bool" -> Encoder.encodeBoolean.apply(row.bool),
      "box" -> TypoBox.encoder.apply(row.box),
      "bpchar" -> Encoder.encodeString.apply(row.bpchar),
      "bytea" -> TypoBytea.encoder.apply(row.bytea),
      "char" -> Encoder.encodeString.apply(row.char),
      "circle" -> TypoCircle.encoder.apply(row.circle),
      "date" -> TypoLocalDate.encoder.apply(row.date),
      "float4" -> Encoder.encodeFloat.apply(row.float4),
      "float8" -> Encoder.encodeDouble.apply(row.float8),
      "hstore" -> TypoHStore.encoder.apply(row.hstore),
      "inet" -> TypoInet.encoder.apply(row.inet),
      "int2" -> TypoShort.encoder.apply(row.int2),
      "int2vector" -> TypoInt2Vector.encoder.apply(row.int2vector),
      "int4" -> Encoder.encodeInt.apply(row.int4),
      "int8" -> Encoder.encodeLong.apply(row.int8),
      "interval" -> TypoInterval.encoder.apply(row.interval),
      "json" -> TypoJson.encoder.apply(row.json),
      "jsonb" -> TypoJsonb.encoder.apply(row.jsonb),
      "line" -> TypoLine.encoder.apply(row.line),
      "lseg" -> TypoLineSegment.encoder.apply(row.lseg),
      "money" -> TypoMoney.encoder.apply(row.money),
      "mydomain" -> Mydomain.encoder.apply(row.mydomain),
      "myenum" -> Myenum.encoder.apply(row.myenum),
      "name" -> Encoder.encodeString.apply(row.name),
      "numeric" -> Encoder.encodeBigDecimal.apply(row.numeric),
      "path" -> TypoPath.encoder.apply(row.path),
      "point" -> TypoPoint.encoder.apply(row.point),
      "polygon" -> TypoPolygon.encoder.apply(row.polygon),
      "text" -> Encoder.encodeString.apply(row.text),
      "time" -> TypoLocalTime.encoder.apply(row.time),
      "timestamp" -> TypoLocalDateTime.encoder.apply(row.timestamp),
      "timestampz" -> TypoInstant.encoder.apply(row.timestampz),
      "timez" -> TypoOffsetTime.encoder.apply(row.timez),
      "uuid" -> TypoUUID.encoder.apply(row.uuid),
      "varchar" -> Encoder.encodeString.apply(row.varchar),
      "vector" -> TypoVector.encoder.apply(row.vector),
      "xml" -> TypoXml.encoder.apply(row.xml),
      "boxes" -> Encoder.encodeIterable[TypoBox, Array](TypoBox.encoder, implicitly).apply(row.boxes),
      "bpchares" -> Encoder.encodeIterable[String, Array](Encoder.encodeString, implicitly).apply(row.bpchares),
      "chares" -> Encoder.encodeIterable[String, Array](Encoder.encodeString, implicitly).apply(row.chares),
      "circlees" -> Encoder.encodeIterable[TypoCircle, Array](TypoCircle.encoder, implicitly).apply(row.circlees),
      "datees" -> Encoder.encodeIterable[TypoLocalDate, Array](TypoLocalDate.encoder, implicitly).apply(row.datees),
      "float4es" -> Encoder.encodeIterable[Float, Array](Encoder.encodeFloat, implicitly).apply(row.float4es),
      "float8es" -> Encoder.encodeIterable[Double, Array](Encoder.encodeDouble, implicitly).apply(row.float8es),
      "inetes" -> Encoder.encodeIterable[TypoInet, Array](TypoInet.encoder, implicitly).apply(row.inetes),
      "int2es" -> Encoder.encodeIterable[TypoShort, Array](TypoShort.encoder, implicitly).apply(row.int2es),
      "int2vectores" -> Encoder.encodeIterable[TypoInt2Vector, Array](TypoInt2Vector.encoder, implicitly).apply(row.int2vectores),
      "int4es" -> Encoder.encodeIterable[Int, Array](Encoder.encodeInt, implicitly).apply(row.int4es),
      "int8es" -> Encoder.encodeIterable[Long, Array](Encoder.encodeLong, implicitly).apply(row.int8es),
      "intervales" -> Encoder.encodeIterable[TypoInterval, Array](TypoInterval.encoder, implicitly).apply(row.intervales),
      "jsones" -> Encoder.encodeIterable[TypoJson, Array](TypoJson.encoder, implicitly).apply(row.jsones),
      "jsonbes" -> Encoder.encodeIterable[TypoJsonb, Array](TypoJsonb.encoder, implicitly).apply(row.jsonbes),
      "linees" -> Encoder.encodeIterable[TypoLine, Array](TypoLine.encoder, implicitly).apply(row.linees),
      "lseges" -> Encoder.encodeIterable[TypoLineSegment, Array](TypoLineSegment.encoder, implicitly).apply(row.lseges),
      "moneyes" -> Encoder.encodeIterable[TypoMoney, Array](TypoMoney.encoder, implicitly).apply(row.moneyes),
      "myenumes" -> Encoder.encodeIterable[Myenum, Array](Myenum.encoder, implicitly).apply(row.myenumes),
      "namees" -> Encoder.encodeIterable[String, Array](Encoder.encodeString, implicitly).apply(row.namees),
      "numerices" -> Encoder.encodeIterable[BigDecimal, Array](Encoder.encodeBigDecimal, implicitly).apply(row.numerices),
      "pathes" -> Encoder.encodeIterable[TypoPath, Array](TypoPath.encoder, implicitly).apply(row.pathes),
      "pointes" -> Encoder.encodeIterable[TypoPoint, Array](TypoPoint.encoder, implicitly).apply(row.pointes),
      "polygones" -> Encoder.encodeIterable[TypoPolygon, Array](TypoPolygon.encoder, implicitly).apply(row.polygones),
      "textes" -> Encoder.encodeIterable[String, Array](Encoder.encodeString, implicitly).apply(row.textes),
      "timees" -> Encoder.encodeIterable[TypoLocalTime, Array](TypoLocalTime.encoder, implicitly).apply(row.timees),
      "timestampes" -> Encoder.encodeIterable[TypoLocalDateTime, Array](TypoLocalDateTime.encoder, implicitly).apply(row.timestampes),
      "timestampzes" -> Encoder.encodeIterable[TypoInstant, Array](TypoInstant.encoder, implicitly).apply(row.timestampzes),
      "timezes" -> Encoder.encodeIterable[TypoOffsetTime, Array](TypoOffsetTime.encoder, implicitly).apply(row.timezes),
      "uuides" -> Encoder.encodeIterable[TypoUUID, Array](TypoUUID.encoder, implicitly).apply(row.uuides),
      "varchares" -> Encoder.encodeIterable[String, Array](Encoder.encodeString, implicitly).apply(row.varchares),
      "xmles" -> Encoder.encodeIterable[TypoXml, Array](TypoXml.encoder, implicitly).apply(row.xmles)
    )
  )
  implicit lazy val read: Read[PgtestRow] = new Read[PgtestRow](
    gets = List(
      (Meta.BooleanMeta.get, Nullability.NoNulls),
      (TypoBox.get, Nullability.NoNulls),
      (Meta.StringMeta.get, Nullability.NoNulls),
      (TypoBytea.get, Nullability.NoNulls),
      (Meta.StringMeta.get, Nullability.NoNulls),
      (TypoCircle.get, Nullability.NoNulls),
      (TypoLocalDate.get, Nullability.NoNulls),
      (Meta.FloatMeta.get, Nullability.NoNulls),
      (Meta.DoubleMeta.get, Nullability.NoNulls),
      (TypoHStore.get, Nullability.NoNulls),
      (TypoInet.get, Nullability.NoNulls),
      (TypoShort.get, Nullability.NoNulls),
      (TypoInt2Vector.get, Nullability.NoNulls),
      (Meta.IntMeta.get, Nullability.NoNulls),
      (Meta.LongMeta.get, Nullability.NoNulls),
      (TypoInterval.get, Nullability.NoNulls),
      (TypoJson.get, Nullability.NoNulls),
      (TypoJsonb.get, Nullability.NoNulls),
      (TypoLine.get, Nullability.NoNulls),
      (TypoLineSegment.get, Nullability.NoNulls),
      (TypoMoney.get, Nullability.NoNulls),
      (Mydomain.get, Nullability.NoNulls),
      (Myenum.get, Nullability.NoNulls),
      (Meta.StringMeta.get, Nullability.NoNulls),
      (Meta.ScalaBigDecimalMeta.get, Nullability.NoNulls),
      (TypoPath.get, Nullability.NoNulls),
      (TypoPoint.get, Nullability.NoNulls),
      (TypoPolygon.get, Nullability.NoNulls),
      (Meta.StringMeta.get, Nullability.NoNulls),
      (TypoLocalTime.get, Nullability.NoNulls),
      (TypoLocalDateTime.get, Nullability.NoNulls),
      (TypoInstant.get, Nullability.NoNulls),
      (TypoOffsetTime.get, Nullability.NoNulls),
      (TypoUUID.get, Nullability.NoNulls),
      (Meta.StringMeta.get, Nullability.NoNulls),
      (TypoVector.get, Nullability.NoNulls),
      (TypoXml.get, Nullability.NoNulls),
      (TypoBox.arrayGet, Nullability.NoNulls),
      (adventureworks.StringArrayMeta.get, Nullability.NoNulls),
      (adventureworks.StringArrayMeta.get, Nullability.NoNulls),
      (TypoCircle.arrayGet, Nullability.NoNulls),
      (TypoLocalDate.arrayGet, Nullability.NoNulls),
      (adventureworks.FloatArrayMeta.get, Nullability.NoNulls),
      (adventureworks.DoubleArrayMeta.get, Nullability.NoNulls),
      (TypoInet.arrayGet, Nullability.NoNulls),
      (TypoShort.arrayGet, Nullability.NoNulls),
      (TypoInt2Vector.arrayGet, Nullability.NoNulls),
      (adventureworks.IntegerArrayMeta.get, Nullability.NoNulls),
      (adventureworks.LongArrayMeta.get, Nullability.NoNulls),
      (TypoInterval.arrayGet, Nullability.NoNulls),
      (TypoJson.arrayGet, Nullability.NoNulls),
      (TypoJsonb.arrayGet, Nullability.NoNulls),
      (TypoLine.arrayGet, Nullability.NoNulls),
      (TypoLineSegment.arrayGet, Nullability.NoNulls),
      (TypoMoney.arrayGet, Nullability.NoNulls),
      (Myenum.arrayGet, Nullability.NoNulls),
      (adventureworks.StringArrayMeta.get, Nullability.NoNulls),
      (adventureworks.BigDecimalMeta.get, Nullability.NoNulls),
      (TypoPath.arrayGet, Nullability.NoNulls),
      (TypoPoint.arrayGet, Nullability.NoNulls),
      (TypoPolygon.arrayGet, Nullability.NoNulls),
      (adventureworks.StringArrayMeta.get, Nullability.NoNulls),
      (TypoLocalTime.arrayGet, Nullability.NoNulls),
      (TypoLocalDateTime.arrayGet, Nullability.NoNulls),
      (TypoInstant.arrayGet, Nullability.NoNulls),
      (TypoOffsetTime.arrayGet, Nullability.NoNulls),
      (TypoUUID.arrayGet, Nullability.NoNulls),
      (adventureworks.StringArrayMeta.get, Nullability.NoNulls),
      (TypoXml.arrayGet, Nullability.NoNulls)
    ),
    unsafeGet = (rs: ResultSet, i: Int) => PgtestRow(
      bool = Meta.BooleanMeta.get.unsafeGetNonNullable(rs, i + 0),
      box = TypoBox.get.unsafeGetNonNullable(rs, i + 1),
      bpchar = Meta.StringMeta.get.unsafeGetNonNullable(rs, i + 2),
      bytea = TypoBytea.get.unsafeGetNonNullable(rs, i + 3),
      char = Meta.StringMeta.get.unsafeGetNonNullable(rs, i + 4),
      circle = TypoCircle.get.unsafeGetNonNullable(rs, i + 5),
      date = TypoLocalDate.get.unsafeGetNonNullable(rs, i + 6),
      float4 = Meta.FloatMeta.get.unsafeGetNonNullable(rs, i + 7),
      float8 = Meta.DoubleMeta.get.unsafeGetNonNullable(rs, i + 8),
      hstore = TypoHStore.get.unsafeGetNonNullable(rs, i + 9),
      inet = TypoInet.get.unsafeGetNonNullable(rs, i + 10),
      int2 = TypoShort.get.unsafeGetNonNullable(rs, i + 11),
      int2vector = TypoInt2Vector.get.unsafeGetNonNullable(rs, i + 12),
      int4 = Meta.IntMeta.get.unsafeGetNonNullable(rs, i + 13),
      int8 = Meta.LongMeta.get.unsafeGetNonNullable(rs, i + 14),
      interval = TypoInterval.get.unsafeGetNonNullable(rs, i + 15),
      json = TypoJson.get.unsafeGetNonNullable(rs, i + 16),
      jsonb = TypoJsonb.get.unsafeGetNonNullable(rs, i + 17),
      line = TypoLine.get.unsafeGetNonNullable(rs, i + 18),
      lseg = TypoLineSegment.get.unsafeGetNonNullable(rs, i + 19),
      money = TypoMoney.get.unsafeGetNonNullable(rs, i + 20),
      mydomain = Mydomain.get.unsafeGetNonNullable(rs, i + 21),
      myenum = Myenum.get.unsafeGetNonNullable(rs, i + 22),
      name = Meta.StringMeta.get.unsafeGetNonNullable(rs, i + 23),
      numeric = Meta.ScalaBigDecimalMeta.get.unsafeGetNonNullable(rs, i + 24),
      path = TypoPath.get.unsafeGetNonNullable(rs, i + 25),
      point = TypoPoint.get.unsafeGetNonNullable(rs, i + 26),
      polygon = TypoPolygon.get.unsafeGetNonNullable(rs, i + 27),
      text = Meta.StringMeta.get.unsafeGetNonNullable(rs, i + 28),
      time = TypoLocalTime.get.unsafeGetNonNullable(rs, i + 29),
      timestamp = TypoLocalDateTime.get.unsafeGetNonNullable(rs, i + 30),
      timestampz = TypoInstant.get.unsafeGetNonNullable(rs, i + 31),
      timez = TypoOffsetTime.get.unsafeGetNonNullable(rs, i + 32),
      uuid = TypoUUID.get.unsafeGetNonNullable(rs, i + 33),
      varchar = Meta.StringMeta.get.unsafeGetNonNullable(rs, i + 34),
      vector = TypoVector.get.unsafeGetNonNullable(rs, i + 35),
      xml = TypoXml.get.unsafeGetNonNullable(rs, i + 36),
      boxes = TypoBox.arrayGet.unsafeGetNonNullable(rs, i + 37),
      bpchares = adventureworks.StringArrayMeta.get.unsafeGetNonNullable(rs, i + 38),
      chares = adventureworks.StringArrayMeta.get.unsafeGetNonNullable(rs, i + 39),
      circlees = TypoCircle.arrayGet.unsafeGetNonNullable(rs, i + 40),
      datees = TypoLocalDate.arrayGet.unsafeGetNonNullable(rs, i + 41),
      float4es = adventureworks.FloatArrayMeta.get.unsafeGetNonNullable(rs, i + 42),
      float8es = adventureworks.DoubleArrayMeta.get.unsafeGetNonNullable(rs, i + 43),
      inetes = TypoInet.arrayGet.unsafeGetNonNullable(rs, i + 44),
      int2es = TypoShort.arrayGet.unsafeGetNonNullable(rs, i + 45),
      int2vectores = TypoInt2Vector.arrayGet.unsafeGetNonNullable(rs, i + 46),
      int4es = adventureworks.IntegerArrayMeta.get.unsafeGetNonNullable(rs, i + 47),
      int8es = adventureworks.LongArrayMeta.get.unsafeGetNonNullable(rs, i + 48),
      intervales = TypoInterval.arrayGet.unsafeGetNonNullable(rs, i + 49),
      jsones = TypoJson.arrayGet.unsafeGetNonNullable(rs, i + 50),
      jsonbes = TypoJsonb.arrayGet.unsafeGetNonNullable(rs, i + 51),
      linees = TypoLine.arrayGet.unsafeGetNonNullable(rs, i + 52),
      lseges = TypoLineSegment.arrayGet.unsafeGetNonNullable(rs, i + 53),
      moneyes = TypoMoney.arrayGet.unsafeGetNonNullable(rs, i + 54),
      myenumes = Myenum.arrayGet.unsafeGetNonNullable(rs, i + 55),
      namees = adventureworks.StringArrayMeta.get.unsafeGetNonNullable(rs, i + 56),
      numerices = adventureworks.BigDecimalMeta.get.unsafeGetNonNullable(rs, i + 57),
      pathes = TypoPath.arrayGet.unsafeGetNonNullable(rs, i + 58),
      pointes = TypoPoint.arrayGet.unsafeGetNonNullable(rs, i + 59),
      polygones = TypoPolygon.arrayGet.unsafeGetNonNullable(rs, i + 60),
      textes = adventureworks.StringArrayMeta.get.unsafeGetNonNullable(rs, i + 61),
      timees = TypoLocalTime.arrayGet.unsafeGetNonNullable(rs, i + 62),
      timestampes = TypoLocalDateTime.arrayGet.unsafeGetNonNullable(rs, i + 63),
      timestampzes = TypoInstant.arrayGet.unsafeGetNonNullable(rs, i + 64),
      timezes = TypoOffsetTime.arrayGet.unsafeGetNonNullable(rs, i + 65),
      uuides = TypoUUID.arrayGet.unsafeGetNonNullable(rs, i + 66),
      varchares = adventureworks.StringArrayMeta.get.unsafeGetNonNullable(rs, i + 67),
      xmles = TypoXml.arrayGet.unsafeGetNonNullable(rs, i + 68)
    )
  )
  implicit lazy val text: Text[PgtestRow] = Text.instance[PgtestRow]{ (row, sb) =>
    Text.booleanInstance.unsafeEncode(row.bool, sb)
    sb.append(Text.DELIMETER)
    TypoBox.text.unsafeEncode(row.box, sb)
    sb.append(Text.DELIMETER)
    Text.stringInstance.unsafeEncode(row.bpchar, sb)
    sb.append(Text.DELIMETER)
    TypoBytea.text.unsafeEncode(row.bytea, sb)
    sb.append(Text.DELIMETER)
    Text.stringInstance.unsafeEncode(row.char, sb)
    sb.append(Text.DELIMETER)
    TypoCircle.text.unsafeEncode(row.circle, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDate.text.unsafeEncode(row.date, sb)
    sb.append(Text.DELIMETER)
    Text.floatInstance.unsafeEncode(row.float4, sb)
    sb.append(Text.DELIMETER)
    Text.doubleInstance.unsafeEncode(row.float8, sb)
    sb.append(Text.DELIMETER)
    TypoHStore.text.unsafeEncode(row.hstore, sb)
    sb.append(Text.DELIMETER)
    TypoInet.text.unsafeEncode(row.inet, sb)
    sb.append(Text.DELIMETER)
    TypoShort.text.unsafeEncode(row.int2, sb)
    sb.append(Text.DELIMETER)
    TypoInt2Vector.text.unsafeEncode(row.int2vector, sb)
    sb.append(Text.DELIMETER)
    Text.intInstance.unsafeEncode(row.int4, sb)
    sb.append(Text.DELIMETER)
    Text.longInstance.unsafeEncode(row.int8, sb)
    sb.append(Text.DELIMETER)
    TypoInterval.text.unsafeEncode(row.interval, sb)
    sb.append(Text.DELIMETER)
    TypoJson.text.unsafeEncode(row.json, sb)
    sb.append(Text.DELIMETER)
    TypoJsonb.text.unsafeEncode(row.jsonb, sb)
    sb.append(Text.DELIMETER)
    TypoLine.text.unsafeEncode(row.line, sb)
    sb.append(Text.DELIMETER)
    TypoLineSegment.text.unsafeEncode(row.lseg, sb)
    sb.append(Text.DELIMETER)
    TypoMoney.text.unsafeEncode(row.money, sb)
    sb.append(Text.DELIMETER)
    Mydomain.text.unsafeEncode(row.mydomain, sb)
    sb.append(Text.DELIMETER)
    Myenum.text.unsafeEncode(row.myenum, sb)
    sb.append(Text.DELIMETER)
    Text.stringInstance.unsafeEncode(row.name, sb)
    sb.append(Text.DELIMETER)
    Text.bigDecimalInstance.unsafeEncode(row.numeric, sb)
    sb.append(Text.DELIMETER)
    TypoPath.text.unsafeEncode(row.path, sb)
    sb.append(Text.DELIMETER)
    TypoPoint.text.unsafeEncode(row.point, sb)
    sb.append(Text.DELIMETER)
    TypoPolygon.text.unsafeEncode(row.polygon, sb)
    sb.append(Text.DELIMETER)
    Text.stringInstance.unsafeEncode(row.text, sb)
    sb.append(Text.DELIMETER)
    TypoLocalTime.text.unsafeEncode(row.time, sb)
    sb.append(Text.DELIMETER)
    TypoLocalDateTime.text.unsafeEncode(row.timestamp, sb)
    sb.append(Text.DELIMETER)
    TypoInstant.text.unsafeEncode(row.timestampz, sb)
    sb.append(Text.DELIMETER)
    TypoOffsetTime.text.unsafeEncode(row.timez, sb)
    sb.append(Text.DELIMETER)
    TypoUUID.text.unsafeEncode(row.uuid, sb)
    sb.append(Text.DELIMETER)
    Text.stringInstance.unsafeEncode(row.varchar, sb)
    sb.append(Text.DELIMETER)
    TypoVector.text.unsafeEncode(row.vector, sb)
    sb.append(Text.DELIMETER)
    TypoXml.text.unsafeEncode(row.xml, sb)
    sb.append(Text.DELIMETER)
    Text.iterableInstance[Array, TypoBox](TypoBox.text, implicitly).unsafeEncode(row.boxes, sb)
    sb.append(Text.DELIMETER)
    Text[Array[String]].unsafeEncode(row.bpchares, sb)
    sb.append(Text.DELIMETER)
    Text[Array[String]].unsafeEncode(row.chares, sb)
    sb.append(Text.DELIMETER)
    Text.iterableInstance[Array, TypoCircle](TypoCircle.text, implicitly).unsafeEncode(row.circlees, sb)
    sb.append(Text.DELIMETER)
    Text.iterableInstance[Array, TypoLocalDate](TypoLocalDate.text, implicitly).unsafeEncode(row.datees, sb)
    sb.append(Text.DELIMETER)
    Text[Array[Float]].unsafeEncode(row.float4es, sb)
    sb.append(Text.DELIMETER)
    Text[Array[Double]].unsafeEncode(row.float8es, sb)
    sb.append(Text.DELIMETER)
    Text.iterableInstance[Array, TypoInet](TypoInet.text, implicitly).unsafeEncode(row.inetes, sb)
    sb.append(Text.DELIMETER)
    Text.iterableInstance[Array, TypoShort](TypoShort.text, implicitly).unsafeEncode(row.int2es, sb)
    sb.append(Text.DELIMETER)
    Text.iterableInstance[Array, TypoInt2Vector](TypoInt2Vector.text, implicitly).unsafeEncode(row.int2vectores, sb)
    sb.append(Text.DELIMETER)
    Text[Array[Int]].unsafeEncode(row.int4es, sb)
    sb.append(Text.DELIMETER)
    Text[Array[Long]].unsafeEncode(row.int8es, sb)
    sb.append(Text.DELIMETER)
    Text.iterableInstance[Array, TypoInterval](TypoInterval.text, implicitly).unsafeEncode(row.intervales, sb)
    sb.append(Text.DELIMETER)
    Text.iterableInstance[Array, TypoJson](TypoJson.text, implicitly).unsafeEncode(row.jsones, sb)
    sb.append(Text.DELIMETER)
    Text.iterableInstance[Array, TypoJsonb](TypoJsonb.text, implicitly).unsafeEncode(row.jsonbes, sb)
    sb.append(Text.DELIMETER)
    Text.iterableInstance[Array, TypoLine](TypoLine.text, implicitly).unsafeEncode(row.linees, sb)
    sb.append(Text.DELIMETER)
    Text.iterableInstance[Array, TypoLineSegment](TypoLineSegment.text, implicitly).unsafeEncode(row.lseges, sb)
    sb.append(Text.DELIMETER)
    Text.iterableInstance[Array, TypoMoney](TypoMoney.text, implicitly).unsafeEncode(row.moneyes, sb)
    sb.append(Text.DELIMETER)
    Text.iterableInstance[Array, Myenum](Myenum.text, implicitly).unsafeEncode(row.myenumes, sb)
    sb.append(Text.DELIMETER)
    Text[Array[String]].unsafeEncode(row.namees, sb)
    sb.append(Text.DELIMETER)
    Text[Array[BigDecimal]].unsafeEncode(row.numerices, sb)
    sb.append(Text.DELIMETER)
    Text.iterableInstance[Array, TypoPath](TypoPath.text, implicitly).unsafeEncode(row.pathes, sb)
    sb.append(Text.DELIMETER)
    Text.iterableInstance[Array, TypoPoint](TypoPoint.text, implicitly).unsafeEncode(row.pointes, sb)
    sb.append(Text.DELIMETER)
    Text.iterableInstance[Array, TypoPolygon](TypoPolygon.text, implicitly).unsafeEncode(row.polygones, sb)
    sb.append(Text.DELIMETER)
    Text[Array[String]].unsafeEncode(row.textes, sb)
    sb.append(Text.DELIMETER)
    Text.iterableInstance[Array, TypoLocalTime](TypoLocalTime.text, implicitly).unsafeEncode(row.timees, sb)
    sb.append(Text.DELIMETER)
    Text.iterableInstance[Array, TypoLocalDateTime](TypoLocalDateTime.text, implicitly).unsafeEncode(row.timestampes, sb)
    sb.append(Text.DELIMETER)
    Text.iterableInstance[Array, TypoInstant](TypoInstant.text, implicitly).unsafeEncode(row.timestampzes, sb)
    sb.append(Text.DELIMETER)
    Text.iterableInstance[Array, TypoOffsetTime](TypoOffsetTime.text, implicitly).unsafeEncode(row.timezes, sb)
    sb.append(Text.DELIMETER)
    Text.iterableInstance[Array, TypoUUID](TypoUUID.text, implicitly).unsafeEncode(row.uuides, sb)
    sb.append(Text.DELIMETER)
    Text[Array[String]].unsafeEncode(row.varchares, sb)
    sb.append(Text.DELIMETER)
    Text.iterableInstance[Array, TypoXml](TypoXml.text, implicitly).unsafeEncode(row.xmles, sb)
  }
}