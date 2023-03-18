package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgInitPrivsRepoImpl extends PgInitPrivsRepo {
  override def selectAll(implicit c: Connection): List[PgInitPrivsRow] = {
    SQL"""select objoid, classoid, objsubid, privtype, initprivs from pg_catalog.pg_init_privs""".as(PgInitPrivsRow.rowParser.*)
  }
  override def selectById(objoidAndClassoidAndObjsubid: PgInitPrivsId)(implicit c: Connection): Option[PgInitPrivsRow] = {
    SQL"""select objoid, classoid, objsubid, privtype, initprivs from pg_catalog.pg_init_privs where objoid = ${objoidAndClassoidAndObjsubid.objoid}, classoid = ${objoidAndClassoidAndObjsubid.classoid}, objsubid = ${objoidAndClassoidAndObjsubid.objsubid}""".as(PgInitPrivsRow.rowParser.singleOpt)
  }
  override def selectByFieldValues(fieldValues: List[PgInitPrivsFieldValue[_]])(implicit c: Connection): List[PgInitPrivsRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgInitPrivsFieldValue.objoid(value) => NamedParameter("objoid", ParameterValue.from(value))
          case PgInitPrivsFieldValue.classoid(value) => NamedParameter("classoid", ParameterValue.from(value))
          case PgInitPrivsFieldValue.objsubid(value) => NamedParameter("objsubid", ParameterValue.from(value))
          case PgInitPrivsFieldValue.privtype(value) => NamedParameter("privtype", ParameterValue.from(value))
          case PgInitPrivsFieldValue.initprivs(value) => NamedParameter("initprivs", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_init_privs where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgInitPrivsRow.rowParser.*)
    }

  }
  override def updateFieldValues(objoidAndClassoidAndObjsubid: PgInitPrivsId, fieldValues: List[PgInitPrivsFieldValue[_]])(implicit c: Connection): Int = {
    fieldValues match {
      case Nil => 0
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgInitPrivsFieldValue.objoid(value) => NamedParameter("objoid", ParameterValue.from(value))
          case PgInitPrivsFieldValue.classoid(value) => NamedParameter("classoid", ParameterValue.from(value))
          case PgInitPrivsFieldValue.objsubid(value) => NamedParameter("objsubid", ParameterValue.from(value))
          case PgInitPrivsFieldValue.privtype(value) => NamedParameter("privtype", ParameterValue.from(value))
          case PgInitPrivsFieldValue.initprivs(value) => NamedParameter("initprivs", ParameterValue.from(value))
        }
        SQL"""update pg_catalog.pg_init_privs
          set ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(", ")}
          where objoid = ${objoidAndClassoidAndObjsubid.objoid}, classoid = ${objoidAndClassoidAndObjsubid.classoid}, objsubid = ${objoidAndClassoidAndObjsubid.objsubid}"""
          .on(namedParams: _*)
          .executeUpdate()
    }

  }
  override def insert(objoidAndClassoidAndObjsubid: PgInitPrivsId, unsaved: PgInitPrivsRowUnsaved)(implicit c: Connection): Unit = {
    val namedParameters = List(
      Some(NamedParameter("privtype", ParameterValue.from(unsaved.privtype))),
      Some(NamedParameter("initprivs", ParameterValue.from(unsaved.initprivs)))
    ).flatten

    SQL"""insert into pg_catalog.pg_init_privs(objoid, classoid, objsubid, ${namedParameters.map(_.name).mkString(", ")})
      values (${objoidAndClassoidAndObjsubid.objoid}, ${objoidAndClassoidAndObjsubid.classoid}, ${objoidAndClassoidAndObjsubid.objsubid}, ${namedParameters.map(np => s"{${np.name}}").mkString(", ")})
      """
      .on(namedParameters :_*)
      .execute()

  }
  override def delete(objoidAndClassoidAndObjsubid: PgInitPrivsId)(implicit c: Connection): Boolean = {
    SQL"""delete from pg_catalog.pg_init_privs where objoid = ${objoidAndClassoidAndObjsubid.objoid}, classoid = ${objoidAndClassoidAndObjsubid.classoid}, objsubid = ${objoidAndClassoidAndObjsubid.objsubid}""".executeUpdate() > 0
  }
}
