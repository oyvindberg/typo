/**
 * File has been automatically generated by `typo` for internal use.
 *
 * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN.
 *
 * (If you're developing `typo` and want to change it: run `bleep generate-sources)
 */
package typo
package generated
package pg_catalog



case class PgConstraintRowUnsaved(
  conname: String,
  connamespace: Long,
  contype: String,
  condeferrable: Boolean,
  condeferred: Boolean,
  convalidated: Boolean,
  conrelid: Long,
  contypid: Long,
  conindid: Long,
  conparentid: Long,
  confrelid: Long,
  confupdtype: String,
  confdeltype: String,
  confmatchtype: String,
  conislocal: Boolean,
  coninhcount: Int,
  connoinherit: Boolean,
  conkey: Option[Array[Short]],
  confkey: Option[Array[Short]],
  conpfeqop: Option[Array[Long]],
  conppeqop: Option[Array[Long]],
  conffeqop: Option[Array[Long]],
  conexclop: Option[Array[Long]],
  conbin: Option[String]
)
object PgConstraintRowUnsaved {
  
}