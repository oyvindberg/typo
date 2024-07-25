package typo
package internal
package metadb

import typo.generated.custom.enums.EnumsSqlRow

object Enums {
  def apply(pgEnums: List[EnumsSqlRow]): List[db.StringEnum] = {
    pgEnums
      .groupBy(row => db.RelationName(row.enumSchema, row.enumName))
      .map { case (relName, values: Seq[EnumsSqlRow]) =>
        db.StringEnum(relName, values.sortBy(_.enumSortOrder).map(_.enumValue))
      }
      .toList
  }
}
