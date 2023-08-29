/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package constraint_column_usage

import anorm.SqlStringInterpolation
import java.sql.Connection
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql

object ConstraintColumnUsageViewRepoImpl extends ConstraintColumnUsageViewRepo {
  override def select: SelectBuilder[ConstraintColumnUsageViewFields, ConstraintColumnUsageViewRow] = {
    SelectBuilderSql("information_schema.constraint_column_usage", ConstraintColumnUsageViewFields, ConstraintColumnUsageViewRow.rowParser)
  }
  override def selectAll(implicit c: Connection): List[ConstraintColumnUsageViewRow] = {
    SQL"""select "table_catalog", "table_schema", "table_name", "column_name", "constraint_catalog", "constraint_schema", "constraint_name"
          from information_schema.constraint_column_usage
       """.as(ConstraintColumnUsageViewRow.rowParser(1).*)
  }
}