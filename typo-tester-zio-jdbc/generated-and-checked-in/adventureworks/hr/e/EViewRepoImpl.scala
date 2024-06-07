/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package hr
package e

import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import zio.jdbc.ZConnection
import zio.jdbc.sqlInterpolator
import zio.stream.ZStream

class EViewRepoImpl extends EViewRepo {
  override def select: SelectBuilder[EViewFields, EViewRow] = {
    SelectBuilderSql("hr.e", EViewFields.structure, EViewRow.jdbcDecoder)
  }
  override def selectAll: ZStream[ZConnection, Throwable, EViewRow] = {
    sql"""select "id", "businessentityid", "nationalidnumber", "loginid", "jobtitle", "birthdate"::text, "maritalstatus", "gender", "hiredate"::text, "salariedflag", "vacationhours", "sickleavehours", "currentflag", "rowguid", "modifieddate"::text, "organizationnode" from hr.e""".query(using EViewRow.jdbcDecoder).selectStream()
  }
}