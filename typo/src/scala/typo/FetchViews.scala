package typo

import java.sql.Connection

case class View(
    name: db.RelationName,
    sql: String,
    isMaterialized: Boolean,
    cols: Seq[AnalyzeSql.Column]
)

object View {
  def from(c: Connection): List[View] = {
    information_schema.ViewsRepo.fetch(c).map { view =>
      val AnalyzeSql.Analyzed(Nil, columns) = AnalyzeSql.from(c, view.view_definition)

      View(
        name = db.RelationName(view.table_schema, view.table_name),
        sql = view.view_definition,
        isMaterialized = view.relkind == "m",
        columns
      )
    }
  }
}
