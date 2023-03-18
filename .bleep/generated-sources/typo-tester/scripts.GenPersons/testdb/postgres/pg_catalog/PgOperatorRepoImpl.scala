package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgOperatorRepoImpl extends PgOperatorRepo {
  override def selectAll(implicit c: Connection): List[PgOperatorRow] = {
    SQL"""select oid, oprname, oprnamespace, oprowner, oprkind, oprcanmerge, oprcanhash, oprleft, oprright, oprresult, oprcom, oprnegate, oprcode, oprrest, oprjoin from pg_catalog.pg_operator""".as(PgOperatorRow.rowParser.*)
  }
  override def selectById(oid: PgOperatorId)(implicit c: Connection): Option[PgOperatorRow] = {
    SQL"""select oid, oprname, oprnamespace, oprowner, oprkind, oprcanmerge, oprcanhash, oprleft, oprright, oprresult, oprcom, oprnegate, oprcode, oprrest, oprjoin from pg_catalog.pg_operator where oid = $oid""".as(PgOperatorRow.rowParser.singleOpt)
  }
  override def selectByIds(oids: List[PgOperatorId])(implicit c: Connection): List[PgOperatorRow] = {
    SQL"""select oid, oprname, oprnamespace, oprowner, oprkind, oprcanmerge, oprcanhash, oprleft, oprright, oprresult, oprcom, oprnegate, oprcode, oprrest, oprjoin from pg_catalog.pg_operator where oid in $oids""".as(PgOperatorRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgOperatorFieldValue[_]])(implicit c: Connection): List[PgOperatorRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgOperatorFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgOperatorFieldValue.oprname(value) => NamedParameter("oprname", ParameterValue.from(value))
          case PgOperatorFieldValue.oprnamespace(value) => NamedParameter("oprnamespace", ParameterValue.from(value))
          case PgOperatorFieldValue.oprowner(value) => NamedParameter("oprowner", ParameterValue.from(value))
          case PgOperatorFieldValue.oprkind(value) => NamedParameter("oprkind", ParameterValue.from(value))
          case PgOperatorFieldValue.oprcanmerge(value) => NamedParameter("oprcanmerge", ParameterValue.from(value))
          case PgOperatorFieldValue.oprcanhash(value) => NamedParameter("oprcanhash", ParameterValue.from(value))
          case PgOperatorFieldValue.oprleft(value) => NamedParameter("oprleft", ParameterValue.from(value))
          case PgOperatorFieldValue.oprright(value) => NamedParameter("oprright", ParameterValue.from(value))
          case PgOperatorFieldValue.oprresult(value) => NamedParameter("oprresult", ParameterValue.from(value))
          case PgOperatorFieldValue.oprcom(value) => NamedParameter("oprcom", ParameterValue.from(value))
          case PgOperatorFieldValue.oprnegate(value) => NamedParameter("oprnegate", ParameterValue.from(value))
          case PgOperatorFieldValue.oprcode(value) => NamedParameter("oprcode", ParameterValue.from(value))
          case PgOperatorFieldValue.oprrest(value) => NamedParameter("oprrest", ParameterValue.from(value))
          case PgOperatorFieldValue.oprjoin(value) => NamedParameter("oprjoin", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_operator where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgOperatorRow.rowParser.*)
    }

  }
  override def updateFieldValues(oid: PgOperatorId, fieldValues: List[PgOperatorFieldValue[_]])(implicit c: Connection): Int = {
    fieldValues match {
      case Nil => 0
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgOperatorFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgOperatorFieldValue.oprname(value) => NamedParameter("oprname", ParameterValue.from(value))
          case PgOperatorFieldValue.oprnamespace(value) => NamedParameter("oprnamespace", ParameterValue.from(value))
          case PgOperatorFieldValue.oprowner(value) => NamedParameter("oprowner", ParameterValue.from(value))
          case PgOperatorFieldValue.oprkind(value) => NamedParameter("oprkind", ParameterValue.from(value))
          case PgOperatorFieldValue.oprcanmerge(value) => NamedParameter("oprcanmerge", ParameterValue.from(value))
          case PgOperatorFieldValue.oprcanhash(value) => NamedParameter("oprcanhash", ParameterValue.from(value))
          case PgOperatorFieldValue.oprleft(value) => NamedParameter("oprleft", ParameterValue.from(value))
          case PgOperatorFieldValue.oprright(value) => NamedParameter("oprright", ParameterValue.from(value))
          case PgOperatorFieldValue.oprresult(value) => NamedParameter("oprresult", ParameterValue.from(value))
          case PgOperatorFieldValue.oprcom(value) => NamedParameter("oprcom", ParameterValue.from(value))
          case PgOperatorFieldValue.oprnegate(value) => NamedParameter("oprnegate", ParameterValue.from(value))
          case PgOperatorFieldValue.oprcode(value) => NamedParameter("oprcode", ParameterValue.from(value))
          case PgOperatorFieldValue.oprrest(value) => NamedParameter("oprrest", ParameterValue.from(value))
          case PgOperatorFieldValue.oprjoin(value) => NamedParameter("oprjoin", ParameterValue.from(value))
        }
        SQL"""update pg_catalog.pg_operator
          set ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(", ")}
          where oid = $oid"""
          .on(namedParams: _*)
          .executeUpdate()
    }

  }
  override def insert(oid: PgOperatorId, unsaved: PgOperatorRowUnsaved)(implicit c: Connection): Unit = {
    val namedParameters = List(
      Some(NamedParameter("oprname", ParameterValue.from(unsaved.oprname))),
      Some(NamedParameter("oprnamespace", ParameterValue.from(unsaved.oprnamespace))),
      Some(NamedParameter("oprowner", ParameterValue.from(unsaved.oprowner))),
      Some(NamedParameter("oprkind", ParameterValue.from(unsaved.oprkind))),
      Some(NamedParameter("oprcanmerge", ParameterValue.from(unsaved.oprcanmerge))),
      Some(NamedParameter("oprcanhash", ParameterValue.from(unsaved.oprcanhash))),
      Some(NamedParameter("oprleft", ParameterValue.from(unsaved.oprleft))),
      Some(NamedParameter("oprright", ParameterValue.from(unsaved.oprright))),
      Some(NamedParameter("oprresult", ParameterValue.from(unsaved.oprresult))),
      Some(NamedParameter("oprcom", ParameterValue.from(unsaved.oprcom))),
      Some(NamedParameter("oprnegate", ParameterValue.from(unsaved.oprnegate))),
      Some(NamedParameter("oprcode", ParameterValue.from(unsaved.oprcode))),
      Some(NamedParameter("oprrest", ParameterValue.from(unsaved.oprrest))),
      Some(NamedParameter("oprjoin", ParameterValue.from(unsaved.oprjoin)))
    ).flatten

    SQL"""insert into pg_catalog.pg_operator(oid, ${namedParameters.map(_.name).mkString(", ")})
      values (${oid}, ${namedParameters.map(np => s"{${np.name}}").mkString(", ")})
      """
      .on(namedParameters :_*)
      .execute()

  }
  override def delete(oid: PgOperatorId)(implicit c: Connection): Boolean = {
    SQL"""delete from pg_catalog.pg_operator where oid = $oid""".executeUpdate() > 0
  }
  override def selectByUnique(oprname: String, oprleft: Long, oprright: Long, oprnamespace: Long)(implicit c: Connection): Option[PgOperatorRow] = {
    ???
  }
}
