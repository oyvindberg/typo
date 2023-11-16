/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package public
package pgtestnull

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
import typo.dsl.SqlExpr.FieldLikeNoHkt
import typo.dsl.SqlExpr.OptField
import typo.dsl.Structure.Relation

class PgtestnullStructure[Row](val prefix: Option[String], val extract: Row => PgtestnullRow, val merge: (Row, PgtestnullRow) => Row)
  extends Relation[PgtestnullFields, PgtestnullRow, Row]
    with PgtestnullFields[Row] { outer =>

  override val bool = new OptField[Boolean, Row](prefix, "bool", None, None)(x => extract(x).bool, (row, value) => merge(row, extract(row).copy(bool = value)))
  override val box = new OptField[TypoBox, Row](prefix, "box", None, Some("box"))(x => extract(x).box, (row, value) => merge(row, extract(row).copy(box = value)))
  override val bpchar = new OptField[/* bpchar, max 3 chars */ String, Row](prefix, "bpchar", None, Some("bpchar"))(x => extract(x).bpchar, (row, value) => merge(row, extract(row).copy(bpchar = value)))
  override val bytea = new OptField[TypoBytea, Row](prefix, "bytea", None, Some("bytea"))(x => extract(x).bytea, (row, value) => merge(row, extract(row).copy(bytea = value)))
  override val char = new OptField[/* bpchar, max 1 chars */ String, Row](prefix, "char", None, Some("bpchar"))(x => extract(x).char, (row, value) => merge(row, extract(row).copy(char = value)))
  override val circle = new OptField[TypoCircle, Row](prefix, "circle", None, Some("circle"))(x => extract(x).circle, (row, value) => merge(row, extract(row).copy(circle = value)))
  override val date = new OptField[TypoLocalDate, Row](prefix, "date", Some("text"), Some("date"))(x => extract(x).date, (row, value) => merge(row, extract(row).copy(date = value)))
  override val float4 = new OptField[Float, Row](prefix, "float4", None, Some("float4"))(x => extract(x).float4, (row, value) => merge(row, extract(row).copy(float4 = value)))
  override val float8 = new OptField[Double, Row](prefix, "float8", None, Some("float8"))(x => extract(x).float8, (row, value) => merge(row, extract(row).copy(float8 = value)))
  override val hstore = new OptField[TypoHStore, Row](prefix, "hstore", None, Some("hstore"))(x => extract(x).hstore, (row, value) => merge(row, extract(row).copy(hstore = value)))
  override val inet = new OptField[TypoInet, Row](prefix, "inet", None, Some("inet"))(x => extract(x).inet, (row, value) => merge(row, extract(row).copy(inet = value)))
  override val int2 = new OptField[TypoShort, Row](prefix, "int2", None, Some("int2"))(x => extract(x).int2, (row, value) => merge(row, extract(row).copy(int2 = value)))
  override val int2vector = new OptField[TypoInt2Vector, Row](prefix, "int2vector", None, Some("int2vector"))(x => extract(x).int2vector, (row, value) => merge(row, extract(row).copy(int2vector = value)))
  override val int4 = new OptField[Int, Row](prefix, "int4", None, Some("int4"))(x => extract(x).int4, (row, value) => merge(row, extract(row).copy(int4 = value)))
  override val int8 = new OptField[Long, Row](prefix, "int8", None, Some("int8"))(x => extract(x).int8, (row, value) => merge(row, extract(row).copy(int8 = value)))
  override val interval = new OptField[TypoInterval, Row](prefix, "interval", None, Some("interval"))(x => extract(x).interval, (row, value) => merge(row, extract(row).copy(interval = value)))
  override val json = new OptField[TypoJson, Row](prefix, "json", None, Some("json"))(x => extract(x).json, (row, value) => merge(row, extract(row).copy(json = value)))
  override val jsonb = new OptField[TypoJsonb, Row](prefix, "jsonb", None, Some("jsonb"))(x => extract(x).jsonb, (row, value) => merge(row, extract(row).copy(jsonb = value)))
  override val line = new OptField[TypoLine, Row](prefix, "line", None, Some("line"))(x => extract(x).line, (row, value) => merge(row, extract(row).copy(line = value)))
  override val lseg = new OptField[TypoLineSegment, Row](prefix, "lseg", None, Some("lseg"))(x => extract(x).lseg, (row, value) => merge(row, extract(row).copy(lseg = value)))
  override val money = new OptField[TypoMoney, Row](prefix, "money", Some("numeric"), Some("money"))(x => extract(x).money, (row, value) => merge(row, extract(row).copy(money = value)))
  override val mydomain = new OptField[Mydomain, Row](prefix, "mydomain", None, Some("text"))(x => extract(x).mydomain, (row, value) => merge(row, extract(row).copy(mydomain = value)))
  override val myenum = new OptField[Myenum, Row](prefix, "myenum", None, Some("public.myenum"))(x => extract(x).myenum, (row, value) => merge(row, extract(row).copy(myenum = value)))
  override val name = new OptField[String, Row](prefix, "name", None, Some("name"))(x => extract(x).name, (row, value) => merge(row, extract(row).copy(name = value)))
  override val numeric = new OptField[BigDecimal, Row](prefix, "numeric", None, Some("numeric"))(x => extract(x).numeric, (row, value) => merge(row, extract(row).copy(numeric = value)))
  override val path = new OptField[TypoPath, Row](prefix, "path", None, Some("path"))(x => extract(x).path, (row, value) => merge(row, extract(row).copy(path = value)))
  override val point = new OptField[TypoPoint, Row](prefix, "point", None, Some("point"))(x => extract(x).point, (row, value) => merge(row, extract(row).copy(point = value)))
  override val polygon = new OptField[TypoPolygon, Row](prefix, "polygon", None, Some("polygon"))(x => extract(x).polygon, (row, value) => merge(row, extract(row).copy(polygon = value)))
  override val text = new OptField[String, Row](prefix, "text", None, None)(x => extract(x).text, (row, value) => merge(row, extract(row).copy(text = value)))
  override val time = new OptField[TypoLocalTime, Row](prefix, "time", Some("text"), Some("time"))(x => extract(x).time, (row, value) => merge(row, extract(row).copy(time = value)))
  override val timestamp = new OptField[TypoLocalDateTime, Row](prefix, "timestamp", Some("text"), Some("timestamp"))(x => extract(x).timestamp, (row, value) => merge(row, extract(row).copy(timestamp = value)))
  override val timestampz = new OptField[TypoInstant, Row](prefix, "timestampz", Some("text"), Some("timestamptz"))(x => extract(x).timestampz, (row, value) => merge(row, extract(row).copy(timestampz = value)))
  override val timez = new OptField[TypoOffsetTime, Row](prefix, "timez", Some("text"), Some("timetz"))(x => extract(x).timez, (row, value) => merge(row, extract(row).copy(timez = value)))
  override val uuid = new OptField[TypoUUID, Row](prefix, "uuid", None, Some("uuid"))(x => extract(x).uuid, (row, value) => merge(row, extract(row).copy(uuid = value)))
  override val varchar = new OptField[String, Row](prefix, "varchar", None, None)(x => extract(x).varchar, (row, value) => merge(row, extract(row).copy(varchar = value)))
  override val vector = new OptField[TypoVector, Row](prefix, "vector", Some("float4[]"), Some("vector"))(x => extract(x).vector, (row, value) => merge(row, extract(row).copy(vector = value)))
  override val xml = new OptField[TypoXml, Row](prefix, "xml", None, Some("xml"))(x => extract(x).xml, (row, value) => merge(row, extract(row).copy(xml = value)))
  override val boxes = new OptField[Array[TypoBox], Row](prefix, "boxes", None, Some("_box"))(x => extract(x).boxes, (row, value) => merge(row, extract(row).copy(boxes = value)))
  override val bpchares = new OptField[Array[/* bpchar */ String], Row](prefix, "bpchares", None, Some("_bpchar"))(x => extract(x).bpchares, (row, value) => merge(row, extract(row).copy(bpchares = value)))
  override val chares = new OptField[Array[/* bpchar */ String], Row](prefix, "chares", None, Some("_bpchar"))(x => extract(x).chares, (row, value) => merge(row, extract(row).copy(chares = value)))
  override val circlees = new OptField[Array[TypoCircle], Row](prefix, "circlees", None, Some("_circle"))(x => extract(x).circlees, (row, value) => merge(row, extract(row).copy(circlees = value)))
  override val datees = new OptField[Array[TypoLocalDate], Row](prefix, "datees", Some("text[]"), Some("_date"))(x => extract(x).datees, (row, value) => merge(row, extract(row).copy(datees = value)))
  override val float4es = new OptField[Array[Float], Row](prefix, "float4es", None, Some("_float4"))(x => extract(x).float4es, (row, value) => merge(row, extract(row).copy(float4es = value)))
  override val float8es = new OptField[Array[Double], Row](prefix, "float8es", None, Some("_float8"))(x => extract(x).float8es, (row, value) => merge(row, extract(row).copy(float8es = value)))
  override val inetes = new OptField[Array[TypoInet], Row](prefix, "inetes", None, Some("_inet"))(x => extract(x).inetes, (row, value) => merge(row, extract(row).copy(inetes = value)))
  override val int2es = new OptField[Array[TypoShort], Row](prefix, "int2es", None, Some("_int2"))(x => extract(x).int2es, (row, value) => merge(row, extract(row).copy(int2es = value)))
  override val int2vectores = new OptField[Array[TypoInt2Vector], Row](prefix, "int2vectores", None, Some("_int2vector"))(x => extract(x).int2vectores, (row, value) => merge(row, extract(row).copy(int2vectores = value)))
  override val int4es = new OptField[Array[Int], Row](prefix, "int4es", None, Some("_int4"))(x => extract(x).int4es, (row, value) => merge(row, extract(row).copy(int4es = value)))
  override val int8es = new OptField[Array[Long], Row](prefix, "int8es", None, Some("_int8"))(x => extract(x).int8es, (row, value) => merge(row, extract(row).copy(int8es = value)))
  override val intervales = new OptField[Array[TypoInterval], Row](prefix, "intervales", None, Some("_interval"))(x => extract(x).intervales, (row, value) => merge(row, extract(row).copy(intervales = value)))
  override val jsones = new OptField[Array[TypoJson], Row](prefix, "jsones", None, Some("_json"))(x => extract(x).jsones, (row, value) => merge(row, extract(row).copy(jsones = value)))
  override val jsonbes = new OptField[Array[TypoJsonb], Row](prefix, "jsonbes", None, Some("_jsonb"))(x => extract(x).jsonbes, (row, value) => merge(row, extract(row).copy(jsonbes = value)))
  override val linees = new OptField[Array[TypoLine], Row](prefix, "linees", None, Some("_line"))(x => extract(x).linees, (row, value) => merge(row, extract(row).copy(linees = value)))
  override val lseges = new OptField[Array[TypoLineSegment], Row](prefix, "lseges", None, Some("_lseg"))(x => extract(x).lseges, (row, value) => merge(row, extract(row).copy(lseges = value)))
  override val moneyes = new OptField[Array[TypoMoney], Row](prefix, "moneyes", Some("numeric[]"), Some("_money"))(x => extract(x).moneyes, (row, value) => merge(row, extract(row).copy(moneyes = value)))
  override val myenumes = new OptField[Array[Myenum], Row](prefix, "myenumes", None, Some("_myenum"))(x => extract(x).myenumes, (row, value) => merge(row, extract(row).copy(myenumes = value)))
  override val namees = new OptField[Array[String], Row](prefix, "namees", None, Some("_name"))(x => extract(x).namees, (row, value) => merge(row, extract(row).copy(namees = value)))
  override val numerices = new OptField[Array[BigDecimal], Row](prefix, "numerices", None, Some("_numeric"))(x => extract(x).numerices, (row, value) => merge(row, extract(row).copy(numerices = value)))
  override val pathes = new OptField[Array[TypoPath], Row](prefix, "pathes", None, Some("_path"))(x => extract(x).pathes, (row, value) => merge(row, extract(row).copy(pathes = value)))
  override val pointes = new OptField[Array[TypoPoint], Row](prefix, "pointes", None, Some("_point"))(x => extract(x).pointes, (row, value) => merge(row, extract(row).copy(pointes = value)))
  override val polygones = new OptField[Array[TypoPolygon], Row](prefix, "polygones", None, Some("_polygon"))(x => extract(x).polygones, (row, value) => merge(row, extract(row).copy(polygones = value)))
  override val textes = new OptField[Array[String], Row](prefix, "textes", None, Some("_text"))(x => extract(x).textes, (row, value) => merge(row, extract(row).copy(textes = value)))
  override val timees = new OptField[Array[TypoLocalTime], Row](prefix, "timees", Some("text[]"), Some("_time"))(x => extract(x).timees, (row, value) => merge(row, extract(row).copy(timees = value)))
  override val timestampes = new OptField[Array[TypoLocalDateTime], Row](prefix, "timestampes", Some("text[]"), Some("_timestamp"))(x => extract(x).timestampes, (row, value) => merge(row, extract(row).copy(timestampes = value)))
  override val timestampzes = new OptField[Array[TypoInstant], Row](prefix, "timestampzes", Some("text[]"), Some("_timestamptz"))(x => extract(x).timestampzes, (row, value) => merge(row, extract(row).copy(timestampzes = value)))
  override val timezes = new OptField[Array[TypoOffsetTime], Row](prefix, "timezes", Some("text[]"), Some("_timetz"))(x => extract(x).timezes, (row, value) => merge(row, extract(row).copy(timezes = value)))
  override val uuides = new OptField[Array[TypoUUID], Row](prefix, "uuides", None, Some("_uuid"))(x => extract(x).uuides, (row, value) => merge(row, extract(row).copy(uuides = value)))
  override val varchares = new OptField[Array[String], Row](prefix, "varchares", None, Some("_varchar"))(x => extract(x).varchares, (row, value) => merge(row, extract(row).copy(varchares = value)))
  override val xmles = new OptField[Array[TypoXml], Row](prefix, "xmles", None, Some("_xml"))(x => extract(x).xmles, (row, value) => merge(row, extract(row).copy(xmles = value)))

  override val columns: List[FieldLikeNoHkt[?, Row]] =
    List[FieldLikeNoHkt[?, Row]](bool, box, bpchar, bytea, char, circle, date, float4, float8, hstore, inet, int2, int2vector, int4, int8, interval, json, jsonb, line, lseg, money, mydomain, myenum, name, numeric, path, point, polygon, text, time, timestamp, timestampz, timez, uuid, varchar, vector, xml, boxes, bpchares, chares, circlees, datees, float4es, float8es, inetes, int2es, int2vectores, int4es, int8es, intervales, jsones, jsonbes, linees, lseges, moneyes, myenumes, namees, numerices, pathes, pointes, polygones, textes, timees, timestampes, timestampzes, timezes, uuides, varchares, xmles)

  override def copy[NewRow](prefix: Option[String], extract: NewRow => PgtestnullRow, merge: (NewRow, PgtestnullRow) => NewRow): PgtestnullStructure[NewRow] =
    new PgtestnullStructure(prefix, extract, merge)
}