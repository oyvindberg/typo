package typo

sealed trait OverrideFrom
object OverrideFrom {
  sealed trait Relation extends OverrideFrom {
    def name: db.RelationName
  }
  case class Table(name: db.RelationName) extends Relation
  case class View(name: db.RelationName) extends Relation
  case class SqlScript(relPath: RelPath) extends OverrideFrom
}
