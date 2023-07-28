package adventureworks

import adventureworks.public.pgtest.{PgtestRepoImpl, PgtestRow}
import adventureworks.public.pgtestnull.{PgtestnullRepoImpl, PgtestnullRow}
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funsuite.AnyFunSuite

class ArrayTest extends AnyFunSuite with TypeCheckedTripleEquals {
  test("works") {
    withConnection { implicit c =>
      val before = new PgtestRow(
        box = TypoBox(3.0, 4.0, 1.0, 2.0),
        circle = TypoCircle(TypoPoint(1.0, 2.0), 3.0),
        line = TypoLine(3.0, 4.5, 5.5),
        lseg = TypoLineSegment(TypoPoint(6.5, 4.3), TypoPoint(1.5, 2.3)),
        path = TypoPath(open = true, List(TypoPoint(6.5, 4.3), TypoPoint(8.5, 4.3))),
        point = TypoPoint(6.5, 4.3),
        polygon = TypoPolygon(List(TypoPoint(6.5, 4.3), TypoPoint(10.5, 4.3), TypoPoint(-6.5, 4.3))),
        interval = TypoInterval(1, 2, 3, 4, 5, 6.5),
        money = TypoMoney(BigDecimal("22.50")),
        xml = TypoXml("<xml/>"),
        json = TypoJson("""{"a": 1}"""),
        jsonb = TypoJsonb("""{"a": 2}"""),
        hstore = TypoHStore(Map("a" -> "1", "b" -> "2")),
        inet = TypoInet("::10.2.3.4"),
        boxes = Array(TypoBox(3.0, 4.0, 1.0, 2.0)),
        circlees = Array(TypoCircle(TypoPoint(1.0, 2.0), 3.0)),
        linees = Array(TypoLine(3.0, 4.5, 5.5)),
        lseges = Array(TypoLineSegment(TypoPoint(6.5, 4.3), TypoPoint(1.5, 2.3))),
        pathes = Array(TypoPath(open = true, List(TypoPoint(6.5, 4.3), TypoPoint(8.5, 4.3)))),
        pointes = Array(TypoPoint(6.5, 4.3)),
        polygones = Array(TypoPolygon(List(TypoPoint(6.5, 4.3), TypoPoint(10.5, 4.3), TypoPoint(-6.5, 4.3)))),
        intervales = Array(TypoInterval(1, 2, 3, 4, 5, 6.5)),
        moneyes = Array(TypoMoney(BigDecimal("22.50"))),
        xmles = Array(TypoXml("<xml/>")),
        jsones = Array(TypoJson("""{"a": 1}""")),
        jsonbes = Array(TypoJsonb("""{"a": 2}""")),
        hstores = Array(), // todo
        inets = Array(TypoInet("::10.2.3.4"))
      )
      val after = PgtestRepoImpl.insert(before)

      assert(after.box === before.box)
      assert(after.circle === before.circle)
      assert(after.line === before.line)
      assert(after.lseg === before.lseg)
      assert(after.path === before.path)
      assert(after.point === before.point)
      assert(after.polygon === before.polygon)
      assert(after.interval === before.interval)
      assert(after.money === before.money)
      assert(after.xml === before.xml)
      assert(after.json === before.json)
      assert(after.jsonb === before.jsonb)
      assert(after.hstore === before.hstore)
      assert(after.inet === before.inet)
      assert(after.boxes === before.boxes)
      assert(after.circlees === before.circlees)
      assert(after.linees === before.linees)
      assert(after.lseges === before.lseges)
      assert(after.pathes === before.pathes)
      assert(after.pointes === before.pointes)
      assert(after.polygones === before.polygones)
      assert(after.intervales === before.intervales)
      assert(after.moneyes === before.moneyes)
      assert(after.xmles === before.xmles)
      assert(after.jsones === before.jsones)
      assert(after.jsonbes === before.jsonbes)
      assert(after.hstores === before.hstores)
      assert(after.inets === before.inets)
    }
  }

  test("nulls") {
    withConnection { implicit c =>
      val before = new PgtestnullRow(
        box = None,
        circle = None,
        line = None,
        lseg = None,
        path = None,
        point = None,
        polygon = None,
        interval = None,
        money = None,
        xml = None,
        json = None,
        jsonb = None,
        hstore = None,
        inet = None,
        boxes = None,
        circlees = None,
        linees = None,
        lseges = None,
        pathes = None,
        pointes = None,
        polygones = None,
        intervales = None,
        moneyes = None,
        xmles = None,
        jsones = None,
        jsonbes = None,
        hstores = None,
        inets = None
      )
      val after = PgtestnullRepoImpl.insert(before)
      assert(after === before)
    }
  }

  test("not nulls") {
    withConnection { implicit c =>
      val before = new PgtestnullRow(
        box = Some(TypoBox(3.0, 4.0, 1.0, 2.0)),
        circle = Some(TypoCircle(TypoPoint(1.0, 2.0), 3.0)),
        line = Some(TypoLine(3.0, 4.5, 5.5)),
        lseg = Some(TypoLineSegment(TypoPoint(6.5, 4.3), TypoPoint(1.5, 2.3))),
        path = Some(TypoPath(open = true, List(TypoPoint(6.5, 4.3), TypoPoint(8.5, 4.3)))),
        point = Some(TypoPoint(6.5, 4.3)),
        polygon = Some(TypoPolygon(List(TypoPoint(6.5, 4.3), TypoPoint(10.5, 4.3), TypoPoint(-6.5, 4.3)))),
        interval = Some(TypoInterval(1, 2, 3, 4, 5, 6.5)),
        money = Some(TypoMoney(BigDecimal("22.50"))),
        xml = Some(TypoXml("<xml/>")),
        json = Some(TypoJson("""{"a": 1}""")),
        jsonb = Some(TypoJsonb("""{"a": 2}""")),
        hstore = Some(TypoHStore(Map("a" -> "1", "b" -> "2"))),
        inet = Some(TypoInet("::10.2.3.4")),
        boxes = Some(Array(TypoBox(3.0, 4.0, 1.0, 2.0))),
        circlees = Some(Array(TypoCircle(TypoPoint(1.0, 2.0), 3.0))),
        linees = Some(Array(TypoLine(3.0, 4.5, 5.5))),
        lseges = Some(Array(TypoLineSegment(TypoPoint(6.5, 4.3), TypoPoint(1.5, 2.3)))),
        pathes = Some(Array(TypoPath(open = true, List(TypoPoint(6.5, 4.3), TypoPoint(8.5, 4.3))))),
        pointes = Some(Array(TypoPoint(6.5, 4.3))),
        polygones = Some(Array(TypoPolygon(List(TypoPoint(6.5, 4.3), TypoPoint(10.5, 4.3), TypoPoint(-6.5, 4.3))))),
        intervales = Some(Array(TypoInterval(1, 2, 3, 4, 5, 6.5))),
        moneyes = Some(Array(TypoMoney(BigDecimal("22.50")))),
        xmles = Some(Array(TypoXml("<xml/>"))),
        jsones = Some(Array(TypoJson("""{"a": 1}"""))),
        jsonbes = Some(Array(TypoJsonb("""{"a": 2}"""))),
        hstores = Some(Array()),
        inets = Some(Array(TypoInet("::10.2.3.4")))
      )
      val after = PgtestnullRepoImpl.insert(before)
      assert(after.box === before.box)
      assert(after.circle === before.circle)
      assert(after.line === before.line)
      assert(after.lseg === before.lseg)
      assert(after.path === before.path)
      assert(after.point === before.point)
      assert(after.polygon === before.polygon)
      assert(after.interval === before.interval)
      assert(after.money === before.money)
      assert(after.xml === before.xml)
      assert(after.json === before.json)
      assert(after.jsonb === before.jsonb)
      assert(after.hstore === before.hstore)
      assert(after.inet === before.inet)
      assert(after.boxes.map(_.toList) === before.boxes.map(_.toList))
      assert(after.circlees.map(_.toList) === before.circlees.map(_.toList))
      assert(after.linees.map(_.toList) === before.linees.map(_.toList))
      assert(after.lseges.map(_.toList) === before.lseges.map(_.toList))
      assert(after.pathes.map(_.toList) === before.pathes.map(_.toList))
      assert(after.pointes.map(_.toList) === before.pointes.map(_.toList))
      assert(after.polygones.map(_.toList) === before.polygones.map(_.toList))
      assert(after.intervales.map(_.toList) === before.intervales.map(_.toList))
      assert(after.moneyes.map(_.toList) === before.moneyes.map(_.toList))
      assert(after.xmles.map(_.toList) === before.xmles.map(_.toList))
      assert(after.jsones.map(_.toList) === before.jsones.map(_.toList))
      assert(after.jsonbes.map(_.toList) === before.jsonbes.map(_.toList))
      assert(after.hstores.map(_.toList) === before.hstores.map(_.toList))
      assert(after.inets.map(_.toList) === before.inets.map(_.toList))
    }
  }

}
