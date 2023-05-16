# Welcome!

`typo` (for "typed postgres") generates code to 
- safely interact with your Postgres database
- avoid having to write repetitive and error-prone code

## Build safer systems

We use the compiler to verify our programs. All is great, until you need to interact with the messy outside world.

One of the best tools we have available to help the compiler verify our interactions with the outside world is contract-driven development.

Say an API is described by an OpenApi definition.
If you use that to generate code for the HTTP layer you guarantee that you implement it correctly because otherwise the compiler can help you!

`typo` intends to provide the same safety in the database layer. 
It does this by generating correct code for all your tables, views and queries based on postgres metadata tables.

## Write less boilerplate code

No more redefining basic crud-operations, writing fragile row mappers, tolerating quirky string interpolation functionality and so on.

## SQL is king!

So what is described above gives you a perfect interface to perform CUD operations. 
However, this is not how we normally (R)ead data!
Typically, you'll join some tables based on some conditions and extract some data. All normal and fine, but how can `typo` help here?

Let's accept that SQL is king in this domain. Let's write SQL in SQL files! 
Let's take an example, [`${PROJECT_HOME}/sql/person_detail.sql`](adventureworks_sql/person_detail.sql).

You point the `typo` at the `sql` folder, it'll discover all the SQL files and generate a case classes, row mappers, and everything else you need.

Say it looks like this:
```sql
SELECT s.businessentityid,
       p.title,
       p.firstname,
       p.middlename,
       p.lastname,
       e.jobtitle,
       a.addressline1,
       a.city,
       a.postalcode
FROM sales.salesperson s
         JOIN humanresources.employee e ON e.businessentityid = s.businessentityid
         JOIN person.person p ON p.businessentityid = s.businessentityid
         JOIN person.businessentityaddress bea ON bea.businessentityid = s.businessentityid
         JOIN person.address a ON a.addressid = bea.addressid
where s.businessentityid = :businessentityid
```
(note that `:businessentityid` is a named parameter. `typo` will generate a method with a parameter of the correct type)

You'll then get a row class like this: 

```scala
case class PersonDetailSqlRow(
  /** Points to [[sales.salesperson.SalespersonRow.businessentityid]] */
  businessentityid: BusinessentityId,
  /** Points to [[person.person.PersonRow.title]] */
  title: Option[String],
  /** Points to [[person.person.PersonRow.firstname]] */
  firstname: Name,
  /** Points to [[person.person.PersonRow.middlename]] */
  middlename: Option[Name],
  /** Points to [[person.person.PersonRow.lastname]] */
  lastname: Name,
  /** Points to [[humanresources.employee.EmployeeRow.jobtitle]] */
  jobtitle: String,
  /** Points to [[person.address.AddressRow.addressline1]] */
  addressline1: String,
  /** Points to [[person.address.AddressRow.city]] */
  city: String,
  /** Points to [[person.address.AddressRow.postalcode]] */
  postalcode: String
)
```
and a repo with a method like this, for doobie:
```scala
trait PersonDetailSqlRepo {
  def apply(businessentityid: /* nullability unknown */ Option[Int]): Stream[ConnectionIO, PersonDetailSqlRow]
}
```
or this, for anorm:
```scala
trait PersonDetailSqlRepo {
  def apply(businessentityid: /* nullability unknown */ Option[Int])(implicit c: Connection): List[PersonDetailSqlRow]
}

```

You also get an implementation of course. 

Note that postgres is not able to decide nullability for parameters. You can override that (and any other type) through customization.

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
```scala
case class AddressRow(
  /** Primary key for Address records. */
  addressid: AddressId,
  /** First street address line. */
  addressline1: String,
  /** Second street address line. */
  addressline2: Option[String],
  /** Name of the city. */
  city: String,
  /** Unique identification number for the state or province. Foreign key to StateProvince table.
      Points to [[stateprovince.StateprovinceRow.stateprovinceid]] */
  stateprovinceid: StateprovinceId,
  /** Postal code for the street address. */
  postalcode: String,
  /** Latitude and longitude of this address. */
  spatiallocation: Option[Array[Byte]],
  rowguid: UUID,
  modifieddate: LocalDateTime
)

```
and a repo like this:
```scala
trait AddressRepo {
  def delete(addressid: AddressId): ConnectionIO[Boolean]
  def insert(unsaved: AddressRow): ConnectionIO[AddressRow]
  def insert(unsaved: AddressRowUnsaved): ConnectionIO[AddressRow]
  def selectAll: Stream[ConnectionIO, AddressRow]
  def selectByFieldValues(fieldValues: List[AddressFieldOrIdValue[_]]): Stream[ConnectionIO, AddressRow]
  def selectById(addressid: AddressId): ConnectionIO[Option[AddressRow]]
  def selectByIds(addressids: Array[AddressId]): Stream[ConnectionIO, AddressRow]
  def update(row: AddressRow): ConnectionIO[Boolean]
  def updateFieldValues(addressid: AddressId, fieldValues: List[AddressFieldValue[_]]): ConnectionIO[Boolean]
  def upsert(unsaved: AddressRow): ConnectionIO[AddressRow]
}
```
(For reference, the implementation is [here](./typo-tester-doobie/generated-and-checked-in/person/address/AddressRepoImpl.scala))

Since the table has auto-increment ID and default values, you will typically use this structure to insert new rows
```scala
/** This class corresponds to a row in table `person.address` which has not been persisted yet */
case class AddressRowUnsaved(
  /** First street address line. */
  addressline1: String,
  /** Second street address line. */
  addressline2: Option[String],
  /** Name of the city. */
  city: String,
  /** Unique identification number for the state or province. Foreign key to StateProvince table.
      Points to [[stateprovince.StateprovinceRow.stateprovinceid]] */
  stateprovinceid: StateprovinceId,
  /** Postal code for the street address. */
  postalcode: String,
  /** Latitude and longitude of this address. */
  spatiallocation: Option[Array[Byte]],
  /** Default: nextval('person.address_addressid_seq'::regclass)
      Primary key for Address records. */
  addressid: Defaulted[AddressId] = Defaulted.UseDefault,
  /** Default: uuid_generate_v1() */
  rowguid: Defaulted[UUID] = Defaulted.UseDefault,
  /** Default: now() */
  modifieddate: Defaulted[LocalDateTime] = Defaulted.UseDefault
)
```

## Other features

Since we have the structure of everything, we might as well generate more and more code:
- mock repositories based on a `mutable.Map`
- scalacheck instances
- json codecs
- logging statements
- unique query names

Of that list, mock repositories and json codecs are implemented.

### Mock repositories
You can wire these in to run tests without touching the database:
```scala
class AddressRepoMock(toRow: Function1[AddressRowUnsaved, AddressRow],
                      map: scala.collection.mutable.Map[AddressId, AddressRow] = scala.collection.mutable.Map.empty) extends AddressRepo {
  override def delete(addressid: AddressId): ConnectionIO[Boolean] = {
    delay(map.remove(addressid).isDefined)
  }
  // ...
}
```
Again since there are auto-increment ID's and defaulted values, you'll need to specify how to fill them in.
If you have a more table that won't be needed.
Otherwise everything is taken care of.

### JSON codecs
This has the potential of being insanely useful. This can lead to generic code across all tables, so you can have one API endpoint which can
patch any row in any table (as long as you allow it) in a type-safe manner!

Much experimentation is needed on that front though. for now you can get json codecs for `play-json`, with circe upcoming.

## Library-agnostic

This first prototype of `typo` generates code for the `anorm` and `doobie` database libraries.

Next planned database library is `skunk`.

## Getting started
There is no sbt plugin yet, so you'll have to run the code generator manually.

This scala-cli script should get you started:
```scala
//> using dep "com.olvind.typo::typo:0.0.3"
//> using scala "3.2.2"
//> using jvm "graalvm-java17:22.3.1"

import typo.*
import typo.internal.FileSync

import java.nio.file.Path
import java.sql.{Connection, DriverManager}
import java.util

// pick tables and views you code generated for here.
val selector: Selector = relation =>
  relation.schema.contains("myschema") ||
    relation.value == "other_schema.table"

// here you can override types for specific columns
// note that these will propagate down through foreign keys and column dependencies
val rewriteColumnTypes = TypeOverride.relation {
  case ("myschema.table", "id") => "com.mytable.Id"
}

// override types for columns read from sql files
val rewriteColumnTypesFromSqlFile = TypeOverride.sqlFile {
  case (_, "my_column") => "com.foo.MyType"
}

// if postgres gets the nullability wrong in an sql file (it happens, unfortunately), you can override it here
val overrideNullabilityFromSqlFiles = NullabilityOverride.sqlFile {
  case (RelPath(List("sql", "myscript")), "myparam") => Nullability.Nullable
}

// setup a connection to your database
given Connection = {
  val url = "jdbc:postgresql://localhost/public"
  val props = new util.Properties
  props.setProperty("user", "postgres")
  props.setProperty("password", "postgres")
  DriverManager.getConnection(url, props)
}

val options = Options(
  pkg = "destination.package",
  JsonLibName.None,
  DbLibName.Anorm,
  naming = pkg =>
    new Naming(pkg) {
      // you can override any part of naming here. add/remove suffixes, change casing, etc.
    },
  typeOverride = rewriteColumnTypes.orElse(rewriteColumnTypesFromSqlFile),
  nullabilityOverride = NullabilityOverride.sqlFileParam { case _ => Nullability.NoNulls }.orElse(overrideNullabilityFromSqlFiles)
)

val cwd = Path.of(sys.props("user.dir"))

// all files in this dir will be overwritten!
val targetDir = cwd.resolve("mymodule/src/main/scala/generated")

// run conversion
typo
  .fromDbAndScripts(
    options,
    scriptsPath = cwd.resolve("sql"),
    selector
  )
  .overwriteFolder(folder = targetDir, soft = true) // soft = true plays better with intellij. typo will overwrite only changes files, so there is less to reindex

// add to git
import scala.sys.process.*
List("git", "add", targetDir.toString).!!

```