---
title: Intro / Motivation
---

typo is a source code generator which uses postgres schema definitions and your sql definitions as sources of truth. 

With a razor-sharp focus on type-safety and DX, hopefully you'll find the experience very pleasant.

## Motivation

### Build safer systems

We use the compiler to verify our programs. All is great, until you need to interact with the messy outside world.

One of the best tools we have available to help the compiler verify our interactions with the outside world is contract-driven development.

Say an API is described by an OpenApi definition.
If you use that to generate code for the HTTP layer, you guarantee that you implement it correctly. Because if you don't, the compiler will helpfully guide you!

typo intends to provide the same safety in the database layer.
It does this by generating correct code for all your tables, views and queries based on postgres metadata tables.

### Existing developer flow for SQL <-> JVM is terrible.

Typical current state of affairs:

- 1) you write some sql
- 2) intellij may not inject sql support, connection and/or schema, so you're telling it what to do again
- 3) you interpolate in some parameters, but the compiler yells at you for not having defined the right typeclass instances. Error messages are typically unhelpful
- 4) you manually map a sequence of either column names or indices to a name in a case class
- 5) you manually map a sequence of either column names or indices to a type in a case class. New typeclass instances to implement.
- 6) the compiler checks none of the above, so now you're writing tests
- 7) tests are tedious to write

and then the worst: **Whenever you refactor you're stuck fighting with points 1-7, for all future.**

You also typically write the same essential boilerplate for ~all tables.

typo provides a better workflow for points 2-7, and frees you from writing boilerplate. Keep reading to see more

### Types of interaction with the database

Interactions with the database can be grouped into four groups.
The distinction is useful when determining which typo feature should be used.

- create, update, delete. pure crud operations. In typo these are covered by [repository methods](what-is/relations.md) and [SQL DSL](what-is/dsl.md)
- "simple" reads from relations. typically with joins, but without aggregations or selections. In typo these are covered by the [SQL DSL](what-is/dsl.md)
- "complex reads" — aggregations, window functions — you name it! in typo these interactions are covered by [writing sql files](what-is/sql-is-king.md)
- dynamic queries. 
These are typically rare and expensive both to implement and run. typo does *not* currently solve this use case well, so you can fall back to your [existing database library](other-features/flexible.md) and implement this yourself. 
Maybe we can come up with some generated solution for some use cases in the future! 