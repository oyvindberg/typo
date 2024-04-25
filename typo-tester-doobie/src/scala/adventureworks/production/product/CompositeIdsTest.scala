package adventureworks.production.product

import adventureworks.customtypes.{TypoLocalDateTime, TypoXml}
import adventureworks.production.productcosthistory.*
import adventureworks.production.unitmeasure.UnitmeasureId
import adventureworks.{TestInsert, withConnection}
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funsuite.AnyFunSuite
import doobie.free.connection.delay

import java.time.LocalDateTime
import scala.util.Random

class CompositeIdsTest extends AnyFunSuite with TypeCheckedTripleEquals {
  implicit class Foo(x: TypoLocalDateTime) {
    def map(f: LocalDateTime => LocalDateTime): TypoLocalDateTime = TypoLocalDateTime(f(x.value))
  }

  test("works") {
    val testInsert = new TestInsert(new Random(0))
    val repo = new ProductcosthistoryRepoImpl()

    withConnection {
      for {
        unitmeasure <- testInsert.productionUnitmeasure(UnitmeasureId("kgg"))
        productCategory <- testInsert.productionProductcategory()
        productSubcategory <- testInsert.productionProductsubcategory(productCategory.productcategoryid)
        productModel <- testInsert.productionProductmodel(catalogdescription = Some(new TypoXml("<xml/>")), instructions = Some(new TypoXml("<instructions/>")))
        product <- testInsert.productionProduct(
          sizeunitmeasurecode = Some(unitmeasure.unitmeasurecode),
          weightunitmeasurecode = Some(unitmeasure.unitmeasurecode),
          `class` = Some("H "),
          style = Some("W "),
          productsubcategoryid = Some(productSubcategory.productsubcategoryid),
          productmodelid = Some(productModel.productmodelid)
        )

        now = TypoLocalDateTime.now
        ph1 <- testInsert.productionProductcosthistory(product.productid, startdate = now, enddate = Some(now.map(_.plusDays(1))))
        ph2 <- testInsert.productionProductcosthistory(product.productid, startdate = now.map(_.plusDays(1)), enddate = Some(now.map(_.plusDays(2))))
        ph3 <- testInsert.productionProductcosthistory(product.productid, startdate = now.map(_.plusDays(2)), enddate = Some(now.map(_.plusDays(3))))
        wanted = Array(ph1.compositeId, ph2.compositeId, ph3.compositeId.copy(productid = ProductId(9999)))
        found <- repo.selectByIds(wanted).compile.toList
        _ <- delay(assert(found.map(_.compositeId).toSet === Set(ph1.compositeId, ph2.compositeId)))
        deleted <- repo.deleteByIds(wanted)
        _ <- delay(assert(deleted === 2))
        all <- repo.selectAll.compile.toList
        _ <- delay(assert(all.map(_.compositeId) === List(ph3.compositeId)))
      } yield true
    }
  }
}
