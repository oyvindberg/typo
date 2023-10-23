package zio.jdbc

import zio.{Chunk, NonEmptyChunk}

object extensions {

  implicit final class RichSqlFragmentType(private val dummy: SqlFragment.type) extends AnyVal {
    def whereAnd(fragments: NonEmptyChunk[SqlFragment]): SqlFragment = sql"WHERE${SqlFragment.intersperse(SqlFragment.and, fragments)}"
    def orderBy(fragments: NonEmptyChunk[SqlFragment]): SqlFragment = sql"ORDER BY${SqlFragment.intersperse(SqlFragment.comma, fragments)}"
  }

  implicit final class RichChunkFragment(private val fragments: Chunk[SqlFragment]) extends AnyVal {
    def intercalate(sep: SqlFragment): SqlFragment = SqlFragment.intersperse(sep, fragments)
  }

  implicit final class RichListFragment(private val fragments: List[SqlFragment]) extends AnyVal {
    def intercalate(sep: SqlFragment): SqlFragment = SqlFragment.intersperse(sep, fragments)
  }

}
