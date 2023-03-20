/**
 * File has been automatically generated by `typo` for internal use.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 *
 * (If you're developing `typo` and want to change it: run `bleep generate-sources)
 */
package typo
package generated
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SQL
import anorm.SqlStringInterpolation
import java.sql.Connection

object PgAttrdefRepoImpl extends PgAttrdefRepo {
  override def selectAll(implicit c: Connection): List[PgAttrdefRow] = {
    SQL"""select oid, adrelid, adnum, adbin from pg_catalog.pg_attrdef""".as(PgAttrdefRow.rowParser.*)
  }
  override def selectById(oid: PgAttrdefId)(implicit c: Connection): Option[PgAttrdefRow] = {
    SQL"""select oid, adrelid, adnum, adbin from pg_catalog.pg_attrdef where oid = $oid""".as(PgAttrdefRow.rowParser.singleOpt)
  }
  override def selectByIds(oids: List[PgAttrdefId])(implicit c: Connection): List[PgAttrdefRow] = {
    SQL"""select oid, adrelid, adnum, adbin from pg_catalog.pg_attrdef where oid in $oids""".as(PgAttrdefRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PgAttrdefFieldValue[_]])(implicit c: Connection): List[PgAttrdefRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgAttrdefFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgAttrdefFieldValue.adrelid(value) => NamedParameter("adrelid", ParameterValue.from(value))
          case PgAttrdefFieldValue.adnum(value) => NamedParameter("adnum", ParameterValue.from(value))
          case PgAttrdefFieldValue.adbin(value) => NamedParameter("adbin", ParameterValue.from(value))
        }
        val q = s"""select * from pg_catalog.pg_attrdef where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
        SQL(q)
          .on(namedParams: _*)
          .as(PgAttrdefRow.rowParser.*)
    }

  }
  override def updateFieldValues(oid: PgAttrdefId, fieldValues: List[PgAttrdefFieldValue[_]])(implicit c: Connection): Int = {
    fieldValues match {
      case Nil => 0
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgAttrdefFieldValue.oid(value) => NamedParameter("oid", ParameterValue.from(value))
          case PgAttrdefFieldValue.adrelid(value) => NamedParameter("adrelid", ParameterValue.from(value))
          case PgAttrdefFieldValue.adnum(value) => NamedParameter("adnum", ParameterValue.from(value))
          case PgAttrdefFieldValue.adbin(value) => NamedParameter("adbin", ParameterValue.from(value))
        }
        val q = s"""update pg_catalog.pg_attrdef
          set ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(", ")}
          where oid = $oid"""
        SQL(q)
          .on(namedParams: _*)
          .executeUpdate()
    }

  }
  override def insert(oid: PgAttrdefId, unsaved: PgAttrdefRowUnsaved)(implicit c: Connection): Unit = {
    val namedParameters = List(
      Some(NamedParameter("adrelid", ParameterValue.from(unsaved.adrelid))),
      Some(NamedParameter("adnum", ParameterValue.from(unsaved.adnum))),
      Some(NamedParameter("adbin", ParameterValue.from(unsaved.adbin)))
    ).flatten

    SQL"""insert into pg_catalog.pg_attrdef(oid, ${namedParameters.map(_.name).mkString(", ")})
      values (${oid}, ${namedParameters.map(np => s"{${np.name}}").mkString(", ")})
      """
      .on(namedParameters :_*)
      .execute()

  }
  override def delete(oid: PgAttrdefId)(implicit c: Connection): Boolean = {
    SQL"""delete from pg_catalog.pg_attrdef where oid = $oid""".executeUpdate() > 0
  }
  override def selectByUnique(adrelid: Long, adnum: Short)(implicit c: Connection): Option[PgAttrdefRow] = {
    ???
  }
}