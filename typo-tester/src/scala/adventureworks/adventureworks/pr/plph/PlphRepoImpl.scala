/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package plph

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

object PlphRepoImpl extends PlphRepo {
  override def selectAll(implicit c: Connection): List[PlphRow] = {
    SQL"""select id, productid, startdate, enddate, listprice, modifieddate from pr.plph""".as(PlphRow.rowParser("").*)
  }
  override def selectByFieldValues(fieldValues: List[PlphFieldOrIdValue[_]])(implicit c: Connection): List[PlphRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PlphFieldValue.id(value) => NamedParameter("id", ParameterValue.from(value))
          case PlphFieldValue.productid(value) => NamedParameter("productid", ParameterValue.from(value))
          case PlphFieldValue.startdate(value) => NamedParameter("startdate", ParameterValue.from(value))
          case PlphFieldValue.enddate(value) => NamedParameter("enddate", ParameterValue.from(value))
          case PlphFieldValue.listprice(value) => NamedParameter("listprice", ParameterValue.from(value))
          case PlphFieldValue.modifieddate(value) => NamedParameter("modifieddate", ParameterValue.from(value))
        }
        val q = s"""select * from pr.plph where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
        // this line is here to include an extension method which is only needed for scala 3. no import is emitted for `SQL` to avoid warning for scala 2
        import anorm._
        SQL(q)
          .on(namedParams: _*)
          .as(PlphRow.rowParser("").*)
    }
  
  }
}