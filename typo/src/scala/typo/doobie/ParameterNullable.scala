// Copyright (c) 2013-2020 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package typo.doobie

import java.sql.ParameterMetaData._

/** @group Types */
sealed abstract class ParameterNullable(val toInt: Int) extends Product with Serializable {
  def toNullability: Nullability =
    Nullability.fromParameterNullable(this)
}

/** @group Modules */
object ParameterNullable {

  /** @group Values */
  case object NoNulls extends ParameterNullable(parameterNoNulls)

  /** @group Values */
  case object Nullable extends ParameterNullable(parameterNullable)

  /** @group Values */
  case object NullableUnknown extends ParameterNullable(parameterNullableUnknown)

  def fromInt(n: Int): Option[ParameterNullable] =
    Some(n) collect {
      case NoNulls.toInt         => NoNulls
      case Nullable.toInt        => Nullable
      case NullableUnknown.toInt => NullableUnknown
    }

  def fromNullability(n: Nullability): ParameterNullable =
    n match {
      case Nullability.NoNulls         => NoNulls
      case Nullability.Nullable        => Nullable
      case Nullability.NullableUnknown => NullableUnknown
    }
}
