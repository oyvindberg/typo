package typo

import org.postgresql.util.PSQLException
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funsuite.AnyFunSuite
import typo.generated.custom.domains.DomainsSqlRepoImpl
import typo.generated.custom.view_column_dependencies.ViewColumnDependenciesSqlRepoImpl
import typo.generated.custom.view_find_all.ViewFindAllSqlRepoImpl
import typo.generated.information_schema

import java.sql.{Connection, DriverManager}

class DbTest extends AnyFunSuite with TypeCheckedTripleEquals {
  test("works") {
    try {
      implicit val conn: Connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=postgres"
      )

      println(information_schema.columns.ColumnsViewRepoImpl.selectAll.take(1))
      println(information_schema.key_column_usage.KeyColumnUsageViewRepoImpl.selectAll.take(1))
      println(information_schema.referential_constraints.ReferentialConstraintsViewRepoImpl.selectAll.take(1))
      println(information_schema.table_constraints.TableConstraintsViewRepoImpl.selectAll.take(1))
      println(information_schema.tables.TablesViewRepoImpl.selectAll.take(1))
      println(ViewFindAllSqlRepoImpl().take(1))
      println(ViewColumnDependenciesSqlRepoImpl(None).take(1))
      println(DomainsSqlRepoImpl().take(1))
    } catch {
      case e: PSQLException =>
        e.printStackTrace()
        pending
    }
  }
}
