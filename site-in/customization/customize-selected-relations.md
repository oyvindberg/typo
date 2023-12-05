---
title: Customize selected relations
---


You typically have many more relations in your database than you want to expose to application code.
Maybe you're generating code for just a part of the system, not the whole thing.

Typo has a mechanism by which you can choose which relations to generate code for.

among the arguments to `generateFromDb` is `selector`, which by default picks all relations except those in the postgres schemas.

```scala
import typo.*

generateFromDb(options, selector = Selector.ExcludePostgresInternal)
```

## `Selector`

You can pick relations by expressing with `Selector` what you want:
```scala mdoc:silent
import typo.*

val personAndPet0 = Selector.fullRelationNames("myschema.person", "myschemapet") // picks exactly these tables
val personAndPet = Selector.relationNames("person", "pet") // picks these regardless of schema
val mySchema = Selector.schemas("myschema") // picks all relations in schema

// heaviest syntax, but most flexible
val custom: Selector = relName => relName.schema.exists(_.contains("foo")) && relName.name.contains("bar")
```

Selectors are also composable:

```scala mdoc:silent
// picks relations which are called `person` or `pet` AND are in the `myschema` schema
personAndPet and mySchema

// picks those who are *both* called `person` or `pet` OR are in the `myschema` schema. 
// This will typically select more relations
personAndPet or mySchema 
```

The and/or names follows boolean logic, and may actually be a bit counter-intuitive in this particular context. Suggestions welcome to improve naming

## Transitive relations

So in Typo we say that relations have dependencies, see [flow typing](type-safety/type-flow.md). 

Say you have some [sql files](what-is/sql-is-king.md) and have chosen some relations, and some of those have dependencies on other relations.
Typo can optionally generate code for these dependencies as well. 

If you want that, you can  [customize](customization/overview.md) Typo and set the `keepDependencies` parameter to `true` to generate code for those dependencies as well. 

`keepDependencies` is set to `false` by default. If it's left at `false`, you'll only see the [primary key types](type-safety/id-types.md) for those relations
