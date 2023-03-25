package typo

@FunctionalInterface
trait Selector {
  def include(relation: db.RelationName): Boolean

  final def or(other: Selector): Selector = rel => include(rel) || other.include(rel)

  final def and(other: Selector): Selector = rel => include(rel) && other.include(rel)
}

object Selector {
  val Internal = Set("pg_catalog", "information_schema").map(Option.apply)
  val All: Selector = _ => true
  val ExcludePostgresInternal: Selector = rel => !Internal(rel.schema)
  val OnlyPostgresInternal: Selector = rel => Internal(rel.schema)

  def keepNames(names: Set[String]): Selector = rel => names(rel.name)
}
