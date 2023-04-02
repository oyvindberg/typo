package typo
package internal
package metadb

object Enums {
  def apply(pgEnums: List[PgEnum.Row]): List[db.StringEnum] = {
    pgEnums
      .groupBy(_.name)
      .map { case (relName, values) =>
        db.StringEnum(relName, values.sortBy(_.enum_sort_order).map(_.enum_value))
      }
      .toList
  }
}
