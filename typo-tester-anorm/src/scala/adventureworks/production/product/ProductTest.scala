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
  // for scala 2.12
  implicit val `Ordering[LocalDateTime]` : Ordering[LocalDateTime] = (x: LocalDateTime, y: LocalDateTime) => x.compareTo(y)

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

      val query =
        productRepo.select
          .where(_.`class` === "H ")
          .where(x => x.daystomanufacture > 25 or x.daystomanufacture <= 0)
          .where(x => x.productline === "foo")
          .join(unitmeasureRepo.select.where(_.name.like("name%")))
          .on { case (p, um) => p.sizeunitmeasurecode === um.unitmeasurecode }
          .join(projectModelRepo.select)
          .leftOn { case ((product, _), productModel) => product.productmodelid === productModel.productmodelid }
          .where { case ((product, _), productModel) => product.productmodelid === productModel(_.productmodelid) }
          .orderBy { case ((product, _), _) => product.productmodelid.asc }
          .orderBy { case ((_, _), productModel) => productModel(_.name).desc.withNullsFirst }

      query.sql.foreach(f => println(f.sql))

      println(query.toList)

      val leftJoined = productRepo.select
        .join(projectModelRepo.select)
        .leftOn { case (p, pm) => p.productmodelid === pm.productmodelid }

      leftJoined.sql.foreach(f => println(f.sql))
      leftJoined.toList.foreach(println)

      val sellStartDate = TypoLocalDateTime.now
      val update = productRepo.update
        .setComputedValue(_.name)(p => (p.reverse.upper || Name("flaff")).substring(2, 4))
        .setValue(_.listprice)(BigDecimal(2))
        .setComputedValue(_.reorderpoint)(_ plus 22)
        .setComputedValue(_.sizeunitmeasurecode)(_ => Some(unitmeasure.unitmeasurecode))
        .setComputedValue(_.sellstartdate)(_ => sellStartDate)
        .where(_.productid === saved1.productid)

      update.sql(returning = true).foreach(f => println(f.sql))
      val List(updated) = update.executeReturnChanged()
      assert(updated.name === Name("MANf"))
      assert(updated.listprice === BigDecimal(2))
      assert(updated.reorderpoint === 40)
      assert(updated.sellstartdate === sellStartDate)

      val q = productRepo.select
        .where(p => !p.name.like("foo%"))
        .where(p => !(p.name.underlying || p.color).like("foo%"))
        .where(p => p.daystomanufacture > 0)
        .where(p => p.modifieddate < TypoLocalDateTime.now)
        .join(projectModelRepo.select.where(p => p.modifieddate < TypoLocalDateTime.now))
        .on { case (p, pm) => p.productmodelid === pm.productmodelid }
        .where { case (_, pm) => !pm.instructions.isNull }

      q.sql.foreach(f => println(f.sql))
      q.toList.foreach(println)

      val q2 = productRepo.select
        // select from id, arrays work
        .where(p => p.productid.in(Array(saved1.productid, new ProductId(22))))
        // call `length` function and compare result
        .where(p => p.name.strLength > 3)
        // concatenate two strings (one of which is a wrapped type in scala) and compare result
        .where(p => !(p.name.underlying || p.color).like("foo%"))
        // tracks nullability
        .whereStrict(p => p.color.coalesce("yellow") !== "blue")
        // compare dates
        .where(p => p.modifieddate < TypoLocalDateTime.now)
        // join, filter table we join with as well
        .join(projectModelRepo.select.where(pm => pm.name.strLength > 0))
        .on { case (p, pm) => p.productmodelid === pm.productmodelid }
        // additional predicates for joined rows.
        .where { case (_, pm) => pm.name.underlying !== "foo" }
        // works arbitrarily deep
        .join(projectModelRepo.select.where(pm => pm.name.strLength > 0))
        .leftOn { case ((p, _), pm2) =>
          p.productmodelid === pm2.productmodelid and false
        }
        // order by
        .orderBy { case (_, pm2) => pm2(_.name).asc }
        .orderBy { case ((p, _), _) => p.color.desc.withNullsFirst }

      q2.sql.foreach(println)
      q2.toList.foreach { case ((p, pm1), pm2) =>
        println(p)
        println(pm1)
        println(pm2)
      }

      // delete
      productRepo.delete.where(_.productid === saved1.productid).execute()

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
