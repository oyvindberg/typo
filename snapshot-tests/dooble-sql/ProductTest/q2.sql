with 
product0 as (
  (select product0 from production.product product0 WHERE (product0).productid = ANY(?)  AND (length((product0).name)  > ? )  AND NOT (((product0).name || (product0).color)  LIKE ? )   AND (coalesce(length((product0).color) , ? )  > ? )  AND ((product0).modifieddate < ?::timestamp ) ) 
) ,
productmodel0 as (
  (select productmodel0 from production.productmodel productmodel0 WHERE NOT ((productmodel0).name LIKE ? )  ) 
) ,
join_cte0 as (
  select product0, productmodel0
  from product0
  join productmodel0
  on ((product0).productmodelid = (productmodel0).productmodelid) 
  WHERE ((productmodel0).productmodelid > ? )  
) ,
productmodel1 as (
  (select productmodel1 from production.productmodel productmodel1 WHERE NOT ((productmodel1).name LIKE ? )  ) 
) ,
left_join_cte0 as (
  select product0, productmodel0, productmodel1
  from join_cte0
  left join productmodel1
  on (((product0).productmodelid = (productmodel1).productmodelid)  AND ? ) 
  ORDER BY (product0).name  ASC   , (productmodel0).rowguid  DESC   NULLS FIRST , (productmodel1).rowguid  ASC    
) 
select (product0)."productid",(product0)."name",(product0)."productnumber",(product0)."makeflag",(product0)."finishedgoodsflag",(product0)."color",(product0)."safetystocklevel",(product0)."reorderpoint",(product0)."standardcost",(product0)."listprice",(product0)."size",(product0)."sizeunitmeasurecode",(product0)."weightunitmeasurecode",(product0)."weight",(product0)."daystomanufacture",(product0)."productline",(product0)."class",(product0)."style",(product0)."productsubcategoryid",(product0)."productmodelid",(product0)."sellstartdate"::text,(product0)."sellenddate"::text,(product0)."discontinueddate"::text,(product0)."rowguid",(product0)."modifieddate"::text,(productmodel0)."productmodelid",(productmodel0)."name",(productmodel0)."catalogdescription",(productmodel0)."instructions",(productmodel0)."rowguid",(productmodel0)."modifieddate"::text,(productmodel1)."productmodelid",(productmodel1)."name",(productmodel1)."catalogdescription",(productmodel1)."instructions",(productmodel1)."rowguid",(productmodel1)."modifieddate"::text from left_join_cte0 