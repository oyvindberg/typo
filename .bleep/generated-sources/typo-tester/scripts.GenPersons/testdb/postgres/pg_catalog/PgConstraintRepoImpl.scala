package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgConstraintRepoImpl extends PgConstraintRepo {
  override def selectAll(implicit c: Connection): List[PgConstraintRow] = {
    SQL"""select oid, conname, connamespace, contype, condeferrable, condeferred, convalidated, conrelid, contypid, conindid, conparentid, confrelid, confupdtype, confdeltype, confmatchtype, conislocal, coninhcount, connoinherit, conkey, confkey, conpfeqop, conppeqop, conffeqop, conexclop, conbin from pg_catalog.pg_constraint""".as(PgConstraintRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgConstraintFieldValue[_]])(implicit c: Connection): List[PgConstraintRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgConstraintFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgConstraintFieldValue.conname(value) => NamedParameter("conname", ParameterValue.from(value))
          case PgConstraintFieldValue.connamespace(value) => NamedParameter("connamespace", ParameterValue.from(value))
          case PgConstraintFieldValue.contype(value) => NamedParameter("contype", ParameterValue.from(value))
          case PgConstraintFieldValue.condeferrable(value) => NamedParameter("condeferrable", ParameterValue.from(value))
          case PgConstraintFieldValue.condeferred(value) => NamedParameter("condeferred", ParameterValue.from(value))
          case PgConstraintFieldValue.convalidated(value) => NamedParameter("convalidated", ParameterValue.from(value))
          case PgConstraintFieldValue.conrelid(value) => NamedParameter("conrelid", ParameterValue.from(value))
          case PgConstraintFieldValue.contypid(value) => NamedParameter("contypid", ParameterValue.from(value))
          case PgConstraintFieldValue.conindid(value) => NamedParameter("conindid", ParameterValue.from(value))
          case PgConstraintFieldValue.conparentid(value) => NamedParameter("conparentid", ParameterValue.from(value))
          case PgConstraintFieldValue.confrelid(value) => NamedParameter("confrelid", ParameterValue.from(value))
          case PgConstraintFieldValue.confupdtype(value) => NamedParameter("confupdtype", ParameterValue.from(value))
          case PgConstraintFieldValue.confdeltype(value) => NamedParameter("confdeltype", ParameterValue.from(value))
          case PgConstraintFieldValue.confmatchtype(value) => NamedParameter("confmatchtype", ParameterValue.from(value))
          case PgConstraintFieldValue.conislocal(value) => NamedParameter("conislocal", ParameterValue.from(value))
          case PgConstraintFieldValue.coninhcount(value) => NamedParameter("coninhcount", ParameterValue.from(value))
          case PgConstraintFieldValue.connoinherit(value) => NamedParameter("connoinherit", ParameterValue.from(value))
          case PgConstraintFieldValue.conkey(value) => NamedParameter("conkey", ParameterValue.from(value))
          case PgConstraintFieldValue.confkey(value) => NamedParameter("confkey", ParameterValue.from(value))
          case PgConstraintFieldValue.conpfeqop(value) => NamedParameter("conpfeqop", ParameterValue.from(value))
          case PgConstraintFieldValue.conppeqop(value) => NamedParameter("conppeqop", ParameterValue.from(value))
          case PgConstraintFieldValue.conffeqop(value) => NamedParameter("conffeqop", ParameterValue.from(value))
          case PgConstraintFieldValue.conexclop(value) => NamedParameter("conexclop", ParameterValue.from(value))
          case PgConstraintFieldValue.conbin(value) => NamedParameter("conbin", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_constraint where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgConstraintRow.rowParser.*)
    }

  }
}
