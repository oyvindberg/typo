package typo
package internal

import typo.sc.Comments

sealed abstract class RepoMethod(val methodName: String, val tiebreaker: Int) {
  val comment: sc.Comments = sc.Comments.Empty
}

object RepoMethod {
  sealed abstract class Mutator(methodName: String, tiebreaker: Int = 0) extends RepoMethod(methodName, tiebreaker)
  sealed abstract class Selector(methodName: String, tiebreaker: Int = 0) extends RepoMethod(methodName, tiebreaker)

  case class SelectAll(
      relName: db.RelationName,
      cols: NonEmptyList[ComputedColumn],
      rowType: sc.Type
  ) extends Selector("selectAll")

  case class SelectBuilder(
      relName: db.RelationName,
      fieldsType: sc.Type,
      rowType: sc.Type
  ) extends Selector("select")

  case class SelectById(
      relName: db.RelationName,
      cols: NonEmptyList[ComputedColumn],
      id: IdComputed,
      rowType: sc.Type
  ) extends Selector("selectById")

  case class SelectByIds(
      relName: db.RelationName,
      cols: NonEmptyList[ComputedColumn],
      idComputed: IdComputed,
      idsParam: sc.Param,
      rowType: sc.Type
  ) extends Selector("selectByIds")

  case class SelectByIdsTracked(
      selectByIds: SelectByIds
  ) extends Selector("selectByIdsTracked")

  case class SelectByUnique(
      relName: db.RelationName,
      keyColumns: NonEmptyList[ComputedColumn],
      allColumns: NonEmptyList[ComputedColumn],
      rowType: sc.Type
  ) extends Selector(s"selectByUnique${keyColumns.map(x => Naming.titleCase(x.name.value)).mkString("And")}")

  case class SelectByFieldValues(
      relName: db.RelationName,
      cols: NonEmptyList[ComputedColumn],
      fieldValueType: sc.Type.Qualified,
      fieldValueOrIdsParam: sc.Param,
      rowType: sc.Type
  ) extends Selector("selectByFieldValues")

  case class UpdateBuilder(
      relName: db.RelationName,
      fieldsType: sc.Type,
      rowType: sc.Type
  ) extends Mutator("update", 2)

  case class UpdateFieldValues(
      relName: db.RelationName,
      id: IdComputed,
      varargs: sc.Param,
      fieldValueType: sc.Type.Qualified,
      cases: NonEmptyList[ComputedColumn],
      rowType: sc.Type
  ) extends Mutator("updateFieldValues")

  case class Update(
      relName: db.RelationName,
      cols: NonEmptyList[ComputedColumn],
      id: IdComputed,
      param: sc.Param,
      colsNotId: NonEmptyList[ComputedColumn]
  ) extends Mutator("update", 1)

  case class Upsert(
      relName: db.RelationName,
      cols: NonEmptyList[ComputedColumn],
      id: IdComputed,
      unsavedParam: sc.Param,
      rowType: sc.Type
  ) extends Mutator("upsert")

  case class UpsertBatch(
      relName: db.RelationName,
      cols: NonEmptyList[ComputedColumn],
      id: IdComputed,
      rowType: sc.Type
  ) extends Mutator("upsertBatch")

  case class UpsertStreaming(
      relName: db.RelationName,
      cols: NonEmptyList[ComputedColumn],
      id: IdComputed,
      rowType: sc.Type
  ) extends Mutator("upsertStreaming") {
    override val comment = Comments(List("NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements"))
  }

  case class Insert(
      relName: db.RelationName,
      cols: NonEmptyList[ComputedColumn],
      unsavedParam: sc.Param,
      rowType: sc.Type
  ) extends Mutator("insert", 2)

  case class InsertUnsaved(
      relName: db.RelationName,
      cols: NonEmptyList[ComputedColumn],
      unsaved: ComputedRowUnsaved,
      unsavedParam: sc.Param,
      default: ComputedDefault,
      rowType: sc.Type
  ) extends Mutator("insert", 1)

  case class InsertStreaming(
      relName: db.RelationName,
      cols: NonEmptyList[ComputedColumn],
      rowType: sc.Type
  ) extends Mutator("insertStreaming")

  case class InsertUnsavedStreaming(
      relName: db.RelationName,
      unsaved: ComputedRowUnsaved
  ) extends Mutator("insertUnsavedStreaming") {
    override val comment = Comments(List("NOTE: this functionality requires PostgreSQL 16 or later!"))
  }

  case class Delete(
      relName: db.RelationName,
      id: IdComputed
  ) extends Mutator("deleteById")

  case class DeleteByIds(
      relName: db.RelationName,
      id: IdComputed,
      idsParam: sc.Param
  ) extends Mutator("deleteByIds")

  case class DeleteBuilder(
      relName: db.RelationName,
      fieldsType: sc.Type,
      rowType: sc.Type
  ) extends Mutator("delete")

  case class SqlFile(sqlFile: ComputedSqlFile) extends RepoMethod("apply", 0)

  implicit val ordering: Ordering[RepoMethod] = Ordering.by(x => (x.methodName, -x.tiebreaker))
}
