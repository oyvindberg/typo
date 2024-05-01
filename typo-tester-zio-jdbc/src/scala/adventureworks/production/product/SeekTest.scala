package adventureworks.production.product

import adventureworks.public.Name
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funsuite.AnyFunSuite

class SeekTest extends AnyFunSuite with TypeCheckedTripleEquals {
  val productRepo = new ProductRepoImpl

  test("uniform ascending") {
    val query = productRepo.select
      .seek(_.name.asc)(Name("foo"))
      .seek(_.weight.asc)(Some(BigDecimal(22.2)))
      .seek(_.listprice.asc)(BigDecimal(33.3))
    assertResult(query.sql.get.toString)(
      s"""Sql(select "productid", "name", "productnumber", "makeflag", "finishedgoodsflag", "color", "safetystocklevel", "reorderpoint", "standardcost", "listprice", "size", "sizeunitmeasurecode", "weightunitmeasurecode", "weight", "daystomanufacture", "productline", "class", "style", "productsubcategoryid", "productmodelid", "sellstartdate"::text, "sellenddate"::text, "discontinueddate"::text, "rowguid", "modifieddate"::text from production.product product0 WHERE ((product0.name,product0.weight,product0.listprice) > (?::VARCHAR,?::DECIMAL,?::DECIMAL)) ORDER BY product0.name ASC , product0.weight ASC , product0.listprice ASC , foo, Some(22.2), 33.3)"""
    )
  }

  test("uniform descending") {
    val query = productRepo.select
      .seek(_.name.desc)(Name("foo"))
      .seek(_.weight.desc)(Some(BigDecimal(22.2)))
      .seek(_.listprice.desc)(BigDecimal(33.3))
    assertResult(query.sql.get.toString)(
      s"""Sql(select "productid", "name", "productnumber", "makeflag", "finishedgoodsflag", "color", "safetystocklevel", "reorderpoint", "standardcost", "listprice", "size", "sizeunitmeasurecode", "weightunitmeasurecode", "weight", "daystomanufacture", "productline", "class", "style", "productsubcategoryid", "productmodelid", "sellstartdate"::text, "sellenddate"::text, "discontinueddate"::text, "rowguid", "modifieddate"::text from production.product product0 WHERE ((product0.name,product0.weight,product0.listprice) < (?::VARCHAR,?::DECIMAL,?::DECIMAL)) ORDER BY product0.name DESC , product0.weight DESC , product0.listprice DESC , foo, Some(22.2), 33.3)"""
    )
  }

  test("complex") {
    val query = productRepo.select
      .seek(_.name.asc)(Name("foo"))
      .seek(_.weight.desc)(Some(BigDecimal(22.2)))
      .seek(_.listprice.desc)(BigDecimal(33.3))
    assertResult(query.sql.get.toString)(
      s"""Sql(select "productid", "name", "productnumber", "makeflag", "finishedgoodsflag", "color", "safetystocklevel", "reorderpoint", "standardcost", "listprice", "size", "sizeunitmeasurecode", "weightunitmeasurecode", "weight", "daystomanufacture", "productline", "class", "style", "productsubcategoryid", "productmodelid", "sellstartdate"::text, "sellenddate"::text, "discontinueddate"::text, "rowguid", "modifieddate"::text from production.product product0 WHERE (((product0.name > ?::VARCHAR) OR ((product0.name = ?::VARCHAR) AND (product0.weight < ?::DECIMAL))) OR (((product0.name = ?::VARCHAR) AND (product0.weight = ?::DECIMAL)) AND (product0.listprice < ?::DECIMAL))) ORDER BY product0.name ASC , product0.weight DESC , product0.listprice DESC , foo, foo, Some(22.2), foo, Some(22.2), 33.3)"""
    )
  }
}
