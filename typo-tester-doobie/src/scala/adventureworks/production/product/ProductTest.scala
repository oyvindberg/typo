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
  // for scala 2.12
  implicit val `Ordering[LocalDateTime]` : Ordering[LocalDateTime] = (x: LocalDateTime, y: LocalDateTime) => x.compareTo(y)

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
        productmodel <- ProductmodelRepoImpl.insert(
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
        query = productRepo.select
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

        _ <- delay(query.sql.foreach(f => println(f)))
        _ <- query.toList.map(println(_))
        leftJoined = productRepo.select.join(projectModelRepo.select).leftOn { case (p, pm) => p.productmodelid === pm.productmodelid }
        _ <- delay(leftJoined.sql.foreach(println))
        _ <- leftJoined.toList.map(println)

        update = productRepo.update
          .setComputedValue(_.name)(p => (p.reverse.upper || Name("flaff")).substring(2, 4))
          .setValue(_.listprice)(BigDecimal(2))
          .setComputedValue(_.reorderpoint)(_ plus 22)
//          .setComputedValue(_.sizeunitmeasurecode)(_ => Some(unitmeasure.unitmeasurecode))
          .where(_.productid === saved1.productid)

        _ <- delay(update.sql(returning = true).foreach(println(_)))
        foo <- update.executeReturnChanged
        List(updated) = foo
        _ <- delay(assert(updated.name === Name("MANf")))
        _ <- delay(assert(updated.listprice === BigDecimal(2)))
        _ <- delay(assert(updated.reorderpoint === 40))
        _ <- delay {
          val q = productRepo.select
            .where(p => !p.name.like("foo%"))
            .where(p => !(p.name.underlying || p.color).like("foo%"))
            .where(p => p.daystomanufacture > 0)
            .where(p => p.modifieddate < TypoLocalDateTime.now)
            .join(projectModelRepo.select.where(p => p.modifieddate < TypoLocalDateTime.now))
            .on { case (p, pm) => p.productmodelid === pm.productmodelid }
            .where { case (_, pm) => !pm.instructions.isNull }

          q.sql.foreach(f => println(f))
          q.toList.map(list => list.foreach(println))

        }
        _ <- {
          val q = productRepo.select
            // select from id, arrays work
            .where(p => p.productid.in(Array(saved1.productid, new ProductId(22))))
            // call `length` function and compare result
            .where(p => p.name.strLength > 3)
            // concatenate two strings (one of which is a wrapped type in scala)
            // and compare result
            .where(p => !(p.name.underlying || p.color).like("foo%"))
            // tracks nullability
            .whereStrict(p => p.color.strLength.coalesce(1) > 0)
            // compare dates
            .where(p => p.modifieddate < TypoLocalDateTime.now)
            // join, filter table we join with as well
            .join(projectModelRepo.select.where(pm => !pm.name.like("foo%")))
            .on { case (p, pm) => p.productmodelid === pm.productmodelid }
            // additional predicates for joined rows.
            .where { case (_, pm) => pm.productmodelid.underlying > 0 }
            // works arbitrarily deep
            .join(projectModelRepo.select.where(pm => !pm.name.like("foo%")))
            .leftOn { case ((p, _), pm2) => p.productmodelid === pm2.productmodelid and false }
            // order by
            .orderBy { case ((p, _), _) => p.name.asc }
            .orderBy { case ((_, pm), _) => pm.rowguid.desc.withNullsFirst }
            .orderBy { case ((_, _), pm2) => pm2(_.rowguid).asc }

//          q.sql.foreach(f => println(f.sql))
          q.toList.map {
            _.map { case ((p, pm1), pm2) =>
              println(p)
              println(pm1)
              println(pm2)
            }
          }
        }
        // delete
        _ <- productRepo.delete(saved1.productid)
        _ <- productRepo.selectAll.compile.toList.map {
          case Nil   => ()
          case other => throw new MatchError(other)
        }
      } yield succeed
    }

    succeed

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
