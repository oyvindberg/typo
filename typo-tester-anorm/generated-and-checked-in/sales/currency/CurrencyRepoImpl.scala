/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package currency

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

object CurrencyRepoImpl extends CurrencyRepo {
  override def delete(currencycode: CurrencyId)(implicit c: Connection): Boolean = {
    SQL"delete from sales.currency where currencycode = $currencycode".executeUpdate() > 0
  }
  override def delete: DeleteBuilder[CurrencyFields, CurrencyRow] = {
    DeleteBuilder("sales.currency", CurrencyFields)
  }
  override def insert(unsaved: CurrencyRow)(implicit c: Connection): CurrencyRow = {
    SQL"""insert into sales.currency(currencycode, "name", modifieddate)
          values (${unsaved.currencycode}::bpchar, ${unsaved.name}::"public"."Name", ${unsaved.modifieddate}::timestamp)
          returning currencycode, "name", modifieddate::text
       """
      .executeInsert(CurrencyRow.rowParser(1).single)
    
  }
  override def insert(unsaved: CurrencyRowUnsaved)(implicit c: Connection): CurrencyRow = {
    val namedParameters = List(
      Some((NamedParameter("currencycode", ParameterValue.from(unsaved.currencycode)), "::bpchar")),
      Some((NamedParameter("name", ParameterValue.from(unsaved.name)), """::"public"."Name"""")),
      unsaved.modifieddate match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((NamedParameter("modifieddate", ParameterValue.from[TypoLocalDateTime](value)), "::timestamp"))
      }
    ).flatten
    val quote = '"'.toString
    if (namedParameters.isEmpty) {
      SQL"""insert into sales.currency default values
            returning currencycode, "name", modifieddate::text
         """
        .executeInsert(CurrencyRow.rowParser(1).single)
    } else {
      val q = s"""insert into sales.currency(${namedParameters.map{case (x, _) => quote + x.name + quote}.mkString(", ")})
                  values (${namedParameters.map{ case (np, cast) => s"{${np.name}}$cast"}.mkString(", ")})
                  returning currencycode, "name", modifieddate::text
               """
      // this line is here to include an extension method which is only needed for scala 3. no import is emitted for `SQL` to avoid warning for scala 2
      import anorm._
      SQL(q)
        .on(namedParameters.map(_._1) :_*)
        .executeInsert(CurrencyRow.rowParser(1).single)
    }
    
  }
  override def select: SelectBuilder[CurrencyFields, CurrencyRow] = {
    SelectBuilderSql("sales.currency", CurrencyFields, CurrencyRow.rowParser)
  }
  override def selectAll(implicit c: Connection): List[CurrencyRow] = {
    SQL"""select currencycode, "name", modifieddate::text
          from sales.currency
       """.as(CurrencyRow.rowParser(1).*)
  }
  override def selectById(currencycode: CurrencyId)(implicit c: Connection): Option[CurrencyRow] = {
    SQL"""select currencycode, "name", modifieddate::text
          from sales.currency
          where currencycode = $currencycode
       """.as(CurrencyRow.rowParser(1).singleOpt)
  }
  override def selectByIds(currencycodes: Array[CurrencyId])(implicit c: Connection): List[CurrencyRow] = {
    SQL"""select currencycode, "name", modifieddate::text
          from sales.currency
          where currencycode = ANY($currencycodes)
       """.as(CurrencyRow.rowParser(1).*)
    
  }
  override def update(row: CurrencyRow)(implicit c: Connection): Boolean = {
    val currencycode = row.currencycode
    SQL"""update sales.currency
          set "name" = ${row.name}::"public"."Name",
              modifieddate = ${row.modifieddate}::timestamp
          where currencycode = $currencycode
       """.executeUpdate() > 0
  }
  override def update: UpdateBuilder[CurrencyFields, CurrencyRow] = {
    UpdateBuilder("sales.currency", CurrencyFields, CurrencyRow.rowParser)
  }
  override def upsert(unsaved: CurrencyRow)(implicit c: Connection): CurrencyRow = {
    SQL"""insert into sales.currency(currencycode, "name", modifieddate)
          values (
            ${unsaved.currencycode}::bpchar,
            ${unsaved.name}::"public"."Name",
            ${unsaved.modifieddate}::timestamp
          )
          on conflict (currencycode)
          do update set
            "name" = EXCLUDED."name",
            modifieddate = EXCLUDED.modifieddate
          returning currencycode, "name", modifieddate::text
       """
      .executeInsert(CurrencyRow.rowParser(1).single)
    
  }
}