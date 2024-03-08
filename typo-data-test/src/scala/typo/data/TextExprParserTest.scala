package typo.data

import munit.{FunSuite, Location}
import TextExpr.*
import anorm.*
import org.postgresql.util.PGobject

import scala.util.control.NonFatal

object withConnection {
  val conn = java.sql.DriverManager.getConnection("jdbc:postgresql://localhost:6432/Adventureworks?user=postgres&password=password")
  conn.setAutoCommit(false)

  def apply[T](f: java.sql.Connection => T): T = {
    try {
      f(conn)
    } finally {
//      conn.rollback()
//      conn.close()
    }
  }
}

class TextExprParserTest extends FunSuite {
  def parserTest(input: => String, expected: TextExpr)(implicit loc: Location): Unit =
    test(s"parse $input") {
      val actual =
        try {
          TextExprParser.parse(input)
        } catch {
          case NonFatal(th) => fail(s"Failed to parse '$input'", th)
        }
      assertEquals(actual, expected)
    }

  def inputFor(sql: String) = withConnection { c =>
    val stmt = c.createStatement();
    stmt.execute(sql)
    val resultSet = stmt.getResultSet
    resultSet.next()
    val str = resultSet.getString(1)
    resultSet.close()
    stmt.close()
    str
  }

  // values
  parserTest(inputFor("select 'a c'"), value("a c"))

}
