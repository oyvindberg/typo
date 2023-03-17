package testdb.information_schema

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait RoutinesRepoImpl extends RoutinesRepo {
  override def selectAll(implicit c: Connection): List[RoutinesRow] = {
    SQL"""select specific_catalog, specific_schema, specific_name, routine_catalog, routine_schema, routine_name, routine_type, module_catalog, module_schema, module_name, udt_catalog, udt_schema, udt_name, data_type, character_maximum_length, character_octet_length, character_set_catalog, character_set_schema, character_set_name, collation_catalog, collation_schema, collation_name, numeric_precision, numeric_precision_radix, numeric_scale, datetime_precision, interval_type, interval_precision, type_udt_catalog, type_udt_schema, type_udt_name, scope_catalog, scope_schema, scope_name, maximum_cardinality, dtd_identifier, routine_body, routine_definition, external_name, external_language, parameter_style, is_deterministic, sql_data_access, is_null_call, sql_path, schema_level_routine, max_dynamic_result_sets, is_user_defined_cast, is_implicitly_invocable, security_type, to_sql_specific_catalog, to_sql_specific_schema, to_sql_specific_name, as_locator, created, last_altered, new_savepoint_level, is_udt_dependent, result_cast_from_data_type, result_cast_as_locator, result_cast_char_max_length, result_cast_char_octet_length, result_cast_char_set_catalog, result_cast_char_set_schema, result_cast_char_set_name, result_cast_collation_catalog, result_cast_collation_schema, result_cast_collation_name, result_cast_numeric_precision, result_cast_numeric_precision_radix, result_cast_numeric_scale, result_cast_datetime_precision, result_cast_interval_type, result_cast_interval_precision, result_cast_type_udt_catalog, result_cast_type_udt_schema, result_cast_type_udt_name, result_cast_scope_catalog, result_cast_scope_schema, result_cast_scope_name, result_cast_maximum_cardinality, result_cast_dtd_identifier from information_schema.routines""".as(RoutinesRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[RoutinesFieldValue[_]])(implicit c: Connection): List[RoutinesRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case RoutinesFieldValue.specificCatalog(value) => NamedParameter("specific_catalog", ParameterValue.from(value))
          case RoutinesFieldValue.specificSchema(value) => NamedParameter("specific_schema", ParameterValue.from(value))
          case RoutinesFieldValue.specificName(value) => NamedParameter("specific_name", ParameterValue.from(value))
          case RoutinesFieldValue.routineCatalog(value) => NamedParameter("routine_catalog", ParameterValue.from(value))
          case RoutinesFieldValue.routineSchema(value) => NamedParameter("routine_schema", ParameterValue.from(value))
          case RoutinesFieldValue.routineName(value) => NamedParameter("routine_name", ParameterValue.from(value))
          case RoutinesFieldValue.routineType(value) => NamedParameter("routine_type", ParameterValue.from(value))
          case RoutinesFieldValue.moduleCatalog(value) => NamedParameter("module_catalog", ParameterValue.from(value))
          case RoutinesFieldValue.moduleSchema(value) => NamedParameter("module_schema", ParameterValue.from(value))
          case RoutinesFieldValue.moduleName(value) => NamedParameter("module_name", ParameterValue.from(value))
          case RoutinesFieldValue.udtCatalog(value) => NamedParameter("udt_catalog", ParameterValue.from(value))
          case RoutinesFieldValue.udtSchema(value) => NamedParameter("udt_schema", ParameterValue.from(value))
          case RoutinesFieldValue.udtName(value) => NamedParameter("udt_name", ParameterValue.from(value))
          case RoutinesFieldValue.dataType(value) => NamedParameter("data_type", ParameterValue.from(value))
          case RoutinesFieldValue.characterMaximumLength(value) => NamedParameter("character_maximum_length", ParameterValue.from(value))
          case RoutinesFieldValue.characterOctetLength(value) => NamedParameter("character_octet_length", ParameterValue.from(value))
          case RoutinesFieldValue.characterSetCatalog(value) => NamedParameter("character_set_catalog", ParameterValue.from(value))
          case RoutinesFieldValue.characterSetSchema(value) => NamedParameter("character_set_schema", ParameterValue.from(value))
          case RoutinesFieldValue.characterSetName(value) => NamedParameter("character_set_name", ParameterValue.from(value))
          case RoutinesFieldValue.collationCatalog(value) => NamedParameter("collation_catalog", ParameterValue.from(value))
          case RoutinesFieldValue.collationSchema(value) => NamedParameter("collation_schema", ParameterValue.from(value))
          case RoutinesFieldValue.collationName(value) => NamedParameter("collation_name", ParameterValue.from(value))
          case RoutinesFieldValue.numericPrecision(value) => NamedParameter("numeric_precision", ParameterValue.from(value))
          case RoutinesFieldValue.numericPrecisionRadix(value) => NamedParameter("numeric_precision_radix", ParameterValue.from(value))
          case RoutinesFieldValue.numericScale(value) => NamedParameter("numeric_scale", ParameterValue.from(value))
          case RoutinesFieldValue.datetimePrecision(value) => NamedParameter("datetime_precision", ParameterValue.from(value))
          case RoutinesFieldValue.intervalType(value) => NamedParameter("interval_type", ParameterValue.from(value))
          case RoutinesFieldValue.intervalPrecision(value) => NamedParameter("interval_precision", ParameterValue.from(value))
          case RoutinesFieldValue.typeUdtCatalog(value) => NamedParameter("type_udt_catalog", ParameterValue.from(value))
          case RoutinesFieldValue.typeUdtSchema(value) => NamedParameter("type_udt_schema", ParameterValue.from(value))
          case RoutinesFieldValue.typeUdtName(value) => NamedParameter("type_udt_name", ParameterValue.from(value))
          case RoutinesFieldValue.scopeCatalog(value) => NamedParameter("scope_catalog", ParameterValue.from(value))
          case RoutinesFieldValue.scopeSchema(value) => NamedParameter("scope_schema", ParameterValue.from(value))
          case RoutinesFieldValue.scopeName(value) => NamedParameter("scope_name", ParameterValue.from(value))
          case RoutinesFieldValue.maximumCardinality(value) => NamedParameter("maximum_cardinality", ParameterValue.from(value))
          case RoutinesFieldValue.dtdIdentifier(value) => NamedParameter("dtd_identifier", ParameterValue.from(value))
          case RoutinesFieldValue.routineBody(value) => NamedParameter("routine_body", ParameterValue.from(value))
          case RoutinesFieldValue.routineDefinition(value) => NamedParameter("routine_definition", ParameterValue.from(value))
          case RoutinesFieldValue.externalName(value) => NamedParameter("external_name", ParameterValue.from(value))
          case RoutinesFieldValue.externalLanguage(value) => NamedParameter("external_language", ParameterValue.from(value))
          case RoutinesFieldValue.parameterStyle(value) => NamedParameter("parameter_style", ParameterValue.from(value))
          case RoutinesFieldValue.isDeterministic(value) => NamedParameter("is_deterministic", ParameterValue.from(value))
          case RoutinesFieldValue.sqlDataAccess(value) => NamedParameter("sql_data_access", ParameterValue.from(value))
          case RoutinesFieldValue.isNullCall(value) => NamedParameter("is_null_call", ParameterValue.from(value))
          case RoutinesFieldValue.sqlPath(value) => NamedParameter("sql_path", ParameterValue.from(value))
          case RoutinesFieldValue.schemaLevelRoutine(value) => NamedParameter("schema_level_routine", ParameterValue.from(value))
          case RoutinesFieldValue.maxDynamicResultSets(value) => NamedParameter("max_dynamic_result_sets", ParameterValue.from(value))
          case RoutinesFieldValue.isUserDefinedCast(value) => NamedParameter("is_user_defined_cast", ParameterValue.from(value))
          case RoutinesFieldValue.isImplicitlyInvocable(value) => NamedParameter("is_implicitly_invocable", ParameterValue.from(value))
          case RoutinesFieldValue.securityType(value) => NamedParameter("security_type", ParameterValue.from(value))
          case RoutinesFieldValue.toSqlSpecificCatalog(value) => NamedParameter("to_sql_specific_catalog", ParameterValue.from(value))
          case RoutinesFieldValue.toSqlSpecificSchema(value) => NamedParameter("to_sql_specific_schema", ParameterValue.from(value))
          case RoutinesFieldValue.toSqlSpecificName(value) => NamedParameter("to_sql_specific_name", ParameterValue.from(value))
          case RoutinesFieldValue.asLocator(value) => NamedParameter("as_locator", ParameterValue.from(value))
          case RoutinesFieldValue.created(value) => NamedParameter("created", ParameterValue.from(value))
          case RoutinesFieldValue.lastAltered(value) => NamedParameter("last_altered", ParameterValue.from(value))
          case RoutinesFieldValue.newSavepointLevel(value) => NamedParameter("new_savepoint_level", ParameterValue.from(value))
          case RoutinesFieldValue.isUdtDependent(value) => NamedParameter("is_udt_dependent", ParameterValue.from(value))
          case RoutinesFieldValue.resultCastFromDataType(value) => NamedParameter("result_cast_from_data_type", ParameterValue.from(value))
          case RoutinesFieldValue.resultCastAsLocator(value) => NamedParameter("result_cast_as_locator", ParameterValue.from(value))
          case RoutinesFieldValue.resultCastCharMaxLength(value) => NamedParameter("result_cast_char_max_length", ParameterValue.from(value))
          case RoutinesFieldValue.resultCastCharOctetLength(value) => NamedParameter("result_cast_char_octet_length", ParameterValue.from(value))
          case RoutinesFieldValue.resultCastCharSetCatalog(value) => NamedParameter("result_cast_char_set_catalog", ParameterValue.from(value))
          case RoutinesFieldValue.resultCastCharSetSchema(value) => NamedParameter("result_cast_char_set_schema", ParameterValue.from(value))
          case RoutinesFieldValue.resultCastCharSetName(value) => NamedParameter("result_cast_char_set_name", ParameterValue.from(value))
          case RoutinesFieldValue.resultCastCollationCatalog(value) => NamedParameter("result_cast_collation_catalog", ParameterValue.from(value))
          case RoutinesFieldValue.resultCastCollationSchema(value) => NamedParameter("result_cast_collation_schema", ParameterValue.from(value))
          case RoutinesFieldValue.resultCastCollationName(value) => NamedParameter("result_cast_collation_name", ParameterValue.from(value))
          case RoutinesFieldValue.resultCastNumericPrecision(value) => NamedParameter("result_cast_numeric_precision", ParameterValue.from(value))
          case RoutinesFieldValue.resultCastNumericPrecisionRadix(value) => NamedParameter("result_cast_numeric_precision_radix", ParameterValue.from(value))
          case RoutinesFieldValue.resultCastNumericScale(value) => NamedParameter("result_cast_numeric_scale", ParameterValue.from(value))
          case RoutinesFieldValue.resultCastDatetimePrecision(value) => NamedParameter("result_cast_datetime_precision", ParameterValue.from(value))
          case RoutinesFieldValue.resultCastIntervalType(value) => NamedParameter("result_cast_interval_type", ParameterValue.from(value))
          case RoutinesFieldValue.resultCastIntervalPrecision(value) => NamedParameter("result_cast_interval_precision", ParameterValue.from(value))
          case RoutinesFieldValue.resultCastTypeUdtCatalog(value) => NamedParameter("result_cast_type_udt_catalog", ParameterValue.from(value))
          case RoutinesFieldValue.resultCastTypeUdtSchema(value) => NamedParameter("result_cast_type_udt_schema", ParameterValue.from(value))
          case RoutinesFieldValue.resultCastTypeUdtName(value) => NamedParameter("result_cast_type_udt_name", ParameterValue.from(value))
          case RoutinesFieldValue.resultCastScopeCatalog(value) => NamedParameter("result_cast_scope_catalog", ParameterValue.from(value))
          case RoutinesFieldValue.resultCastScopeSchema(value) => NamedParameter("result_cast_scope_schema", ParameterValue.from(value))
          case RoutinesFieldValue.resultCastScopeName(value) => NamedParameter("result_cast_scope_name", ParameterValue.from(value))
          case RoutinesFieldValue.resultCastMaximumCardinality(value) => NamedParameter("result_cast_maximum_cardinality", ParameterValue.from(value))
          case RoutinesFieldValue.resultCastDtdIdentifier(value) => NamedParameter("result_cast_dtd_identifier", ParameterValue.from(value))
        }
        SQL"""select * from information_schema.routines where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(RoutinesRow.rowParser.*)
    }

  }
}
