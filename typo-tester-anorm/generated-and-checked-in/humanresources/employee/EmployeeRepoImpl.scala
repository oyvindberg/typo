/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package humanresources
package employee

import adventureworks.Defaulted
import adventureworks.TypoLocalDateTime
import adventureworks.person.businessentity.BusinessentityId
import adventureworks.public.Flag
import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection
import java.util.UUID
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import typo.dsl.UpdateBuilder

object EmployeeRepoImpl extends EmployeeRepo {
  override def delete(businessentityid: BusinessentityId)(implicit c: Connection): Boolean = {
    SQL"delete from humanresources.employee where businessentityid = $businessentityid".executeUpdate() > 0
  }
  override def delete: DeleteBuilder[EmployeeFields, EmployeeRow] = {
    DeleteBuilder("humanresources.employee", EmployeeFields)
  }
  override def insert(unsaved: EmployeeRow)(implicit c: Connection): EmployeeRow = {
    SQL"""insert into humanresources.employee(businessentityid, nationalidnumber, loginid, jobtitle, birthdate, maritalstatus, gender, hiredate, salariedflag, vacationhours, sickleavehours, currentflag, rowguid, modifieddate, organizationnode)
          values (${unsaved.businessentityid}::int4, ${unsaved.nationalidnumber}, ${unsaved.loginid}, ${unsaved.jobtitle}, ${unsaved.birthdate}::date, ${unsaved.maritalstatus}::bpchar, ${unsaved.gender}::bpchar, ${unsaved.hiredate}::date, ${unsaved.salariedflag}::"public"."Flag", ${unsaved.vacationhours}::int2, ${unsaved.sickleavehours}::int2, ${unsaved.currentflag}::"public"."Flag", ${unsaved.rowguid}::uuid, ${unsaved.modifieddate}::timestamp, ${unsaved.organizationnode})
          returning businessentityid, nationalidnumber, loginid, jobtitle, birthdate::text, maritalstatus, gender, hiredate::text, salariedflag, vacationhours, sickleavehours, currentflag, rowguid, modifieddate::text, organizationnode
       """
      .executeInsert(EmployeeRow.rowParser(1).single)
    
  }
  override def insert(unsaved: EmployeeRowUnsaved)(implicit c: Connection): EmployeeRow = {
    val namedParameters = List(
      Some((NamedParameter("businessentityid", ParameterValue.from(unsaved.businessentityid)), "::int4")),
      Some((NamedParameter("nationalidnumber", ParameterValue.from(unsaved.nationalidnumber)), "")),
      Some((NamedParameter("loginid", ParameterValue.from(unsaved.loginid)), "")),
      Some((NamedParameter("jobtitle", ParameterValue.from(unsaved.jobtitle)), "")),
      Some((NamedParameter("birthdate", ParameterValue.from(unsaved.birthdate)), "::date")),
      Some((NamedParameter("maritalstatus", ParameterValue.from(unsaved.maritalstatus)), "::bpchar")),
      Some((NamedParameter("gender", ParameterValue.from(unsaved.gender)), "::bpchar")),
      Some((NamedParameter("hiredate", ParameterValue.from(unsaved.hiredate)), "::date")),
      unsaved.salariedflag match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((NamedParameter("salariedflag", ParameterValue.from[Flag](value)), """::"public"."Flag""""))
      },
      unsaved.vacationhours match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((NamedParameter("vacationhours", ParameterValue.from[Int](value)), "::int2"))
      },
      unsaved.sickleavehours match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((NamedParameter("sickleavehours", ParameterValue.from[Int](value)), "::int2"))
      },
      unsaved.currentflag match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((NamedParameter("currentflag", ParameterValue.from[Flag](value)), """::"public"."Flag""""))
      },
      unsaved.rowguid match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((NamedParameter("rowguid", ParameterValue.from[UUID](value)), "::uuid"))
      },
      unsaved.modifieddate match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((NamedParameter("modifieddate", ParameterValue.from[TypoLocalDateTime](value)), "::timestamp"))
      },
      unsaved.organizationnode match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((NamedParameter("organizationnode", ParameterValue.from[Option[String]](value)), ""))
      }
    ).flatten
    val quote = '"'.toString
    if (namedParameters.isEmpty) {
      SQL"""insert into humanresources.employee default values
            returning businessentityid, nationalidnumber, loginid, jobtitle, birthdate::text, maritalstatus, gender, hiredate::text, salariedflag, vacationhours, sickleavehours, currentflag, rowguid, modifieddate::text, organizationnode
         """
        .executeInsert(EmployeeRow.rowParser(1).single)
    } else {
      val q = s"""insert into humanresources.employee(${namedParameters.map{case (x, _) => quote + x.name + quote}.mkString(", ")})
                  values (${namedParameters.map{ case (np, cast) => s"{${np.name}}$cast"}.mkString(", ")})
                  returning businessentityid, nationalidnumber, loginid, jobtitle, birthdate::text, maritalstatus, gender, hiredate::text, salariedflag, vacationhours, sickleavehours, currentflag, rowguid, modifieddate::text, organizationnode
               """
      // this line is here to include an extension method which is only needed for scala 3. no import is emitted for `SQL` to avoid warning for scala 2
      import anorm._
      SQL(q)
        .on(namedParameters.map(_._1) :_*)
        .executeInsert(EmployeeRow.rowParser(1).single)
    }
    
  }
  override def select: SelectBuilder[EmployeeFields, EmployeeRow] = {
    SelectBuilderSql("humanresources.employee", EmployeeFields, EmployeeRow.rowParser)
  }
  override def selectAll(implicit c: Connection): List[EmployeeRow] = {
    SQL"""select businessentityid, nationalidnumber, loginid, jobtitle, birthdate::text, maritalstatus, gender, hiredate::text, salariedflag, vacationhours, sickleavehours, currentflag, rowguid, modifieddate::text, organizationnode
          from humanresources.employee
       """.as(EmployeeRow.rowParser(1).*)
  }
  override def selectById(businessentityid: BusinessentityId)(implicit c: Connection): Option[EmployeeRow] = {
    SQL"""select businessentityid, nationalidnumber, loginid, jobtitle, birthdate::text, maritalstatus, gender, hiredate::text, salariedflag, vacationhours, sickleavehours, currentflag, rowguid, modifieddate::text, organizationnode
          from humanresources.employee
          where businessentityid = $businessentityid
       """.as(EmployeeRow.rowParser(1).singleOpt)
  }
  override def selectByIds(businessentityids: Array[BusinessentityId])(implicit c: Connection): List[EmployeeRow] = {
    SQL"""select businessentityid, nationalidnumber, loginid, jobtitle, birthdate::text, maritalstatus, gender, hiredate::text, salariedflag, vacationhours, sickleavehours, currentflag, rowguid, modifieddate::text, organizationnode
          from humanresources.employee
          where businessentityid = ANY($businessentityids)
       """.as(EmployeeRow.rowParser(1).*)
    
  }
  override def update(row: EmployeeRow)(implicit c: Connection): Boolean = {
    val businessentityid = row.businessentityid
    SQL"""update humanresources.employee
          set nationalidnumber = ${row.nationalidnumber},
              loginid = ${row.loginid},
              jobtitle = ${row.jobtitle},
              birthdate = ${row.birthdate}::date,
              maritalstatus = ${row.maritalstatus}::bpchar,
              gender = ${row.gender}::bpchar,
              hiredate = ${row.hiredate}::date,
              salariedflag = ${row.salariedflag}::"public"."Flag",
              vacationhours = ${row.vacationhours}::int2,
              sickleavehours = ${row.sickleavehours}::int2,
              currentflag = ${row.currentflag}::"public"."Flag",
              rowguid = ${row.rowguid}::uuid,
              modifieddate = ${row.modifieddate}::timestamp,
              organizationnode = ${row.organizationnode}
          where businessentityid = $businessentityid
       """.executeUpdate() > 0
  }
  override def update: UpdateBuilder[EmployeeFields, EmployeeRow] = {
    UpdateBuilder("humanresources.employee", EmployeeFields, EmployeeRow.rowParser)
  }
  override def upsert(unsaved: EmployeeRow)(implicit c: Connection): EmployeeRow = {
    SQL"""insert into humanresources.employee(businessentityid, nationalidnumber, loginid, jobtitle, birthdate, maritalstatus, gender, hiredate, salariedflag, vacationhours, sickleavehours, currentflag, rowguid, modifieddate, organizationnode)
          values (
            ${unsaved.businessentityid}::int4,
            ${unsaved.nationalidnumber},
            ${unsaved.loginid},
            ${unsaved.jobtitle},
            ${unsaved.birthdate}::date,
            ${unsaved.maritalstatus}::bpchar,
            ${unsaved.gender}::bpchar,
            ${unsaved.hiredate}::date,
            ${unsaved.salariedflag}::"public"."Flag",
            ${unsaved.vacationhours}::int2,
            ${unsaved.sickleavehours}::int2,
            ${unsaved.currentflag}::"public"."Flag",
            ${unsaved.rowguid}::uuid,
            ${unsaved.modifieddate}::timestamp,
            ${unsaved.organizationnode}
          )
          on conflict (businessentityid)
          do update set
            nationalidnumber = EXCLUDED.nationalidnumber,
            loginid = EXCLUDED.loginid,
            jobtitle = EXCLUDED.jobtitle,
            birthdate = EXCLUDED.birthdate,
            maritalstatus = EXCLUDED.maritalstatus,
            gender = EXCLUDED.gender,
            hiredate = EXCLUDED.hiredate,
            salariedflag = EXCLUDED.salariedflag,
            vacationhours = EXCLUDED.vacationhours,
            sickleavehours = EXCLUDED.sickleavehours,
            currentflag = EXCLUDED.currentflag,
            rowguid = EXCLUDED.rowguid,
            modifieddate = EXCLUDED.modifieddate,
            organizationnode = EXCLUDED.organizationnode
          returning businessentityid, nationalidnumber, loginid, jobtitle, birthdate::text, maritalstatus, gender, hiredate::text, salariedflag, vacationhours, sickleavehours, currentflag, rowguid, modifieddate::text, organizationnode
       """
      .executeInsert(EmployeeRow.rowParser(1).single)
    
  }
}