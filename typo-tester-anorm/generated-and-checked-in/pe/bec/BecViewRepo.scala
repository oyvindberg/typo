/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pe
package bec

import java.sql.Connection

trait BecViewRepo {
  def selectAll(implicit c: Connection): List[BecViewRow]
  def selectByFieldValues(fieldValues: List[BecViewFieldOrIdValue[_]])(implicit c: Connection): List[BecViewRow]
}