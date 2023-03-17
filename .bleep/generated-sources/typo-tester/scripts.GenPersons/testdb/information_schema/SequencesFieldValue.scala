package testdb
package information_schema



sealed abstract class SequencesFieldValue[T](val name: String, val value: T)

object SequencesFieldValue {
  case class sequenceCatalog(override val value: /* unknown nullability */ Option[String]) extends SequencesFieldValue("sequence_catalog", value)
  case class sequenceSchema(override val value: /* unknown nullability */ Option[String]) extends SequencesFieldValue("sequence_schema", value)
  case class sequenceName(override val value: /* unknown nullability */ Option[String]) extends SequencesFieldValue("sequence_name", value)
  case class dataType(override val value: /* unknown nullability */ Option[String]) extends SequencesFieldValue("data_type", value)
  case class numericPrecision(override val value: /* unknown nullability */ Option[Int]) extends SequencesFieldValue("numeric_precision", value)
  case class numericPrecisionRadix(override val value: /* unknown nullability */ Option[Int]) extends SequencesFieldValue("numeric_precision_radix", value)
  case class numericScale(override val value: /* unknown nullability */ Option[Int]) extends SequencesFieldValue("numeric_scale", value)
  case class startValue(override val value: /* unknown nullability */ Option[String]) extends SequencesFieldValue("start_value", value)
  case class minimumValue(override val value: /* unknown nullability */ Option[String]) extends SequencesFieldValue("minimum_value", value)
  case class maximumValue(override val value: /* unknown nullability */ Option[String]) extends SequencesFieldValue("maximum_value", value)
  case class increment(override val value: /* unknown nullability */ Option[String]) extends SequencesFieldValue("increment", value)
  case class cycleOption(override val value: /* unknown nullability */ Option[String]) extends SequencesFieldValue("cycle_option", value)
}
