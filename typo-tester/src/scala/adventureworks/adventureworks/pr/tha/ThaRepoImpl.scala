/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package tha

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

object ThaRepoImpl extends ThaRepo {
  override def selectAll(implicit c: Connection): List[ThaRow] = {
    SQL"""select id, transactionid, productid, referenceorderid, referenceorderlineid, transactiondate, transactiontype, quantity, actualcost, modifieddate from pr.tha""".as(ThaRow.rowParser("").*)
  }
  override def selectByFieldValues(fieldValues: List[ThaFieldOrIdValue[_]])(implicit c: Connection): List[ThaRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case ThaFieldValue.id(value) => NamedParameter("id", ParameterValue.from(value))
          case ThaFieldValue.transactionid(value) => NamedParameter("transactionid", ParameterValue.from(value))
          case ThaFieldValue.productid(value) => NamedParameter("productid", ParameterValue.from(value))
          case ThaFieldValue.referenceorderid(value) => NamedParameter("referenceorderid", ParameterValue.from(value))
          case ThaFieldValue.referenceorderlineid(value) => NamedParameter("referenceorderlineid", ParameterValue.from(value))
          case ThaFieldValue.transactiondate(value) => NamedParameter("transactiondate", ParameterValue.from(value))
          case ThaFieldValue.transactiontype(value) => NamedParameter("transactiontype", ParameterValue.from(value))
          case ThaFieldValue.quantity(value) => NamedParameter("quantity", ParameterValue.from(value))
          case ThaFieldValue.actualcost(value) => NamedParameter("actualcost", ParameterValue.from(value))
          case ThaFieldValue.modifieddate(value) => NamedParameter("modifieddate", ParameterValue.from(value))
        }
        val q = s"""select * from pr.tha where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
        // this line is here to include an extension method which is only needed for scala 3. no import is emitted for `SQL` to avoid warning for scala 2
        import anorm._
        SQL(q)
          .on(namedParams: _*)
          .as(ThaRow.rowParser("").*)
    }
  
  }
}