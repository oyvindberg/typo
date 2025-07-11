/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package person_detail

import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.person.businessentity.BusinessentityId
import doobie.free.connection.ConnectionIO
import doobie.syntax.SqlInterpolator.SingleFragment.fromWrite
import doobie.syntax.string.toSqlInterpolator
import doobie.util.Write
import fs2.Stream

class PersonDetailSqlRepoImpl extends PersonDetailSqlRepo {
  override def apply(businessentityid: /* user-picked */ BusinessentityId, modifiedAfter: TypoLocalDateTime): Stream[ConnectionIO, PersonDetailSqlRow] = {
    val sql =
      sql"""SELECT s.businessentityid,
                   p.title,
                   p.firstname,
                   p.middlename,
                   p.lastname,
                   e.jobtitle,
                   a.addressline1,
                   a.city,
                   a.postalcode,
                   a.rowguid as "rowguid:java.lang.String!"
            FROM sales.salesperson s
                     JOIN humanresources.employee e ON e.businessentityid = s.businessentityid
                     JOIN person.person p ON p.businessentityid = s.businessentityid
                     JOIN person.businessentityaddress bea ON bea.businessentityid = s.businessentityid
                     LEFT JOIN person.address a ON a.addressid = bea.addressid
            where s.businessentityid = ${fromWrite(businessentityid)(new Write.Single(/* user-picked */ BusinessentityId.put))}::int4
              and p.modifieddate > ${fromWrite(modifiedAfter)(new Write.Single(TypoLocalDateTime.put))}::timestamp"""
    sql.query(using PersonDetailSqlRow.read).stream
  }
}
