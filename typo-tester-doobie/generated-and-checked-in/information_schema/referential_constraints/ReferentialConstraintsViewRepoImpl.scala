/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package information_schema
package referential_constraints

import doobie.free.connection.ConnectionIO
import doobie.syntax.string.toSqlInterpolator
import fs2.Stream

object ReferentialConstraintsViewRepoImpl extends ReferentialConstraintsViewRepo {
  override def selectAll: Stream[ConnectionIO, ReferentialConstraintsViewRow] = {
    sql"""select "constraint_catalog", "constraint_schema", "constraint_name", unique_constraint_catalog, unique_constraint_schema, unique_constraint_name, match_option, update_rule, delete_rule from information_schema.referential_constraints""".query[ReferentialConstraintsViewRow].stream
  }
}