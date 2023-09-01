---
title: User-selected types
---

If you're integrating the generated code into an existing codebase it may be beneficial to reuse existing types, in particular ID types.

You can [customize](../customization.md) typo to override types.

## Note
You need to implement a bunch of typeclass instances for the types you use.
The compiler will guide you, but it's basically everything which is needed to use the type with your database and json library.

You can likely copy/paste from the generated id types and adapt them. 

### Choose column types from relations

```scala mdoc:silent
import typo.{Options, TypeOverride}

val rewriteColumnTypes = TypeOverride.relation {
  case ("schema.table", "column") => "org.foo.ColumnId"
}

Options(
  pkg = "org.foo",
  dbLib = None,
  typeOverride = rewriteColumnTypes
)
```

### Explicit version

The version above is "simplified", in that is takes a descriptive type and explodes it into strings. 
You may prefer the version below which is more cumbersome but more structured:

```scala mdoc:silent
import typo.db.RelationName

val rewriteMore = TypeOverride.of { 
  case (RelationName(Some(schema), tableName), colName) if schema.contains("foo") && colName.value.startsWith("foo") => "org.foo.Bar" 
}
```

### Composing multiple column overrides:

```scala mdoc:silent
rewriteColumnTypes.orElse(rewriteMore)
```

## Choose nullability and types for parameters and columns in sql files

This is done inline in the SQL you write:

```sql
-- foo is nullable, by default pg cannot infer nullability
select 1 where :foo = 1;
-- foo is explicitly set to nullable
select 1 where :"foo?" = 1;
-- foo is explicitly set to not nullable
select 1 where :"foo!" = 1;
-- foo is explicitly set to not nullable and `Long`
select 1 where :"foo:scala.Long!" = 1;
```

also works for columns, naturally:
```sql
select 1 as "foo:scala.Double?" -- rewrite `Int` to `Option[Double]`
```

and if you mention a parameter multiple times you only need to customize it at first mention
```sql
select 1 where :"foo!" = 1 and :foo != 2;
```
