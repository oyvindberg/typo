---
title: Testing with random values
---

This covers a lot of interesting ground, test-wise.

If you enable `enableTestInserts` in `typo.Options` you now get an `TestInsert` class, with a method to insert a row for each table Typo knows about. 
All values except ids, foreign keys and so on are *randomly generated*, but you can override them with named parameters.

The idea is that you:
- can easily insert rows for testing
- can explicitly set the values you *do* care about
- will get random values for the rest
- are still forced to follow FKs to setup the data graph correctly
- it's easy to follow those FKs, because after inserting a row you get the persisted version back, including generated IDs
- can get the same values each time by hard coding the seed `new TestInsert(new scala.util.Random(0L))`, or you can run it multiple times with different seeds to see that the random values really do not matter
- do not need to write *any* code to get all this available to you, like the rest of Typo.

In summary, this is a fantastic way of setting up complex test scenarios in the database!


### Usage example

```scala mdoc:invisible
import java.sql.{Connection, DriverManager}
implicit val c: Connection = DriverManager.getConnection("jdbc:postgresql://localhost:6432/Adventureworks?user=postgres&password=password")
c.setAutoCommit(false)
```

```scala mdoc
import adventureworks.customtypes.{Defaulted, TypoShort, TypoLocalDateTime, TypoXml}
import adventureworks.production.unitmeasure.UnitmeasureId
import adventureworks.TestInsert

import scala.util.Random

val testInsert = new TestInsert(new Random(0))

val unitmeasure = testInsert.productionUnitmeasure(UnitmeasureId("kgg"))
val productCategory = testInsert.productionProductcategory()
val productSubcategory = testInsert.productionProductsubcategory(productCategory.productcategoryid)
val productModel = testInsert.productionProductmodel(catalogdescription = Some(new TypoXml("<xml/>")), instructions = Some(new TypoXml("<instructions/>")))
testInsert.productionProduct(
  safetystocklevel = TypoShort(1),
  reorderpoint = TypoShort(1),
  standardcost = BigDecimal(1),
  listprice = BigDecimal(1),
  daystomanufacture = 10,
  sellstartdate = TypoLocalDateTime.now,
  sizeunitmeasurecode = Some(unitmeasure.unitmeasurecode),
  weightunitmeasurecode = Some(unitmeasure.unitmeasurecode),
  `class` = Some("H "),
  style = Some("W "),
  productsubcategoryid = Some(productSubcategory.productsubcategoryid),
  productmodelid = Some(productModel.productmodelid)
)

```

```scala mdoc:invisible
c.rollback()
c.close()
```

### Comparison with scalacheck

This does look a lot like scalacheck indeed.

But look closer, there are:
- no implicits
- no integration glue code with test libraries
- almost no imports, you need to mention very few types
- no keeping track of all the possible row types and repositories
- and so on

This feature is meant to be easy to use, and I really think/hope it is!
