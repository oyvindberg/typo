package typo

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}

import java.sql.Connection
import javax.sql.DataSource
import scala.concurrent.{ExecutionContext, Future, blocking}

case class TypoDataSource(ds: DataSource) {
  def run[T](f: Connection => T)(implicit ec: ExecutionContext): Future[T] =
    blocking {
      Future {
        val conn = ds.getConnection
        try f(conn)
        finally conn.close()
      }
    }
}

object TypoDataSource {
  def hikari(server: String, port: Int, databaseName: String, username: String, password: String): TypoDataSource = {
    val config = new HikariConfig
    config.setJdbcUrl(s"jdbc:postgresql://$server:$port/$databaseName")
    config.setUsername(username)
    config.setPassword(password)
    val ds = new HikariDataSource(config)
    TypoDataSource(ds)
  }
}
