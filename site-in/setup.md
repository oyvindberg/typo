---
title: Getting started
---

## Database library
Note that you're supposed to bring your own database library. You choose either anorm or doobie in `Options` (see below),
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

## example script

The Typo code generator is shipped as a library, the easiest way to get started is something like this scala-cli script:

put it in `gen-db.sc` and run `scala-cli gen-db.sc`

```scala mdoc:silent
//
// remember to give the project a github star if you like it <3
//
//> using dep "com.olvind.typo::typo:@VERSION@"
//> using scala "3.3.0"

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

// where Typo will look for sql files
val scriptsFolder = location.resolve("sql")

generateFromDb(options, scriptsPaths = List(scriptsFolder))
  .overwriteFolder(folder = targetDir)

// add changed files to git, so you can keep them under control
//scala.sys.process.Process(List("git", "add", targetDir.toString)).!!
```

## sbt plugin

It's natural to think an sbt plugin would be a good match for Typo. This will likely be added in the future.
