package adventureworks.production.product

import adventureworks.production.productcategory.*
import adventureworks.production.productmodel.*
import adventureworks.production.productsubcategory.*
import adventureworks.production.unitmeasure.*
import adventureworks.public.{Flag, Name}
import adventureworks.{Defaulted, TypoLocalDateTime, TypoXml, withConnection}
import doobie.free.connection.delay
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.Assertion
import org.scalatest.funsuite.AnyFunSuite

import java.time.LocalDateTime
import java.util.UUID

class ProductTest extends AnyFunSuite with TypeCheckedTripleEquals {

  def runTest(
      productRepo: ProductRepo,
      projectModelRepo: ProductmodelRepo,
      unitmeasureRepo: UnitmeasureRepo,
      productcategoryRepo: ProductcategoryRepo,
      productsubcategoryRepo: ProductsubcategoryRepo
  ): Assertion = {
    withConnection {
      for {
        // setup
        unitmeasure <- unitmeasureRepo.insert(
          UnitmeasureRowUnsaved(
            unitmeasurecode = UnitmeasureId("kgg"),
            name = Name("name")
          )
        )
        productCategory <- productcategoryRepo.insert(
          ProductcategoryRowUnsaved(
            name = Name("name")
          )
        )
        productSubcategory <- productsubcategoryRepo.insert(
          ProductsubcategoryRowUnsaved(
            productcategoryid = productCategory.productcategoryid,
            name = Name("name")
          )
        )
        productmodel <- projectModelRepo.insert(
          ProductmodelRowUnsaved(
            name = Name("name"),
            catalogdescription = Some(new TypoXml("<xml/>")),
            instructions = Some(new TypoXml("<instructions/>"))
          )
        )

        unsaved1 = ProductRowUnsaved(
          name = Name("name"),
          productnumber = "productnumber",
          color = Some("color"),
          safetystocklevel = 16,
          reorderpoint = 18,
          standardcost = 20,
          listprice = 22,
          size = Some("size"),
          sizeunitmeasurecode = Some(unitmeasure.unitmeasurecode),
          weightunitmeasurecode = Some(unitmeasure.unitmeasurecode),
          weight = Some(1.00),
          daystomanufacture = 26,
          productline = Some("T "),
          `class` = Some("H "),
          style = Some("W "),
          productsubcategoryid = Some(productSubcategory.productsubcategoryid),
          productmodelid = Some(productmodel.productmodelid),
          sellstartdate = TypoLocalDateTime(LocalDateTime.now().plusDays(1).withNano(0)),
          sellenddate = Some(TypoLocalDateTime(LocalDateTime.now().plusDays(10).withNano(0))),
          discontinueddate = Some(TypoLocalDateTime(LocalDateTime.now().plusDays(100).withNano(0))),
          productid = Defaulted.UseDefault,
          makeflag = Defaulted.Provided(Flag(true)),
          finishedgoodsflag = Defaulted.Provided(Flag(true)),
          rowguid = Defaulted.Provided(UUID.randomUUID()),
          modifieddate = Defaulted.Provided(TypoLocalDateTime.now)
        )
        // insert and round trip check
        saved1 <- productRepo.insert(unsaved1)
        saved2 = unsaved1.toRow(saved1.productid, ???, ???, ???, ???)
        _ <- delay(assert(saved1 === saved2))

        // check field values
        newModifiedDate = TypoLocalDateTime(saved1.modifieddate.value.minusDays(1))
        _ <- productRepo.update(saved1.copy(modifieddate = newModifiedDate))
        saved3 <- productRepo.selectAll.compile.toList.map {
          case List(x) => x
          case other   => throw new MatchError(other)
        }
        _ <- delay(assert(saved3.modifieddate == newModifiedDate))
        _ <- productRepo.update(saved3.copy(size = None)).map(res => assert(res))
        // delete
        _ <- productRepo.delete(saved1.productid)
        _ <- productRepo.selectAll.compile.toList.map {
          case Nil   => ()
          case other => throw new MatchError(other)
        }

      } yield succeed
    }
  }

  test("in-memory") {
    runTest(
      productRepo = new ProductRepoMock(_.toRow(ProductId(0), Flag.apply(true), Flag.apply(false), UUID.randomUUID(), TypoLocalDateTime.now)),
      projectModelRepo = new ProductmodelRepoMock(_.toRow(ProductmodelId(0), UUID.randomUUID(), TypoLocalDateTime.now)),
      unitmeasureRepo = new UnitmeasureRepoMock(_.toRow(TypoLocalDateTime.now)),
      productcategoryRepo = new ProductcategoryRepoMock(_.toRow(ProductcategoryId(0), UUID.randomUUID(), TypoLocalDateTime.now)),
      productsubcategoryRepo = new ProductsubcategoryRepoMock(_.toRow(ProductsubcategoryId(0), UUID.randomUUID(), TypoLocalDateTime.now))
    )
  }

  test("pg") {
    runTest(
      productRepo = ProductRepoImpl,
      projectModelRepo = ProductmodelRepoImpl,
      unitmeasureRepo = UnitmeasureRepoImpl,
      productcategoryRepo = ProductcategoryRepoImpl,
      productsubcategoryRepo = ProductsubcategoryRepoImpl
    )
  }
}
