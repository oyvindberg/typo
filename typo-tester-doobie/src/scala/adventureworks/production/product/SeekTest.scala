package adventureworks.production.product

import adventureworks.public.Name
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funsuite.AnyFunSuite
import typo.dsl.SqlExpr

class SeekTest extends AnyFunSuite with TypeCheckedTripleEquals {
  val productRepo = new ProductRepoImpl

  test("uniform ascending") {
    val query = productRepo.select
      .seek(_.name.asc)(Name("foo"))
      .seek(_.weight.asc)(SqlExpr.asConstOpt(Some(BigDecimal(22.2))))
      .seek(_.listprice.asc)(BigDecimal(33.3))
    assertResult(query.sql.get.toString)(
      s"""Fragment("select "productid", "name", "productnumber", "makeflag", "finishedgoodsflag", "color", "safetystocklevel", "reorderpoint", "standardcost", "listprice", "size", "sizeunitmeasurecode", "weightunitmeasurecode", "weight", "daystomanufacture", "productline", "class", "style", "productsubcategoryid", "productmodelid", "sellstartdate"::text, "sellenddate"::text, "discontinueddate"::text, "rowguid", "modifieddate"::text from production.product product0 WHERE ((product0.name, product0.weight, product0.listprice)  > (? , ? , ? ) ) ORDER BY product0.name  ASC   , product0.weight  ASC   , product0.listprice  ASC   ")"""
    )
  }

  test("uniform descending") {
    val query = productRepo.select
      .seek(_.name.desc)(Name("foo"))
      .seek(_.weight.desc)(Some(BigDecimal(22.2)))
      .seek(_.listprice.desc)(BigDecimal(33.3))
    assertResult(query.sql.get.toString)(
      s"""Fragment("select "productid", "name", "productnumber", "makeflag", "finishedgoodsflag", "color", "safetystocklevel", "reorderpoint", "standardcost", "listprice", "size", "sizeunitmeasurecode", "weightunitmeasurecode", "weight", "daystomanufacture", "productline", "class", "style", "productsubcategoryid", "productmodelid", "sellstartdate"::text, "sellenddate"::text, "discontinueddate"::text, "rowguid", "modifieddate"::text from production.product product0 WHERE ((product0.name, product0.weight, product0.listprice)  < (? , ? , ? ) ) ORDER BY product0.name  DESC   , product0.weight  DESC   , product0.listprice  DESC   ")"""
    )
  }

  test("complex") {
    val query = productRepo.select
      .seek(_.name.asc)(Name("foo"))
      .seek(_.weight.desc)(Some(BigDecimal(22.2)))
      .seek(_.listprice.desc)(BigDecimal(33.3))
    assertResult(query.sql.get.toString)(
      s"""Fragment("select "productid", "name", "productnumber", "makeflag", "finishedgoodsflag", "color", "safetystocklevel", "reorderpoint", "standardcost", "listprice", "size", "sizeunitmeasurecode", "weightunitmeasurecode", "weight", "daystomanufacture", "productline", "class", "style", "productsubcategoryid", "productmodelid", "sellstartdate"::text, "sellenddate"::text, "discontinueddate"::text, "rowguid", "modifieddate"::text from production.product product0 WHERE (((product0.name > ? )  OR ((product0.name = ? )  AND (product0.weight < ? ) ) )  OR (((product0.name = ? )  AND (product0.weight = ? ) )  AND (product0.listprice < ? ) ) ) ORDER BY product0.name  ASC   , product0.weight  DESC   , product0.listprice  DESC   ")"""
    )
  }
}
