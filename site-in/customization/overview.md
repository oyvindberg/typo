---
title: Customizing Typo
---

All customizations are done through the `typo.Options` object passed to typo:

```scala mdoc:silent
import typo.*

val options = Options(
  pkg = "org.foo",
  jsonLibs = List(JsonLibName.PlayJson),
  dbLib = Some(DbLibName.Anorm),
  // .. more options here
)

```

## All options

| Field Name               | Effect                                                                                                                                                                                                        |
|--------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `pkg`                    | Specifies the package name for the generated code.                                                                                                                                                            |
| `dbLib`                  | Defines the database library to use for code generation (anorm, doobie, zio-jdbc or `None`).                                                                                                                  |
| `jsonLibs`               | JSON libraries to generate codecs for (default is empty).                                                                                                                                                     |
| `silentBanner`           | Controls whether to suppress the Typo banner while running Typo (default is `false`).                                                                                                                         |
| `fileHeader`             | Sets the header text that appears at the top of generated files.                                                                                                                                              |
| `naming`                 | Configures naming conventions for generated code. See section below                                                                                                                                           |
| `typeOverride`           | Defines type overrides for specific database types See section below.                                                                                                                                         |
| `nullabilityOverride`    | Defines nullability overrides for specific columns See section below.                                                                                                                                         |
| `generateMockRepos`      | Specifies which repositories to generate mock versions for (default is all).                                                                                                                                  |
| `enableFieldValue`       | Controls whether to enable `FieldValue` code generation for specific repositories (default is disabled).                                                                                                      |
| `enableStreamingInserts` | Controls whether to enable [streaming inserts](other-features/streaming-inserts.md)                                                                                                                           |
| `enableTestInserts`      | Controls whether to enable [test inserts](other-features/testing-with-random-values.md) for specific repositories (default is none).                                                                          |
| `enablePrimaryKeyType`   | Controls whether to enable [primary key types](type-safety/id-types.md) for specific repositories (default is all).                                                                                           |
| `readonlyRepo`           | Specifies whether to generate read-only repositories for specific repositories. Useful when you're working on a part of the system where you only consume certain tables. (default is `false` - all mutable). |
| `enableDsl`              | Enables the [SQL DSL](what-is/dsl.md) for code generation (default is `false`).                                                                                                                               |
| `keepDependencies`       | Specifies whether to generate [table dependencies](type-safety/type-flow.md) in generated code even if you didn't select them (default is `false`).                                                           |

## Development options

| Field Name        | Effect                                                                                                  |
|-------------------|---------------------------------------------------------------------------------------------------------|
| `debugTypes`      | Enables debug mode for types during code generation (default is `false`).                               |
| `inlineImplicits` | Controls whether to inline implicits for generated code (default is `true`).                            |
| `logger`          | Specifies the logger to use for logging during code generation (default is console logging). Useful for |



