/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package role_routine_grants

import anorm.SqlStringInterpolation
import java.sql.Connection

object RoleRoutineGrantsViewRepoImpl extends RoleRoutineGrantsViewRepo {
  override def selectAll(implicit c: Connection): List[RoleRoutineGrantsViewRow] = {
    SQL"""select grantor, grantee, specific_catalog, specific_schema, "specific_name", "routine_catalog", "routine_schema", "routine_name", privilege_type, is_grantable
          from information_schema.role_routine_grants
       """.as(RoleRoutineGrantsViewRow.rowParser(1).*)
  }
}