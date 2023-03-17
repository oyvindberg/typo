package testdb.pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgSequencesRepoImpl extends PgSequencesRepo {
  override def selectAll(implicit c: Connection): List[PgSequencesRow] = {
    SQL"""select schemaname, sequencename, sequenceowner, data_type, start_value, min_value, max_value, increment_by, cycle, cache_size, last_value from pg_catalog.pg_sequences""".as(PgSequencesRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgSequencesFieldValue[_]])(implicit c: Connection): List[PgSequencesRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgSequencesFieldValue.schemaname(value) => NamedParameter("schemaname", ParameterValue.from(value))
          case PgSequencesFieldValue.sequencename(value) => NamedParameter("sequencename", ParameterValue.from(value))
          case PgSequencesFieldValue.sequenceowner(value) => NamedParameter("sequenceowner", ParameterValue.from(value))
          case PgSequencesFieldValue.dataType(value) => NamedParameter("data_type", ParameterValue.from(value))
          case PgSequencesFieldValue.startValue(value) => NamedParameter("start_value", ParameterValue.from(value))
          case PgSequencesFieldValue.minValue(value) => NamedParameter("min_value", ParameterValue.from(value))
          case PgSequencesFieldValue.maxValue(value) => NamedParameter("max_value", ParameterValue.from(value))
          case PgSequencesFieldValue.incrementBy(value) => NamedParameter("increment_by", ParameterValue.from(value))
          case PgSequencesFieldValue.cycle(value) => NamedParameter("cycle", ParameterValue.from(value))
          case PgSequencesFieldValue.cacheSize(value) => NamedParameter("cache_size", ParameterValue.from(value))
          case PgSequencesFieldValue.lastValue(value) => NamedParameter("last_value", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_sequences where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgSequencesRow.rowParser.*)
    }

  }
}
