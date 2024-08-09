with
productionproduct0 as (
  (select productionproduct0 from "production"."product" productionproduct0  WHERE (productionproduct0).productid  = ANY(?) AND (length((productionproduct0).name ) > ?::int4) AND NOT (((productionproduct0).name  || (productionproduct0).color ) LIKE ?::text) AND (coalesce(length((productionproduct0).color ), ?::int4) > ?::int4) AND ((productionproduct0).modifieddate  < ?::timestamp))
),
productionproductmodel0 as (
  (select productionproductmodel0 from "production"."productmodel" productionproductmodel0  WHERE NOT ((productionproductmodel0).name  LIKE ?::text))
),
join_cte0 as (
  select productionproduct0, productionproductmodel0
  from productionproduct0
  join productionproductmodel0
  on ((productionproduct0).productmodelid  = (productionproductmodel0).productmodelid )
   WHERE ((productionproductmodel0).productmodelid  > ?::int4)
),
productionproductmodel1 as (
  (select productionproductmodel1 from "production"."productmodel" productionproductmodel1  WHERE NOT ((productionproductmodel1).name  LIKE ?::text))
),
left_join_cte0 as (
  select productionproduct0, productionproductmodel0, productionproductmodel1
  from join_cte0
  left join productionproductmodel1
  on (((productionproduct0).productmodelid  = (productionproductmodel1).productmodelid ) AND ?::boolean)
   ORDER BY (productionproduct0).name  ASC , (productionproductmodel0).rowguid  DESC NULLS FIRST, (productionproductmodel1).rowguid  ASC 
)
select (productionproduct0)."productid",(productionproduct0)."name",(productionproduct0)."productnumber",(productionproduct0)."makeflag",(productionproduct0)."finishedgoodsflag",(productionproduct0)."color",(productionproduct0)."safetystocklevel",(productionproduct0)."reorderpoint",(productionproduct0)."standardcost",(productionproduct0)."listprice",(productionproduct0)."size",(productionproduct0)."sizeunitmeasurecode",(productionproduct0)."weightunitmeasurecode",(productionproduct0)."weight",(productionproduct0)."daystomanufacture",(productionproduct0)."productline",(productionproduct0)."class",(productionproduct0)."style",(productionproduct0)."productsubcategoryid",(productionproduct0)."productmodelid",(productionproduct0)."sellstartdate"::text,(productionproduct0)."sellenddate"::text,(productionproduct0)."discontinueddate"::text,(productionproduct0)."rowguid",(productionproduct0)."modifieddate"::text,(productionproductmodel0)."productmodelid",(productionproductmodel0)."name",(productionproductmodel0)."catalogdescription",(productionproductmodel0)."instructions",(productionproductmodel0)."rowguid",(productionproductmodel0)."modifieddate"::text,(productionproductmodel1)."productmodelid",(productionproductmodel1)."name",(productionproductmodel1)."catalogdescription",(productionproductmodel1)."instructions",(productionproductmodel1)."rowguid",(productionproductmodel1)."modifieddate"::text from left_join_cte0