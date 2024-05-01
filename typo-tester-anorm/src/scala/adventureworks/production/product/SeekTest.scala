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
      s"""Fragment(List(NamedParameter(param1,ParameterValue(Name(foo))), NamedParameter(param2,ParameterValue(Some(22.2))), NamedParameter(param3,ParameterValue(33.3))),select "productid","name","productnumber","makeflag","finishedgoodsflag","color","safetystocklevel","reorderpoint","standardcost","listprice","size","sizeunitmeasurecode","weightunitmeasurecode","weight","daystomanufacture","productline","class","style","productsubcategoryid","productmodelid","sellstartdate"::text,"sellenddate"::text,"discontinueddate"::text,"rowguid","modifieddate"::text from production.product product0
         | where ((product0.name,product0.weight,product0.listprice) > ({param1}::VARCHAR,{param2}::DECIMAL,{param3}::DECIMAL)) order by product0.name ASC , product0.weight ASC , product0.listprice ASC${" "}
         |)""".stripMargin
    )
  }

  test("uniform descending") {
    val query = productRepo.select
      .seek(_.name.desc)(Name("foo"))
      .seek(_.weight.desc)(Some(BigDecimal(22.2)))
      .seek(_.listprice.desc)(BigDecimal(33.3))
    assertResult(query.sql.get.toString)(
      s"""Fragment(List(NamedParameter(param1,ParameterValue(Name(foo))), NamedParameter(param2,ParameterValue(Some(22.2))), NamedParameter(param3,ParameterValue(33.3))),select "productid","name","productnumber","makeflag","finishedgoodsflag","color","safetystocklevel","reorderpoint","standardcost","listprice","size","sizeunitmeasurecode","weightunitmeasurecode","weight","daystomanufacture","productline","class","style","productsubcategoryid","productmodelid","sellstartdate"::text,"sellenddate"::text,"discontinueddate"::text,"rowguid","modifieddate"::text from production.product product0
         | where ((product0.name,product0.weight,product0.listprice) < ({param1}::VARCHAR,{param2}::DECIMAL,{param3}::DECIMAL)) order by product0.name DESC , product0.weight DESC , product0.listprice DESC${" "}
         |)""".stripMargin
    )
  }

  test("complex") {
    val query = productRepo.select
      .seek(_.name.asc)(Name("foo"))
      .seek(_.weight.desc)(Some(BigDecimal(22.2)))
      .seek(_.listprice.desc)(BigDecimal(33.3))
    assertResult(query.sql.get.toString)(
      s"""Fragment(List(NamedParameter(param1,ParameterValue(Name(foo))), NamedParameter(param2,ParameterValue(Name(foo))), NamedParameter(param3,ParameterValue(Some(22.2))), NamedParameter(param4,ParameterValue(Name(foo))), NamedParameter(param5,ParameterValue(Some(22.2))), NamedParameter(param6,ParameterValue(33.3))),select "productid","name","productnumber","makeflag","finishedgoodsflag","color","safetystocklevel","reorderpoint","standardcost","listprice","size","sizeunitmeasurecode","weightunitmeasurecode","weight","daystomanufacture","productline","class","style","productsubcategoryid","productmodelid","sellstartdate"::text,"sellenddate"::text,"discontinueddate"::text,"rowguid","modifieddate"::text from production.product product0
         | where (((product0.name > {param1}::VARCHAR) OR ((product0.name = {param2}::VARCHAR) AND (product0.weight < {param3}::DECIMAL))) OR (((product0.name = {param4}::VARCHAR) AND (product0.weight = {param5}::DECIMAL)) AND (product0.listprice < {param6}::DECIMAL))) order by product0.name ASC , product0.weight DESC , product0.listprice DESC 
         |)""".stripMargin
    )
  }
}
