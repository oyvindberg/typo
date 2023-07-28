package adventureworks.production.product

import adventureworks.production.productcategory.{ProductcategoryRepoImpl, ProductcategoryRowUnsaved}
import adventureworks.production.productmodel.{ProductmodelRepoImpl, ProductmodelRowUnsaved}
import adventureworks.production.productsubcategory.{ProductsubcategoryRepoImpl, ProductsubcategoryRowUnsaved}
import adventureworks.production.unitmeasure.{UnitmeasureId, UnitmeasureRepoImpl, UnitmeasureRowUnsaved}
import adventureworks.public.{Flag, Name}
import adventureworks.{Defaulted, TypoXml, withConnection}
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funsuite.AnyFunSuite

import java.time.LocalDateTime
import java.util.UUID

class ProductTest extends AnyFunSuite with TypeCheckedTripleEquals {
  val repo = ProductRepoImpl

  test("works") {
    withConnection { implicit c =>
      // setup
      val unitmeasure = UnitmeasureRepoImpl.insert(
        UnitmeasureRowUnsaved(
          unitmeasurecode = UnitmeasureId("kgg"),
          name = Name("name")
        )
      )
      val productCategory = ProductcategoryRepoImpl.insert(
        new ProductcategoryRowUnsaved(
          name = Name("name")
        )
      )

      val productSubcategory = ProductsubcategoryRepoImpl.insert(
        ProductsubcategoryRowUnsaved(
          productcategoryid = productCategory.productcategoryid,
          name = Name("name")
        )
      )
      val productmodel = ProductmodelRepoImpl.insert(
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
        sellstartdate = LocalDateTime.now().plusDays(1).withNano(0),
        sellenddate = Some(LocalDateTime.now().plusDays(10).withNano(0)),
        discontinueddate = Some(LocalDateTime.now().plusDays(100).withNano(0)),
        productid = Defaulted.UseDefault,
        makeflag = Defaulted.Provided(Flag(true)),
        finishedgoodsflag = Defaulted.Provided(Flag(true)),
        rowguid = Defaulted.Provided(UUID.randomUUID()),
        modifieddate = Defaulted.Provided(LocalDateTime.now().withNano(0))
      )

      // insert and round trip check
      val saved1 = repo.insert(unsaved1)
      val saved2 = unsaved1.toRow(saved1.productid, ???, ???, ???, ???)
      assert(saved1 === saved2)

      // check field values
      val newModifiedDate = saved1.modifieddate.minusDays(1)
      repo.updateFieldValues(saved1.productid, List(ProductFieldValue.modifieddate(newModifiedDate)))
      val List(saved3) = repo.selectAll: @unchecked
      assert(saved3.modifieddate == newModifiedDate)
      val true = repo.update(saved3.copy(size = None)): @unchecked
      // delete
      repo.delete(saved1.productid)

      val List() = repo.selectAll: @unchecked

      // done
      succeed
    }
  }
}
