package typo.information_schema

case class ViewRow(
    table_schema: String,
    table_name: String,
    // "m" or "v"
    relkind: String,
    view_definition: String
)

object ViewRow {
  val rowMapper: anorm.RowParser[ViewRow] = row =>
    anorm.Success(
      ViewRow(
        table_schema = row[String]("table_schema"),
        table_name = row[String]("table_name"),
        relkind = row[String]("relkind"),
        view_definition = row[String]("view_definition")
      )
    )
}
