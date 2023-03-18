package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgAggregateRepoImpl extends PgAggregateRepo {
  override def selectAll(implicit c: Connection): List[PgAggregateRow] = {
    SQL"""select aggfnoid, aggkind, aggnumdirectargs, aggtransfn, aggfinalfn, aggcombinefn, aggserialfn, aggdeserialfn, aggmtransfn, aggminvtransfn, aggmfinalfn, aggfinalextra, aggmfinalextra, aggfinalmodify, aggmfinalmodify, aggsortop, aggtranstype, aggtransspace, aggmtranstype, aggmtransspace, agginitval, aggminitval from pg_catalog.pg_aggregate""".as(PgAggregateRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgAggregateFieldValue[_]])(implicit c: Connection): List[PgAggregateRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgAggregateFieldValue.aggfnoid(value) => NamedParameter("aggfnoid", ParameterValue.from(value))
          case PgAggregateFieldValue.aggkind(value) => NamedParameter("aggkind", ParameterValue.from(value))
          case PgAggregateFieldValue.aggnumdirectargs(value) => NamedParameter("aggnumdirectargs", ParameterValue.from(value))
          case PgAggregateFieldValue.aggtransfn(value) => NamedParameter("aggtransfn", ParameterValue.from(value))
          case PgAggregateFieldValue.aggfinalfn(value) => NamedParameter("aggfinalfn", ParameterValue.from(value))
          case PgAggregateFieldValue.aggcombinefn(value) => NamedParameter("aggcombinefn", ParameterValue.from(value))
          case PgAggregateFieldValue.aggserialfn(value) => NamedParameter("aggserialfn", ParameterValue.from(value))
          case PgAggregateFieldValue.aggdeserialfn(value) => NamedParameter("aggdeserialfn", ParameterValue.from(value))
          case PgAggregateFieldValue.aggmtransfn(value) => NamedParameter("aggmtransfn", ParameterValue.from(value))
          case PgAggregateFieldValue.aggminvtransfn(value) => NamedParameter("aggminvtransfn", ParameterValue.from(value))
          case PgAggregateFieldValue.aggmfinalfn(value) => NamedParameter("aggmfinalfn", ParameterValue.from(value))
          case PgAggregateFieldValue.aggfinalextra(value) => NamedParameter("aggfinalextra", ParameterValue.from(value))
          case PgAggregateFieldValue.aggmfinalextra(value) => NamedParameter("aggmfinalextra", ParameterValue.from(value))
          case PgAggregateFieldValue.aggfinalmodify(value) => NamedParameter("aggfinalmodify", ParameterValue.from(value))
          case PgAggregateFieldValue.aggmfinalmodify(value) => NamedParameter("aggmfinalmodify", ParameterValue.from(value))
          case PgAggregateFieldValue.aggsortop(value) => NamedParameter("aggsortop", ParameterValue.from(value))
          case PgAggregateFieldValue.aggtranstype(value) => NamedParameter("aggtranstype", ParameterValue.from(value))
          case PgAggregateFieldValue.aggtransspace(value) => NamedParameter("aggtransspace", ParameterValue.from(value))
          case PgAggregateFieldValue.aggmtranstype(value) => NamedParameter("aggmtranstype", ParameterValue.from(value))
          case PgAggregateFieldValue.aggmtransspace(value) => NamedParameter("aggmtransspace", ParameterValue.from(value))
          case PgAggregateFieldValue.agginitval(value) => NamedParameter("agginitval", ParameterValue.from(value))
          case PgAggregateFieldValue.aggminitval(value) => NamedParameter("aggminitval", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_aggregate where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgAggregateRow.rowParser.*)
    }

  }
}
