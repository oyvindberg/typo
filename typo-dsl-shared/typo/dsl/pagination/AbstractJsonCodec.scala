package typo.dsl.pagination

/** abstract over json libraries */
class AbstractJsonCodec[T, E](
    val encode: T => E,
    val decode: E => Either[String, T]
)
