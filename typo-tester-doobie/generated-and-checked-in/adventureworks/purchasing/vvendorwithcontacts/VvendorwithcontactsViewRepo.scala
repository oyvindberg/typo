/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package purchasing
package vvendorwithcontacts

import doobie.free.connection.ConnectionIO
import fs2.Stream
import typo.dsl.SelectBuilder

trait VvendorwithcontactsViewRepo {
  def select: SelectBuilder[VvendorwithcontactsViewFields, VvendorwithcontactsViewRow]
  def selectAll: Stream[ConnectionIO, VvendorwithcontactsViewRow]
}