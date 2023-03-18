package testdb
package postgres
package pg_catalog

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait PgInheritsRepoImpl extends PgInheritsRepo {
  override def selectAll(implicit c: Connection): List[PgInheritsRow] = {
    SQL"""select inhrelid, inhparent, inhseqno, inhdetachpending from pg_catalog.pg_inherits""".as(PgInheritsRow.rowParser.*)
  }
  override def selectById(inhrelidAndInhseqno: PgInheritsId)(implicit c: Connection): Option[PgInheritsRow] = {
    SQL"""select inhrelid, inhparent, inhseqno, inhdetachpending from pg_catalog.pg_inherits where inhrelid = ${inhrelidAndInhseqno.inhrelid}, inhseqno = ${inhrelidAndInhseqno.inhseqno}""".as(PgInheritsRow.rowParser.singleOpt)
  }
  override def selectByFieldValues(fieldValues: List[PgInheritsFieldValue[_]])(implicit c: Connection): List[PgInheritsRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgInheritsFieldValue.inhrelid(value) => NamedParameter("inhrelid", ParameterValue.from(value))
          case PgInheritsFieldValue.inhparent(value) => NamedParameter("inhparent", ParameterValue.from(value))
          case PgInheritsFieldValue.inhseqno(value) => NamedParameter("inhseqno", ParameterValue.from(value))
          case PgInheritsFieldValue.inhdetachpending(value) => NamedParameter("inhdetachpending", ParameterValue.from(value))
        }
        SQL"""select * from pg_catalog.pg_inherits where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PgInheritsRow.rowParser.*)
    }

  }
  override def updateFieldValues(inhrelidAndInhseqno: PgInheritsId, fieldValues: List[PgInheritsFieldValue[_]])(implicit c: Connection): Int = {
    fieldValues match {
      case Nil => 0
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PgInheritsFieldValue.inhrelid(value) => NamedParameter("inhrelid", ParameterValue.from(value))
          case PgInheritsFieldValue.inhparent(value) => NamedParameter("inhparent", ParameterValue.from(value))
          case PgInheritsFieldValue.inhseqno(value) => NamedParameter("inhseqno", ParameterValue.from(value))
          case PgInheritsFieldValue.inhdetachpending(value) => NamedParameter("inhdetachpending", ParameterValue.from(value))
        }
        SQL"""update pg_catalog.pg_inherits
          set ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(", ")}
          where inhrelid = ${inhrelidAndInhseqno.inhrelid}, inhseqno = ${inhrelidAndInhseqno.inhseqno}"""
          .on(namedParams: _*)
          .executeUpdate()
    }

  }
  override def insert(inhrelidAndInhseqno: PgInheritsId, unsaved: PgInheritsRowUnsaved)(implicit c: Connection): Unit = {
    val namedParameters = List(
      Some(NamedParameter("inhparent", ParameterValue.from(unsaved.inhparent))),
      Some(NamedParameter("inhdetachpending", ParameterValue.from(unsaved.inhdetachpending)))
    ).flatten

    SQL"""insert into pg_catalog.pg_inherits(inhrelid, inhseqno, ${namedParameters.map(_.name).mkString(", ")})
      values (${inhrelidAndInhseqno.inhrelid}, ${inhrelidAndInhseqno.inhseqno}, ${namedParameters.map(np => s"{${np.name}}").mkString(", ")})
      """
      .on(namedParameters :_*)
      .execute()

  }
  override def delete(inhrelidAndInhseqno: PgInheritsId)(implicit c: Connection): Boolean = {
    SQL"""delete from pg_catalog.pg_inherits where inhrelid = ${inhrelidAndInhseqno.inhrelid}, inhseqno = ${inhrelidAndInhseqno.inhseqno}""".executeUpdate() > 0
  }
}
