/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pu
package v

import typo.dsl.SelectBuilder
import zio.jdbc.ZConnection
import zio.stream.ZStream

trait VViewRepo {
  def select: SelectBuilder[VViewFields, VViewRow]
  def selectAll: ZStream[ZConnection, Throwable, VViewRow]
}
