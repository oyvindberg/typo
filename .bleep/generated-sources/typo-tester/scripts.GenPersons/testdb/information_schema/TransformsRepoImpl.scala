package testdb.information_schema

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait TransformsRepoImpl extends TransformsRepo {
  override def selectAll(implicit c: Connection): List[TransformsRow] = {
    SQL"""select udt_catalog, udt_schema, udt_name, specific_catalog, specific_schema, specific_name, group_name, transform_type from information_schema.transforms""".as(TransformsRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[TransformsFieldValue[_]])(implicit c: Connection): List[TransformsRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case TransformsFieldValue.udtCatalog(value) => NamedParameter("udt_catalog", ParameterValue.from(value))
          case TransformsFieldValue.udtSchema(value) => NamedParameter("udt_schema", ParameterValue.from(value))
          case TransformsFieldValue.udtName(value) => NamedParameter("udt_name", ParameterValue.from(value))
          case TransformsFieldValue.specificCatalog(value) => NamedParameter("specific_catalog", ParameterValue.from(value))
          case TransformsFieldValue.specificSchema(value) => NamedParameter("specific_schema", ParameterValue.from(value))
          case TransformsFieldValue.specificName(value) => NamedParameter("specific_name", ParameterValue.from(value))
          case TransformsFieldValue.groupName(value) => NamedParameter("group_name", ParameterValue.from(value))
          case TransformsFieldValue.transformType(value) => NamedParameter("transform_type", ParameterValue.from(value))
        }
        SQL"""select * from information_schema.transforms where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(TransformsRow.rowParser.*)
    }

  }
}
