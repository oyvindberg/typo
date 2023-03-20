package typo
package metadb

class Enums(
    pgEnums: List[PgEnum.Row]
) {
  lazy val getAsList: List[db.StringEnum] = pgEnums
    .groupBy(_.name)
    .map { case (relName, values) =>
      db.StringEnum(relName, values.sortBy(_.enum_sort_order).map(_.enum_value))
    }
    .toList

  lazy val getAsMap: Map[String, db.StringEnum] = getAsList.map(e => (e.name.name, e)).toMap
}
