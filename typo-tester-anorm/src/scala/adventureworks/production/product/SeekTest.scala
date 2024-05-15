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
      s"""Fragment(List(NamedParameter(param1,ParameterValue(Name(foo))), NamedParameter(param2,ParameterValue(Some(22.2))), NamedParameter(param3,ParameterValue(33.3))),select "productid","name","productnumber","makeflag","finishedgoodsflag","color","safetystocklevel","reorderpoint","standardcost","listprice","size","sizeunitmeasurecode","weightunitmeasurecode","weight","daystomanufacture","productline","class","style","productsubcategoryid","productmodelid","sellstartdate"::text,"sellenddate"::text,"discontinueddate"::text,"rowguid","modifieddate"::text from production.product
         | where ((name,weight,listprice) > ({param1}::VARCHAR,{param2}::DECIMAL,{param3}::DECIMAL)) order by name ASC , weight ASC , listprice ASC${" "}
         |)""".stripMargin
    )
  }

  test("uniform descending") {
    val query = productRepo.select
      .seek(_.name.desc)(Name("foo"))
      .seek(_.weight.desc)(Some(BigDecimal(22.2)))
      .seek(_.listprice.desc)(BigDecimal(33.3))
    assertResult(query.sql.get.toString)(
      s"""Fragment(List(NamedParameter(param1,ParameterValue(Name(foo))), NamedParameter(param2,ParameterValue(Some(22.2))), NamedParameter(param3,ParameterValue(33.3))),select "productid","name","productnumber","makeflag","finishedgoodsflag","color","safetystocklevel","reorderpoint","standardcost","listprice","size","sizeunitmeasurecode","weightunitmeasurecode","weight","daystomanufacture","productline","class","style","productsubcategoryid","productmodelid","sellstartdate"::text,"sellenddate"::text,"discontinueddate"::text,"rowguid","modifieddate"::text from production.product
         | where ((name,weight,listprice) < ({param1}::VARCHAR,{param2}::DECIMAL,{param3}::DECIMAL)) order by name DESC , weight DESC , listprice DESC${" "}
         |)""".stripMargin
    )
  }

  test("complex") {
    val query = productRepo.select
      .seek(_.name.asc)(Name("foo"))
      .seek(_.weight.desc)(Some(BigDecimal(22.2)))
      .seek(_.listprice.desc)(BigDecimal(33.3))
    assertResult(query.sql.get.toString)(
      s"""Fragment(List(NamedParameter(param1,ParameterValue(Name(foo))), NamedParameter(param2,ParameterValue(Name(foo))), NamedParameter(param3,ParameterValue(Some(22.2))), NamedParameter(param4,ParameterValue(Name(foo))), NamedParameter(param5,ParameterValue(Some(22.2))), NamedParameter(param6,ParameterValue(33.3))),select "productid","name","productnumber","makeflag","finishedgoodsflag","color","safetystocklevel","reorderpoint","standardcost","listprice","size","sizeunitmeasurecode","weightunitmeasurecode","weight","daystomanufacture","productline","class","style","productsubcategoryid","productmodelid","sellstartdate"::text,"sellenddate"::text,"discontinueddate"::text,"rowguid","modifieddate"::text from production.product
         | where (((name > {param1}::VARCHAR) OR ((name = {param2}::VARCHAR) AND (weight < {param3}::DECIMAL))) OR (((name = {param4}::VARCHAR) AND (weight = {param5}::DECIMAL)) AND (listprice < {param6}::DECIMAL))) order by name ASC , weight DESC , listprice DESC${" "}
         |)""".stripMargin
    )
  }
}
