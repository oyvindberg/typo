/**
 * File automatically generated by `typo` for its own test suite.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN
 */
package testdb
package hardcoded
package myschema
package marital_status

import anorm.SqlStringInterpolation
import java.sql.Connection
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import typo.dsl.UpdateBuilder

object MaritalStatusRepoImpl extends MaritalStatusRepo {
  override def delete(id: MaritalStatusId)(implicit c: Connection): Boolean = {
    SQL"""delete from myschema.marital_status where "id" = $id""".executeUpdate() > 0
  }
  override def delete: DeleteBuilder[MaritalStatusFields, MaritalStatusRow] = {
    DeleteBuilder("myschema.marital_status", MaritalStatusFields)
  }
  override def insert(unsaved: MaritalStatusRow)(implicit c: Connection): MaritalStatusRow = {
    SQL"""insert into myschema.marital_status("id")
          values (${unsaved.id}::int8)
          returning "id"
       """
      .executeInsert(MaritalStatusRow.rowParser(1).single)
    
  }
  override def select: SelectBuilder[MaritalStatusFields, MaritalStatusRow] = {
    SelectBuilderSql("myschema.marital_status", MaritalStatusFields, MaritalStatusRow.rowParser)
  }
  override def selectAll(implicit c: Connection): List[MaritalStatusRow] = {
    SQL"""select "id"
          from myschema.marital_status
       """.as(MaritalStatusRow.rowParser(1).*)
  }
  override def selectById(id: MaritalStatusId)(implicit c: Connection): Option[MaritalStatusRow] = {
    SQL"""select "id"
          from myschema.marital_status
          where "id" = $id
       """.as(MaritalStatusRow.rowParser(1).singleOpt)
  }
  override def selectByIds(ids: Array[MaritalStatusId])(implicit c: Connection): List[MaritalStatusRow] = {
    SQL"""select "id"
          from myschema.marital_status
          where "id" = ANY($ids)
       """.as(MaritalStatusRow.rowParser(1).*)
    
  }
  override def update: UpdateBuilder[MaritalStatusFields, MaritalStatusRow] = {
    UpdateBuilder("myschema.marital_status", MaritalStatusFields, MaritalStatusRow.rowParser)
  }
  override def upsert(unsaved: MaritalStatusRow)(implicit c: Connection): MaritalStatusRow = {
    SQL"""insert into myschema.marital_status("id")
          values (
            ${unsaved.id}::int8
          )
          on conflict ("id")
          do update set
            
          returning "id"
       """
      .executeInsert(MaritalStatusRow.rowParser(1).single)
    
  }
}