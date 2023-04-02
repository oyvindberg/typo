package typo
package internal

import typo.internal.codegen.{DbLib, JsonLib}

case class InternalOptions(
    pkg: sc.QIdent,
    jsonLib: JsonLib,
    dbLib: DbLib,
    naming: sc.QIdent => Naming,
    typeOverride: TypeOverride,
    header: String,
    debugTypes: Boolean
)
