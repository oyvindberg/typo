package typo
package internal

sealed trait RepoMethod {
  val comment: Option[String] = None
}

object RepoMethod {
  sealed trait Mutator extends RepoMethod
  sealed trait Selector extends RepoMethod

  case class SelectAll(
      relName: db.RelationName,
      cols: NonEmptyList[ComputedColumn],
      rowType: sc.Type
  ) extends Selector

  case class SelectBuilder(
      relName: db.RelationName,
      fieldsType: sc.Type,
      rowType: sc.Type
  ) extends Selector

  case class SelectById(
      relName: db.RelationName,
      cols: NonEmptyList[ComputedColumn],
      id: IdComputed,
      rowType: sc.Type
  ) extends Selector

  case class SelectAllByIds(
      relName: db.RelationName,
      cols: NonEmptyList[ComputedColumn],
      unaryId: IdComputed.Unary,
      idsParam: sc.Param,
      rowType: sc.Type
  ) extends Selector

  case class SelectByUnique(
      relName: db.RelationName,
      params: NonEmptyList[ComputedColumn],
      rowType: sc.Type
  ) extends Selector

  case class SelectByFieldValues(
      relName: db.RelationName,
      cols: NonEmptyList[ComputedColumn],
      fieldValueType: sc.Type.Qualified,
      fieldValueOrIdsParam: sc.Param,
      rowType: sc.Type
  ) extends Selector

  case class UpdateBuilder(
      relName: db.RelationName,
      fieldsType: sc.Type,
      rowType: sc.Type
  ) extends Mutator

  case class UpdateFieldValues(
      relName: db.RelationName,
      id: IdComputed,
      varargs: sc.Param,
      fieldValueType: sc.Type.Qualified,
      cases: NonEmptyList[ComputedColumn],
      rowType: sc.Type
  ) extends Mutator

  case class Update(
      relName: db.RelationName,
      cols: NonEmptyList[ComputedColumn],
      id: IdComputed,
      param: sc.Param,
      colsNotId: NonEmptyList[ComputedColumn]
  ) extends Mutator

  case class Upsert(
      relName: db.RelationName,
      cols: NonEmptyList[ComputedColumn],
      id: IdComputed,
      unsavedParam: sc.Param,
      rowType: sc.Type
  ) extends Mutator

  case class Insert(
      relName: db.RelationName,
      cols: NonEmptyList[ComputedColumn],
      unsavedParam: sc.Param,
      rowType: sc.Type
  ) extends Mutator

  case class InsertUnsaved(
      relName: db.RelationName,
      cols: NonEmptyList[ComputedColumn],
      unsaved: ComputedRowUnsaved,
      unsavedParam: sc.Param,
      default: ComputedDefault,
      rowType: sc.Type
  ) extends Mutator

  case class InsertStreaming(
      relName: db.RelationName,
      cols: NonEmptyList[ComputedColumn],
      rowType: sc.Type
  ) extends Mutator

  case class InsertUnsavedStreaming(
      relName: db.RelationName,
      unsaved: ComputedRowUnsaved
  ) extends Mutator {
    override val comment: Option[String] = Some("/* NOTE: this functionality requires PostgreSQL 16 or later! */")
  }

  case class Delete(
      relName: db.RelationName,
      id: IdComputed
  ) extends Mutator

  case class DeleteBuilder(
      relName: db.RelationName,
      fieldsType: sc.Type,
      rowType: sc.Type
  ) extends Mutator

  case class SqlFile(sqlFile: ComputedSqlFile) extends RepoMethod

  implicit val ordering: Ordering[RepoMethod] = Ordering.by {
    case _: SelectBuilder          => "Select1"
    case _: SelectAll              => "Select2"
    case _: SelectById             => "Select3"
    case _: SelectAllByIds         => "Select4"
    case x: SelectByUnique         => s"SelectByUnique(${x.params.map(_.name.value).mkString(", ")})"
    case _: SelectByFieldValues    => "SelectByFieldValues"
    case _: UpdateFieldValues      => "UpdateFieldValues"
    case _: Update                 => "Update1"
    case _: UpdateBuilder          => "Update2"
    case _: Upsert                 => "Upsert"
    case _: Insert                 => "Insert1"
    case _: InsertStreaming        => "Insert2"
    case _: InsertUnsaved          => "Insert3"
    case _: InsertUnsavedStreaming => "Insert4"
    case _: Delete                 => "Delete1"
    case _: DeleteBuilder          => "Delete2"
    case _: SqlFile                => "SqlFile"
  }
}
