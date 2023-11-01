package adventureworks

import adventureworks.customtypes.*
import adventureworks.public.{Mydomain, Myenum}
import adventureworks.public.pgtest.{PgtestRepoImpl, PgtestRow}
import adventureworks.public.pgtestnull.{PgtestnullRepoImpl, PgtestnullRow}
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funsuite.AnyFunSuite

import scala.annotation.nowarn

class ArrayTest extends AnyFunSuite with TypeCheckedTripleEquals {
  test("works") {
    withConnection {
      val before = new PgtestRow(
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
      PgtestRepoImpl.insert(before).map { after =>
        assert(after.bool === before.bool): @nowarn
        assert(after.box === before.box): @nowarn
        assert(after.bpchar === before.bpchar): @nowarn
        assert(after.bytea.value === before.bytea.value): @nowarn
        assert(after.char === before.char): @nowarn
        assert(after.circle === before.circle): @nowarn
        assert(after.date === before.date): @nowarn
        assert(after.float4 === before.float4): @nowarn
        assert(after.float8 === before.float8): @nowarn
        assert(after.hstore === before.hstore): @nowarn
        assert(after.inet === before.inet): @nowarn
        assert(after.int2 === before.int2): @nowarn
        assert(after.int2vector === before.int2vector): @nowarn
        assert(after.int4 === before.int4): @nowarn
        assert(after.int8 === before.int8): @nowarn
        assert(after.interval === before.interval): @nowarn
        assert(after.json === before.json): @nowarn
        assert(after.jsonb === before.jsonb): @nowarn
        assert(after.line === before.line): @nowarn
        assert(after.lseg === before.lseg): @nowarn
        assert(after.money === before.money): @nowarn
        assert(after.mydomain === before.mydomain): @nowarn
        assert(after.myenum === before.myenum): @nowarn
        assert(after.name === before.name): @nowarn
        assert(after.numeric === before.numeric): @nowarn
        assert(after.path === before.path): @nowarn
        assert(after.point === before.point): @nowarn
        assert(after.polygon === before.polygon): @nowarn
        assert(after.text === before.text): @nowarn
        assert(after.time === before.time): @nowarn
        assert(after.timestamp === before.timestamp): @nowarn
        assert(after.timestampz === before.timestampz): @nowarn
        assert(after.timez === before.timez): @nowarn
        assert(after.uuid === before.uuid): @nowarn
        assert(after.varchar === before.varchar): @nowarn
        assert(after.vector.value === before.vector.value): @nowarn
        assert(after.xml === before.xml): @nowarn
        assert(after.boxes === before.boxes): @nowarn
        assert(after.bpchares === before.bpchares): @nowarn
        assert(after.chares === before.chares): @nowarn
        assert(after.circlees === before.circlees): @nowarn
        assert(after.datees === before.datees): @nowarn
        assert(after.float4es === before.float4es): @nowarn
        assert(after.float8es === before.float8es): @nowarn
        assert(after.inetes === before.inetes): @nowarn
        assert(after.int2es === before.int2es): @nowarn
        assert(after.int2vectores === before.int2vectores): @nowarn
        assert(after.int4es === before.int4es): @nowarn
        assert(after.int8es === before.int8es): @nowarn
        assert(after.intervales === before.intervales): @nowarn
        assert(after.jsones === before.jsones): @nowarn
        assert(after.jsonbes === before.jsonbes): @nowarn
        assert(after.linees === before.linees): @nowarn
        assert(after.lseges === before.lseges): @nowarn
        assert(after.moneyes === before.moneyes): @nowarn
        assert(after.myenumes === before.myenumes): @nowarn
        assert(after.namees === before.namees): @nowarn
        assert(after.numerices === before.numerices): @nowarn
        assert(after.pathes === before.pathes): @nowarn
        assert(after.pointes === before.pointes): @nowarn
        assert(after.polygones === before.polygones): @nowarn
        assert(after.textes === before.textes): @nowarn
        assert(after.timees === before.timees): @nowarn
        assert(after.timestampes === before.timestampes): @nowarn
        assert(after.timestampzes === before.timestampzes): @nowarn
        assert(after.timezes === before.timezes): @nowarn
        assert(after.uuides === before.uuides): @nowarn
        assert(after.varchares === before.varchares): @nowarn
        assert(after.xmles === before.xmles)
      }
    }
  }

  test("nulls") {
    withConnection {
      val before = new PgtestnullRow(
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

      PgtestnullRepoImpl.insert(before).map { after =>
        assert(after === before)
      }
    }
  }

  test("not nulls") {
    withConnection {
      val before = new PgtestnullRow(
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

      PgtestnullRepoImpl.insert(before).map { after =>
        assert(after.bool === before.bool): @nowarn
        assert(after.box === before.box): @nowarn
        assert(after.bpchar === before.bpchar): @nowarn
        assert(after.bytea.map(_.value.toList) === before.bytea.map(_.value.toList)): @nowarn
        assert(after.char === before.char): @nowarn
        assert(after.circle === before.circle): @nowarn
        assert(after.date === before.date): @nowarn
        assert(after.float4 === before.float4): @nowarn
        assert(after.float8 === before.float8): @nowarn
        assert(after.hstore === before.hstore): @nowarn
        assert(after.inet === before.inet): @nowarn
        assert(after.int2 === before.int2): @nowarn
        assert(after.int2vector === before.int2vector): @nowarn
        assert(after.int4 === before.int4): @nowarn
        assert(after.int8 === before.int8): @nowarn
        assert(after.interval === before.interval): @nowarn
        assert(after.json === before.json): @nowarn
        assert(after.jsonb === before.jsonb): @nowarn
        assert(after.line === before.line): @nowarn
        assert(after.lseg === before.lseg): @nowarn
        assert(after.money === before.money): @nowarn
        assert(after.mydomain === before.mydomain): @nowarn
        assert(after.myenum === before.myenum): @nowarn
        assert(after.name === before.name): @nowarn
        assert(after.numeric === before.numeric): @nowarn
        assert(after.path === before.path): @nowarn
        assert(after.point === before.point): @nowarn
        assert(after.polygon === before.polygon): @nowarn
        assert(after.text === before.text): @nowarn
        assert(after.time === before.time): @nowarn
        assert(after.timestamp === before.timestamp): @nowarn
        assert(after.timestampz === before.timestampz): @nowarn
        assert(after.timez === before.timez): @nowarn
        assert(after.uuid === before.uuid): @nowarn
        assert(after.varchar === before.varchar): @nowarn
        assert(after.vector.map(_.value.toList) === before.vector.map(_.value.toList)): @nowarn
        assert(after.xml === before.xml): @nowarn
        assert(after.boxes.map(_.toList) === before.boxes.map(_.toList)): @nowarn
        assert(after.bpchares.map(_.toList) === before.bpchares.map(_.toList)): @nowarn
        assert(after.chares.map(_.toList) === before.chares.map(_.toList)): @nowarn
        assert(after.circlees.map(_.toList) === before.circlees.map(_.toList)): @nowarn
        assert(after.datees.map(_.toList) === before.datees.map(_.toList)): @nowarn
        assert(after.float4es.map(_.toList) === before.float4es.map(_.toList)): @nowarn
        assert(after.float8es.map(_.toList) === before.float8es.map(_.toList)): @nowarn
        assert(after.inetes.map(_.toList) === before.inetes.map(_.toList)): @nowarn
        assert(after.int2es.map(_.toList) === before.int2es.map(_.toList)): @nowarn
        assert(after.int2vectores.map(_.toList) === before.int2vectores.map(_.toList)): @nowarn
        assert(after.int4es.map(_.toList) === before.int4es.map(_.toList)): @nowarn
        assert(after.int8es.map(_.toList) === before.int8es.map(_.toList)): @nowarn
        assert(after.intervales.map(_.toList) === before.intervales.map(_.toList)): @nowarn
        assert(after.jsones.map(_.toList) === before.jsones.map(_.toList)): @nowarn
        assert(after.jsonbes.map(_.toList) === before.jsonbes.map(_.toList)): @nowarn
        assert(after.linees.map(_.toList) === before.linees.map(_.toList)): @nowarn
        assert(after.lseges.map(_.toList) === before.lseges.map(_.toList)): @nowarn
        assert(after.moneyes.map(_.toList) === before.moneyes.map(_.toList)): @nowarn
        assert(after.myenumes.map(_.toList) === before.myenumes.map(_.toList)): @nowarn
        assert(after.namees.map(_.toList) === before.namees.map(_.toList)): @nowarn
        assert(after.numerices.map(_.toList) === before.numerices.map(_.toList)): @nowarn
        assert(after.pathes.map(_.toList) === before.pathes.map(_.toList)): @nowarn
        assert(after.pointes.map(_.toList) === before.pointes.map(_.toList)): @nowarn
        assert(after.polygones.map(_.toList) === before.polygones.map(_.toList)): @nowarn
        assert(after.textes.map(_.toList) === before.textes.map(_.toList)): @nowarn
        assert(after.timees.map(_.toList) === before.timees.map(_.toList)): @nowarn
        assert(after.timestampes.map(_.toList) === before.timestampes.map(_.toList)): @nowarn
        assert(after.timestampzes.map(_.toList) === before.timestampzes.map(_.toList)): @nowarn
        assert(after.timezes.map(_.toList) === before.timezes.map(_.toList)): @nowarn
        assert(after.uuides.map(_.toList) === before.uuides.map(_.toList)): @nowarn
        assert(after.varchares.map(_.toList) === before.varchares.map(_.toList)): @nowarn
        assert(after.xmles.map(_.toList) === before.xmles.map(_.toList))
      }
    }
  }
}
