/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package personcreditcard

import adventureworks.Defaulted
import adventureworks.TypoLocalDateTime
import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import typo.dsl.UpdateBuilder

object PersoncreditcardRepoImpl extends PersoncreditcardRepo {
  override def delete(compositeId: PersoncreditcardId)(implicit c: Connection): Boolean = {
    SQL"delete from sales.personcreditcard where businessentityid = ${compositeId.businessentityid} AND creditcardid = ${compositeId.creditcardid}".executeUpdate() > 0
  }
  override def delete: DeleteBuilder[PersoncreditcardFields, PersoncreditcardRow] = {
    DeleteBuilder("sales.personcreditcard", PersoncreditcardFields)
  }
  override def insert(unsaved: PersoncreditcardRow)(implicit c: Connection): PersoncreditcardRow = {
    SQL"""insert into sales.personcreditcard(businessentityid, creditcardid, modifieddate)
          values (${unsaved.businessentityid}::int4, ${unsaved.creditcardid}::int4, ${unsaved.modifieddate}::timestamp)
          returning businessentityid, creditcardid, modifieddate::text
       """
      .executeInsert(PersoncreditcardRow.rowParser(1).single)
    
  }
  override def insert(unsaved: PersoncreditcardRowUnsaved)(implicit c: Connection): PersoncreditcardRow = {
    val namedParameters = List(
      Some((NamedParameter("businessentityid", ParameterValue.from(unsaved.businessentityid)), "::int4")),
      Some((NamedParameter("creditcardid", ParameterValue.from(unsaved.creditcardid)), "::int4")),
      unsaved.modifieddate match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((NamedParameter("modifieddate", ParameterValue.from[TypoLocalDateTime](value)), "::timestamp"))
      }
    ).flatten
    val quote = '"'.toString
    if (namedParameters.isEmpty) {
      SQL"""insert into sales.personcreditcard default values
            returning businessentityid, creditcardid, modifieddate::text
         """
        .executeInsert(PersoncreditcardRow.rowParser(1).single)
    } else {
      val q = s"""insert into sales.personcreditcard(${namedParameters.map{case (x, _) => quote + x.name + quote}.mkString(", ")})
                  values (${namedParameters.map{ case (np, cast) => s"{${np.name}}$cast"}.mkString(", ")})
                  returning businessentityid, creditcardid, modifieddate::text
               """
      // this line is here to include an extension method which is only needed for scala 3. no import is emitted for `SQL` to avoid warning for scala 2
      import anorm._
      SQL(q)
        .on(namedParameters.map(_._1) :_*)
        .executeInsert(PersoncreditcardRow.rowParser(1).single)
    }
    
  }
  override def select: SelectBuilder[PersoncreditcardFields, PersoncreditcardRow] = {
    SelectBuilderSql("sales.personcreditcard", PersoncreditcardFields, PersoncreditcardRow.rowParser)
  }
  override def selectAll(implicit c: Connection): List[PersoncreditcardRow] = {
    SQL"""select businessentityid, creditcardid, modifieddate::text
          from sales.personcreditcard
       """.as(PersoncreditcardRow.rowParser(1).*)
  }
  override def selectById(compositeId: PersoncreditcardId)(implicit c: Connection): Option[PersoncreditcardRow] = {
    SQL"""select businessentityid, creditcardid, modifieddate::text
          from sales.personcreditcard
          where businessentityid = ${compositeId.businessentityid} AND creditcardid = ${compositeId.creditcardid}
       """.as(PersoncreditcardRow.rowParser(1).singleOpt)
  }
  override def update(row: PersoncreditcardRow)(implicit c: Connection): Boolean = {
    val compositeId = row.compositeId
    SQL"""update sales.personcreditcard
          set modifieddate = ${row.modifieddate}::timestamp
          where businessentityid = ${compositeId.businessentityid} AND creditcardid = ${compositeId.creditcardid}
       """.executeUpdate() > 0
  }
  override def update: UpdateBuilder[PersoncreditcardFields, PersoncreditcardRow] = {
    UpdateBuilder("sales.personcreditcard", PersoncreditcardFields, PersoncreditcardRow.rowParser)
  }
  override def upsert(unsaved: PersoncreditcardRow)(implicit c: Connection): PersoncreditcardRow = {
    SQL"""insert into sales.personcreditcard(businessentityid, creditcardid, modifieddate)
          values (
            ${unsaved.businessentityid}::int4,
            ${unsaved.creditcardid}::int4,
            ${unsaved.modifieddate}::timestamp
          )
          on conflict (businessentityid, creditcardid)
          do update set
            modifieddate = EXCLUDED.modifieddate
          returning businessentityid, creditcardid, modifieddate::text
       """
      .executeInsert(PersoncreditcardRow.rowParser(1).single)
    
  }
}