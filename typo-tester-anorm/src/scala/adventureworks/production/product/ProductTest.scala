package adventureworks.production.product

import adventureworks.production.productcategory.*
import adventureworks.production.productmodel.*
import adventureworks.production.productsubcategory.*
import adventureworks.production.unitmeasure.*
import adventureworks.public.{Flag, Name}
import adventureworks.{Defaulted, TypoLocalDateTime, TypoXml, withConnection}
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
    withConnection { implicit c =>
      // setup
      val unitmeasure = unitmeasureRepo.insert(
        UnitmeasureRowUnsaved(
          unitmeasurecode = UnitmeasureId("kgg"),
          name = Name("name")
        )
      )
      val productCategory = productcategoryRepo.insert(
        new ProductcategoryRowUnsaved(
          name = Name("name")
        )
      )

      val productSubcategory = productsubcategoryRepo.insert(
        ProductsubcategoryRowUnsaved(
          productcategoryid = productCategory.productcategoryid,
          name = Name("name")
        )
      )
      val productmodel = projectModelRepo.insert(
        ProductmodelRowUnsaved(
          name = Name("name"),
          catalogdescription = Some(new TypoXml("<xml/>")),
          instructions = Some(new TypoXml("<instructions/>"))
        )
      )
      val unsaved1 = ProductRowUnsaved(
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
        sellstartdate = TypoLocalDateTime(LocalDateTime.now().plusDays(1)),
        sellenddate = Some(TypoLocalDateTime(LocalDateTime.now().plusDays(10))),
        discontinueddate = Some(TypoLocalDateTime(LocalDateTime.now().plusDays(100))),
        productid = Defaulted.UseDefault,
        makeflag = Defaulted.Provided(Flag(true)),
        finishedgoodsflag = Defaulted.Provided(Flag(true)),
        rowguid = Defaulted.Provided(UUID.randomUUID()),
        modifieddate = Defaulted.Provided(TypoLocalDateTime.now)
      )

      // insert and round trip check
      val saved1 = productRepo.insert(unsaved1)
      val saved2 = unsaved1.toRow(saved1.productid, ???, ???, ???, ???)
      assert(saved1 === saved2)

      // check field values
      val newModifiedDate = TypoLocalDateTime(saved1.modifieddate.value.minusDays(1))
      productRepo.update(saved1.copy(modifieddate = newModifiedDate))
      val List(saved3) = productRepo.selectAll: @unchecked
      assert(saved3.modifieddate == newModifiedDate)
      val true = productRepo.update(saved3.copy(size = None)): @unchecked
      // delete
      productRepo.delete(saved1.productid)

      val List() = productRepo.selectAll: @unchecked

      // done
      succeed
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
