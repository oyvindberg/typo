---
title: Testing with stubs
---

It can be incredibly tiring to write tests for the database layer.

Often you want to split you code in pure/effectful code and just test the pure parts,
but sometimes you want to observe mutations in the database as well.

Sometimes spinning up a real database for this is the right answer, sometimes it's not.
It is always slow, however, so it's way easier to get a fast test suite if you're not doing it.

The argument for the approach taken by typo is that since the interaction between Scala
and Postgres is guaranteed to be correct*, it is less important to back your tests with a real database.

This leads us to stubs (called mocks in the generated code), implementations of the repository
interfaces backed by a mutable `Map`. This can be generated for all tables with a primary key.

## DSL

Notable, these mocks work with the [dsl](what-is/dsl.md), which lets you describe semi-complex joins, updates, where predicates,
string operations and so on in your code, and test it in-memory!

### *Note

typo guarantees schema correctness, but you can still break constraints.
Or your tests need more advanced postgres functionality.

Stubs are obviously not a full replacement, but if they can be used for some non-zero percentage
of your tests it's still very beneficial!

## An example of a generated `RepoMock`:

```scala mdoc:silent
import java.sql.Connection
import typo.generated.pg_catalog.pg_namespace.*

class PgNamespaceRepoMock(map: scala.collection.mutable.Map[PgNamespaceId, PgNamespaceRow] = scala.collection.mutable.Map.empty) extends PgNamespaceRepo {
  override def delete(oid: PgNamespaceId)(implicit c: Connection): Boolean = {
    map.remove(oid).isDefined
  }

  override def insert(unsaved: PgNamespaceRow)(implicit c: Connection): PgNamespaceRow = {
    if (map.contains(unsaved.oid))
      sys.error(s"id ${unsaved.oid} already exists")
    else
      map.put(unsaved.oid, unsaved)
    unsaved
  }

  override def selectAll(implicit c: Connection): List[PgNamespaceRow] = {
    map.values.toList
  }

  override def selectById(oid: PgNamespaceId)(implicit c: Connection): Option[PgNamespaceRow] = {
    map.get(oid)
  }

  override def selectByIds(oids: Array[PgNamespaceId])(implicit c: Connection): List[PgNamespaceRow] = {
    oids.flatMap(map.get).toList
  }

  override def selectByUnique(nspname: String)(implicit c: Connection): Option[PgNamespaceRow] = {
    map.values.find(v => nspname == v.nspname)
  }

  override def update(row: PgNamespaceRow)(implicit c: Connection): Boolean = {
    map.get(row.oid) match {
      case Some(`row`) => false
      case Some(_) =>
        map.put(row.oid, row)
        true
      case None => false
    }
  }

  override def upsert(unsaved: PgNamespaceRow)(implicit c: Connection): PgNamespaceRow = {
    map.put(unsaved.oid, unsaved)
    unsaved
  }
}
```

