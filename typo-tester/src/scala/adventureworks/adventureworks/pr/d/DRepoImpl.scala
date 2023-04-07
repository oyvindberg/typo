/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package pr
package d

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

object DRepoImpl extends DRepo {
  override def selectAll(implicit c: Connection): List[DRow] = {
    SQL"""select title, owner, folderflag, filename, fileextension, revision, changenumber, status, documentsummary, document, rowguid, modifieddate, documentnode from pr.d""".as(DRow.rowParser("").*)
  }
  override def selectByFieldValues(fieldValues: List[DFieldOrIdValue[_]])(implicit c: Connection): List[DRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case DFieldValue.title(value) => NamedParameter("title", ParameterValue.from(value))
          case DFieldValue.owner(value) => NamedParameter("owner", ParameterValue.from(value))
          case DFieldValue.folderflag(value) => NamedParameter("folderflag", ParameterValue.from(value))
          case DFieldValue.filename(value) => NamedParameter("filename", ParameterValue.from(value))
          case DFieldValue.fileextension(value) => NamedParameter("fileextension", ParameterValue.from(value))
          case DFieldValue.revision(value) => NamedParameter("revision", ParameterValue.from(value))
          case DFieldValue.changenumber(value) => NamedParameter("changenumber", ParameterValue.from(value))
          case DFieldValue.status(value) => NamedParameter("status", ParameterValue.from(value))
          case DFieldValue.documentsummary(value) => NamedParameter("documentsummary", ParameterValue.from(value))
          case DFieldValue.document(value) => NamedParameter("document", ParameterValue.from(value))
          case DFieldValue.rowguid(value) => NamedParameter("rowguid", ParameterValue.from(value))
          case DFieldValue.modifieddate(value) => NamedParameter("modifieddate", ParameterValue.from(value))
          case DFieldValue.documentnode(value) => NamedParameter("documentnode", ParameterValue.from(value))
        }
        val q = s"""select * from pr.d where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
        // this line is here to include an extension method which is only needed for scala 3. no import is emitted for `SQL` to avoid warning for scala 2
        import anorm._
        SQL(q)
          .on(namedParams: _*)
          .as(DRow.rowParser("").*)
    }
  
  }
}