package testdb.information_schema

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait ViewsRepoImpl extends ViewsRepo {
  override def selectAll(implicit c: Connection): List[ViewsRow] = {
    SQL"""select table_catalog, table_schema, table_name, view_definition, check_option, is_updatable, is_insertable_into, is_trigger_updatable, is_trigger_deletable, is_trigger_insertable_into from information_schema.views""".as(ViewsRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[ViewsFieldValue[_]])(implicit c: Connection): List[ViewsRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case ViewsFieldValue.tableCatalog(value) => NamedParameter("table_catalog", ParameterValue.from(value))
          case ViewsFieldValue.tableSchema(value) => NamedParameter("table_schema", ParameterValue.from(value))
          case ViewsFieldValue.tableName(value) => NamedParameter("table_name", ParameterValue.from(value))
          case ViewsFieldValue.viewDefinition(value) => NamedParameter("view_definition", ParameterValue.from(value))
          case ViewsFieldValue.checkOption(value) => NamedParameter("check_option", ParameterValue.from(value))
          case ViewsFieldValue.isUpdatable(value) => NamedParameter("is_updatable", ParameterValue.from(value))
          case ViewsFieldValue.isInsertableInto(value) => NamedParameter("is_insertable_into", ParameterValue.from(value))
          case ViewsFieldValue.isTriggerUpdatable(value) => NamedParameter("is_trigger_updatable", ParameterValue.from(value))
          case ViewsFieldValue.isTriggerDeletable(value) => NamedParameter("is_trigger_deletable", ParameterValue.from(value))
          case ViewsFieldValue.isTriggerInsertableInto(value) => NamedParameter("is_trigger_insertable_into", ParameterValue.from(value))
        }
        SQL"""select * from information_schema.views where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(ViewsRow.rowParser.*)
    }

  }
}
