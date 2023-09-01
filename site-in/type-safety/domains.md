---
title: Domains
---

If you use domains in postgres like this:

```postgresql
CREATE DOMAIN "OrderNumber" varchar(25) NULL;
```

typo will generate a newtype for it with the corresponding type class instances:

```scala
/** Domain `public.OrderNumber`
  * No constraint
  */
case class OrderNumber(value: String) extends AnyVal

object OrderNumber {
  // ...instances
}
```