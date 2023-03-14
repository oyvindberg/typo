// Copyright (c) 2013-2020 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package typo.doobie

import java.sql.ResultSetMetaData.*

/** @group Types */
sealed abstract class ColumnNullable(val toInt: Int) extends Product with Serializable {
  def toNullability: Nullability =
    Nullability.fromColumnNullable(this)
}

/** @group Modules */
object ColumnNullable {

  /** @group Values */
  case object NoNulls extends ColumnNullable(columnNoNulls)

  /** @group Values */
  case object Nullable extends ColumnNullable(columnNullable)

  /** @group Values */
  case object NullableUnknown extends ColumnNullable(columnNullableUnknown)

  def fromInt(n: Int): Option[ColumnNullable] =
    Some(n) collect {
      case NoNulls.toInt         => NoNulls
      case Nullable.toInt        => Nullable
      case NullableUnknown.toInt => NullableUnknown
    }

  def fromNullability(n: Nullability): ColumnNullable =
    n match {
      case Nullability.NoNulls         => NoNulls
      case Nullability.Nullable        => Nullable
      case Nullability.NullableUnknown => NullableUnknown
    }
}
