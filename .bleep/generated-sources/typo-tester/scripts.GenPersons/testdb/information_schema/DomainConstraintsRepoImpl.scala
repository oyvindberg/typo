package testdb.information_schema

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait DomainConstraintsRepoImpl extends DomainConstraintsRepo {
  override def selectAll(implicit c: Connection): List[DomainConstraintsRow] = {
    SQL"""select constraint_catalog, constraint_schema, constraint_name, domain_catalog, domain_schema, domain_name, is_deferrable, initially_deferred from information_schema.domain_constraints""".as(DomainConstraintsRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[DomainConstraintsFieldValue[_]])(implicit c: Connection): List[DomainConstraintsRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case DomainConstraintsFieldValue.constraintCatalog(value) => NamedParameter("constraint_catalog", ParameterValue.from(value))
          case DomainConstraintsFieldValue.constraintSchema(value) => NamedParameter("constraint_schema", ParameterValue.from(value))
          case DomainConstraintsFieldValue.constraintName(value) => NamedParameter("constraint_name", ParameterValue.from(value))
          case DomainConstraintsFieldValue.domainCatalog(value) => NamedParameter("domain_catalog", ParameterValue.from(value))
          case DomainConstraintsFieldValue.domainSchema(value) => NamedParameter("domain_schema", ParameterValue.from(value))
          case DomainConstraintsFieldValue.domainName(value) => NamedParameter("domain_name", ParameterValue.from(value))
          case DomainConstraintsFieldValue.isDeferrable(value) => NamedParameter("is_deferrable", ParameterValue.from(value))
          case DomainConstraintsFieldValue.initiallyDeferred(value) => NamedParameter("initially_deferred", ParameterValue.from(value))
        }
        SQL"""select * from information_schema.domain_constraints where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(DomainConstraintsRow.rowParser.*)
    }

  }
}
