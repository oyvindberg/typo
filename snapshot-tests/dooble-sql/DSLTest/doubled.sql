with 
salesperson0 as (
  (select salesperson0 from sales.salesperson salesperson0 WHERE ((salesperson0).rowguid = ?::uuid ) ) 
) ,
employee0 as (
  (select employee0 from humanresources.employee employee0 ) 
) ,
join_cte5 as (
  select salesperson0, employee0
  from salesperson0
  join employee0
  on ((salesperson0).businessentityid = (employee0).businessentityid) 
   
) ,
person0 as (
  (select person0 from person.person person0 ) 
) ,
join_cte4 as (
  select salesperson0, employee0, person0
  from join_cte5
  join person0
  on ((employee0).businessentityid = (person0).businessentityid) 
   
) ,
businessentity0 as (
  (select businessentity0 from person.businessentity businessentity0 ) 
) ,
join_cte3 as (
  select salesperson0, employee0, person0, businessentity0
  from join_cte4
  join businessentity0
  on ((person0).businessentityid = (businessentity0).businessentityid) 
   
) ,
emailaddress0 as (
  (select emailaddress0 from person.emailaddress emailaddress0 ORDER BY (emailaddress0).rowguid  ASC   ) 
) ,
join_cte2 as (
  select salesperson0, employee0, person0, businessentity0, emailaddress0
  from join_cte3
  join emailaddress0
  on ((emailaddress0).businessentityid = (businessentity0).businessentityid) 
   
) ,
salesperson1 as (
  (select salesperson1 from sales.salesperson salesperson1 ) 
) ,
join_cte1 as (
  select salesperson0, employee0, person0, businessentity0, emailaddress0, salesperson1
  from join_cte2
  join salesperson1
  on ((emailaddress0).businessentityid = (salesperson1).businessentityid) 
   
) ,
salesperson2 as (
  (select salesperson2 from sales.salesperson salesperson2 WHERE ((salesperson2).rowguid = ?::uuid ) ) 
) ,
employee1 as (
  (select employee1 from humanresources.employee employee1 ) 
) ,
join_cte10 as (
  select salesperson2, employee1
  from salesperson2
  join employee1
  on ((salesperson2).businessentityid = (employee1).businessentityid) 
   
) ,
person1 as (
  (select person1 from person.person person1 ) 
) ,
join_cte9 as (
  select salesperson2, employee1, person1
  from join_cte10
  join person1
  on ((employee1).businessentityid = (person1).businessentityid) 
   
) ,
businessentity1 as (
  (select businessentity1 from person.businessentity businessentity1 ) 
) ,
join_cte8 as (
  select salesperson2, employee1, person1, businessentity1
  from join_cte9
  join businessentity1
  on ((person1).businessentityid = (businessentity1).businessentityid) 
   
) ,
emailaddress1 as (
  (select emailaddress1 from person.emailaddress emailaddress1 ORDER BY (emailaddress1).rowguid  ASC   ) 
) ,
join_cte7 as (
  select salesperson2, employee1, person1, businessentity1, emailaddress1
  from join_cte8
  join emailaddress1
  on ((emailaddress1).businessentityid = (businessentity1).businessentityid) 
   
) ,
salesperson3 as (
  (select salesperson3 from sales.salesperson salesperson3 ) 
) ,
join_cte6 as (
  select salesperson2, employee1, person1, businessentity1, emailaddress1, salesperson3
  from join_cte7
  join salesperson3
  on ((emailaddress1).businessentityid = (salesperson3).businessentityid) 
   
) ,
join_cte0 as (
  select salesperson0, employee0, person0, businessentity0, emailaddress0, salesperson1, salesperson2, employee1, person1, businessentity1, emailaddress1, salesperson3
  from join_cte1
  join join_cte6
  on ((emailaddress0).businessentityid = (emailaddress1).businessentityid) 
   
) 
select (salesperson0)."businessentityid",(salesperson0)."territoryid",(salesperson0)."salesquota",(salesperson0)."bonus",(salesperson0)."commissionpct",(salesperson0)."salesytd",(salesperson0)."saleslastyear",(salesperson0)."rowguid",(salesperson0)."modifieddate"::text,(employee0)."businessentityid",(employee0)."nationalidnumber",(employee0)."loginid",(employee0)."jobtitle",(employee0)."birthdate"::text,(employee0)."maritalstatus",(employee0)."gender",(employee0)."hiredate"::text,(employee0)."salariedflag",(employee0)."vacationhours",(employee0)."sickleavehours",(employee0)."currentflag",(employee0)."rowguid",(employee0)."modifieddate"::text,(employee0)."organizationnode",(person0)."businessentityid",(person0)."persontype",(person0)."namestyle",(person0)."title",(person0)."firstname",(person0)."middlename",(person0)."lastname",(person0)."suffix",(person0)."emailpromotion",(person0)."additionalcontactinfo",(person0)."demographics",(person0)."rowguid",(person0)."modifieddate"::text,(businessentity0)."businessentityid",(businessentity0)."rowguid",(businessentity0)."modifieddate"::text,(emailaddress0)."businessentityid",(emailaddress0)."emailaddressid",(emailaddress0)."emailaddress",(emailaddress0)."rowguid",(emailaddress0)."modifieddate"::text,(salesperson1)."businessentityid",(salesperson1)."territoryid",(salesperson1)."salesquota",(salesperson1)."bonus",(salesperson1)."commissionpct",(salesperson1)."salesytd",(salesperson1)."saleslastyear",(salesperson1)."rowguid",(salesperson1)."modifieddate"::text,(salesperson2)."businessentityid",(salesperson2)."territoryid",(salesperson2)."salesquota",(salesperson2)."bonus",(salesperson2)."commissionpct",(salesperson2)."salesytd",(salesperson2)."saleslastyear",(salesperson2)."rowguid",(salesperson2)."modifieddate"::text,(employee1)."businessentityid",(employee1)."nationalidnumber",(employee1)."loginid",(employee1)."jobtitle",(employee1)."birthdate"::text,(employee1)."maritalstatus",(employee1)."gender",(employee1)."hiredate"::text,(employee1)."salariedflag",(employee1)."vacationhours",(employee1)."sickleavehours",(employee1)."currentflag",(employee1)."rowguid",(employee1)."modifieddate"::text,(employee1)."organizationnode",(person1)."businessentityid",(person1)."persontype",(person1)."namestyle",(person1)."title",(person1)."firstname",(person1)."middlename",(person1)."lastname",(person1)."suffix",(person1)."emailpromotion",(person1)."additionalcontactinfo",(person1)."demographics",(person1)."rowguid",(person1)."modifieddate"::text,(businessentity1)."businessentityid",(businessentity1)."rowguid",(businessentity1)."modifieddate"::text,(emailaddress1)."businessentityid",(emailaddress1)."emailaddressid",(emailaddress1)."emailaddress",(emailaddress1)."rowguid",(emailaddress1)."modifieddate"::text,(salesperson3)."businessentityid",(salesperson3)."territoryid",(salesperson3)."salesquota",(salesperson3)."bonus",(salesperson3)."commissionpct",(salesperson3)."salesytd",(salesperson3)."saleslastyear",(salesperson3)."rowguid",(salesperson3)."modifieddate"::text from join_cte0 