# Welcome!

`typo` (for "typed postgres") generates code to 
- safely interact with your Postgres database
- avoid having to write repetitive and error-prone code

(`typo` is however just starting out - the functionality described here is not fully developed and tested. Get involved if you want to change that!)

## Build safer systems

We use the compiler to verify our programs. All is great, until you need to interact with the outside world, which can be messy.

One of the best tools we have available to help the compiler verify our interactions with the outside world is contract-driven development.

Say our API is described by an OpenApi definition.
If you use that to generate code for the HTTP layer you guarantee that you implement it correctly because otherwise the compiler can help you!

`typo` intends to provide the same safety in the database layer. 
It does this by generating correct code for all your tables and views based on postgres metadata tables.

## Write less boilerplate code

No more redefining basic crud-operations, writing fragile row mappers, tolerating quirky string interpolation functionality and so on.

## SQL is king!

So what is described above gives you a perfect interface to perform CUD operations. 
However, this is not how we normally (R)ead data!
Typically, you'll join some tables based on some conditions and extract some data. All normal and fine, but how can `typo` help here?

Let's accept that SQL is king in this domain. Let's write SQL in SQL files! 
Let's put an example in [`${PROJECT_HOME}/sql/person_detail.sql`](adventureworks_sql/person_detail.sql) with this content:

You point the `typo` to that folder, and it'll generate a case class, row mapper, and a repo with sql code

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

You'll then get a row class like this. It'll also generate code and references to all the tables and views you use in your queries.

```scala
case class PersonDetailRow(
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
and you will get a repo with a method like this:
```scala
trait PersonDetailRepo {
  def apply(businessentityid: /* nullability unknown */ Option[Int])(implicit c: Connection): List[PersonDetailRow]
}
```

along with an implementation. Note that postgres is not able to decide nullability for parameters. You can override that (and any other type) through customization.


## Other features

Since we have the structure of everything, we might as well generate more code:
- mock repositories based on a `mutable.Map`
- scalacheck instances
- json codecs
- logging statements
- unique query names

### JSON codecs
Of that list, only json codecs are implemented.
This has the potential of being insanely useful. This can lead to generic code across all tables, so you can have one API endpoint which can
patch any row in any table (as long as you allow it) in a type-safe manner!

## Library-agnostic

This first prototype of `typo` generates code for the `anorm` database library. 
There is not really much code needed to expand that to any (type class-based) database library!

A port to doobie is underway, probably with skunk next.
