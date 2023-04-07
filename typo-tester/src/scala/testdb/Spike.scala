//package testdb
//
//import anorm.NamedParameter
//import testdb.postgres.institusjoner.InstitutionId
//import testdb.postgres.regelverk.KompetanseregelverkFieldValue.aktiv
//import testdb.postgres.regelverk.{KompetanseregelverkFieldValue, KompetanseregelverkRow}
//import testdb.postgres.saksbehandling.SakFieldValue.idInstitution
//import testdb.postgres.saksbehandling.{MangelregistreringFieldValue, MangelregistreringId, MangelregistreringRow, MangelregistreringRowUnsaved, SakFieldValue, SakRow}
//
//import java.sql.Connection
//
//sealed trait JoinedSelect[T]
//object JoinedSelect {
//  case class Alias(str: String)
//
//  case class Root[T](relationName: String, rowParser: Alias => T, where: List[NamedParameter]) extends JoinedSelect[T]
//  case class JoinWith[T, U](base: JoinedSelect[T], join: JoinedSelect[U]) extends JoinedSelect[(T, U)]
//  case class OptionalJoinWith[T, U](base: JoinedSelect[T], join: JoinedSelect[U]) extends JoinedSelect[(T, Option[U])]
//}
//
//trait MangelregistreringRepo {
//  case class Select[F[_], T](fieldValues: List[MangelregistreringFieldValue[_]]) {
//    def joinKompetanseregelverkskode(fvs: KompetanseregelverkFieldValue[_]*): Select[F, (T, KompetanseregelverkRow)] = ???
//    def joinSak(fvs: SakFieldValue[_]*): Select[F, (T, SakRow)] = ???
//    def run: F[T] = ???
//  }
//
//  def select(implicit c: Connection): Select[List, MangelregistreringRow]
//  def selectAll(implicit c: Connection): List[MangelregistreringRow]
//  def selectById(compositeId: MangelregistreringId)(implicit c: Connection): Option[MangelregistreringRow]
//  def selectByFieldValues(fieldValues: List[MangelregistreringFieldValue[_]])(implicit c: Connection): List[MangelregistreringRow]
//  def updateFieldValues(compositeId: MangelregistreringId, fieldValues: List[MangelregistreringFieldValue[_]])(implicit c: Connection): Int
//  def insert(compositeId: MangelregistreringId, unsaved: MangelregistreringRowUnsaved)(implicit c: Connection): Boolean
//  def delete(compositeId: MangelregistreringId)(implicit c: Connection): Boolean
//
//}
//
//object Spike {
//  implicit val c: Connection = ???
//  val repo: MangelregistreringRepo = ???
//
//  val foo: List[((MangelregistreringRow, SakRow), KompetanseregelverkRow)] =
//    repo.select.joinSak(new idInstitution(new InstitutionId(1)))
//      .joinKompetanseregelverkskode(new aktiv(false))
//      .run
//
//  println(foo)
//
//}
