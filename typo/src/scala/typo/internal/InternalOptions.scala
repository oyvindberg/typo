package typo
package internal

import typo.internal.codegen.{DbLib, JsonLib}

case class InternalOptions(
    pkg: sc.QIdent,
    jsonLibs: List[JsonLib],
    dbLib: Option[DbLib],
    naming: Naming,
    typeOverride: TypeOverride,
    generateMockRepos: Selector,
    header: String,
    enableFieldValue: Boolean,
    enableDsl: Boolean,
    debugTypes: Boolean
)
