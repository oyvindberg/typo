package typo
package internal

import typo.internal.codegen.{DbLib, JsonLib}

case class InternalOptions(
    pkg: sc.QIdent,
    jsonLibs: List[JsonLib],
    dbLib: Option[DbLib],
    logger: TypoLogger,
    naming: Naming,
    typeOverride: TypeOverride,
    generateMockRepos: Selector,
    fileHeader: String,
    enableFieldValue: Selector,
    enableDsl: Boolean,
    enableTestInserts: Selector,
    keepDependencies: Boolean,
    debugTypes: Boolean
)
