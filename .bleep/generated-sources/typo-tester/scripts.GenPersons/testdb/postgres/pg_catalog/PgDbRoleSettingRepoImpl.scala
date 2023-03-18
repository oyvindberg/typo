package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgDbRoleSettingRepoImpl extends PgDbRoleSettingRepo {
  override def selectAll(implicit c: Connection): List[PgDbRoleSettingRow] = {
    SQL"""select setdatabase, setrole, setconfig from pg_catalog.pg_db_role_setting""".as(PgDbRoleSettingRow.rowParser.*)
  }
  override def selectById(setdatabaseAndSetrole: PgDbRoleSettingId)(implicit c: Connection): Option[PgDbRoleSettingRow] = {
    SQL"""select setdatabase, setrole, setconfig from pg_catalog.pg_db_role_setting where setdatabase = ${setdatabaseAndSetrole.setdatabase}, setrole = ${setdatabaseAndSetrole.setrole}""".as(PgDbRoleSettingRow.rowParser.singleOpt)
  }
  override def selectByFieldValues(fieldValues: List[PgDbRoleSettingFieldValue[_]])(implicit c: Connection): List[PgDbRoleSettingRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgDbRoleSettingFieldValue.setdatabase(value) => NamedParameter("setdatabase", ParameterValue.from(value))
          case PgDbRoleSettingFieldValue.setrole(value) => NamedParameter("setrole", ParameterValue.from(value))
          case PgDbRoleSettingFieldValue.setconfig(value) => NamedParameter("setconfig", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_db_role_setting where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgDbRoleSettingRow.rowParser.*)
    }

  }
  override def updateFieldValues(setdatabaseAndSetrole: PgDbRoleSettingId, fieldValues: List[PgDbRoleSettingFieldValue[_]])(implicit c: Connection): Int = {
    fieldValues match {
      case Nil => 0
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgDbRoleSettingFieldValue.setdatabase(value) => NamedParameter("setdatabase", ParameterValue.from(value))
          case PgDbRoleSettingFieldValue.setrole(value) => NamedParameter("setrole", ParameterValue.from(value))
          case PgDbRoleSettingFieldValue.setconfig(value) => NamedParameter("setconfig", ParameterValue.from(value))
        }
        SQL"""update pg_catalog.pg_db_role_setting
          set ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(", ")}
          where setdatabase = ${setdatabaseAndSetrole.setdatabase}, setrole = ${setdatabaseAndSetrole.setrole}"""
          .on(namedParams: _*)
          .executeUpdate()
    }

  }
  override def insert(setdatabaseAndSetrole: PgDbRoleSettingId, unsaved: PgDbRoleSettingRowUnsaved)(implicit c: Connection): Unit = {
    val namedParameters = List(
      Some(NamedParameter("setconfig", ParameterValue.from(unsaved.setconfig)))
    ).flatten

    SQL"""insert into pg_catalog.pg_db_role_setting(setdatabase, setrole, ${namedParameters.map(_.name).mkString(", ")})
      values (${setdatabaseAndSetrole.setdatabase}, ${setdatabaseAndSetrole.setrole}, ${namedParameters.map(np => s"{${np.name}}").mkString(", ")})
      """
      .on(namedParameters :_*)
      .execute()

  }
  override def delete(setdatabaseAndSetrole: PgDbRoleSettingId)(implicit c: Connection): Boolean = {
    SQL"""delete from pg_catalog.pg_db_role_setting where setdatabase = ${setdatabaseAndSetrole.setdatabase}, setrole = ${setdatabaseAndSetrole.setrole}""".executeUpdate() > 0
  }
}
