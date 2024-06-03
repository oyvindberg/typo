---
title: Getting started
---

## Database library
Note that you're supposed to [bring your own database library](other-features/flexible.md). You choose either anorm, doobie or zio-jdbc in `Options` (see below),
and you need to have that added to your build as well.

## Getting started with DSL

If you want to use the [SQL DSL](what-is/dsl.md), you enable it by [customizing](customization/overview.md) Typo by setting `enableDsl = true`.

```scala mdoc:silent
import typo.Options
Options(
  pkg = "mypkg",
  dbLib = None,
  enableDsl = true
)
```

You also need to add a dependency to your build in that case, which varies by database library:
- for doobie: https://mvnrepository.com/artifact/com.olvind.typo/typo-dsl-doobie
- for anorm: https://mvnrepository.com/artifact/com.olvind.typo/typo-dsl-anorm
- for zio-jdbc: https://mvnrepository.com/artifact/com.olvind.typo/typo-dsl-zio-jdbc

## example script

The Typo code generator is shipped as a library, the easiest way to get started is something like this scala-cli script:

put it in `gen-db.sc` and run `scala-cli gen-db.sc`

```scala mdoc:silent
//
// remember to give the project a github star if you like it <3
//
//> using dep "com.olvind.typo::typo:@VERSION@"
//> using scala "3.4.0"

import typo.*

// adapt to your instance and credentials
implicit val c: java.sql.Connection =
  java.sql.DriverManager.getConnection("jdbc:postgresql://localhost:6432/postgres?user=postgres&password=password")

val options = Options(
  // customize package name for generated code
  pkg = "org.foo.generated",
  // pick your database library
  dbLib = Some(DbLibName.Anorm),
  jsonLibs = Nil,
  // many more possibilities for customization here
  // ...
)

// current folder, where you run the script from
val location = java.nio.file.Path.of(sys.props("user.dir"))

// destination folder. All files in this dir will be overwritten!
val targetDir = location.resolve("myproject/src/main/scala/org/foo/generated")
val testTargetDir = location.resolve("myproject/src/test/scala/org/foo/generated")

// where Typo will look for sql files
val scriptsFolder = location.resolve("sql")

// you can use this to customize which relations you want to generate code for, see below
val selector = Selector.ExcludePostgresInternal

generateFromDb(
  options, 
  targetFolder = targetDir,
  testTargetFolder = Some(testTargetDir),
  selector = selector, 
  scriptsPaths = List(scriptsFolder)
).overwriteFolder()

// add changed files to git, so you can keep them under control
//scala.sys.process.Process(List("git", "add", targetDir.toString)).!!
```

## Compiling the generated code

For **Scala 2** you need to set add `-Xsource:3` to `scalacOptions`, with a recent version of Scala 2.13.
For **Scala 3** you'll need 3.4.0 or later.

## `selector`
You can customize which relations you generate code for, see [customize selected relations](customization/customize-selected-relations.md)

## `ProjectGraph`
If you want to split the generated code across multiple projects in your build, have a look at [Generate code into multiple projects](other-features/generate-into-multiple-projects.md)

## sbt plugin
It's natural to think an sbt plugin would be a good match for Typo. This will likely be added in the future.
