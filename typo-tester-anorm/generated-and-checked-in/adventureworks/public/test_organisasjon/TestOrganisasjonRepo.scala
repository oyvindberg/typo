/**
 * File has been automatically generated by `typo`.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 */
package adventureworks
package public
package test_organisasjon

import java.sql.Connection
import typo.dsl.DeleteBuilder
import typo.dsl.SelectBuilder
import typo.dsl.UpdateBuilder

trait TestOrganisasjonRepo {
  def delete: DeleteBuilder[TestOrganisasjonFields, TestOrganisasjonRow]
  def deleteById(organisasjonskode: TestOrganisasjonId)(implicit c: Connection): Boolean
  def deleteByIds(organisasjonskodes: Array[TestOrganisasjonId])(implicit c: Connection): Int
  def insert(unsaved: TestOrganisasjonRow)(implicit c: Connection): TestOrganisasjonRow
  def insertStreaming(unsaved: Iterator[TestOrganisasjonRow], batchSize: Int = 10000)(implicit c: Connection): Long
  def select: SelectBuilder[TestOrganisasjonFields, TestOrganisasjonRow]
  def selectAll(implicit c: Connection): List[TestOrganisasjonRow]
  def selectById(organisasjonskode: TestOrganisasjonId)(implicit c: Connection): Option[TestOrganisasjonRow]
  def selectByIds(organisasjonskodes: Array[TestOrganisasjonId])(implicit c: Connection): List[TestOrganisasjonRow]
  def selectByIdsTracked(organisasjonskodes: Array[TestOrganisasjonId])(implicit c: Connection): Map[TestOrganisasjonId, TestOrganisasjonRow]
  def update: UpdateBuilder[TestOrganisasjonFields, TestOrganisasjonRow]
  def upsert(unsaved: TestOrganisasjonRow)(implicit c: Connection): TestOrganisasjonRow
  def upsertBatch(unsaved: Iterable[TestOrganisasjonRow])(implicit c: Connection): List[TestOrganisasjonRow]
  /* NOTE: this functionality is not safe if you use auto-commit mode! it runs 3 SQL statements */
  def upsertStreaming(unsaved: Iterator[TestOrganisasjonRow], batchSize: Int = 10000)(implicit c: Connection): Int
}
