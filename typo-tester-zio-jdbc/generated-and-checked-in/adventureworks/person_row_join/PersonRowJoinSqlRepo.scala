/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person_row_join

import zio.jdbc.ZConnection
import zio.stream.ZStream

trait PersonRowJoinSqlRepo {
  def apply(): ZStream[ZConnection, Throwable, PersonRowJoinSqlRow]
}