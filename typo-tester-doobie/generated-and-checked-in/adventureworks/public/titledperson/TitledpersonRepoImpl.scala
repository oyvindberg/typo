/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package public
package titledperson

import adventureworks.public.title.TitleId
import adventureworks.public.title_domain.TitleDomainId
import doobie.free.connection.ConnectionIO
import doobie.postgres.syntax.FragmentOps
import doobie.syntax.SqlInterpolator.SingleFragment.fromWrite
import doobie.syntax.string.toSqlInterpolator
import doobie.util.Write
import doobie.util.meta.Meta
import fs2.Stream
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import typo.dsl.UpdateBuilder

class TitledpersonRepoImpl extends TitledpersonRepo {
  override def delete: DeleteBuilder[TitledpersonFields, TitledpersonRow] = {
    DeleteBuilder(""""public"."titledperson"""", TitledpersonFields.structure)
  }
  override def insert(unsaved: TitledpersonRow): ConnectionIO[TitledpersonRow] = {
    sql"""insert into "public"."titledperson"("title_short", "title", "name")
          values (${fromWrite(unsaved.titleShort)(new Write.Single(TitleDomainId.put))}::text, ${fromWrite(unsaved.title)(new Write.Single(TitleId.put))}, ${fromWrite(unsaved.name)(new Write.Single(Meta.StringMeta.put))})
          returning "title_short", "title", "name"
       """.query(using TitledpersonRow.read).unique
  }
  override def insertStreaming(unsaved: Stream[ConnectionIO, TitledpersonRow], batchSize: Int = 10000): ConnectionIO[Long] = {
    new FragmentOps(sql"""COPY "public"."titledperson"("title_short", "title", "name") FROM STDIN""").copyIn(unsaved, batchSize)(using TitledpersonRow.text)
  }
  override def select: SelectBuilder[TitledpersonFields, TitledpersonRow] = {
    SelectBuilderSql(""""public"."titledperson"""", TitledpersonFields.structure, TitledpersonRow.read)
  }
  override def selectAll: Stream[ConnectionIO, TitledpersonRow] = {
    sql"""select "title_short", "title", "name" from "public"."titledperson"""".query(using TitledpersonRow.read).stream
  }
  override def update: UpdateBuilder[TitledpersonFields, TitledpersonRow] = {
    UpdateBuilder(""""public"."titledperson"""", TitledpersonFields.structure, TitledpersonRow.read)
  }
}
