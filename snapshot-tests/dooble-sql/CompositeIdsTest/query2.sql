with 
personemailaddress0 as (
  (select personemailaddress0 from "person"."emailaddress" personemailaddress0 WHERE ((personemailaddress0).businessentityid , (personemailaddress0).emailaddressid ) in (select unnest(?::_int4 ), unnest(?::_int4 ))) 
) 
select (personemailaddress0)."businessentityid",(personemailaddress0)."emailaddressid",(personemailaddress0)."emailaddress",(personemailaddress0)."rowguid",(personemailaddress0)."modifieddate"::text from personemailaddress0 