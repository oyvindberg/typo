package typo

import org.postgresql.util.PSQLException
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funsuite.AnyFunSuite
import typo.generated.custom.view_find_all.ViewFindAllRepoImpl
import typo.generated.custom.view_column_dependencies.ViewColumnDependenciesRepoImpl
import typo.generated.custom.domains.DomainsRepoImpl
import typo.generated.{information_schema, pg_catalog}

import java.sql.{Connection, DriverManager}

class DbTest extends AnyFunSuite with TypeCheckedTripleEquals {
  test("works") {
    try {
      implicit val conn: Connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=postgres"
      )

      println(information_schema.columns.ColumnsRepoImpl.selectAll.take(1))
      println(information_schema.key_column_usage.KeyColumnUsageRepoImpl.selectAll.take(1))
      println(information_schema.referential_constraints.ReferentialConstraintsRepoImpl.selectAll.take(1))
      println(information_schema.table_constraints.TableConstraintsRepoImpl.selectAll.take(1))
      println(information_schema.tables.TablesRepoImpl.selectAll.take(1))
      println(pg_catalog.pg_attribute.PgAttributeRepoImpl.selectAll.take(1))
      println(pg_catalog.pg_class.PgClassRepoImpl.selectAll.take(1))
      println(ViewFindAllRepoImpl().take(1))
      println(ViewColumnDependenciesRepoImpl().take(1))
      println(DomainsRepoImpl().take(1))
    } catch {
      case e: PSQLException =>
        e.printStackTrace()
        pending
    }
  }
}
