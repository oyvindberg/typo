/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package vsalespersonsalesbyfiscalyears

import doobie.free.connection.ConnectionIO
import doobie.syntax.string.toSqlInterpolator
import fs2.Stream
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql

class VsalespersonsalesbyfiscalyearsViewRepoImpl extends VsalespersonsalesbyfiscalyearsViewRepo {
  override def select: SelectBuilder[VsalespersonsalesbyfiscalyearsViewFields, VsalespersonsalesbyfiscalyearsViewRow] = {
    SelectBuilderSql("sales.vsalespersonsalesbyfiscalyears", VsalespersonsalesbyfiscalyearsViewFields.structure, VsalespersonsalesbyfiscalyearsViewRow.read)
  }
  override def selectAll: Stream[ConnectionIO, VsalespersonsalesbyfiscalyearsViewRow] = {
    sql"""select "SalesPersonID", "FullName", "JobTitle", "SalesTerritory", "2012", "2013", "2014" from sales.vsalespersonsalesbyfiscalyears""".query(using VsalespersonsalesbyfiscalyearsViewRow.read).stream
  }
}