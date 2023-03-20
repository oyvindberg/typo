package typo

import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funsuite.AnyFunSuite
import typo.metadb.MetaDb

import java.sql.{Connection, DriverManager}

class AppTest extends AnyFunSuite with TypeCheckedTripleEquals {
  test("works") {
    val url = "jdbc:postgresql://localhost:5432/samordnaopptak?user=postgres&password=postgres"
    val conn: Connection = DriverManager.getConnection(url)

    try {
      val metaDb = new MetaDb(conn)

      println(metaDb.foreignKeys.getAsMap)
    } finally {
      conn.close()
    }
    assert(true)
  }
}


