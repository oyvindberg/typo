package testdb

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection

trait FootballClubRepoImpl extends FootballClubRepo {
  override def selectAll(implicit c: Connection): List[FootballClubRow] = {
    SQL"""select id, name from football_club""".as(FootballClubRow.rowParser.*)
  }
  override def selectById(id: FootballClubId)(implicit c: Connection): Option[FootballClubRow] = {
    SQL"""select id, name from football_club where id = $id""".as(FootballClubRow.rowParser.singleOpt)
  }
  override def selectByIds(ids: List[FootballClubId])(implicit c: Connection): List[FootballClubRow] = {
    SQL"""select id, name from football_club where id in $ids""".as(FootballClubRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[FootballClubFieldValue[_]])(implicit c: Connection): List[FootballClubRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case FootballClubFieldValue.id(value) => NamedParameter("id", ParameterValue.from(value))
          case FootballClubFieldValue.name(value) => NamedParameter("name", ParameterValue.from(value))
        }
        SQL"""select * from football_club where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(FootballClubRow.rowParser.*)
    }

  }
  override def updateFieldValues(id: FootballClubId, fieldValues: List[FootballClubFieldValue[_]])(implicit c: Connection): Int = {
    fieldValues match {
      case Nil => 0
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case FootballClubFieldValue.id(value) => NamedParameter("id", ParameterValue.from(value))
          case FootballClubFieldValue.name(value) => NamedParameter("name", ParameterValue.from(value))
        }
        SQL"""update football_club
          set ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(", ")}
          where id = ${id}}"""
          .on(namedParams: _*)
          .executeUpdate()
    }

  }
  override def insert(id: FootballClubId, unsaved: FootballClubRowUnsaved)(implicit c: Connection): Unit = {
    ???
  }
  override def delete(id: FootballClubId)(implicit c: Connection): Boolean = {
    SQL"""delete from football_club where id = ${id}}""".executeUpdate() > 0
  }
}
