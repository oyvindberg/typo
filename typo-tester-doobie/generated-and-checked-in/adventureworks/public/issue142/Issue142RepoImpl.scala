/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package public
package issue142

import cats.instances.list.catsStdInstancesForList
import doobie.free.connection.ConnectionIO
import doobie.postgres.syntax.FragmentOps
import doobie.syntax.SqlInterpolator.SingleFragment.fromWrite
import doobie.syntax.string.toSqlInterpolator
import doobie.util.Write
import doobie.util.update.Update
import fs2.Stream
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.SelectBuilderSql
import typo.dsl.UpdateBuilder

class Issue142RepoImpl extends Issue142Repo {
  override def delete: DeleteBuilder[Issue142Fields, Issue142Row] = {
    DeleteBuilder(""""public"."issue142"""", Issue142Fields.structure)
  }
  override def deleteById(tabellkode: Issue142Id): ConnectionIO[Boolean] = {
    sql"""delete from "public"."issue142" where "tabellkode" = ${fromWrite(tabellkode)(new Write.Single(Issue142Id.put))}""".update.run.map(_ > 0)
  }
  override def deleteByIds(tabellkodes: Array[Issue142Id]): ConnectionIO[Int] = {
    sql"""delete from "public"."issue142" where "tabellkode" = ANY(${tabellkodes})""".update.run
  }
  override def insert(unsaved: Issue142Row): ConnectionIO[Issue142Row] = {
    sql"""insert into "public"."issue142"("tabellkode")
          values (${fromWrite(unsaved.tabellkode)(new Write.Single(Issue142Id.put))})
          returning "tabellkode"
       """.query(using Issue142Row.read).unique
  }
  override def insertStreaming(unsaved: Stream[ConnectionIO, Issue142Row], batchSize: Int = 10000): ConnectionIO[Long] = {
    new FragmentOps(sql"""COPY "public"."issue142"("tabellkode") FROM STDIN""").copyIn(unsaved, batchSize)(using Issue142Row.text)
  }
  override def select: SelectBuilder[Issue142Fields, Issue142Row] = {
    SelectBuilderSql(""""public"."issue142"""", Issue142Fields.structure, Issue142Row.read)
  }
  override def selectAll: Stream[ConnectionIO, Issue142Row] = {
    sql"""select "tabellkode" from "public"."issue142"""".query(using Issue142Row.read).stream
  }
  override def selectById(tabellkode: Issue142Id): ConnectionIO[Option[Issue142Row]] = {
    sql"""select "tabellkode" from "public"."issue142" where "tabellkode" = ${fromWrite(tabellkode)(new Write.Single(Issue142Id.put))}""".query(using Issue142Row.read).option
  }
  override def selectByIds(tabellkodes: Array[Issue142Id]): Stream[ConnectionIO, Issue142Row] = {
    sql"""select "tabellkode" from "public"."issue142" where "tabellkode" = ANY(${tabellkodes})""".query(using Issue142Row.read).stream
  }
  override def selectByIdsTracked(tabellkodes: Array[Issue142Id]): ConnectionIO[Map[Issue142Id, Issue142Row]] = {
    selectByIds(tabellkodes).compile.toList.map { rows =>
      val byId = rows.view.map(x => (x.tabellkode, x)).toMap
      tabellkodes.view.flatMap(id => byId.get(id).map(x => (id, x))).toMap
    }
  }
  override def update: UpdateBuilder[Issue142Fields, Issue142Row] = {
    UpdateBuilder(""""public"."issue142"""", Issue142Fields.structure, Issue142Row.read)
  }
  override def upsert(unsaved: Issue142Row): ConnectionIO[Issue142Row] = {
    sql"""insert into "public"."issue142"("tabellkode")
          values (
            ${fromWrite(unsaved.tabellkode)(new Write.Single(Issue142Id.put))}
          )
          on conflict ("tabellkode")
          do update set "tabellkode" = EXCLUDED."tabellkode"
          returning "tabellkode"
       """.query(using Issue142Row.read).unique
  }
  override def upsertBatch(unsaved: List[Issue142Row]): Stream[ConnectionIO, Issue142Row] = {
    Update[Issue142Row](
      s"""insert into "public"."issue142"("tabellkode")
          values (?)
          on conflict ("tabellkode")
          do nothing
          returning "tabellkode""""
    )(using Issue142Row.write)
    .updateManyWithGeneratedKeys[Issue142Row]("tabellkode")(unsaved)(using catsStdInstancesForList, Issue142Row.read)
  }
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  override def upsertStreaming(unsaved: Stream[ConnectionIO, Issue142Row], batchSize: Int = 10000): ConnectionIO[Int] = {
    for {
      _ <- sql"""create temporary table issue142_TEMP (like "public"."issue142") on commit drop""".update.run
      _ <- new FragmentOps(sql"""copy issue142_TEMP("tabellkode") from stdin""").copyIn(unsaved, batchSize)(using Issue142Row.text)
      res <- sql"""insert into "public"."issue142"("tabellkode")
                   select * from issue142_TEMP
                   on conflict ("tabellkode")
                   do nothing
                   ;
                   drop table issue142_TEMP;""".update.run
    } yield res
  }
}
