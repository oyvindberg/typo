/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package humanresources
package employeedepartmenthistory

import adventureworks.Defaulted.Provided
import adventureworks.Defaulted.UseDefault
import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection
import java.time.LocalDateTime

object EmployeedepartmenthistoryRepoImpl extends EmployeedepartmenthistoryRepo {
  override def delete(compositeId: EmployeedepartmenthistoryId)(implicit c: Connection): Boolean = {
    SQL"""delete from humanresources.employeedepartmenthistory where businessentityid = ${compositeId.businessentityid}, startdate = ${compositeId.startdate}, departmentid = ${compositeId.departmentid}, shiftid = ${compositeId.shiftid}""".executeUpdate() > 0
  }
  override def insert(compositeId: EmployeedepartmenthistoryId, unsaved: EmployeedepartmenthistoryRowUnsaved)(implicit c: Connection): Boolean = {
    val namedParameters = List(
      Some(NamedParameter("enddate", ParameterValue.from(unsaved.enddate))),
      unsaved.modifieddate match {
        case UseDefault => None
        case Provided(value) => Some(NamedParameter("modifieddate", ParameterValue.from[LocalDateTime](value)))
      }
    ).flatten
    
    SQL"""insert into humanresources.employeedepartmenthistory(businessentityid, startdate, departmentid, shiftid, ${namedParameters.map(_.name).mkString(", ")})
          values (${compositeId.businessentityid}, ${compositeId.startdate}, ${compositeId.departmentid}, ${compositeId.shiftid}, ${namedParameters.map(np => s"{${np.name}}").mkString(", ")})
    """
      .on(namedParameters :_*)
      .execute()
  
  }
  override def selectAll(implicit c: Connection): List[EmployeedepartmenthistoryRow] = {
    SQL"""select businessentityid, departmentid, shiftid, startdate, enddate, modifieddate from humanresources.employeedepartmenthistory""".as(EmployeedepartmenthistoryRow.rowParser("").*)
  }
  override def selectByFieldValues(fieldValues: List[EmployeedepartmenthistoryFieldOrIdValue[_]])(implicit c: Connection): List[EmployeedepartmenthistoryRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case EmployeedepartmenthistoryFieldValue.businessentityid(value) => NamedParameter("businessentityid", ParameterValue.from(value))
          case EmployeedepartmenthistoryFieldValue.departmentid(value) => NamedParameter("departmentid", ParameterValue.from(value))
          case EmployeedepartmenthistoryFieldValue.shiftid(value) => NamedParameter("shiftid", ParameterValue.from(value))
          case EmployeedepartmenthistoryFieldValue.startdate(value) => NamedParameter("startdate", ParameterValue.from(value))
          case EmployeedepartmenthistoryFieldValue.enddate(value) => NamedParameter("enddate", ParameterValue.from(value))
          case EmployeedepartmenthistoryFieldValue.modifieddate(value) => NamedParameter("modifieddate", ParameterValue.from(value))
        }
        val q = s"""select * from humanresources.employeedepartmenthistory where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
        // this line is here to include an extension method which is only needed for scala 3. no import is emitted for `SQL` to avoid warning for scala 2
        import anorm._
        SQL(q)
          .on(namedParams: _*)
          .as(EmployeedepartmenthistoryRow.rowParser("").*)
    }
  
  }
  override def selectById(compositeId: EmployeedepartmenthistoryId)(implicit c: Connection): Option[EmployeedepartmenthistoryRow] = {
    SQL"""select businessentityid, departmentid, shiftid, startdate, enddate, modifieddate from humanresources.employeedepartmenthistory where businessentityid = ${compositeId.businessentityid}, startdate = ${compositeId.startdate}, departmentid = ${compositeId.departmentid}, shiftid = ${compositeId.shiftid}""".as(EmployeedepartmenthistoryRow.rowParser("").singleOpt)
  }
  override def update(compositeId: EmployeedepartmenthistoryId, row: EmployeedepartmenthistoryRow)(implicit c: Connection): Boolean = {
    SQL"""update humanresources.employeedepartmenthistory
          set enddate = ${row.enddate},
              modifieddate = ${row.modifieddate}
          where businessentityid = ${compositeId.businessentityid}, startdate = ${compositeId.startdate}, departmentid = ${compositeId.departmentid}, shiftid = ${compositeId.shiftid}""".executeUpdate() > 0
  }
  override def updateFieldValues(compositeId: EmployeedepartmenthistoryId, fieldValues: List[EmployeedepartmenthistoryFieldValue[_]])(implicit c: Connection): Boolean = {
    fieldValues match {
      case Nil => false
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case EmployeedepartmenthistoryFieldValue.enddate(value) => NamedParameter("enddate", ParameterValue.from(value))
          case EmployeedepartmenthistoryFieldValue.modifieddate(value) => NamedParameter("modifieddate", ParameterValue.from(value))
        }
        val q = s"""update humanresources.employeedepartmenthistory
                    set ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(", ")}
                    where businessentityid = ${compositeId.businessentityid}, startdate = ${compositeId.startdate}, departmentid = ${compositeId.departmentid}, shiftid = ${compositeId.shiftid}"""
        // this line is here to include an extension method which is only needed for scala 3. no import is emitted for `SQL` to avoid warning for scala 2
        import anorm._
        SQL(q)
          .on(namedParams: _*)
          .executeUpdate() > 0
    }
  
  }
}