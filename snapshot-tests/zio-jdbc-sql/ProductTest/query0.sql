with
product0 as (
  (select product0 from production.product product0 )
),
productmodel0 as (
  (select productmodel0 from production.productmodel productmodel0 )
),
join_cte2 as (
  select product0, productmodel0
  from product0
  join productmodel0
  on ((product0).productmodelid = (productmodel0).productmodelid)
  
),
productsubcategory0 as (
  (select productsubcategory0 from production.productsubcategory productsubcategory0 )
),
join_cte1 as (
  select product0, productmodel0, productsubcategory0
  from join_cte2
  join productsubcategory0
  on ((product0).productsubcategoryid = (productsubcategory0).productsubcategoryid)
  
),
productcategory0 as (
  (select productcategory0 from production.productcategory productcategory0 )
),
join_cte0 as (
  select product0, productmodel0, productsubcategory0, productcategory0
  from join_cte1
  join productcategory0
  on ((productsubcategory0).productcategoryid = (productcategory0).productcategoryid)
  
)
select (product0)."productid",(product0)."name",(product0)."productnumber",(product0)."makeflag",(product0)."finishedgoodsflag",(product0)."color",(product0)."safetystocklevel",(product0)."reorderpoint",(product0)."standardcost",(product0)."listprice",(product0)."size",(product0)."sizeunitmeasurecode",(product0)."weightunitmeasurecode",(product0)."weight",(product0)."daystomanufacture",(product0)."productline",(product0)."class",(product0)."style",(product0)."productsubcategoryid",(product0)."productmodelid",(product0)."sellstartdate"::text,(product0)."sellenddate"::text,(product0)."discontinueddate"::text,(product0)."rowguid",(product0)."modifieddate"::text,(productmodel0)."productmodelid",(productmodel0)."name",(productmodel0)."catalogdescription",(productmodel0)."instructions",(productmodel0)."rowguid",(productmodel0)."modifieddate"::text,(productsubcategory0)."productsubcategoryid",(productsubcategory0)."productcategoryid",(productsubcategory0)."name",(productsubcategory0)."rowguid",(productsubcategory0)."modifieddate"::text,(productcategory0)."productcategoryid",(productcategory0)."name",(productcategory0)."rowguid",(productcategory0)."modifieddate"::text from join_cte0