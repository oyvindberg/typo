/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package pp

import typo.dsl.SelectBuilder
import zio.jdbc.ZConnection
import zio.stream.ZStream

trait PpViewRepo {
  def select: SelectBuilder[PpViewFields, PpViewRow]
  def selectAll: ZStream[ZConnection, Throwable, PpViewRow]
}
