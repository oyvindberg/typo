/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package vsalespersonsalesbyfiscalyearsdata

import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import zio.jdbc.ZConnection
import zio.jdbc.sqlInterpolator
import zio.stream.ZStream

class VsalespersonsalesbyfiscalyearsdataViewRepoImpl extends VsalespersonsalesbyfiscalyearsdataViewRepo {
  override def select: SelectBuilder[VsalespersonsalesbyfiscalyearsdataViewFields, VsalespersonsalesbyfiscalyearsdataViewRow] = {
    SelectBuilderSql("sales.vsalespersonsalesbyfiscalyearsdata", VsalespersonsalesbyfiscalyearsdataViewFields.structure, VsalespersonsalesbyfiscalyearsdataViewRow.jdbcDecoder)
  }
  override def selectAll: ZStream[ZConnection, Throwable, VsalespersonsalesbyfiscalyearsdataViewRow] = {
    sql"""select "salespersonid", "fullname", "jobtitle", "salesterritory", "salestotal", "fiscalyear" from sales.vsalespersonsalesbyfiscalyearsdata""".query(using VsalespersonsalesbyfiscalyearsdataViewRow.jdbcDecoder).selectStream()
  }
}