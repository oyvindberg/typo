package testdb
package postgres
package public

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait FooRepoImpl extends FooRepo {
  override def selectAll(implicit c: Connection): List[FooRow] = {
    SQL"""select constraint_catalog, constraint_schema, constraint_name, table_catalog, table_schema, table_name, constraint_type, is_deferrable, initially_deferred, enforced from public.foo""".as(FooRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[FooFieldValue[_]])(implicit c: Connection): List[FooRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case FooFieldValue.constraintCatalog(value) => NamedParameter("constraint_catalog", ParameterValue.from(value))
          case FooFieldValue.constraintSchema(value) => NamedParameter("constraint_schema", ParameterValue.from(value))
          case FooFieldValue.constraintName(value) => NamedParameter("constraint_name", ParameterValue.from(value))
          case FooFieldValue.tableCatalog(value) => NamedParameter("table_catalog", ParameterValue.from(value))
          case FooFieldValue.tableSchema(value) => NamedParameter("table_schema", ParameterValue.from(value))
          case FooFieldValue.tableName(value) => NamedParameter("table_name", ParameterValue.from(value))
          case FooFieldValue.constraintType(value) => NamedParameter("constraint_type", ParameterValue.from(value))
          case FooFieldValue.isDeferrable(value) => NamedParameter("is_deferrable", ParameterValue.from(value))
          case FooFieldValue.initiallyDeferred(value) => NamedParameter("initially_deferred", ParameterValue.from(value))
          case FooFieldValue.enforced(value) => NamedParameter("enforced", ParameterValue.from(value))
        }
        SQL"""select * from public.foo where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(FooRow.rowParser.*)
    }

  }
}
