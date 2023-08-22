SELECT s.businessentityid
       , p.title
       , p.firstname
       , p.middlename
       , p.namestyle
       , e.jobtitle
       , a.addressline1
       , a.city
       , a.postalcode
FROM sales.salesperson s
         JOIN humanresources.employee e ON e.businessentityid = s.businessentityid
         JOIN person.person p ON p.businessentityid = s.businessentityid
         JOIN person.businessentityaddress bea ON bea.businessentityid = s.businessentityid
         LEFT JOIN person.address a ON a.addressid = bea.addressid
where s.businessentityid = :businessentityid
and p.modifieddate > :modified_after