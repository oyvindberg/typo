---
title: Id types
---

For every table with primary keys, a corresponding id type is created.

```scala 
case class PgNamespaceId(value: /* oid */ Long) extends AnyVal
object PgNamespaceId {
  // ... instances
}
```

## Composite keys
Composite keys are also supported, and the id type is a product type in that case.  

```scala mdoc
import adventureworks.customtypes.TypoLocalDate
import adventureworks.humanresources.employeedepartmenthistory.EmployeedepartmenthistoryId
import adventureworks.humanresources.department.DepartmentId
import adventureworks.humanresources.shift.ShiftId
import adventureworks.person.businessentity.BusinessentityId

/** Type for the composite primary key of table `humanresources.employeedepartmenthistory` */
case class EmployeedepartmenthistoryId(
    businessentityid: BusinessentityId, 
    startdate: TypoLocalDate, 
    departmentid: DepartmentId, 
    shiftid: ShiftId
)
object EmployeedepartmenthistoryId {
  // ...instances
}
```