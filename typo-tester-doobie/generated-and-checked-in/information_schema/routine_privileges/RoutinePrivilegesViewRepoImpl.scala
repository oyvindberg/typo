/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package routine_privileges

import doobie.free.connection.ConnectionIO
import doobie.syntax.string.toSqlInterpolator
import fs2.Stream
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql

object RoutinePrivilegesViewRepoImpl extends RoutinePrivilegesViewRepo {
  override def select: SelectBuilder[RoutinePrivilegesViewFields, RoutinePrivilegesViewRow] = {
    SelectBuilderSql("information_schema.routine_privileges", RoutinePrivilegesViewFields, RoutinePrivilegesViewRow.read)
  }
  override def selectAll: Stream[ConnectionIO, RoutinePrivilegesViewRow] = {
    sql"""select grantor, grantee, specific_catalog, specific_schema, "specific_name", "routine_catalog", "routine_schema", "routine_name", privilege_type, is_grantable from information_schema.routine_privileges""".query(RoutinePrivilegesViewRow.read).stream
  }
}