with 
emailaddress0 as (
  (select emailaddress0 from person.emailaddress emailaddress0 where ((emailaddress0).businessentityid, (emailaddress0).emailaddressid) in (select unnest(?::int4[]), unnest(?::int4[])))
)
select (emailaddress0)."businessentityid",(emailaddress0)."emailaddressid",(emailaddress0)."emailaddress",(emailaddress0)."rowguid",(emailaddress0)."modifieddate"::text from emailaddress0