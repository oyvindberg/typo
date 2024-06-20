select product0.productid, product0.name, product0.productnumber, product0.makeflag, product0.finishedgoodsflag, product0.color, product0.safetystocklevel, product0.reorderpoint, product0.standardcost, product0.listprice, product0.size, product0.sizeunitmeasurecode, product0.weightunitmeasurecode, product0.weight, product0.daystomanufacture, product0.productline, product0.class, product0.style, product0.productsubcategoryid, product0.productmodelid, product0.sellstartdate, product0.sellenddate, product0.discontinueddate, product0.rowguid, product0.modifieddate, unitmeasure0.unitmeasurecode, unitmeasure0.name, unitmeasure0.modifieddate, productmodel0.productmodelid, productmodel0.name, productmodel0.catalogdescription, productmodel0.instructions, productmodel0.rowguid, productmodel0.modifieddate
from (
  select "productid","name","productnumber","makeflag","finishedgoodsflag","color","safetystocklevel","reorderpoint","standardcost","listprice","size","sizeunitmeasurecode","weightunitmeasurecode","weight","daystomanufacture","productline","class","style","productsubcategoryid","productmodelid","sellstartdate"::text,"sellenddate"::text,"discontinueddate"::text,"rowguid","modifieddate"::text from production.product product0
   where (((product0.class = ?::VARCHAR) AND ((product0.daystomanufacture > ?::INTEGER) OR (product0.daystomanufacture <= ?::INTEGER))) AND (product0.productline = ?::VARCHAR))
) product0
join (
  select "unitmeasurecode","name","modifieddate"::text from production.unitmeasure unitmeasure0
   where (unitmeasure0.name LIKE ?::VARCHAR)
) unitmeasure0 on (product0.sizeunitmeasurecode = unitmeasure0.unitmeasurecode)
left join (
  select "productmodelid","name","catalogdescription","instructions","rowguid","modifieddate"::text from production.productmodel productmodel0
) productmodel0 on (product0.productmodelid = productmodel0.productmodelid)

 where (product0.productmodelid = productmodel0.productmodelid) order by product0.productmodelid ASC , productmodel0.name DESC NULLS FIRST
