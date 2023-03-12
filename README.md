# Welcome!

`typo` (for "typed postgres") generates code to 
- safely interact with your Postgres database
- avoid having to write repetitive and error-prone code

(`typo` is however just starting out - the functionality described here does not really work yet. Get involved if you want to change that!)

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
Let's put an example in `${PROJECT_HOME}/sql/customers_get_by_total.sql`

You point the `typo` to that folder, and it'll generate a case class, row mapper, and a repo with sql code

## Other features

Since we have the structure of everything, we might as well generate more code:
- mock repositories based on a `mutable.Map`
- scalacheck instances
- json codecs
- logging statements
- unique query names

### JSON codecs

This has the potential of being insanely useful. This can lead to generic code across all tables, so you can have one API endpoint which can
patch any row in any table (as long as you allow it) in a type-safe manner!

## Library-agnostic

This first prototype of `typo` generates code for the `anorm` database library. 
There is not really much code needed to expand that to any (type class-based) database library!
