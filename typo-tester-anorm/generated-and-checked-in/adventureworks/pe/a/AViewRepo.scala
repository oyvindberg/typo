/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pe
package a

import java.sql.Connection
import typo.dsl.SelectBuilder

trait AViewRepo {
  def select: SelectBuilder[AViewFields, AViewRow]
  def selectAll(implicit c: Connection): List[AViewRow]
}