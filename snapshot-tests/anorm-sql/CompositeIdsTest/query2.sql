select "businessentityid","emailaddressid","emailaddress","rowguid","modifieddate"::text from person.emailaddress emailaddress0
 where (emailaddress0.businessentityid, emailaddress0.emailaddressid) in (select unnest(?::int4[]), unnest(?::int4[]))
