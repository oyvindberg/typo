with 
product0 as (
  (select product0 from production.product product0 where (((((product0).productid = ANY(?) AND (length((product0).name) > ?::INTEGER)) AND NOT (((product0).name || (product0).color) LIKE ?::VARCHAR)) AND (coalesce((product0).color, ?::VARCHAR) != ?::VARCHAR)) AND ((product0).modifieddate < ?::timestamp)))
),
productmodel0 as (
  (select productmodel0 from production.productmodel productmodel0 where (length((productmodel0).name) > ?::INTEGER))
),
join_cte0 as (
  select product0, productmodel0
  from product0
  join productmodel0
  on ((product0).productmodelid = (productmodel0).productmodelid)
  where ((productmodel0).name != ?::VARCHAR)
),
productmodel1 as (
  (select productmodel1 from production.productmodel productmodel1 where (length((productmodel1).name) > ?::INTEGER))
),
left_join_cte0 as (
  select product0, productmodel0, productmodel1
  from join_cte0
  left join productmodel1
  on (((product0).productmodelid = (productmodel1).productmodelid) AND ?::BOOLEAN)
  order by (productmodel1).name ASC , (product0).color DESC NULLS FIRST
)
select (product0)."productid",(product0)."name",(product0)."productnumber",(product0)."makeflag",(product0)."finishedgoodsflag",(product0)."color",(product0)."safetystocklevel",(product0)."reorderpoint",(product0)."standardcost",(product0)."listprice",(product0)."size",(product0)."sizeunitmeasurecode",(product0)."weightunitmeasurecode",(product0)."weight",(product0)."daystomanufacture",(product0)."productline",(product0)."class",(product0)."style",(product0)."productsubcategoryid",(product0)."productmodelid",(product0)."sellstartdate"::text,(product0)."sellenddate"::text,(product0)."discontinueddate"::text,(product0)."rowguid",(product0)."modifieddate"::text,(productmodel0)."productmodelid",(productmodel0)."name",(productmodel0)."catalogdescription",(productmodel0)."instructions",(productmodel0)."rowguid",(productmodel0)."modifieddate"::text,(productmodel1)."productmodelid",(productmodel1)."name",(productmodel1)."catalogdescription",(productmodel1)."instructions",(productmodel1)."rowguid",(productmodel1)."modifieddate"::text from left_join_cte0