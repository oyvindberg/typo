---
title: Date/time types
---

Typo uses its own wrapper types for date/time, which delegates to `java.time`.

For `timetz`, `timestamptz` `OffsetTime` and `OffsetDateTime` is used, since PostgreSQL does not store zone names.

See [typo-types](type-safety/typo-types.md) for some motivation for why the wrapper types were necessary.

The most important reason was that the PostgreSQL driver loses precision and offsets, and for accuracy it's important to avoid that code path. 
To that end, values are transferred as strings. You can make use of these data types outside of Typo if cast to `::text` explicitly in sql.

With Typo you can round-trip rows with `OffsetDateTime` and the resulting row will compare equal to the one you inserted.

Ref [arrays](type-safety/arrays.md), arrays of date/times are usable if you define such a column.

```scala mdoc
import java.time.*

/** This is `java.time.LocalDate`, but transferred to and from postgres as strings. The reason is that postgres driver and db libs are broken */
case class TypoLocalDate(value: LocalDate)
/** This is `java.time.LocalDateTime`, but with microsecond precision and transferred to and from postgres as strings. The reason is that postgres driver and db libs are broken */
case class TypoLocalDateTime(value: LocalDateTime)
/** This is `java.time.LocalTime`, but with microsecond precision and transferred to and from postgres as strings. The reason is that postgres driver and db libs are broken */
case class TypoLocalTime(value: LocalTime)
/** This is `java.time.OffsetDateTime`, but with microsecond precision and transferred to and from postgres as strings. The reason is that postgres driver and db libs are broken */
case class TypoOffsetDateTime(value: OffsetDateTime)
/** This is `java.time.OffsetTime`, but with microsecond precision and transferred to and from postgres as strings. The reason is that postgres driver and db libs are broken */
case class TypoOffsetTime(value: OffsetTime)

```