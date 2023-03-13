package testdb

import anorm.SqlStringInterpolation
import java.sql.Connection

trait MaritalStatusRepoImpl extends MaritalStatusRepo {
  override def selectAll(implicit c: Connection): List[MaritalStatusRow] = 
    SQL"""select id from marital_status""".as(MaritalStatusRow.rowParser.*)
  override def selectById(id: MaritalStatusId)(implicit c: Connection): Option[MaritalStatusRow] = 
    SQL"""select id from marital_status where id = $id""".as(MaritalStatusRow.rowParser.singleOpt)
  override def selectByIds(ids: List[MaritalStatusId])(implicit c: Connection): List[MaritalStatusRow] = 
    SQL"""select id from marital_status where id in $ids""".as(MaritalStatusRow.rowParser.*)
  override def selectByFieldValues(fieldValues: List[MaritalStatusFieldValue[_]])(implicit c: Connection): List[MaritalStatusRow] = 
    fieldValues match {
      case Nil => selectAll
      case nonEmpty =>
        SQL"""select * from marital_status where ${nonEmpty.map(x => s"{${x.name}}")}"""
          .on(nonEmpty.map(_.toNamedParameter): _*)
          .as(MaritalStatusRow.rowParser.*)
    }

  override def updateFieldValues(id: MaritalStatusId, fieldValues: List[MaritalStatusFieldValue[_]])(implicit c: Connection): Int = 
    fieldValues match {
      case Nil => 0
      case nonEmpty =>
        SQL"""update marital_status set ${nonEmpty.map(x => s"${x.name} = {${x.name}}").mkString(", ")} where id = ${id}}"""
          .on(nonEmpty.map(_.toNamedParameter): _*)
          .executeUpdate()
    }

  override def delete(id: MaritalStatusId)(implicit c: Connection): Boolean = 
    ???
}
