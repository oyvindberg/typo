package adventureworks

import adventureworks.customtypes.*
import adventureworks.public.pgtest.{PgtestRepoImpl, PgtestRow}
import adventureworks.public.pgtestnull.{PgtestnullRepoImpl, PgtestnullRow}
import adventureworks.public.{Mydomain, Myenum}
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.Assertion
import org.scalatest.funsuite.AnyFunSuite
import zio.Chunk
import zio.json.JsonEncoder
import zio.stream.ZStream

class ArrayTest extends AnyFunSuite with TypeCheckedTripleEquals {
  // need to compare json instead of case classes because of arrays
  def assertJsonEquals[A: JsonEncoder](a1: A, a2: A): Assertion =
    assert(JsonEncoder[A].toJsonAST(a1) === JsonEncoder[A].toJsonAST(a2))

  test("can insert pgtest rows") {
    withConnection {
      val before = ArrayTestData.pgTestRow
      PgtestRepoImpl.insert(before).map { after =>
        assertJsonEquals(before, after)
      }
    }
  }

  test("can stream pgtest rows") {
    withConnection {
      val before = Chunk(ArrayTestData.pgTestRow)
      for {
        _ <- PgtestRepoImpl.insertStreaming(ZStream.fromChunk(before), 1)
        after <- PgtestRepoImpl.selectAll.runCollect
      } yield assertJsonEquals(before, after)
    }
  }

  // https://github.com/zio/zio-jdbc/issues/188 see also `can stream pgtestnull rows` test below
//  test("can insert null pgtestnull rows") {
//    withConnection {
//      val before = ArrayTestData.pgtestnullRow
//      PgtestnullRepoImpl.insert(before).map { after =>
//        assert(after === before)
//      }
//    }
//  }

  test("can insert non-null pgtestnull rows") {
    withConnection {
      val before = ArrayTestData.pgtestnullRowWithValues
      PgtestnullRepoImpl.insert(before).map { after =>
        assertJsonEquals(before, after)
      }
    }
  }

  test("can stream pgtestnull rows") {
    withConnection {
      val before = Chunk(
//        ArrayTestData.pgtestnullRow,
        ArrayTestData.pgtestnullRowWithValues
      )
      for {
        _ <- PgtestnullRepoImpl.insertStreaming(ZStream.fromChunk(before), 1)
        after <- PgtestnullRepoImpl.selectAll.runCollect
      } yield assertJsonEquals(before, after)
    }
  }
}

object ArrayTestData {
  val pgTestRow = new PgtestRow(
    bool = true,
    box = TypoBox(3.0, 4.0, 1.0, 2.0),
    bpchar = "abc",
    bytea = TypoBytea(Array[Byte](1, 2, 3)),
    char = "a",
    circle = TypoCircle(TypoPoint(1.0, 2.0), 3.0),
    date = TypoLocalDate.now,
    float4 = 1.0f,
    float8 = 2.45,
    hstore = TypoHStore(Map("a" -> "1", "b" -> "2")),
    inet = TypoInet("::10.2.3.4"),
    int2 = TypoShort(1),
    int2vector = TypoInt2Vector("1 2 3"),
    int4 = 4,
    int8 = Int.MaxValue.toLong + 1,
    interval = TypoInterval(1, 2, 3, 4, 5, 6.5),
    json = TypoJson("""{"a": 1}"""),
    jsonb = TypoJsonb("""{"a": 2}"""),
    line = TypoLine(3.0, 4.5, 5.5),
    lseg = TypoLineSegment(TypoPoint(6.5, 4.3), TypoPoint(1.5, 2.3)),
    money = TypoMoney(BigDecimal("22.50")),
    mydomain = Mydomain("a"),
    myenum = Myenum.c,
    name = "foo",
    numeric = BigDecimal("3.14159"),
    path = TypoPath(open = true, List(TypoPoint(6.5, 4.3), TypoPoint(8.5, 4.3))),
    point = TypoPoint(6.5, 4.3),
    polygon = TypoPolygon(List(TypoPoint(6.5, 4.3), TypoPoint(10.5, 4.3), TypoPoint(-6.5, 4.3))),
    text = "flaff",
    time = TypoLocalTime.now,
    timestamp = TypoLocalDateTime.now,
    timestampz = TypoInstant.now,
    timez = TypoOffsetTime.now,
    uuid = TypoUUID.randomUUID,
    varchar = "asd asd ",
    vector = TypoVector(Array(1.0f, 2.2f, 3.3f)),
    xml = TypoXml("<xml/>"),
    boxes = Array(TypoBox(3.0, 4.0, 1.0, 2.0)),
    bpchares = Array("abc"),
    chares = Array("a"),
    circlees = Array(TypoCircle(TypoPoint(1.0, 2.0), 3.0)),
    datees = Array(TypoLocalDate.now),
    float4es = Array(1.0f),
    float8es = Array(2.45),
    inetes = Array(TypoInet("::10.2.3.4")),
    int2es = Array(TypoShort(1)),
    int2vectores = Array(TypoInt2Vector("1 2 3")),
    int4es = Array(4),
    int8es = Array(Int.MaxValue.toLong + 1),
    intervales = Array(TypoInterval(1, 2, 3, 4, 5, 6.5)),
    jsones = Array(TypoJson("""{"a": 1}""")),
    jsonbes = Array(TypoJsonb("""{"a": 2}""")),
    linees = Array(TypoLine(3.0, 4.5, 5.5)),
    lseges = Array(TypoLineSegment(TypoPoint(6.5, 4.3), TypoPoint(1.5, 2.3))),
    moneyes = Array(TypoMoney(BigDecimal("22.50"))),
    myenumes = Array(Myenum.c),
    namees = Array("foo"),
    numerices = Array(BigDecimal("3.14159")),
    pathes = Array(TypoPath(open = true, List(TypoPoint(6.5, 4.3), TypoPoint(8.5, 4.3)))),
    pointes = Array(TypoPoint(6.5, 4.3)),
    polygones = Array(TypoPolygon(List(TypoPoint(6.5, 4.3), TypoPoint(10.5, 4.3), TypoPoint(-6.5, 4.3)))),
    textes = Array("flaff"),
    timees = Array(TypoLocalTime.now),
    timestampes = Array(TypoLocalDateTime.now),
    timestampzes = Array(TypoInstant.now),
    timezes = Array(TypoOffsetTime.now),
    uuides = Array(TypoUUID.randomUUID),
    varchares = Array("asd asd "),
    xmles = Array(TypoXml("<xml/>"))
  )

  val pgtestnullRow = new PgtestnullRow(
    bool = None,
    box = None,
    bpchar = None,
    bytea = None,
    char = None,
    circle = None,
    date = None,
    float4 = None,
    float8 = None,
    hstore = None,
    inet = None,
    int2 = None,
    int2vector = None,
    int4 = None,
    int8 = None,
    interval = None,
    json = None,
    jsonb = None,
    line = None,
    lseg = None,
    money = None,
    mydomain = None,
    myenum = None,
    name = None,
    numeric = None,
    path = None,
    point = None,
    polygon = None,
    text = None,
    time = None,
    timestamp = None,
    timestampz = None,
    timez = None,
    uuid = None,
    varchar = None,
    vector = None,
    xml = None,
    boxes = None,
    bpchares = None,
    chares = None,
    circlees = None,
    datees = None,
    float4es = None,
    float8es = None,
    inetes = None,
    int2es = None,
    int2vectores = None,
    int4es = None,
    int8es = None,
    intervales = None,
    jsones = None,
    jsonbes = None,
    linees = None,
    lseges = None,
    moneyes = None,
    myenumes = None,
    namees = None,
    numerices = None,
    pathes = None,
    pointes = None,
    polygones = None,
    textes = None,
    timees = None,
    timestampes = None,
    timestampzes = None,
    timezes = None,
    uuides = None,
    varchares = None,
    xmles = None
  )

  val pgtestnullRowWithValues = new PgtestnullRow(
    bool = Some(true),
    box = Some(TypoBox(3.0, 4.0, 1.0, 2.0)),
    bpchar = Some("abc"),
    bytea = Some(TypoBytea(Array[Byte](1, 2, 3))),
    char = Some("a"),
    circle = Some(TypoCircle(TypoPoint(1.0, 2.0), 3.0)),
    date = Some(TypoLocalDate.now),
    float4 = Some(1.0f),
    float8 = Some(2.45),
    hstore = Some(TypoHStore(Map("a" -> "1", "b" -> "2"))),
    inet = Some(TypoInet("::10.2.3.4")),
    int2 = Some(TypoShort(1)),
    int2vector = Some(TypoInt2Vector("1 2 3")),
    int4 = Some(4),
    int8 = Some(Int.MaxValue.toLong + 1),
    interval = Some(TypoInterval(1, 2, 3, 4, 5, 6.5)),
    json = Some(TypoJson("""{"a": 1}""")),
    jsonb = Some(TypoJsonb("""{"a": 2}""")),
    line = Some(TypoLine(3.0, 4.5, 5.5)),
    lseg = Some(TypoLineSegment(TypoPoint(6.5, 4.3), TypoPoint(1.5, 2.3))),
    money = Some(TypoMoney(BigDecimal("22.50"))),
    mydomain = Some(Mydomain("a")),
    myenum = Some(Myenum.c),
    name = Some("foo"),
    numeric = Some(BigDecimal("3.14159")),
    path = Some(TypoPath(open = true, List(TypoPoint(6.5, 4.3), TypoPoint(8.5, 4.3)))),
    point = Some(TypoPoint(6.5, 4.3)),
    polygon = Some(TypoPolygon(List(TypoPoint(6.5, 4.3), TypoPoint(10.5, 4.3), TypoPoint(-6.5, 4.3)))),
    text = Some("flaff"),
    time = Some(TypoLocalTime.now),
    timestamp = Some(TypoLocalDateTime.now),
    timestampz = Some(TypoInstant.now),
    timez = Some(TypoOffsetTime.now),
    uuid = Some(TypoUUID.randomUUID),
    varchar = Some("asd asd "),
    vector = Some(TypoVector(Array(1.0f, 2.2f, 3.3f))),
    xml = Some(TypoXml("<xml/>")),
    boxes = Some(Array(TypoBox(3.0, 4.0, 1.0, 2.0))),
    bpchares = Some(Array("abc")),
    chares = Some(Array("a")),
    circlees = Some(Array(TypoCircle(TypoPoint(1.0, 2.0), 3.0))),
    datees = Some(Array(TypoLocalDate.now)),
    float4es = Some(Array(1.0f)),
    float8es = Some(Array(2.45)),
    inetes = Some(Array(TypoInet("::10.2.3.4"))),
    int2es = Some(Array(TypoShort(1))),
    int2vectores = Some(Array(TypoInt2Vector("1 2 3"))),
    int4es = Some(Array(4)),
    int8es = Some(Array(Int.MaxValue.toLong + 1)),
    intervales = Some(Array(TypoInterval(1, 2, 3, 4, 5, 6.5))),
    jsones = Some(Array(TypoJson("""{"a": 1}"""))),
    jsonbes = Some(Array(TypoJsonb("""{"a": 2}"""))),
    linees = Some(Array(TypoLine(3.0, 4.5, 5.5))),
    lseges = Some(Array(TypoLineSegment(TypoPoint(6.5, 4.3), TypoPoint(1.5, 2.3)))),
    moneyes = Some(Array(TypoMoney(BigDecimal("22.50")))),
    myenumes = Some(Array(Myenum.c)),
    namees = Some(Array("foo")),
    numerices = Some(Array(BigDecimal("3.14159"))),
    pathes = Some(Array(TypoPath(open = true, List(TypoPoint(6.5, 4.3), TypoPoint(8.5, 4.3))))),
    pointes = Some(Array(TypoPoint(6.5, 4.3))),
    polygones = Some(Array(TypoPolygon(List(TypoPoint(6.5, 4.3), TypoPoint(10.5, 4.3), TypoPoint(-6.5, 4.3))))),
    textes = Some(Array("flaff")),
    timees = Some(Array(TypoLocalTime.now)),
    timestampes = Some(Array(TypoLocalDateTime.now)),
    timestampzes = Some(Array(TypoInstant.now)),
    timezes = Some(Array(TypoOffsetTime.now)),
    uuides = Some(Array(TypoUUID.randomUUID)),
    varchares = Some(Array("asd asd ")),
    xmles = Some(Array(TypoXml("<xml/>")))
  )
}
