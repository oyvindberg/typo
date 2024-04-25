package adventureworks.production.product

import adventureworks.customtypes.{TypoLocalDateTime, TypoXml}
import adventureworks.production.productcosthistory.*
import adventureworks.production.unitmeasure.UnitmeasureId
import adventureworks.{TestInsert, withConnection}
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funsuite.AnyFunSuite

import java.time.LocalDateTime
import scala.annotation.nowarn
import scala.util.Random

class CompositeIdsTest extends AnyFunSuite with TypeCheckedTripleEquals {
  implicit class Foo(x: TypoLocalDateTime) {
    def map(f: LocalDateTime => LocalDateTime): TypoLocalDateTime = TypoLocalDateTime(f(x.value))
  }

  test("works") {
    val testInsert = new TestInsert(new Random(0))

    withConnection { implicit c =>
      val unitmeasure = testInsert.productionUnitmeasure(UnitmeasureId("kgg"))
      val productCategory = testInsert.productionProductcategory()
      val productSubcategory = testInsert.productionProductsubcategory(productCategory.productcategoryid)
      val productModel = testInsert.productionProductmodel(catalogdescription = Some(new TypoXml("<xml/>")), instructions = Some(new TypoXml("<instructions/>")))
      val product = testInsert.productionProduct(
        sizeunitmeasurecode = Some(unitmeasure.unitmeasurecode),
        weightunitmeasurecode = Some(unitmeasure.unitmeasurecode),
        `class` = Some("H "),
        style = Some("W "),
        productsubcategoryid = Some(productSubcategory.productsubcategoryid),
        productmodelid = Some(productModel.productmodelid)
      )

      val now = TypoLocalDateTime.now
      val ph1 = testInsert.productionProductcosthistory(product.productid, startdate = now, enddate = Some(now.map(_.plusDays(1))))
      val ph2 = testInsert.productionProductcosthistory(product.productid, startdate = now.map(_.plusDays(1)), enddate = Some(now.map(_.plusDays(2))))
      val ph3 = testInsert.productionProductcosthistory(product.productid, startdate = now.map(_.plusDays(2)), enddate = Some(now.map(_.plusDays(3))))
      List(ph1.compositeId, ph2.compositeId, ph3.compositeId) foreach println
      val wanted = Array(ph1.compositeId, ph2.compositeId, ph3.compositeId.copy(productid = ProductId(9999)))

      val repo = new ProductcosthistoryRepoImpl()
      assert(repo.selectByIds(wanted).map(_.compositeId) === List(ph1.compositeId, ph2.compositeId)): @nowarn
      assert(repo.deleteByIds(wanted) === 2): @nowarn
      assert(repo.selectAll.map(_.compositeId) === List(ph3.compositeId))
    }
  }
}
