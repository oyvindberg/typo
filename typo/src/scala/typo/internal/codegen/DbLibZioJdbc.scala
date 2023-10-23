package typo.internal.codegen

import typo.internal.{ComputedColumn, ComputedTestInserts, CustomType, IdComputed, RepoMethod}
import typo.internal.analysis.MaybeReturnsRows
import typo.sc.Type
import typo.{NonEmptyList, sc}

class DbLibZioJdbc(pkg: sc.QIdent, inlineImplicits: Boolean) extends DbLib {
  private val ZConnection = sc.Type.Qualified("zio.jdbc.ZConnection")
  private val Throwable = sc.Type.Qualified("java.lang.Throwable")
  private val ZStream = sc.Type.Qualified("zio.stream.ZStream")
  private val ZIO = sc.Type.Qualified("zio.ZIO")
  private val JdbcEncoder = sc.Type.Qualified("zio.jdbc.JdbcEncoder")

  val SqlInterpolator = sc.Type.Qualified("zio.jdbc.SqlInterpolator")

  def SQL(content: sc.Code) = sc.StringInterpolate(SqlInterpolator, sc.Ident("sql"), content)

  private def dbNames(cols: NonEmptyList[ComputedColumn], isRead: Boolean): sc.Code =
    cols
      .map(c => c.dbName.code ++ (if (isRead) sqlCast.fromPgCode(c) else sc.Code.Empty))
      .mkCode(", ")

  override def repoSig(repoMethod: RepoMethod): sc.Code = repoMethod match {
    case RepoMethod.SelectBuilder(_, fieldsType, rowType) =>
      code"def select: ${sc.Type.dsl.SelectBuilder.of(fieldsType, rowType)}"
    case RepoMethod.SelectAll(_, _, rowType) =>
      code"def selectAll: ${ZStream.of(ZConnection, Throwable, rowType)}"
    case RepoMethod.SelectById(_, _, id, rowType) =>
      code"def selectById(${id.param}): ${ZIO.of(ZConnection, Throwable, sc.Type.Option.of(rowType))}"
    case RepoMethod.SelectAllByIds(_, _, unaryId, idsParam, rowType) =>
      unaryId match {
        case IdComputed.UnaryUserSpecified(_, tpe) =>
          code"def selectByIds($idsParam)(implicit puts: ${JdbcEncoder.of(sc.Type.Array.of(tpe))}): ${ZStream.of(ZConnection, Throwable, rowType)}"
        case _ =>
          code"def selectByIds($idsParam): ${ZStream.of(ZConnection, Throwable, rowType)}"
      }
    case RepoMethod.SelectByUnique(_, params, rowType) =>
      code"def selectByUnique(${params.map(_.param.code).mkCode(", ")}): ${ZIO.of(ZConnection, Throwable, sc.Type.Option.of(rowType))}"
    case RepoMethod.SelectByFieldValues(_, _, _, fieldValueOrIdsParam, rowType) =>
      code"def selectByFieldValues($fieldValueOrIdsParam): ${ZStream.of(ZConnection, Throwable, rowType)}"
    case RepoMethod.UpdateBuilder(_, fieldsType, rowType) =>
      code"def update: ${sc.Type.dsl.UpdateBuilder.of(fieldsType, rowType)}"
    case RepoMethod.UpdateFieldValues(_, id, varargs, _, _, _) =>
      code"def updateFieldValues(${id.param}, $varargs): ${ZIO.of(ZConnection, Throwable, sc.Type.Boolean)}"
    case RepoMethod.Update(_, _, _, param, _) =>
      code"def update($param): ${ZIO.of(ZConnection, Throwable, sc.Type.Boolean)}"
    case RepoMethod.Insert(_, _, unsavedParam, rowType) =>
      code"def insert($unsavedParam): ${ZIO.of(ZConnection, Throwable, rowType)}"
    case RepoMethod.InsertUnsaved(_, _, _, unsavedParam, _, rowType) =>
      code"def insert($unsavedParam): ${ZIO.of(ZConnection, Throwable, rowType)}"
    case RepoMethod.Upsert(_, _, _, unsavedParam, rowType) =>
      code"def upsert($unsavedParam): ${ZIO.of(ZConnection, Throwable, rowType)}"
    case RepoMethod.DeleteBuilder(_, fieldsType, rowType) =>
      code"def delete: ${sc.Type.dsl.DeleteBuilder.of(fieldsType, rowType)}"
    case RepoMethod.Delete(_, id) =>
      code"def delete(${id.param}): ${ZIO.of(ZConnection, Throwable, sc.Type.Boolean)}"
    case RepoMethod.SqlFile(sqlScript) =>
      val params = sc.Params(sqlScript.params.map(p => sc.Param(p.name, p.tpe, None)))

      val retType = sqlScript.maybeRowName match {
        case MaybeReturnsRows.Query(rowName) => ZStream.of(ZConnection, Throwable, rowName)
        case MaybeReturnsRows.Update => ZIO.of(ZConnection, Throwable, sc.Type.Long)
      }

      code"def apply$params: $retType"
  }

  override def repoImpl(repoMethod: RepoMethod): sc.Code = ???

  override def mockRepoImpl(id: IdComputed, repoMethod: RepoMethod, maybeToRow: Option[sc.Param]): sc.Code = ???

  override def testInsertMethod(x: ComputedTestInserts.InsertMethod): sc.Value = ???

  override def stringEnumInstances(wrapperType: sc.Type, underlying: sc.Type): List[sc.ClassMember] = ???

  override def anyValInstances(wrapperType: Type.Qualified, underlying: sc.Type): List[sc.ClassMember] = ???

  override def missingInstances: List[sc.ClassMember] = ???

  override def rowInstances(tpe: sc.Type, cols: NonEmptyList[ComputedColumn]): List[sc.ClassMember] = ???

  override def customTypeInstances(ct: CustomType): List[sc.ClassMember] = ???
}
