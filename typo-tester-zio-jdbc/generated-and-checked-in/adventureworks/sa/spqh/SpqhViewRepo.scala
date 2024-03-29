/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package spqh

import typo.dsl.SelectBuilder
import zio.jdbc.ZConnection
import zio.stream.ZStream

trait SpqhViewRepo {
  def select: SelectBuilder[SpqhViewFields, SpqhViewRow]
  def selectAll: ZStream[ZConnection, Throwable, SpqhViewRow]
}
