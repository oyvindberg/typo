/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package soh

import java.sql.Connection
import typo.dsl.SelectBuilder

trait SohViewRepo {
  def select: SelectBuilder[SohViewFields, SohViewRow]
  def selectAll(implicit c: Connection): List[SohViewRow]
}
