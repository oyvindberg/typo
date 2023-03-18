package typo

case class View(
    name: db.RelationName,
    sql: String,
    isMaterialized: Boolean,
    cols: List[AnalyzeSql.Column]
)

