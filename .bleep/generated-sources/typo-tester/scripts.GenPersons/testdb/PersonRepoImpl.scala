package testdb

import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlParser
import anorm.SqlStringInterpolation
import java.sql.Connection
import testdb.Defaulted.Provided
import testdb.Defaulted.UseDefault

trait PersonRepoImpl extends PersonRepo {
  override def selectAll(implicit c: Connection): List[PersonRow] = {
    SQL"""select id, favourite_football_club_id, name, nick_name, blog_url, email, phone, likes_pizza, marital_status_id, work_email, sector from person""".as(PersonRow.rowParser.*)
  }
  override def selectById(id: PersonId)(implicit c: Connection): Option[PersonRow] = {
    SQL"""select id, favourite_football_club_id, name, nick_name, blog_url, email, phone, likes_pizza, marital_status_id, work_email, sector from person where id = $id""".as(PersonRow.rowParser.singleOpt)
  }
  override def selectByIds(ids: List[PersonId])(implicit c: Connection): List[PersonRow] = {
    SQL"""select id, favourite_football_club_id, name, nick_name, blog_url, email, phone, likes_pizza, marital_status_id, work_email, sector from person where id in $ids""".as(PersonRow.rowParser.*)
  }
  override def selectByFieldValues(fieldValues: List[PersonFieldValue[_]])(implicit c: Connection): List[PersonRow] = {
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PersonFieldValue.id(value) => NamedParameter("id", ParameterValue.from(value))
          case PersonFieldValue.favouriteFootballClubId(value) => NamedParameter("favourite_football_club_id", ParameterValue.from(value))
          case PersonFieldValue.name(value) => NamedParameter("name", ParameterValue.from(value))
          case PersonFieldValue.nickName(value) => NamedParameter("nick_name", ParameterValue.from(value))
          case PersonFieldValue.blogUrl(value) => NamedParameter("blog_url", ParameterValue.from(value))
          case PersonFieldValue.email(value) => NamedParameter("email", ParameterValue.from(value))
          case PersonFieldValue.phone(value) => NamedParameter("phone", ParameterValue.from(value))
          case PersonFieldValue.likesPizza(value) => NamedParameter("likes_pizza", ParameterValue.from(value))
          case PersonFieldValue.maritalStatusId(value) => NamedParameter("marital_status_id", ParameterValue.from(value))
          case PersonFieldValue.workEmail(value) => NamedParameter("work_email", ParameterValue.from(value))
          case PersonFieldValue.sector(value) => NamedParameter("sector", ParameterValue.from(value))
        }
        SQL"""select * from person where ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(" AND ")}"""
          .on(namedParams: _*)
          .as(PersonRow.rowParser.*)
    }

  }
  override def updateFieldValues(id: PersonId, fieldValues: List[PersonFieldValue[_]])(implicit c: Connection): Int = {
    fieldValues match {
      case Nil => 0
      case nonEmpty =>
        val namedParams = nonEmpty.map{
          case PersonFieldValue.id(value) => NamedParameter("id", ParameterValue.from(value))
          case PersonFieldValue.favouriteFootballClubId(value) => NamedParameter("favourite_football_club_id", ParameterValue.from(value))
          case PersonFieldValue.name(value) => NamedParameter("name", ParameterValue.from(value))
          case PersonFieldValue.nickName(value) => NamedParameter("nick_name", ParameterValue.from(value))
          case PersonFieldValue.blogUrl(value) => NamedParameter("blog_url", ParameterValue.from(value))
          case PersonFieldValue.email(value) => NamedParameter("email", ParameterValue.from(value))
          case PersonFieldValue.phone(value) => NamedParameter("phone", ParameterValue.from(value))
          case PersonFieldValue.likesPizza(value) => NamedParameter("likes_pizza", ParameterValue.from(value))
          case PersonFieldValue.maritalStatusId(value) => NamedParameter("marital_status_id", ParameterValue.from(value))
          case PersonFieldValue.workEmail(value) => NamedParameter("work_email", ParameterValue.from(value))
          case PersonFieldValue.sector(value) => NamedParameter("sector", ParameterValue.from(value))
        }
        SQL"""update person
          set ${namedParams.map(x => s"${x.name} = {${x.name}}").mkString(", ")}
          where id = ${id}}"""
          .on(namedParams: _*)
          .executeUpdate()
    }

  }
  override def insert(unsaved: PersonRowUnsaved)(implicit c: Connection): PersonId = {
    val namedParameters = List(
      Some(NamedParameter("favourite_football_club_id", ParameterValue.from(unsaved.favouriteFootballClubId))),
      Some(NamedParameter("name", ParameterValue.from(unsaved.name))),
      Some(NamedParameter("nick_name", ParameterValue.from(unsaved.nickName))),
      Some(NamedParameter("blog_url", ParameterValue.from(unsaved.blogUrl))),
      Some(NamedParameter("email", ParameterValue.from(unsaved.email))),
      Some(NamedParameter("phone", ParameterValue.from(unsaved.phone))),
      Some(NamedParameter("likes_pizza", ParameterValue.from(unsaved.likesPizza))),
      unsaved.maritalStatusId match {
        case UseDefault => None
        case Provided(value) => Some(NamedParameter("marital_status_id", ParameterValue.from[String](value)))
      },
      Some(NamedParameter("work_email", ParameterValue.from(unsaved.workEmail))),
      unsaved.sector match {
        case UseDefault => None
        case Provided(value) => Some(NamedParameter("sector", ParameterValue.from[SectorEnum](value)))
      }
    ).flatten

    SQL"""insert into person(${namedParameters.map(_.name).mkString(", ")})
      values (${namedParameters.map(np => s"{${np.name}}").mkString(", ")})
      returning id
      """
      .on(namedParameters :_*)
      .executeInsert(SqlParser.get[PersonId]("id").single)

  }
  override def delete(id: PersonId)(implicit c: Connection): Boolean = {
    SQL"""delete from person where id = ${id}}""".executeUpdate() > 0
  }
}
