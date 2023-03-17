package testdb.pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgCursorsRepoImpl extends PgCursorsRepo {
  override def selectAll(implicit c: Connection): List[PgCursorsRow] = {
    SQL"""select name, statement, is_holdable, is_binary, is_scrollable, creation_time from pg_catalog.pg_cursors""".as(PgCursorsRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgCursorsFieldValue[_]])(implicit c: Connection): List[PgCursorsRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgCursorsFieldValue.name(value) => NamedParameter("name", ParameterValue.from(value))
          case PgCursorsFieldValue.statement(value) => NamedParameter("statement", ParameterValue.from(value))
          case PgCursorsFieldValue.isHoldable(value) => NamedParameter("is_holdable", ParameterValue.from(value))
          case PgCursorsFieldValue.isBinary(value) => NamedParameter("is_binary", ParameterValue.from(value))
          case PgCursorsFieldValue.isScrollable(value) => NamedParameter("is_scrollable", ParameterValue.from(value))
          case PgCursorsFieldValue.creationTime(value) => NamedParameter("creation_time", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_cursors where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgCursorsRow.rowParser.*)
    }

  }
}
