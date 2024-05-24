package adventureworks.production.product

import adventureworks.customtypes.*
import adventureworks.production.productcategory.*
import adventureworks.production.productmodel.*
import adventureworks.production.productsubcategory.*
import adventureworks.production.unitmeasure.*
import adventureworks.public.{Flag, Name}
import adventureworks.withConnection
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.Assertion
import org.scalatest.funsuite.AnyFunSuite
import zio.{Chunk, ZIO}

import java.time.LocalDateTime

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
          safetystocklevel = TypoShort(16),
          reorderpoint = TypoShort(18),
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
          rowguid = Defaulted.Provided(TypoUUID.randomUUID),
          modifieddate = Defaulted.Provided(TypoLocalDateTime.now)
        )
        // insert and round trip check
        saved1 <- productRepo.insert(unsaved1)
        saved2 = unsaved1.toRow(saved1.productid, ???, ???, ???, ???)
        _ <- ZIO.succeed(assert(saved1 === saved2))

        // check field values
        newModifiedDate = TypoLocalDateTime(saved1.modifieddate.value.minusDays(1))
        _ <- productRepo.update(saved1.copy(modifieddate = newModifiedDate))
        saved3 <- productRepo.selectAll.runCollect.map(_.toList).map {
          case List(x) => x
          case other   => throw new MatchError(other)
        }
        _ <- ZIO.succeed(assert(saved3.modifieddate == newModifiedDate))
        _ <- productRepo.update(saved3.copy(size = None)).map(res => assert(res))
        query0 = productRepo.select
          .joinFk(_.fkProductmodel)(projectModelRepo.select)
          .joinFk(_._1.fkProductsubcategory)(productsubcategoryRepo.select)
          .joinFk(_._2.fkProductcategory)(productcategoryRepo.select)
        _ <- query0.toChunk.map(println(_))
        _ <- ZIO.succeed(query0.sql.foreach(f => println(f)))
        _ <- ZIO.succeed(println("foo"))
        query = productRepo.select
          .where(_.`class` === "H ")
          .where(x => (x.daystomanufacture > 25).or(x.daystomanufacture <= 0))
          .where(x => x.productline === "foo")
          .join(unitmeasureRepo.select.where(_.name.like("name%")))
          .on { case (p, um) => p.sizeunitmeasurecode.isEqual(um.unitmeasurecode) }
          .join(projectModelRepo.select)
          .leftOn { case ((product, _), productModel) => product.productmodelid === productModel.productmodelid }
          .where { case ((product, _), productModel) => product.productmodelid === productModel(_.productmodelid) }
          .orderBy { case ((product, _), _) => product.productmodelid.asc }
          .orderBy { case ((_, _), productModel) => productModel(_.name).desc.withNullsFirst }

        _ <- ZIO.succeed(query.sql.foreach(f => println(f)))
        _ <- query.toChunk.map(println(_))
        leftJoined = productRepo.select.join(projectModelRepo.select).leftOn { case (p, pm) => p.productmodelid === pm.productmodelid }
        _ <- ZIO.succeed(leftJoined.sql.foreach(println))
        _ <- leftJoined.toChunk.map(println)

        update = productRepo.update
          .setComputedValue(_.name)(p => (p.reverse.upper || Name("flaff")).substring(2, 4))
          .setValue(_.listprice)(BigDecimal(2))
          .setComputedValue(_.reorderpoint)(_ + TypoShort(22))
//          .setComputedValue(_.sizeunitmeasurecode)(_ => Some(unitmeasure.unitmeasurecode))
          .where(_.productid === saved1.productid)

        _ <- ZIO.succeed(update.sql(returning = true).foreach(println(_)))
        foo <- update.executeReturnChanged
        Chunk(updated) = foo
        _ <- ZIO.succeed(assert(updated.name === Name("MANf")))
        _ <- ZIO.succeed(assert(updated.listprice === BigDecimal(2)))
        _ <- ZIO.succeed(assert(updated.reorderpoint === TypoShort(40)))
        _ <- {
          val q = productRepo.select
            .where(p => !p.name.like("foo%"))
            .where(p => !(p.name.underlying || p.color).like("foo%"))
            .where(p => p.daystomanufacture > 0)
            .where(p => p.modifieddate < TypoLocalDateTime.now)
            .join(projectModelRepo.select.where(p => p.modifieddate < TypoLocalDateTime.now))
            .on { case (p, pm) => p.productmodelid === pm.productmodelid }
            .where { case (_, pm) => !pm.instructions.isNull }

          q.sql.foreach(f => println(f))
          q.toChunk.map(list => list.foreach(println))
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
            .leftOn { case ((p, _), pm2) => (p.productmodelid === pm2.productmodelid).and(false) }
            // order by
            .orderBy { case ((p, _), _) => p.name.asc }
            .orderBy { case ((_, pm), _) => pm.rowguid.desc.withNullsFirst }
            .orderBy { case ((_, _), pm2) => pm2(_.rowguid).asc }

//          q.sql.foreach(f => println(f.sql))
          q.toChunk.map {
            _.map { case ((p, pm1), pm2) =>
              println(p)
              println(pm1)
              println(pm2)
            }
          }
        }
        // delete
        _ <- productRepo.deleteById(saved1.productid)
        _ <- productRepo.selectAll.runCollect.map(_.toList).map {
          case Nil   => ()
          case other => throw new MatchError(other)
        }
      } yield succeed
    }
  }

  test("in-memory") {
    runTest(
      productRepo = new ProductRepoMock(_.toRow(ProductId(0), Flag.apply(true), Flag.apply(false), TypoUUID.randomUUID, TypoLocalDateTime.now)),
      projectModelRepo = new ProductmodelRepoMock(_.toRow(ProductmodelId(0), TypoUUID.randomUUID, TypoLocalDateTime.now)),
      unitmeasureRepo = new UnitmeasureRepoMock(_.toRow(TypoLocalDateTime.now)),
      productcategoryRepo = new ProductcategoryRepoMock(_.toRow(ProductcategoryId(0), TypoUUID.randomUUID, TypoLocalDateTime.now)),
      productsubcategoryRepo = new ProductsubcategoryRepoMock(_.toRow(ProductsubcategoryId(0), TypoUUID.randomUUID, TypoLocalDateTime.now))
    )
  }

  test("pg") {
    runTest(
      productRepo = new ProductRepoImpl,
      projectModelRepo = new ProductmodelRepoImpl,
      unitmeasureRepo = new UnitmeasureRepoImpl,
      productcategoryRepo = new ProductcategoryRepoImpl,
      productsubcategoryRepo = new ProductsubcategoryRepoImpl
    )
  }
}
