package typo.dsl

import zio.NonEmptyChunk
import zio.jdbc.SqlFragment

object extensions {
  implicit final class FragmentOps[I[t] <: Iterable[t]](private val fragments: I[SqlFragment]) extends AnyVal {
    def mkFragment(sep: SqlFragment): SqlFragment =
      SqlFragment.intersperse(sep, fragments)

    def mkFragment(start: SqlFragment, sep: SqlFragment, end: SqlFragment): SqlFragment =
      start ++ SqlFragment.intersperse(sep, fragments) ++ end
  }

  implicit final class NonEmptyChunkOps(private val fragments: NonEmptyChunk[SqlFragment]) extends AnyVal {
    def mkFragment(sep: SqlFragment): SqlFragment =
      fragments.toChunk.mkFragment(sep)
    def mkFragment(start: SqlFragment, sep: SqlFragment, end: SqlFragment): SqlFragment =
      fragments.toChunk.mkFragment(start, sep, end)
  }
}
