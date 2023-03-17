package testdb.information_schema

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait ReferentialConstraintsRepoImpl extends ReferentialConstraintsRepo {
  override def selectAll(implicit c: Connection): List[ReferentialConstraintsRow] = {
    SQL"""select constraint_catalog, constraint_schema, constraint_name, unique_constraint_catalog, unique_constraint_schema, unique_constraint_name, match_option, update_rule, delete_rule from information_schema.referential_constraints""".as(ReferentialConstraintsRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[ReferentialConstraintsFieldValue[_]])(implicit c: Connection): List[ReferentialConstraintsRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case ReferentialConstraintsFieldValue.constraintCatalog(value) => NamedParameter("constraint_catalog", ParameterValue.from(value))
          case ReferentialConstraintsFieldValue.constraintSchema(value) => NamedParameter("constraint_schema", ParameterValue.from(value))
          case ReferentialConstraintsFieldValue.constraintName(value) => NamedParameter("constraint_name", ParameterValue.from(value))
          case ReferentialConstraintsFieldValue.uniqueConstraintCatalog(value) => NamedParameter("unique_constraint_catalog", ParameterValue.from(value))
          case ReferentialConstraintsFieldValue.uniqueConstraintSchema(value) => NamedParameter("unique_constraint_schema", ParameterValue.from(value))
          case ReferentialConstraintsFieldValue.uniqueConstraintName(value) => NamedParameter("unique_constraint_name", ParameterValue.from(value))
          case ReferentialConstraintsFieldValue.matchOption(value) => NamedParameter("match_option", ParameterValue.from(value))
          case ReferentialConstraintsFieldValue.updateRule(value) => NamedParameter("update_rule", ParameterValue.from(value))
          case ReferentialConstraintsFieldValue.deleteRule(value) => NamedParameter("delete_rule", ParameterValue.from(value))
        }
        SQL"""select * from information_schema.referential_constraints where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(ReferentialConstraintsRow.rowParser.*)
    }

  }
}
