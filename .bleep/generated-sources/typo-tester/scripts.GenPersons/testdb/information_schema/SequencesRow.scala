package testdb.information_schema

import anorm.RowParser
import anorm.Success
import play.api.libs.json.JsObject
import play.api.libs.json.JsResult
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import scala.util.Try

case class SequencesRow(
  sequenceCatalog: /* unknown nullability */ Option[String],
  sequenceSchema: /* unknown nullability */ Option[String],
  sequenceName: /* unknown nullability */ Option[String],
  dataType: /* unknown nullability */ Option[String],
  numericPrecision: /* unknown nullability */ Option[Int],
  numericPrecisionRadix: /* unknown nullability */ Option[Int],
  numericScale: /* unknown nullability */ Option[Int],
  startValue: /* unknown nullability */ Option[String],
  minimumValue: /* unknown nullability */ Option[String],
  maximumValue: /* unknown nullability */ Option[String],
  increment: /* unknown nullability */ Option[String],
  cycleOption: /* unknown nullability */ Option[String]
)

object SequencesRow {
  implicit val rowParser: RowParser[SequencesRow] = { row =>
    Success(
      SequencesRow(
        sequenceCatalog = row[/* unknown nullability */ Option[String]]("sequence_catalog"),
        sequenceSchema = row[/* unknown nullability */ Option[String]]("sequence_schema"),
        sequenceName = row[/* unknown nullability */ Option[String]]("sequence_name"),
        dataType = row[/* unknown nullability */ Option[String]]("data_type"),
        numericPrecision = row[/* unknown nullability */ Option[Int]]("numeric_precision"),
        numericPrecisionRadix = row[/* unknown nullability */ Option[Int]]("numeric_precision_radix"),
        numericScale = row[/* unknown nullability */ Option[Int]]("numeric_scale"),
        startValue = row[/* unknown nullability */ Option[String]]("start_value"),
        minimumValue = row[/* unknown nullability */ Option[String]]("minimum_value"),
        maximumValue = row[/* unknown nullability */ Option[String]]("maximum_value"),
        increment = row[/* unknown nullability */ Option[String]]("increment"),
        cycleOption = row[/* unknown nullability */ Option[String]]("cycle_option")
      )
    )
  }

  implicit val oFormat: OFormat[SequencesRow] = new OFormat[SequencesRow]{
    override def writes(o: SequencesRow): JsObject =
      Json.obj(
        "sequence_catalog" -> o.sequenceCatalog,
      "sequence_schema" -> o.sequenceSchema,
      "sequence_name" -> o.sequenceName,
      "data_type" -> o.dataType,
      "numeric_precision" -> o.numericPrecision,
      "numeric_precision_radix" -> o.numericPrecisionRadix,
      "numeric_scale" -> o.numericScale,
      "start_value" -> o.startValue,
      "minimum_value" -> o.minimumValue,
      "maximum_value" -> o.maximumValue,
      "increment" -> o.increment,
      "cycle_option" -> o.cycleOption
      )

    override def reads(json: JsValue): JsResult[SequencesRow] = {
      JsResult.fromTry(
        Try(
          SequencesRow(
            sequenceCatalog = json.\("sequence_catalog").toOption.map(_.as[String]),
            sequenceSchema = json.\("sequence_schema").toOption.map(_.as[String]),
            sequenceName = json.\("sequence_name").toOption.map(_.as[String]),
            dataType = json.\("data_type").toOption.map(_.as[String]),
            numericPrecision = json.\("numeric_precision").toOption.map(_.as[Int]),
            numericPrecisionRadix = json.\("numeric_precision_radix").toOption.map(_.as[Int]),
            numericScale = json.\("numeric_scale").toOption.map(_.as[Int]),
            startValue = json.\("start_value").toOption.map(_.as[String]),
            minimumValue = json.\("minimum_value").toOption.map(_.as[String]),
            maximumValue = json.\("maximum_value").toOption.map(_.as[String]),
            increment = json.\("increment").toOption.map(_.as[String]),
            cycleOption = json.\("cycle_option").toOption.map(_.as[String])
          )
        )
      )
    }
  }
}
