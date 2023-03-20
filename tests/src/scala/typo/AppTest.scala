package typo

import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funsuite.AnyFunSuite
import typo.metadb.MetaDb

import java.sql.{Connection, DriverManager}

class AppTest extends AnyFunSuite with TypeCheckedTripleEquals {
  test("works") {
    implicit val conn: Connection = DriverManager.getConnection(
      "jdbc:postgresql://localhost:5432/samordnaopptak?user=postgres&password=postgres"
    )

    try {
      val metaDb = new MetaDb

      println(metaDb.foreignKeys.getAsMap)
    } finally {
      conn.close()
    }
    assert(true)
  }
}
