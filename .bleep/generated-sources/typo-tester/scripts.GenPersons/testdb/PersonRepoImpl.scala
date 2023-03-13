package testdb

import anorm.SqlStringInterpolation
import java.sql.Connection

trait PersonRepoImpl extends PersonRepo {
  override def selectAll(implicit c: Connection): List[PersonRow] = 
    SQL"""select id, favourite_football_club_id, name, nick_name, blog_url, email, phone, likes_pizza, marital_status_id, work_email, sector from person""".as(PersonRow.rowParser.*)
  override def selectById(id: PersonId)(implicit c: Connection): Option[PersonRow] = 
    SQL"""select id, favourite_football_club_id, name, nick_name, blog_url, email, phone, likes_pizza, marital_status_id, work_email, sector from person where id = $id""".as(PersonRow.rowParser.singleOpt)
  override def selectByIds(ids: List[PersonId])(implicit c: Connection): List[PersonRow] = 
    SQL"""select id, favourite_football_club_id, name, nick_name, blog_url, email, phone, likes_pizza, marital_status_id, work_email, sector from person where id in $ids""".as(PersonRow.rowParser.*)
  override def selectByFieldValues(fieldValues: List[PersonFieldValue[_]])(implicit c: Connection): List[PersonRow] = 
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        SQL"""select * from person where ${nonEmpty.map(x => s"{${x.name}}")}"""
          .on(nonEmpty.map(_.toNamedParameter): _*)
          .as(PersonRow.rowParser.*)
    }

  override def updateFieldValues(id: PersonId, fieldValues: List[PersonFieldValue[_]])(implicit c: Connection): Int = 
    fieldValues match {
      case Nil => 0
      case nonEmpty =>
        SQL"""update person set ${nonEmpty.map(x => s"${x.name} = {${x.name}}").mkString(", ")} where id = ${id}}"""
          .on(nonEmpty.map(_.toNamedParameter): _*)
          .executeUpdate()
    }

  override def insert(unsaved: PersonRowUnsaved)(implicit c: Connection): PersonId = 
    ???
  override def delete(id: PersonId)(implicit c: Connection): Boolean = 
    ???
}
