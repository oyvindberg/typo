/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package vstorewithdemographics

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.SelectBuilder

trait VstorewithdemographicsViewRepo {
  def select: SelectBuilder[VstorewithdemographicsViewFields, VstorewithdemographicsViewRow]
  def selectAll: Stream[ConnectionIO, VstorewithdemographicsViewRow]
}
