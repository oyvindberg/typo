/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package vsalespersonsalesbyfiscalyearsdata

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.SelectBuilder

trait VsalespersonsalesbyfiscalyearsdataViewRepo {
  def select: SelectBuilder[VsalespersonsalesbyfiscalyearsdataViewFields, VsalespersonsalesbyfiscalyearsdataViewRow]
  def selectAll: Stream[ConnectionIO, VsalespersonsalesbyfiscalyearsdataViewRow]
}
