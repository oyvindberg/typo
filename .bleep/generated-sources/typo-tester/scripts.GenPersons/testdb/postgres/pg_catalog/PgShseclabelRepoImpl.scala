package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgShseclabelRepoImpl extends PgShseclabelRepo {
  override def selectAll(implicit c: Connection): List[PgShseclabelRow] = {
    SQL"""select objoid, classoid, provider, label from pg_catalog.pg_shseclabel""".as(PgShseclabelRow.rowParser.*)
  }
  override def selectById(objoidAndClassoidAndProvider: PgShseclabelId)(implicit c: Connection): Option[PgShseclabelRow] = {
    SQL"""select objoid, classoid, provider, label from pg_catalog.pg_shseclabel where objoid = ${objoidAndClassoidAndProvider.objoid}, classoid = ${objoidAndClassoidAndProvider.classoid}, provider = ${objoidAndClassoidAndProvider.provider}""".as(PgShseclabelRow.rowParser.singleOpt)
  }
  override def selectByFieldValues(fieldValues: List[PgShseclabelFieldValue[_]])(implicit c: Connection): List[PgShseclabelRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgShseclabelFieldValue.objoid(value) => NamedParameter("objoid", ParameterValue.from(value))
          case PgShseclabelFieldValue.classoid(value) => NamedParameter("classoid", ParameterValue.from(value))
          case PgShseclabelFieldValue.provider(value) => NamedParameter("provider", ParameterValue.from(value))
          case PgShseclabelFieldValue.label(value) => NamedParameter("label", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_shseclabel where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgShseclabelRow.rowParser.*)
    }

  }
  override def updateFieldValues(objoidAndClassoidAndProvider: PgShseclabelId, fieldValues: List[PgShseclabelFieldValue[_]])(implicit c: Connection): Int = {
    fieldValues match {
      case Nil => 0
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgShseclabelFieldValue.objoid(value) => NamedParameter("objoid", ParameterValue.from(value))
          case PgShseclabelFieldValue.classoid(value) => NamedParameter("classoid", ParameterValue.from(value))
          case PgShseclabelFieldValue.provider(value) => NamedParameter("provider", ParameterValue.from(value))
          case PgShseclabelFieldValue.label(value) => NamedParameter("label", ParameterValue.from(value))
        }
        SQL"""update pg_catalog.pg_shseclabel
          set ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(", ")}
          where objoid = ${objoidAndClassoidAndProvider.objoid}, classoid = ${objoidAndClassoidAndProvider.classoid}, provider = ${objoidAndClassoidAndProvider.provider}"""
          .on(namedParams: _*)
          .executeUpdate()
    }

  }
  override def insert(objoidAndClassoidAndProvider: PgShseclabelId, unsaved: PgShseclabelRowUnsaved)(implicit c: Connection): Unit = {
    val namedParameters = List(
      Some(NamedParameter("label", ParameterValue.from(unsaved.label)))
    ).flatten

    SQL"""insert into pg_catalog.pg_shseclabel(objoid, classoid, provider, ${namedParameters.map(_.name).mkString(", ")})
      values (${objoidAndClassoidAndProvider.objoid}, ${objoidAndClassoidAndProvider.classoid}, ${objoidAndClassoidAndProvider.provider}, ${namedParameters.map(np => s"{${np.name}}").mkString(", ")})
      """
      .on(namedParameters :_*)
      .execute()

  }
  override def delete(objoidAndClassoidAndProvider: PgShseclabelId)(implicit c: Connection): Boolean = {
    SQL"""delete from pg_catalog.pg_shseclabel where objoid = ${objoidAndClassoidAndProvider.objoid}, classoid = ${objoidAndClassoidAndProvider.classoid}, provider = ${objoidAndClassoidAndProvider.provider}""".executeUpdate() > 0
  }
}
