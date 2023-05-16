package adventureworks

import cats.effect.IO
import doobie._
import doobie.syntax.all._
import cats.effect.unsafe.implicits.global

object withConnection {
  val xa = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver",
    "jdbc:postgresql://localhost:6432/Adventureworks?user=postgres&password=password"
  )
  val testXa = Transactor.after.set(xa, HC.rollback)

  def apply[T](f: ConnectionIO[T]): T =
    f.transact(testXa).unsafeRunSync()
}
