/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package vsalespersonsalesbyfiscalyears

import anorm.SqlStringInterpolation
import java.sql.Connection
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql

object VsalespersonsalesbyfiscalyearsViewRepoImpl extends VsalespersonsalesbyfiscalyearsViewRepo {
  override def select: SelectBuilder[VsalespersonsalesbyfiscalyearsViewFields, VsalespersonsalesbyfiscalyearsViewRow] = {
    SelectBuilderSql("sales.vsalespersonsalesbyfiscalyears", VsalespersonsalesbyfiscalyearsViewFields, VsalespersonsalesbyfiscalyearsViewRow.rowParser)
  }
  override def selectAll(implicit c: Connection): List[VsalespersonsalesbyfiscalyearsViewRow] = {
    SQL"""select "SalesPersonID", "FullName", "JobTitle", "SalesTerritory", "2012", "2013", "2014"
          from sales.vsalespersonsalesbyfiscalyears
       """.as(VsalespersonsalesbyfiscalyearsViewRow.rowParser(1).*)
  }
}