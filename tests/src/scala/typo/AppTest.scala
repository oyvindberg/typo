package typo

import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funsuite.AnyFunSuite
import typo.generated.views.find_all_views.FindAllViewsRepoImpl
import typo.generated.views.view_column_dependencies.ViewColumnDependenciesRepoImpl
import typo.generated.{information_schema, pg_catalog}

import java.sql.{Connection, DriverManager}

class AppTest extends AnyFunSuite with TypeCheckedTripleEquals {
  test("works") {
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
    println(FindAllViewsRepoImpl().take(1))
    println(ViewColumnDependenciesRepoImpl().take(1))
  }
}
