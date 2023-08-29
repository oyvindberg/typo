---
title: SQL DSL
---

Yes, yes, yes. We all know SQL DSLs terrible. 
But there are so many times you just need to grab some data, and it's *so* convenient to do it with a DSL.

That's why typo ships an *optional* (but strongly recommended) SQL DSL. 
It's optional because people seem to have strong feelings about the concept of SQL DSLs. 

## getting started

See [Getting started](setup.md) for some information about how to set up the DSL.

## select DSL

some features
- type-safe
- generates not-too-terrible SQL
- arbitrarily deep joins
- order by
- expresses a rich set of predicates (sql operators, functions, string functions, comparisons, etc)
- for instance string and array functions (!, [arrays](../type-safety/arrays.md) are incredibly difficult to use through JDBC)
- set of operators and functions are extendable by user
- works both when backed by [in-memory stubs](../other-features/testing-with-stubs.md) and by Postgres


<video 
    width="100%" 
    controls 
    autoplay="autoplay" 
    src="https://user-images.githubusercontent.com/247937/257662719-2a295f48-7cd2-4c49-b043-90bc8511de67.mp4"
/>

## update DSL

Can express batch updates, where you `set` arbitrary number of columns with an arbitrary number of (implicitly `AND`ed) predicates.

Column values can be computed from the original value in the column or from the entire row, as seen below

<video
width="100%"
controls
autoplay="autoplay"
src="https://user-images.githubusercontent.com/247937/257148737-7b32df2c-af54-4397-85d3-eab863179d78.mp4"
/>

## delete DSL

There is also a `delete` DSL, similar to `select` and `update`. It has no video yet, unfortunately.

## usage example

```scala mdoc:invisible
import java.sql.{Connection, DriverManager}
implicit val c: Connection = DriverManager.getConnection("jdbc:postgresql://localhost:6432/Adventureworks?user=postgres&password=password")
c.setAutoCommit(false)
```

## imports for examples below
```scala mdoc:silent
import java.time.LocalDateTime
import java.util.UUID
import adventureworks.customtypes.{Defaulted, TypoLocalDateTime, TypoShort, TypoXml}
import adventureworks.production.product.*
import adventureworks.production.productcategory.*
import adventureworks.production.productmodel.*
import adventureworks.production.productsubcategory.*
import adventureworks.production.unitmeasure.*
import adventureworks.public.{Flag, Name}
import adventureworks.withConnection

val productRepo: ProductRepo = ProductRepoImpl
val projectModelRepo: ProductmodelRepo = ProductmodelRepoImpl
val unitmeasureRepo: UnitmeasureRepo = UnitmeasureRepoImpl
val productcategoryRepo: ProductcategoryRepo = ProductcategoryRepoImpl
val productsubcategoryRepo: ProductsubcategoryRepo = ProductsubcategoryRepoImpl
```

## inserts

```scala mdoc
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
  safetystocklevel = TypoShort(16),
  reorderpoint = TypoShort(18),
  standardcost = BigDecimal(20),
  listprice = BigDecimal(22),
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

val saved1 = productRepo.insert(unsaved1)
```

## query dsl:
```scala mdoc
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
  .toList

```

```scala mdoc:invisible
c.rollback()
c.close()
```