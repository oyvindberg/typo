package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgDescriptionRepoImpl extends PgDescriptionRepo {
  override def selectAll(implicit c: Connection): List[PgDescriptionRow] = {
    SQL"""select objoid, classoid, objsubid, description from pg_catalog.pg_description""".as(PgDescriptionRow.rowParser.*)
  }
  override def selectById(objoidAndClassoidAndObjsubid: PgDescriptionId)(implicit c: Connection): Option[PgDescriptionRow] = {
    SQL"""select objoid, classoid, objsubid, description from pg_catalog.pg_description where objoid = ${objoidAndClassoidAndObjsubid.objoid}, classoid = ${objoidAndClassoidAndObjsubid.classoid}, objsubid = ${objoidAndClassoidAndObjsubid.objsubid}""".as(PgDescriptionRow.rowParser.singleOpt)
  }
  override def selectByFieldValues(fieldValues: List[PgDescriptionFieldValue[_]])(implicit c: Connection): List[PgDescriptionRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgDescriptionFieldValue.objoid(value) => NamedParameter("objoid", ParameterValue.from(value))
          case PgDescriptionFieldValue.classoid(value) => NamedParameter("classoid", ParameterValue.from(value))
          case PgDescriptionFieldValue.objsubid(value) => NamedParameter("objsubid", ParameterValue.from(value))
          case PgDescriptionFieldValue.description(value) => NamedParameter("description", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_description where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgDescriptionRow.rowParser.*)
    }

  }
  override def updateFieldValues(objoidAndClassoidAndObjsubid: PgDescriptionId, fieldValues: List[PgDescriptionFieldValue[_]])(implicit c: Connection): Int = {
    fieldValues match {
      case Nil => 0
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgDescriptionFieldValue.objoid(value) => NamedParameter("objoid", ParameterValue.from(value))
          case PgDescriptionFieldValue.classoid(value) => NamedParameter("classoid", ParameterValue.from(value))
          case PgDescriptionFieldValue.objsubid(value) => NamedParameter("objsubid", ParameterValue.from(value))
          case PgDescriptionFieldValue.description(value) => NamedParameter("description", ParameterValue.from(value))
        }
        SQL"""update pg_catalog.pg_description
          set ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(", ")}
          where objoid = ${objoidAndClassoidAndObjsubid.objoid}, classoid = ${objoidAndClassoidAndObjsubid.classoid}, objsubid = ${objoidAndClassoidAndObjsubid.objsubid}"""
          .on(namedParams: _*)
          .executeUpdate()
    }

  }
  override def insert(objoidAndClassoidAndObjsubid: PgDescriptionId, unsaved: PgDescriptionRowUnsaved)(implicit c: Connection): Unit = {
    val namedParameters = List(
      Some(NamedParameter("description", ParameterValue.from(unsaved.description)))
    ).flatten

    SQL"""insert into pg_catalog.pg_description(objoid, classoid, objsubid, ${namedParameters.map(_.name).mkString(", ")})
      values (${objoidAndClassoidAndObjsubid.objoid}, ${objoidAndClassoidAndObjsubid.classoid}, ${objoidAndClassoidAndObjsubid.objsubid}, ${namedParameters.map(np => s"{${np.name}}").mkString(", ")})
      """
      .on(namedParameters :_*)
      .execute()

  }
  override def delete(objoidAndClassoidAndObjsubid: PgDescriptionId)(implicit c: Connection): Boolean = {
    SQL"""delete from pg_catalog.pg_description where objoid = ${objoidAndClassoidAndObjsubid.objoid}, classoid = ${objoidAndClassoidAndObjsubid.classoid}, objsubid = ${objoidAndClassoidAndObjsubid.objsubid}""".executeUpdate() > 0
  }
}
