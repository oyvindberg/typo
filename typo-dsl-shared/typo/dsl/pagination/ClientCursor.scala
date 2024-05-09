package typo.dsl.pagination

/** This will typically be JSON encoded and passed to clients.
  *
  * It represents a cursor that can be used to fetch the next page of results.
  *
  * The position is a given row for a set of [[order by]] expressions.
  *
  * The [[ClientCursor]] itself is a glorified [[Map]], with pairs of stringified `order by` expressions and a JSON representation of the corresponding value from the current row
  *
  * The design has a few interesting tradeoffs:
  *   - it leaks database column names to the client, so you may want to obfuscate/encrypt it
  *   - it can be re-used for "similar" queries, not just the exact same query. Fewer `order by` expressions or different ordering of `order by` expressions is fine.
  *   - [[SortOrderRepr]] does not encode ascending/descending or nulls first/last, but you're still anchored to a specific row for a set of orderings. If you want, you can change your query to go
  *     both ways from a given cursor.
  */
case class ClientCursor[E](parts: Map[SortOrderRepr, E])
