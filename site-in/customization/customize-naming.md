---
title: Customize naming
---

You provide a `typo.Naming` instance in `typo.Options` when running typo.
This is responsible for computing all scala names based on names from PostgreSQL.

```scala mdoc
import typo.*

val naming = new Naming(sc.QIdent("org.foo")) {
  override def enumName(name: db.RelationName): sc.QIdent = tpe(name, suffix = "ENUM")
}

sc.renderTree(naming.enumName(db.RelationName(Some("schema"), "foo")))
```

### Customize field names

```scala mdoc
// say you you have some weird naming standard in your schemas, for instance `id_table` instead of `table_id`. 
// This is how it can be prettified in the generated scala code
val fixIdPattern = new Naming(sc.QIdent("org.foo")) {
  override def field(name: db.ColName): sc.Ident = {
    val newName = if (name.value.startsWith("id_")) db.ColName(name.value.drop(3) + "_id") else name
    super.field(newName)
  }
}

sc.renderTree(fixIdPattern.field(db.ColName("id_flaff")))
```

