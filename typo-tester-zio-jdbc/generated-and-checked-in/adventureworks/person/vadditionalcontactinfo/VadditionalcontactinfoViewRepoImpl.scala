/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person
package vadditionalcontactinfo

import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import zio.jdbc.ZConnection
import zio.jdbc.sqlInterpolator
import zio.stream.ZStream

class VadditionalcontactinfoViewRepoImpl extends VadditionalcontactinfoViewRepo {
  override def select: SelectBuilder[VadditionalcontactinfoViewFields, VadditionalcontactinfoViewRow] = {
    SelectBuilderSql("person.vadditionalcontactinfo", VadditionalcontactinfoViewFields.structure, VadditionalcontactinfoViewRow.jdbcDecoder)
  }
  override def selectAll: ZStream[ZConnection, Throwable, VadditionalcontactinfoViewRow] = {
    sql"""select "businessentityid", "firstname", "middlename", "lastname", "telephonenumber", "telephonespecialinstructions", "street", "city", "stateprovince", "postalcode", "countryregion", "homeaddressspecialinstructions", "emailaddress", "emailspecialinstructions", "emailtelephonenumber", "rowguid", "modifieddate"::text from person.vadditionalcontactinfo""".query(using VadditionalcontactinfoViewRow.jdbcDecoder).selectStream()
  }
}