---
title: Generated code for relations
---

typo generates repositories for all tables and views (or a subset, if you prefer)  

## Tables

Given a table like this:
```sql
create table address
(
    addressid       serial
        constraint "PK_Address_AddressID" primary key,
    addressline1    varchar(60)                          not null,
    addressline2    varchar(60),
    city            varchar(30)                          not null,
    stateprovinceid integer                              not null 
        constraint "FK_Address_StateProvince_StateProvinceID" references stateprovince,
    postalcode      varchar(15)                          not null,
    spatiallocation bytea,
    rowguid         uuid      default uuid_generate_v1() not null,
    modifieddate    timestamp default now()              not null
);

comment on table address is 'Street address information for customers, employees, and vendors.';
comment on column address.addressid is 'Primary key for Address records.';
comment on column address.addressline1 is 'First street address line.';
comment on column address.addressline2 is 'Second street address line.';
comment on column address.city is 'Name of the city.';
comment on column address.stateprovinceid is 'Unique identification number for the state or province. Foreign key to StateProvince table.';
comment on column address.postalcode is 'Postal code for the street address.';
comment on column address.spatiallocation is 'Latitude and longitude of this address.';
```

You'll get a row class like this:
```scala mdoc

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.person.address.AddressId
import adventureworks.person.stateprovince.StateprovinceId
import java.util.UUID

case class AddressRow(
  /** Primary key for Address records. */
  addressid: AddressId,
  /** First street address line. */
  addressline1: /* max 60 chars */ String,
  /** Second street address line. */
  addressline2: Option[/* max 60 chars */ String],
  /** Name of the city. */
  city: /* max 30 chars */ String,
  /** Unique identification number for the state or province. Foreign key to StateProvince table.
      Points to [[stateprovince.StateprovinceRow.stateprovinceid]] */
  stateprovinceid: StateprovinceId,
  /** Postal code for the street address. */
  postalcode: /* max 15 chars */ String,
  /** Latitude and longitude of this address. */
  spatiallocation: Option[Array[Byte]],
  rowguid: UUID,
  modifieddate: TypoLocalDateTime
)
```

and a repo like this:
```scala mdoc
import adventureworks.person.address.{AddressFields, AddressRow}
import java.sql.Connection
import typo.dsl.{DeleteBuilder, SelectBuilder, UpdateBuilder}

trait AddressRepo {
  def delete(addressid: AddressId)(implicit c: Connection): Boolean
  def delete: DeleteBuilder[AddressFields, AddressRow]
  def insert(unsaved: AddressRow)(implicit c: Connection): AddressRow
  def insert(unsaved: AddressRowUnsaved)(implicit c: Connection): AddressRow
  def select: SelectBuilder[AddressFields, AddressRow]
  def selectAll(implicit c: Connection): List[AddressRow]
  def selectById(addressid: AddressId)(implicit c: Connection): Option[AddressRow]
  def selectByIds(addressids: Array[AddressId])(implicit c: Connection): List[AddressRow]
  def update(row: AddressRow)(implicit c: Connection): Boolean
  def update: UpdateBuilder[AddressFields, AddressRow]
  def upsert(unsaved: AddressRow)(implicit c: Connection): AddressRow
}
```

Since this particular table has auto-increment ID and default values, you will typically use this structure to insert new rows
```scala mdoc
import adventureworks.customtypes.Defaulted

/** This class corresponds to a row in table `person.address` which has not been persisted yet */
case class AddressRowUnsaved(
  /** First street address line. */
  addressline1: /* max 60 chars */ String,
  /** Second street address line. */
  addressline2: Option[/* max 60 chars */ String],
  /** Name of the city. */
  city: /* max 30 chars */ String,
  /** Unique identification number for the state or province. Foreign key to StateProvince table.
      Points to [[stateprovince.StateprovinceRow.stateprovinceid]] */
  stateprovinceid: StateprovinceId,
  /** Postal code for the street address. */
  postalcode: /* max 15 chars */ String,
  /** Latitude and longitude of this address. */
  spatiallocation: Option[Array[Byte]],
  /** Default: nextval('person.address_addressid_seq'::regclass)
      Primary key for Address records. */
  addressid: Defaulted[AddressId] = Defaulted.UseDefault,
  /** Default: uuid_generate_v1() */
  rowguid: Defaulted[UUID] = Defaulted.UseDefault,
  /** Default: now() */
  modifieddate: Defaulted[TypoLocalDateTime] = Defaulted.UseDefault
) 
```

## Views

Less code is generated for views. For now this is what it looks like:

Given the following view:
```sql
create view vemployee
            (businessentityid, title, firstname, middlename, lastname, suffix, jobtitle, phonenumber, phonenumbertype,
             emailaddress, emailpromotion, addressline1, addressline2, city, stateprovincename, postalcode,
             countryregionname, additionalcontactinfo)
as
SELECT e.businessentityid,
       p.title,
       p.firstname,
       p.middlename,
       p.lastname,
       p.suffix,
       e.jobtitle,
       pp.phonenumber,
       pnt.name AS phonenumbertype,
       ea.emailaddress,
       p.emailpromotion,
       a.addressline1,
       a.addressline2,
       a.city,
       sp.name  AS stateprovincename,
       a.postalcode,
       cr.name  AS countryregionname,
       p.additionalcontactinfo
FROM humanresources.employee e
         JOIN person.person p ON p.businessentityid = e.businessentityid
         JOIN person.businessentityaddress bea ON bea.businessentityid = e.businessentityid
         JOIN person.address a ON a.addressid = bea.addressid
         JOIN person.stateprovince sp ON sp.stateprovinceid = a.stateprovinceid
         JOIN person.countryregion cr ON cr.countryregioncode::text = sp.countryregioncode::text
         LEFT JOIN person.personphone pp ON pp.businessentityid = p.businessentityid
         LEFT JOIN person.phonenumbertype pnt ON pp.phonenumbertypeid = pnt.phonenumbertypeid
         LEFT JOIN person.emailaddress ea ON p.businessentityid = ea.businessentityid;

```

You get the following row type:
```scala mdoc
import adventureworks.customtypes.TypoXml
import adventureworks.person.businessentity.BusinessentityId
import adventureworks.public.Name
import adventureworks.public.Phone

case class VemployeeViewRow(
  /** Points to [[person.person.PersonRow.businessentityid]] */
  businessentityid: BusinessentityId,
  /** Points to [[person.person.PersonRow.title]] */
  title: /* max 8 chars */ String,
  /** Points to [[person.person.PersonRow.firstname]] */
  firstname: Name,
  /** Points to [[person.person.PersonRow.middlename]] */
  middlename: Name,
  /** Points to [[person.person.PersonRow.lastname]] */
  lastname: Name,
  /** Points to [[person.person.PersonRow.suffix]] */
  suffix: /* max 10 chars */ String,
  /** Points to [[employee.EmployeeRow.jobtitle]] */
  jobtitle: /* max 50 chars */ String,
  /** Points to [[person.personphone.PersonphoneRow.phonenumber]] */
  phonenumber: Option[Phone],
  phonenumbertype: Option[Name],
  /** Points to [[person.emailaddress.EmailaddressRow.emailaddress]] */
  emailaddress: Option[/* max 50 chars */ String],
  /** Points to [[person.person.PersonRow.emailpromotion]] */
  emailpromotion: Int,
  /** Points to [[person.address.AddressRow.addressline1]] */
  addressline1: /* max 60 chars */ String,
  /** Points to [[person.address.AddressRow.addressline2]] */
  addressline2: /* max 60 chars */ String,
  /** Points to [[person.address.AddressRow.city]] */
  city: /* max 30 chars */ String,
  stateprovincename: Name,
  /** Points to [[person.address.AddressRow.postalcode]] */
  postalcode: /* max 15 chars */ String,
  countryregionname: Name,
  /** Points to [[person.person.PersonRow.additionalcontactinfo]] */
  additionalcontactinfo: TypoXml
)
```

And this repo:
```scala mdoc
import adventureworks.humanresources.vemployee.{VemployeeViewFields, VemployeeViewRow}

trait VemployeeViewRepo {
  def select: SelectBuilder[VemployeeViewFields, VemployeeViewRow]
  def selectAll(implicit c: Connection): List[VemployeeViewRow]
}
```