package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgUserMappingRepoImpl extends PgUserMappingRepo {
  override def selectAll(implicit c: Connection): List[PgUserMappingRow] = {
    SQL"""select oid, umuser, umserver, umoptions from pg_catalog.pg_user_mapping""".as(PgUserMappingRow.rowParser.*)
  }
  override def selectById(oid: PgUserMappingId)(implicit c: Connection): Option[PgUserMappingRow] = {
    SQL"""select oid, umuser, umserver, umoptions from pg_catalog.pg_user_mapping where oid = $oid""".as(PgUserMappingRow.rowParser.singleOpt)
  }
  override def selectByIds(oids: List[PgUserMappingId])(implicit c: Connection): List[PgUserMappingRow] = {
    SQL"""select oid, umuser, umserver, umoptions from pg_catalog.pg_user_mapping where oid in $oids""".as(PgUserMappingRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgUserMappingFieldValue[_]])(implicit c: Connection): List[PgUserMappingRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgUserMappingFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgUserMappingFieldValue.umuser(value) => NamedParameter("umuser", ParameterValue.from(value))
          case PgUserMappingFieldValue.umserver(value) => NamedParameter("umserver", ParameterValue.from(value))
          case PgUserMappingFieldValue.umoptions(value) => NamedParameter("umoptions", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_user_mapping where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgUserMappingRow.rowParser.*)
    }

  }
  override def updateFieldValues(oid: PgUserMappingId, fieldValues: List[PgUserMappingFieldValue[_]])(implicit c: Connection): Int = {
    fieldValues match {
      case Nil => 0
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgUserMappingFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgUserMappingFieldValue.umuser(value) => NamedParameter("umuser", ParameterValue.from(value))
          case PgUserMappingFieldValue.umserver(value) => NamedParameter("umserver", ParameterValue.from(value))
          case PgUserMappingFieldValue.umoptions(value) => NamedParameter("umoptions", ParameterValue.from(value))
        }
        SQL"""update pg_catalog.pg_user_mapping
          set ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(", ")}
          where oid = $oid"""
          .on(namedParams: _*)
          .executeUpdate()
    }

  }
  override def insert(oid: PgUserMappingId, unsaved: PgUserMappingRowUnsaved)(implicit c: Connection): Unit = {
    val namedParameters = List(
      Some(NamedParameter("umuser", ParameterValue.from(unsaved.umuser))),
      Some(NamedParameter("umserver", ParameterValue.from(unsaved.umserver))),
      Some(NamedParameter("umoptions", ParameterValue.from(unsaved.umoptions)))
    ).flatten

    SQL"""insert into pg_catalog.pg_user_mapping(oid, ${namedParameters.map(_.name).mkString(", ")})
      values (${oid}, ${namedParameters.map(np => s"{${np.name}}").mkString(", ")})
      """
      .on(namedParameters :_*)
      .execute()

  }
  override def delete(oid: PgUserMappingId)(implicit c: Connection): Boolean = {
    SQL"""delete from pg_catalog.pg_user_mapping where oid = $oid""".executeUpdate() > 0
  }
  override def selectByUnique(umuser: Long, umserver: Long)(implicit c: Connection): Option[PgUserMappingRow] = {
    ???
  }
}
