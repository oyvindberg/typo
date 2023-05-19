/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package psc

import doobie.free.connection.ConnectionIO
import fs2.Stream

trait PscViewRepo {
  def selectAll: Stream[ConnectionIO, PscViewRow]
  def selectByFieldValues(fieldValues: List[PscViewFieldOrIdValue[_]]): Stream[ConnectionIO, PscViewRow]
}