/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package sales
package salestaxrate

import adventureworks.Defaulted
import adventureworks.TypoLocalDateTime
import anorm.NamedParameter
import anorm.ParameterValue
import anorm.SqlStringInterpolation
import java.sql.Connection
import java.util.UUID
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import typo.dsl.UpdateBuilder

object SalestaxrateRepoImpl extends SalestaxrateRepo {
  override def delete(salestaxrateid: SalestaxrateId)(implicit c: Connection): Boolean = {
    SQL"delete from sales.salestaxrate where salestaxrateid = $salestaxrateid".executeUpdate() > 0
  }
  override def delete: DeleteBuilder[SalestaxrateFields, SalestaxrateRow] = {
    DeleteBuilder("sales.salestaxrate", SalestaxrateFields)
  }
  override def insert(unsaved: SalestaxrateRow)(implicit c: Connection): SalestaxrateRow = {
    SQL"""insert into sales.salestaxrate(salestaxrateid, stateprovinceid, taxtype, taxrate, "name", rowguid, modifieddate)
          values (${unsaved.salestaxrateid}::int4, ${unsaved.stateprovinceid}::int4, ${unsaved.taxtype}::int2, ${unsaved.taxrate}::numeric, ${unsaved.name}::"public"."Name", ${unsaved.rowguid}::uuid, ${unsaved.modifieddate}::timestamp)
          returning salestaxrateid, stateprovinceid, taxtype, taxrate, "name", rowguid, modifieddate::text
       """
      .executeInsert(SalestaxrateRow.rowParser(1).single)
    
  }
  override def insert(unsaved: SalestaxrateRowUnsaved)(implicit c: Connection): SalestaxrateRow = {
    val namedParameters = List(
      Some((NamedParameter("stateprovinceid", ParameterValue.from(unsaved.stateprovinceid)), "::int4")),
      Some((NamedParameter("taxtype", ParameterValue.from(unsaved.taxtype)), "::int2")),
      Some((NamedParameter("name", ParameterValue.from(unsaved.name)), """::"public"."Name"""")),
      unsaved.salestaxrateid match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((NamedParameter("salestaxrateid", ParameterValue.from[SalestaxrateId](value)), "::int4"))
      },
      unsaved.taxrate match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((NamedParameter("taxrate", ParameterValue.from[BigDecimal](value)), "::numeric"))
      },
      unsaved.rowguid match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((NamedParameter("rowguid", ParameterValue.from[UUID](value)), "::uuid"))
      },
      unsaved.modifieddate match {
        case Defaulted.UseDefault => None
        case Defaulted.Provided(value) => Some((NamedParameter("modifieddate", ParameterValue.from[TypoLocalDateTime](value)), "::timestamp"))
      }
    ).flatten
    val quote = '"'.toString
    if (namedParameters.isEmpty) {
      SQL"""insert into sales.salestaxrate default values
            returning salestaxrateid, stateprovinceid, taxtype, taxrate, "name", rowguid, modifieddate::text
         """
        .executeInsert(SalestaxrateRow.rowParser(1).single)
    } else {
      val q = s"""insert into sales.salestaxrate(${namedParameters.map{case (x, _) => quote + x.name + quote}.mkString(", ")})
                  values (${namedParameters.map{ case (np, cast) => s"{${np.name}}$cast"}.mkString(", ")})
                  returning salestaxrateid, stateprovinceid, taxtype, taxrate, "name", rowguid, modifieddate::text
               """
      // this line is here to include an extension method which is only needed for scala 3. no import is emitted for `SQL` to avoid warning for scala 2
      import anorm._
      SQL(q)
        .on(namedParameters.map(_._1) :_*)
        .executeInsert(SalestaxrateRow.rowParser(1).single)
    }
    
  }
  override def select: SelectBuilder[SalestaxrateFields, SalestaxrateRow] = {
    SelectBuilderSql("sales.salestaxrate", SalestaxrateFields, SalestaxrateRow.rowParser)
  }
  override def selectAll(implicit c: Connection): List[SalestaxrateRow] = {
    SQL"""select salestaxrateid, stateprovinceid, taxtype, taxrate, "name", rowguid, modifieddate::text
          from sales.salestaxrate
       """.as(SalestaxrateRow.rowParser(1).*)
  }
  override def selectById(salestaxrateid: SalestaxrateId)(implicit c: Connection): Option[SalestaxrateRow] = {
    SQL"""select salestaxrateid, stateprovinceid, taxtype, taxrate, "name", rowguid, modifieddate::text
          from sales.salestaxrate
          where salestaxrateid = $salestaxrateid
       """.as(SalestaxrateRow.rowParser(1).singleOpt)
  }
  override def selectByIds(salestaxrateids: Array[SalestaxrateId])(implicit c: Connection): List[SalestaxrateRow] = {
    SQL"""select salestaxrateid, stateprovinceid, taxtype, taxrate, "name", rowguid, modifieddate::text
          from sales.salestaxrate
          where salestaxrateid = ANY($salestaxrateids)
       """.as(SalestaxrateRow.rowParser(1).*)
    
  }
  override def update(row: SalestaxrateRow)(implicit c: Connection): Boolean = {
    val salestaxrateid = row.salestaxrateid
    SQL"""update sales.salestaxrate
          set stateprovinceid = ${row.stateprovinceid}::int4,
              taxtype = ${row.taxtype}::int2,
              taxrate = ${row.taxrate}::numeric,
              "name" = ${row.name}::"public"."Name",
              rowguid = ${row.rowguid}::uuid,
              modifieddate = ${row.modifieddate}::timestamp
          where salestaxrateid = $salestaxrateid
       """.executeUpdate() > 0
  }
  override def update: UpdateBuilder[SalestaxrateFields, SalestaxrateRow] = {
    UpdateBuilder("sales.salestaxrate", SalestaxrateFields, SalestaxrateRow.rowParser)
  }
  override def upsert(unsaved: SalestaxrateRow)(implicit c: Connection): SalestaxrateRow = {
    SQL"""insert into sales.salestaxrate(salestaxrateid, stateprovinceid, taxtype, taxrate, "name", rowguid, modifieddate)
          values (
            ${unsaved.salestaxrateid}::int4,
            ${unsaved.stateprovinceid}::int4,
            ${unsaved.taxtype}::int2,
            ${unsaved.taxrate}::numeric,
            ${unsaved.name}::"public"."Name",
            ${unsaved.rowguid}::uuid,
            ${unsaved.modifieddate}::timestamp
          )
          on conflict (salestaxrateid)
          do update set
            stateprovinceid = EXCLUDED.stateprovinceid,
            taxtype = EXCLUDED.taxtype,
            taxrate = EXCLUDED.taxrate,
            "name" = EXCLUDED."name",
            rowguid = EXCLUDED.rowguid,
            modifieddate = EXCLUDED.modifieddate
          returning salestaxrateid, stateprovinceid, taxtype, taxrate, "name", rowguid, modifieddate::text
       """
      .executeInsert(SalestaxrateRow.rowParser(1).single)
    
  }
}