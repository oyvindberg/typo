---
title: Customization
---

All customizations are done through the `typo.Options` object passed to typo:

```scala mdoc
import typo.*

val options = Options(
  pkg = "org.foo",
  jsonLibs = List(JsonLibName.PlayJson),
  dbLib = Some(DbLibName.Anorm)
)

```

## Customize naming:

You provide a `typo.Naming` instance in `typo.Options` when running typo.
This is responsible for computing all scala names based on names from postgres.

```scala mdoc
val naming = new Naming(sc.QIdent("org.foo")) {
  override def enumName(name: db.RelationName): sc.QIdent = tpe(name, suffix = "ENUM")
}

sc.renderTree(naming.enumName(db.RelationName(Some("schema"), "foo")))
```

### Customize field names

```scala mdoc
// say database uses `id_table` instead of `table_id`, this is how it can be reversed in scala code
val fixIdPattern = new Naming(sc.QIdent("org.foo")) {
  override def field(name: db.ColName): sc.Ident = {
    val newName = if (name.value.startsWith("id_")) db.ColName(name.value.drop(3) + "_id") else name
    super.field(newName)
  }
}

sc.renderTree(fixIdPattern.field(db.ColName("id_flaff")))
```

