package testdb
package information_schema

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait InformationSchemaCatalogNameRepoImpl extends InformationSchemaCatalogNameRepo {
  override def selectAll(implicit c: Connection): List[InformationSchemaCatalogNameRow] = {
    SQL"""select catalog_name from information_schema.information_schema_catalog_name""".as(InformationSchemaCatalogNameRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[InformationSchemaCatalogNameFieldValue[_]])(implicit c: Connection): List[InformationSchemaCatalogNameRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case InformationSchemaCatalogNameFieldValue.catalogName(value) => NamedParameter("catalog_name", ParameterValue.from(value))
        }
        SQL"""select * from information_schema.information_schema_catalog_name where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(InformationSchemaCatalogNameRow.rowParser.*)
    }

  }
}
