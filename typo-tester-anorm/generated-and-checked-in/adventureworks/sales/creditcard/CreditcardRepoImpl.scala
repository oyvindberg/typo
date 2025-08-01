/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package creditcard

import adventureworks.customtypes.Defaulted
import adventureworks.customtypes.TypoLocalDateTime
import adventureworks.customtypes.TypoShort
import adventureworks.userdefined.CustomCreditcardId
import anorm.BatchSql
import anorm.NamedParameter
import anorm.ParameterValue
import anorm.RowParser
import anorm.SQL
import anorm.SimpleSql
import anorm.SqlStringInterpolation
import anorm.ToStatement
import java.sql.Connection
import scala.annotation.nowarn
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import typo.dsl.UpdateBuilder

class CreditcardRepoImpl extends CreditcardRepo {
  override def delete: DeleteBuilder[CreditcardFields, CreditcardRow] = {
    DeleteBuilder(""""sales"."creditcard"""", CreditcardFields.structure)
  }
  override def deleteById(creditcardid: /* user-picked */ CustomCreditcardId)(implicit c: Connection): Boolean = {
    SQL"""delete from "sales"."creditcard" where "creditcardid" = ${ParameterValue(creditcardid, null, /* user-picked */ CustomCreditcardId.toStatement)}""".executeUpdate() > 0
  }
  override def deleteByIds(creditcardids: Array[/* user-picked */ CustomCreditcardId])(implicit c: Connection, toStatement0: ToStatement[Array[/* user-picked */ CustomCreditcardId]]): Int = {
    SQL"""delete
          from "sales"."creditcard"
          where "creditcardid" = ANY(${creditcardids})
       """.executeUpdate()
    
  }
  override def insert(unsaved: CreditcardRow)(implicit c: Connection): CreditcardRow = {
    SQL"""insert into "sales"."creditcard"("creditcardid", "cardtype", "cardnumber", "expmonth", "expyear", "modifieddate")
          values (${ParameterValue(unsaved.creditcardid, null, /* user-picked */ CustomCreditcardId.toStatement)}::int4, ${ParameterValue(unsaved.cardtype, null, ToStatement.stringToStatement)}, ${ParameterValue(unsaved.cardnumber, null, ToStatement.stringToStatement)}, ${ParameterValue(unsaved.expmonth, null, TypoShort.toStatement)}::int2, ${ParameterValue(unsaved.expyear, null, TypoShort.toStatement)}::int2, ${ParameterValue(unsaved.modifieddate, null, TypoLocalDateTime.toStatement)}::timestamp)
          returning "creditcardid", "cardtype", "cardnumber", "expmonth", "expyear", "modifieddate"::text
       """
      .executeInsert(CreditcardRow.rowParser(1).single)
    
  }
  override def insert(unsaved: CreditcardRowUnsaved)(implicit c: Connection): CreditcardRow = {
    val namedParameters = List(
      Some((NamedParameter("cardtype", ParameterValue(unsaved.cardtype, null, ToStatement.stringToStatement)), "")),
      Some((NamedParameter("cardnumber", ParameterValue(unsaved.cardnumber, null, ToStatement.stringToStatement)), "")),
      Some((NamedParameter("expmonth", ParameterValue(unsaved.expmonth, null, TypoShort.toStatement)), "::int2")),
      Some((NamedParameter("expyear", ParameterValue(unsaved.expyear, null, TypoShort.toStatement)), "::int2")),
      unsaved.creditcardid match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((NamedParameter("creditcardid", ParameterValue(value, null, /* user-picked */ CustomCreditcardId.toStatement)), "::int4"))
      },
      unsaved.modifieddate match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((NamedParameter("modifieddate", ParameterValue(value, null, TypoLocalDateTime.toStatement)), "::timestamp"))
      }
    ).flatten
    val quote = '"'.toString
    if (namedParameters.isEmpty) {
      SQL"""insert into "sales"."creditcard" default values
            returning "creditcardid", "cardtype", "cardnumber", "expmonth", "expyear", "modifieddate"::text
         """
        .executeInsert(CreditcardRow.rowParser(1).single)
    } else {
      val q = s"""insert into "sales"."creditcard"(${namedParameters.map{case (x, _) => quote + x.name + quote}.mkString(", ")})
                  values (${namedParameters.map{ case (np, cast) => s"{${np.name}}$cast"}.mkString(", ")})
                  returning "creditcardid", "cardtype", "cardnumber", "expmonth", "expyear", "modifieddate"::text
               """
      SimpleSql(SQL(q), namedParameters.map { case (np, _) => np.tupled }.toMap, RowParser.successful)
        .executeInsert(CreditcardRow.rowParser(1).single)
    }
    
  }
  override def insertStreaming(unsaved: Iterator[CreditcardRow], batchSize: Int = 10000)(implicit c: Connection): Long = {
    streamingInsert(s"""COPY "sales"."creditcard"("creditcardid", "cardtype", "cardnumber", "expmonth", "expyear", "modifieddate") FROM STDIN""", batchSize, unsaved)(CreditcardRow.text, c)
  }
  /* NOTE: this functionality requires PostgreSQL 16 or later! */
  override def insertUnsavedStreaming(unsaved: Iterator[CreditcardRowUnsaved], batchSize: Int = 10000)(implicit c: Connection): Long = {
    streamingInsert(s"""COPY "sales"."creditcard"("cardtype", "cardnumber", "expmonth", "expyear", "creditcardid", "modifieddate") FROM STDIN (DEFAULT '__DEFAULT_VALUE__')""", batchSize, unsaved)(CreditcardRowUnsaved.text, c)
  }
  override def select: SelectBuilder[CreditcardFields, CreditcardRow] = {
    SelectBuilderSql(""""sales"."creditcard"""", CreditcardFields.structure, CreditcardRow.rowParser)
  }
  override def selectAll(implicit c: Connection): List[CreditcardRow] = {
    SQL"""select "creditcardid", "cardtype", "cardnumber", "expmonth", "expyear", "modifieddate"::text
          from "sales"."creditcard"
       """.as(CreditcardRow.rowParser(1).*)
  }
  override def selectById(creditcardid: /* user-picked */ CustomCreditcardId)(implicit c: Connection): Option[CreditcardRow] = {
    SQL"""select "creditcardid", "cardtype", "cardnumber", "expmonth", "expyear", "modifieddate"::text
          from "sales"."creditcard"
          where "creditcardid" = ${ParameterValue(creditcardid, null, /* user-picked */ CustomCreditcardId.toStatement)}
       """.as(CreditcardRow.rowParser(1).singleOpt)
  }
  override def selectByIds(creditcardids: Array[/* user-picked */ CustomCreditcardId])(implicit c: Connection, toStatement0: ToStatement[Array[/* user-picked */ CustomCreditcardId]]): List[CreditcardRow] = {
    SQL"""select "creditcardid", "cardtype", "cardnumber", "expmonth", "expyear", "modifieddate"::text
          from "sales"."creditcard"
          where "creditcardid" = ANY(${creditcardids})
       """.as(CreditcardRow.rowParser(1).*)
    
  }
  override def selectByIdsTracked(creditcardids: Array[/* user-picked */ CustomCreditcardId])(implicit c: Connection, toStatement0: ToStatement[Array[/* user-picked */ CustomCreditcardId]]): Map[/* user-picked */ CustomCreditcardId, CreditcardRow] = {
    val byId = selectByIds(creditcardids).view.map(x => (x.creditcardid, x)).toMap
    creditcardids.view.flatMap(id => byId.get(id).map(x => (id, x))).toMap
  }
  override def update: UpdateBuilder[CreditcardFields, CreditcardRow] = {
    UpdateBuilder(""""sales"."creditcard"""", CreditcardFields.structure, CreditcardRow.rowParser)
  }
  override def update(row: CreditcardRow)(implicit c: Connection): Option[CreditcardRow] = {
    val creditcardid = row.creditcardid
    SQL"""update "sales"."creditcard"
          set "cardtype" = ${ParameterValue(row.cardtype, null, ToStatement.stringToStatement)},
              "cardnumber" = ${ParameterValue(row.cardnumber, null, ToStatement.stringToStatement)},
              "expmonth" = ${ParameterValue(row.expmonth, null, TypoShort.toStatement)}::int2,
              "expyear" = ${ParameterValue(row.expyear, null, TypoShort.toStatement)}::int2,
              "modifieddate" = ${ParameterValue(row.modifieddate, null, TypoLocalDateTime.toStatement)}::timestamp
          where "creditcardid" = ${ParameterValue(creditcardid, null, /* user-picked */ CustomCreditcardId.toStatement)}
          returning "creditcardid", "cardtype", "cardnumber", "expmonth", "expyear", "modifieddate"::text
       """.executeInsert(CreditcardRow.rowParser(1).singleOpt)
  }
  override def upsert(unsaved: CreditcardRow)(implicit c: Connection): CreditcardRow = {
    SQL"""insert into "sales"."creditcard"("creditcardid", "cardtype", "cardnumber", "expmonth", "expyear", "modifieddate")
          values (
            ${ParameterValue(unsaved.creditcardid, null, /* user-picked */ CustomCreditcardId.toStatement)}::int4,
            ${ParameterValue(unsaved.cardtype, null, ToStatement.stringToStatement)},
            ${ParameterValue(unsaved.cardnumber, null, ToStatement.stringToStatement)},
            ${ParameterValue(unsaved.expmonth, null, TypoShort.toStatement)}::int2,
            ${ParameterValue(unsaved.expyear, null, TypoShort.toStatement)}::int2,
            ${ParameterValue(unsaved.modifieddate, null, TypoLocalDateTime.toStatement)}::timestamp
          )
          on conflict ("creditcardid")
          do update set
            "cardtype" = EXCLUDED."cardtype",
            "cardnumber" = EXCLUDED."cardnumber",
            "expmonth" = EXCLUDED."expmonth",
            "expyear" = EXCLUDED."expyear",
            "modifieddate" = EXCLUDED."modifieddate"
          returning "creditcardid", "cardtype", "cardnumber", "expmonth", "expyear", "modifieddate"::text
       """
      .executeInsert(CreditcardRow.rowParser(1).single)
    
  }
  override def upsertBatch(unsaved: Iterable[CreditcardRow])(implicit c: Connection): List[CreditcardRow] = {
    def toNamedParameter(row: CreditcardRow): List[NamedParameter] = List(
      NamedParameter("creditcardid", ParameterValue(row.creditcardid, null, /* user-picked */ CustomCreditcardId.toStatement)),
      NamedParameter("cardtype", ParameterValue(row.cardtype, null, ToStatement.stringToStatement)),
      NamedParameter("cardnumber", ParameterValue(row.cardnumber, null, ToStatement.stringToStatement)),
      NamedParameter("expmonth", ParameterValue(row.expmonth, null, TypoShort.toStatement)),
      NamedParameter("expyear", ParameterValue(row.expyear, null, TypoShort.toStatement)),
      NamedParameter("modifieddate", ParameterValue(row.modifieddate, null, TypoLocalDateTime.toStatement))
    )
    unsaved.toList match {
      case Nil => Nil
      case head :: rest =>
        new anorm.adventureworks.ExecuteReturningSyntax.Ops(
          BatchSql(
            s"""insert into "sales"."creditcard"("creditcardid", "cardtype", "cardnumber", "expmonth", "expyear", "modifieddate")
                values ({creditcardid}::int4, {cardtype}, {cardnumber}, {expmonth}::int2, {expyear}::int2, {modifieddate}::timestamp)
                on conflict ("creditcardid")
                do update set
                  "cardtype" = EXCLUDED."cardtype",
                  "cardnumber" = EXCLUDED."cardnumber",
                  "expmonth" = EXCLUDED."expmonth",
                  "expyear" = EXCLUDED."expyear",
                  "modifieddate" = EXCLUDED."modifieddate"
                returning "creditcardid", "cardtype", "cardnumber", "expmonth", "expyear", "modifieddate"::text
             """,
            toNamedParameter(head),
            rest.map(toNamedParameter)*
          )
        ).executeReturning(CreditcardRow.rowParser(1).*)
    }
  }
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  override def upsertStreaming(unsaved: Iterator[CreditcardRow], batchSize: Int = 10000)(implicit c: Connection): Int = {
    SQL"""create temporary table creditcard_TEMP (like "sales"."creditcard") on commit drop""".execute(): @nowarn
    streamingInsert(s"""copy creditcard_TEMP("creditcardid", "cardtype", "cardnumber", "expmonth", "expyear", "modifieddate") from stdin""", batchSize, unsaved)(CreditcardRow.text, c): @nowarn
    SQL"""insert into "sales"."creditcard"("creditcardid", "cardtype", "cardnumber", "expmonth", "expyear", "modifieddate")
          select * from creditcard_TEMP
          on conflict ("creditcardid")
          do update set
            "cardtype" = EXCLUDED."cardtype",
            "cardnumber" = EXCLUDED."cardnumber",
            "expmonth" = EXCLUDED."expmonth",
            "expyear" = EXCLUDED."expyear",
            "modifieddate" = EXCLUDED."modifieddate"
          ;
          drop table creditcard_TEMP;""".executeUpdate()
  }
}
