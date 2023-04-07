/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package c

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

object CRepoImpl extends CRepo {
  override def selectAll(implicit c: Connection): List[CRow] = {
    SQL"""select id, cultureid, name, modifieddate from pr.c""".as(CRow.rowParser("").*)
  }
  override def selectByFieldValues(fieldValues: List[CFieldOrIdValue[_]])(implicit c: Connection): List[CRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case CFieldValue.id(value) => NamedParameter("id", ParameterValue.from(value))
          case CFieldValue.cultureid(value) => NamedParameter("cultureid", ParameterValue.from(value))
          case CFieldValue.name(value) => NamedParameter("name", ParameterValue.from(value))
          case CFieldValue.modifieddate(value) => NamedParameter("modifieddate", ParameterValue.from(value))
        }
        val q = s"""select * from pr.c where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
        // this line is here to include an extension method which is only needed for scala 3. no import is emitted for `SQL` to avoid warning for scala 2
        import anorm._
        SQL(q)
          .on(namedParams: _*)
          .as(CRow.rowParser("").*)
    }
  
  }
}