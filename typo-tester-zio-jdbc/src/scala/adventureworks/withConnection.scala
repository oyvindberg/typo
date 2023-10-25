package adventureworks

import zio.jdbc.*
import zio.{Unsafe, ZIO, ZLayer}

import scala.annotation.nowarn

object withConnection {
  val connectionPool: ZLayer[ZConnectionPoolConfig, Throwable, ZConnectionPool] =
    ZLayer.succeed(ZConnectionPoolConfig.default) >>>
      ZConnectionPool.postgres(
        "localhost",
        6432,
        "Adventureworks",
        Map(
          "user" -> "postgres",
          "password" -> "postgres"
        )
      )

  def apply[T](f: ZIO[ZConnection, Throwable, T]): T = {
    Unsafe
      .unsafe { implicit u =>
        zio.Runtime.default.unsafe
          .run {
            (
              for {
                connection <- ZIO.service[ZConnection]
                r <- f.provideLayer(ZLayer.succeed(connection))
                _ <- connection.rollback
              } yield r
            ).provideLayer(connectionPool.flatMap(_.get.transaction))
          }
          .getOrThrow()
      }: @nowarn("msg=parameter value u in anonymous function is never used")

  }
}
