/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pe
package cr

import java.sql.Connection
import typo.dsl.SelectBuilder

trait CrViewRepo {
  def select: SelectBuilder[CrViewFields, CrViewRow]
  def selectAll(implicit c: Connection): List[CrViewRow]
}