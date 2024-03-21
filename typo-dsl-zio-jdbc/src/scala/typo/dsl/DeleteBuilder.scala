package typo.dsl

import zio.ZIO
import zio.jdbc.*

import java.util.concurrent.atomic.AtomicInteger
import scala.annotation.nowarn

trait DeleteBuilder[Fields[_], Row] {
  protected def params: DeleteParams[Fields, Row]
  protected def withParams(sqlParams: DeleteParams[Fields, Row]): DeleteBuilder[Fields, Row]

  final def where[N[_]: Nullability](v: Fields[Row] => SqlExpr[Boolean, N, Row]): DeleteBuilder[Fields, Row] =
    whereStrict(f => v(f).?.coalesce(false))

  final def whereStrict(v: Fields[Row] => SqlExpr[Boolean, Required, Row]): DeleteBuilder[Fields, Row] =
    withParams(params.where(v))

  def sql: Option[SqlFragment]
  def execute: ZIO[ZConnection, Throwable, Long]
}

object DeleteBuilder {
  def apply[Fields[_], Row](name: String, structure: Structure.Relation[Fields, ?, Row]): DeleteBuilderSql[Fields, Row] =
    DeleteBuilderSql(name, structure, DeleteParams.empty)

  final case class DeleteBuilderSql[Fields[_], Row](
      name: String,
      structure: Structure.Relation[Fields, ?, Row],
      params: DeleteParams[Fields, Row]
  ) extends DeleteBuilder[Fields, Row] {
    override def withParams(params: DeleteParams[Fields, Row]): DeleteBuilder[Fields, Row] =
      copy(params = params)

    def mkSql(counter: AtomicInteger): SqlFragment = {
      List[Iterable[SqlFragment]](
        Some(SqlFragment.deleteFrom(name)),
        params.where
          .map(w => w(structure.fields))
          .reduceLeftOption(_.and(_))
          .map { where => sql" where " ++ where.render(counter) }
      ).flatten.reduce(_ ++ _)
    }

    override def sql: Option[SqlFragment] =
      Some(mkSql(new AtomicInteger(1)))

    override def execute: ZIO[ZConnection, Throwable, Long] =
      mkSql(new AtomicInteger(0)).update
  }

  final case class DeleteBuilderMock[Id, Fields[_], Row](
      params: DeleteParams[Fields, Row],
      fields: Fields[Row],
      map: scala.collection.mutable.Map[Id, Row]
  ) extends DeleteBuilder[Fields, Row] {
    override def withParams(params: DeleteParams[Fields, Row]): DeleteBuilder[Fields, Row] =
      copy(params = params)

    override def sql: Option[SqlFragment] = None

    override def execute: ZIO[ZConnection, Throwable, Long] = ZIO.succeed {
      var changed: Long = 0L
      map.foreach { case (id, row) =>
        if (params.where.forall(w => w(fields).eval(row))) {
          map.remove(id): @nowarn
          changed += 1
        }
      }
      changed
    }
  }
}
