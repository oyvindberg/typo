package testdb
package information_schema

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait TriggersRepoImpl extends TriggersRepo {
  override def selectAll(implicit c: Connection): List[TriggersRow] = {
    SQL"""select trigger_catalog, trigger_schema, trigger_name, event_manipulation, event_object_catalog, event_object_schema, event_object_table, action_order, action_condition, action_statement, action_orientation, action_timing, action_reference_old_table, action_reference_new_table, action_reference_old_row, action_reference_new_row, created from information_schema.triggers""".as(TriggersRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[TriggersFieldValue[_]])(implicit c: Connection): List[TriggersRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case TriggersFieldValue.triggerCatalog(value) => NamedParameter("trigger_catalog", ParameterValue.from(value))
          case TriggersFieldValue.triggerSchema(value) => NamedParameter("trigger_schema", ParameterValue.from(value))
          case TriggersFieldValue.triggerName(value) => NamedParameter("trigger_name", ParameterValue.from(value))
          case TriggersFieldValue.eventManipulation(value) => NamedParameter("event_manipulation", ParameterValue.from(value))
          case TriggersFieldValue.eventObjectCatalog(value) => NamedParameter("event_object_catalog", ParameterValue.from(value))
          case TriggersFieldValue.eventObjectSchema(value) => NamedParameter("event_object_schema", ParameterValue.from(value))
          case TriggersFieldValue.eventObjectTable(value) => NamedParameter("event_object_table", ParameterValue.from(value))
          case TriggersFieldValue.actionOrder(value) => NamedParameter("action_order", ParameterValue.from(value))
          case TriggersFieldValue.actionCondition(value) => NamedParameter("action_condition", ParameterValue.from(value))
          case TriggersFieldValue.actionStatement(value) => NamedParameter("action_statement", ParameterValue.from(value))
          case TriggersFieldValue.actionOrientation(value) => NamedParameter("action_orientation", ParameterValue.from(value))
          case TriggersFieldValue.actionTiming(value) => NamedParameter("action_timing", ParameterValue.from(value))
          case TriggersFieldValue.actionReferenceOldTable(value) => NamedParameter("action_reference_old_table", ParameterValue.from(value))
          case TriggersFieldValue.actionReferenceNewTable(value) => NamedParameter("action_reference_new_table", ParameterValue.from(value))
          case TriggersFieldValue.actionReferenceOldRow(value) => NamedParameter("action_reference_old_row", ParameterValue.from(value))
          case TriggersFieldValue.actionReferenceNewRow(value) => NamedParameter("action_reference_new_row", ParameterValue.from(value))
          case TriggersFieldValue.created(value) => NamedParameter("created", ParameterValue.from(value))
        }
        SQL"""select * from information_schema.triggers where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(TriggersRow.rowParser.*)
    }

  }
}
