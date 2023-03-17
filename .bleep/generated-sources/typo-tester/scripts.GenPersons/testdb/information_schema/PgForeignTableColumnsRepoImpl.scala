package testdb.information_schema

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgForeignTableColumnsRepoImpl extends PgForeignTableColumnsRepo {
  override def selectAll(implicit c: Connection): List[PgForeignTableColumnsRow] = {
    SQL"""select nspname, relname, attname, attfdwoptions from information_schema._pg_foreign_table_columns""".as(PgForeignTableColumnsRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgForeignTableColumnsFieldValue[_]])(implicit c: Connection): List[PgForeignTableColumnsRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgForeignTableColumnsFieldValue.nspname(value) => NamedParameter("nspname", ParameterValue.from(value))
          case PgForeignTableColumnsFieldValue.relname(value) => NamedParameter("relname", ParameterValue.from(value))
          case PgForeignTableColumnsFieldValue.attname(value) => NamedParameter("attname", ParameterValue.from(value))
          case PgForeignTableColumnsFieldValue.attfdwoptions(value) => NamedParameter("attfdwoptions", ParameterValue.from(value))
        }
        SQL"""select * from information_schema._pg_foreign_table_columns where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgForeignTableColumnsRow.rowParser.*)
    }

  }
}
