/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sa
package so

import java.sql.Connection
import typo.dsl.SelectBuilder

trait SoViewRepo {
  def select: SelectBuilder[SoViewFields, SoViewRow]
  def selectAll(implicit c: Connection): List[SoViewRow]
}
