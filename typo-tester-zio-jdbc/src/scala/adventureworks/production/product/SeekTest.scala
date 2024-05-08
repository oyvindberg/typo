package adventureworks.production.product

import adventureworks.public.Name
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funsuite.AnyFunSuite

class SeekTest extends AnyFunSuite with TypeCheckedTripleEquals {
  val productRepo = new ProductRepoImpl

  val base =
    """select "productid", "name", "productnumber", "makeflag", "finishedgoodsflag", "color", "safetystocklevel", "reorderpoint", "standardcost", "listprice", "size", "sizeunitmeasurecode", "weightunitmeasurecode", "weight", "daystomanufacture", "productline", "class", "style", "productsubcategoryid", "productmodelid", "sellstartdate"::text, "sellenddate"::text, "discontinueddate"::text, "rowguid", "modifieddate"::text from production.product"""

  test("uniform ascending") {
    val query = productRepo.select
      .seek(_.name.asc)(Name("foo"))
      .seek(_.weight.asc)(Some(BigDecimal(22.2)))
      .seek(_.listprice.asc)(BigDecimal(33.3))
    assertResult(query.sql.get.toString)(
      s"""Sql($base WHERE ((name,weight,listprice) > (?::VARCHAR,?::DECIMAL,?::DECIMAL)) ORDER BY name ASC , weight ASC , listprice ASC , foo, Some(22.2), 33.3)"""
    )
  }

  test("uniform descending") {
    val query = productRepo.select
      .seek(_.name.desc)(Name("foo"))
      .seek(_.weight.desc)(Some(BigDecimal(22.2)))
      .seek(_.listprice.desc)(BigDecimal(33.3))
    assertResult(query.sql.get.toString)(
      s"""Sql($base WHERE ((name,weight,listprice) < (?::VARCHAR,?::DECIMAL,?::DECIMAL)) ORDER BY name DESC , weight DESC , listprice DESC , foo, Some(22.2), 33.3)"""
    )
  }

  test("complex") {
    val query = productRepo.select
      .seek(_.name.asc)(Name("foo"))
      .seek(_.weight.desc)(Some(BigDecimal(22.2)))
      .seek(_.listprice.desc)(BigDecimal(33.3))
    assertResult(query.sql.get.toString)(
      s"""Sql($base WHERE (((name > ?::VARCHAR) OR ((name = ?::VARCHAR) AND (weight < ?::DECIMAL))) OR (((name = ?::VARCHAR) AND (weight = ?::DECIMAL)) AND (listprice < ?::DECIMAL))) ORDER BY name ASC , weight DESC , listprice DESC , foo, foo, Some(22.2), foo, Some(22.2), 33.3)"""
    )
  }
}
