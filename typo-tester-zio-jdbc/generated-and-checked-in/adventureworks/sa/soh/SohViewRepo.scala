/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package soh

import typo.dsl.SelectBuilder
import zio.jdbc.ZConnection
import zio.stream.ZStream

trait SohViewRepo {
  def select: SelectBuilder[SohViewFields, SohViewRow]
  def selectAll: ZStream[ZConnection, Throwable, SohViewRow]
}