package adventureworks

import adventureworks.customtypes.*
import adventureworks.public.pgtest.{PgtestRepoImpl, PgtestRow}
import adventureworks.public.pgtestnull.{PgtestnullRepoImpl, PgtestnullRow}
import zio.jdbc.*
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funsuite.AnyFunSuite

import java.time.*
import scala.annotation.nowarn

class ArrayTest2 extends AnyFunSuite with TypeCheckedTripleEquals {
  def tableFor(dbType: String): Unit =
    withConnection {
      val tableName = s"test_type_${dbType.filter(_.isLetterOrDigit)}"
      sql"""create table if not exists $tableName (
           |    one        $dbType   not null,
           |    maybe_one  $dbType,
           |    many       $dbType[] not null,
           |    maybe_many $dbType[]
           |    );
           |""".execute
    }
}
class ArrayTest extends AnyFunSuite with TypeCheckedTripleEquals {
  test("works") {
    withConnection {
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
        timestamp = TypoLocalDateTime.now,
        timestampz = TypoInstant.now,
        time = TypoLocalTime.now,
        timez = TypoOffsetTime.now,
        date = TypoLocalDate(LocalDate.now),
        uuid = TypoUUID.randomUUID,
        numeric = BigDecimal("3.14159"),
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
        inets = Array(TypoInet("::10.2.3.4")),
        timestamps = Array(TypoLocalDateTime.now),
        timestampzs = Array(TypoInstant.now),
        times = Array(TypoLocalTime.now),
        timezs = Array(TypoOffsetTime.now),
        dates = Array(TypoLocalDate(LocalDate.now)),
        uuids = Array(TypoUUID.randomUUID),
        numerics = Array(BigDecimal("3.14159"))
      )
      PgtestRepoImpl.insert(before).map(_.updatedKeys.head).map { after =>
        assert(after.box === before.box): @nowarn
        assert(after.circle === before.circle): @nowarn
        assert(after.line === before.line): @nowarn
        assert(after.lseg === before.lseg): @nowarn
        assert(after.path === before.path): @nowarn
        assert(after.point === before.point): @nowarn
        assert(after.polygon === before.polygon): @nowarn
        assert(after.interval === before.interval): @nowarn
        assert(after.money === before.money): @nowarn
        assert(after.xml === before.xml): @nowarn
        assert(after.json === before.json): @nowarn
        assert(after.jsonb === before.jsonb): @nowarn
        assert(after.hstore === before.hstore): @nowarn
        assert(after.inet === before.inet): @nowarn
        assert(after.timestamp === before.timestamp): @nowarn
        assert(after.timestampz === before.timestampz): @nowarn
        assert(after.time === before.time): @nowarn
        assert(after.timez === before.timez): @nowarn
        assert(after.date === before.date): @nowarn
        assert(after.uuid === before.uuid): @nowarn
        assert(after.numeric === before.numeric): @nowarn
        assert(after.boxes === before.boxes): @nowarn
        assert(after.circlees === before.circlees): @nowarn
        assert(after.linees === before.linees): @nowarn
        assert(after.lseges === before.lseges): @nowarn
        assert(after.pathes === before.pathes): @nowarn
        assert(after.pointes === before.pointes): @nowarn
        assert(after.polygones === before.polygones): @nowarn
        assert(after.intervales === before.intervales): @nowarn
        assert(after.moneyes === before.moneyes): @nowarn
        assert(after.xmles === before.xmles): @nowarn
        assert(after.jsones === before.jsones): @nowarn
        assert(after.jsonbes === before.jsonbes): @nowarn
        assert(after.hstores === before.hstores): @nowarn
        assert(after.inets === before.inets): @nowarn
        assert(after.timestamps === before.timestamps): @nowarn
        assert(after.timestampzs === before.timestampzs): @nowarn
        assert(after.times === before.times): @nowarn
        assert(after.timezs === before.timezs): @nowarn
        assert(after.dates === before.dates): @nowarn
        assert(after.uuids === before.uuids): @nowarn
        assert(after.numerics === before.numerics)
      }
    }
  }

  test("nulls") {
    withConnection {
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
        timestamp = None,
        timestampz = None,
        time = None,
        timez = None,
        date = None,
        uuid = None,
        numeric = None,
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
        inets = None,
        timestamps = None,
        timestampzs = None,
        times = None,
        timezs = None,
        dates = None,
        uuids = None,
        numerics = None
      )
      PgtestnullRepoImpl.insert(before).map { after =>
        assert(after.rowsUpdated === 1L)
        assert(after.updatedKeys.head === before)
      }
    }
  }

  test("not nulls") {
    withConnection {
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
        timestamp = Some(TypoLocalDateTime.now),
        timestampz = Some(TypoInstant.now),
        time = Some(TypoLocalTime.now),
        timez = Some(TypoOffsetTime.now),
        date = Some(TypoLocalDate(LocalDate.now)),
        uuid = Some(TypoUUID.randomUUID),
        numeric = Some(BigDecimal("3.14159")),
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
        inets = Some(Array(TypoInet("::10.2.3.4"))),
        timestamps = Some(Array(TypoLocalDateTime.now)),
        timestampzs = Some(Array(TypoInstant.now)),
        times = Some(Array(TypoLocalTime.now)),
        timezs = Some(Array(TypoOffsetTime.now)),
        dates = Some(Array(TypoLocalDate(LocalDate.now))),
        uuids = Some(Array(TypoUUID.randomUUID)),
        numerics = Some(Array(BigDecimal("3.14159")))
      )

      PgtestnullRepoImpl.insert(before).map(_.updatedKeys.head).map { after =>
        assert(after.box === before.box): @nowarn
        assert(after.circle === before.circle): @nowarn
        assert(after.line === before.line): @nowarn
        assert(after.lseg === before.lseg): @nowarn
        assert(after.path === before.path): @nowarn
        assert(after.point === before.point): @nowarn
        assert(after.polygon === before.polygon): @nowarn
        assert(after.interval === before.interval): @nowarn
        assert(after.money === before.money): @nowarn
        assert(after.xml === before.xml): @nowarn
        assert(after.json === before.json): @nowarn
        assert(after.jsonb === before.jsonb): @nowarn
        assert(after.hstore === before.hstore): @nowarn
        assert(after.inet === before.inet): @nowarn
        assert(after.timestamp === before.timestamp): @nowarn
        assert(after.timestampz === before.timestampz): @nowarn
        assert(after.time === before.time): @nowarn
        assert(after.timez === before.timez): @nowarn
        assert(after.date === before.date): @nowarn
        assert(after.uuid === before.uuid): @nowarn
        assert(after.numeric === before.numeric): @nowarn
        assert(after.boxes.map(_.toList) === before.boxes.map(_.toList)): @nowarn
        assert(after.circlees.map(_.toList) === before.circlees.map(_.toList)): @nowarn
        assert(after.linees.map(_.toList) === before.linees.map(_.toList)): @nowarn
        assert(after.lseges.map(_.toList) === before.lseges.map(_.toList)): @nowarn
        assert(after.pathes.map(_.toList) === before.pathes.map(_.toList)): @nowarn
        assert(after.pointes.map(_.toList) === before.pointes.map(_.toList)): @nowarn
        assert(after.polygones.map(_.toList) === before.polygones.map(_.toList)): @nowarn
        assert(after.intervales.map(_.toList) === before.intervales.map(_.toList)): @nowarn
        assert(after.moneyes.map(_.toList) === before.moneyes.map(_.toList)): @nowarn
        assert(after.xmles.map(_.toList) === before.xmles.map(_.toList)): @nowarn
        assert(after.jsones.map(_.toList) === before.jsones.map(_.toList)): @nowarn
        assert(after.jsonbes.map(_.toList) === before.jsonbes.map(_.toList)): @nowarn
        assert(after.hstores.map(_.toList) === before.hstores.map(_.toList)): @nowarn
        assert(after.inets.map(_.toList) === before.inets.map(_.toList)): @nowarn
        assert(after.timestamps.map(_.toList) === before.timestamps.map(_.toList)): @nowarn
        assert(after.timestampzs.map(_.toList) === before.timestampzs.map(_.toList)): @nowarn
        assert(after.times.map(_.toList) === before.times.map(_.toList)): @nowarn
        assert(after.timezs.map(_.toList) === before.timezs.map(_.toList)): @nowarn
        assert(after.dates.map(_.toList) === before.dates.map(_.toList)): @nowarn
        assert(after.uuids.map(_.toList) === before.uuids.map(_.toList)): @nowarn
        assert(after.numerics.map(_.toList) === before.numerics.map(_.toList))
      }
    }
  }
}
