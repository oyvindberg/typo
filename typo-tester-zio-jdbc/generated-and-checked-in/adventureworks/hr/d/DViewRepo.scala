/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package hr
package d

import typo.dsl.SelectBuilder
import zio.jdbc.ZConnection
import zio.stream.ZStream

trait DViewRepo {
  def select: SelectBuilder[DViewFields, DViewRow]
  def selectAll: ZStream[ZConnection, Throwable, DViewRow]
}