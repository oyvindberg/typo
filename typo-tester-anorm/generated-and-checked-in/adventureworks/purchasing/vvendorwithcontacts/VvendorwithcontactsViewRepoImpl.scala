/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package purchasing
package vvendorwithcontacts

import anorm.SqlStringInterpolation
import java.sql.Connection
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql

object VvendorwithcontactsViewRepoImpl extends VvendorwithcontactsViewRepo {
  override def select: SelectBuilder[VvendorwithcontactsViewFields, VvendorwithcontactsViewRow] = {
    SelectBuilderSql("purchasing.vvendorwithcontacts", VvendorwithcontactsViewFields, VvendorwithcontactsViewRow.rowParser)
  }
  override def selectAll(implicit c: Connection): List[VvendorwithcontactsViewRow] = {
    SQL"""select "businessentityid", "name", "contacttype", "title", "firstname", "middlename", "lastname", "suffix", "phonenumber", "phonenumbertype", "emailaddress", "emailpromotion"
          from purchasing.vvendorwithcontacts
       """.as(VvendorwithcontactsViewRow.rowParser(1).*)
  }
}