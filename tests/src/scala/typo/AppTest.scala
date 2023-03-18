package typo

import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funsuite.AnyFunSuite

import java.sql.{Connection, DriverManager}

class AppTest extends AnyFunSuite with TypeCheckedTripleEquals {
  test("works") {
    val url = "jdbc:postgresql://localhost:5432/samordnaopptak?user=postgres&password=postgres"
    implicit val conn: Connection = DriverManager.getConnection(url)

    try {
      val enums = Gen.genEnums
      val tables = Gen.genTables(enums)

      println(
        tables
          .filter(
            _.uniqueKeys.nonEmpty
          )
          .mkString("\n")
      )

    } finally {
      conn.close()
    }
    assert(true)
  }
}


