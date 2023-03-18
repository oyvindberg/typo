package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgIndexRepoImpl extends PgIndexRepo {
  override def selectAll(implicit c: Connection): List[PgIndexRow] = {
    SQL"""select indexrelid, indrelid, indnatts, indnkeyatts, indisunique, indisprimary, indisexclusion, indimmediate, indisclustered, indisvalid, indcheckxmin, indisready, indislive, indisreplident, indkey, indcollation, indclass, indoption, indexprs, indpred from pg_catalog.pg_index""".as(PgIndexRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgIndexFieldValue[_]])(implicit c: Connection): List[PgIndexRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgIndexFieldValue.indexrelid(value) => NamedParameter("indexrelid", ParameterValue.from(value))
          case PgIndexFieldValue.indrelid(value) => NamedParameter("indrelid", ParameterValue.from(value))
          case PgIndexFieldValue.indnatts(value) => NamedParameter("indnatts", ParameterValue.from(value))
          case PgIndexFieldValue.indnkeyatts(value) => NamedParameter("indnkeyatts", ParameterValue.from(value))
          case PgIndexFieldValue.indisunique(value) => NamedParameter("indisunique", ParameterValue.from(value))
          case PgIndexFieldValue.indisprimary(value) => NamedParameter("indisprimary", ParameterValue.from(value))
          case PgIndexFieldValue.indisexclusion(value) => NamedParameter("indisexclusion", ParameterValue.from(value))
          case PgIndexFieldValue.indimmediate(value) => NamedParameter("indimmediate", ParameterValue.from(value))
          case PgIndexFieldValue.indisclustered(value) => NamedParameter("indisclustered", ParameterValue.from(value))
          case PgIndexFieldValue.indisvalid(value) => NamedParameter("indisvalid", ParameterValue.from(value))
          case PgIndexFieldValue.indcheckxmin(value) => NamedParameter("indcheckxmin", ParameterValue.from(value))
          case PgIndexFieldValue.indisready(value) => NamedParameter("indisready", ParameterValue.from(value))
          case PgIndexFieldValue.indislive(value) => NamedParameter("indislive", ParameterValue.from(value))
          case PgIndexFieldValue.indisreplident(value) => NamedParameter("indisreplident", ParameterValue.from(value))
          case PgIndexFieldValue.indkey(value) => NamedParameter("indkey", ParameterValue.from(value))
          case PgIndexFieldValue.indcollation(value) => NamedParameter("indcollation", ParameterValue.from(value))
          case PgIndexFieldValue.indclass(value) => NamedParameter("indclass", ParameterValue.from(value))
          case PgIndexFieldValue.indoption(value) => NamedParameter("indoption", ParameterValue.from(value))
          case PgIndexFieldValue.indexprs(value) => NamedParameter("indexprs", ParameterValue.from(value))
          case PgIndexFieldValue.indpred(value) => NamedParameter("indpred", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_index where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgIndexRow.rowParser.*)
    }

  }
}
