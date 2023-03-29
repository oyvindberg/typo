package typo

import typo.codegen.{DbLib, JsonLib}

case class Options(
    pkg: sc.QIdent,
    jsonLib: JsonLib,
    dbLib: DbLib,
    typeOverride: TypeOverride,
    header: sc.Code,
    debugTypes: Boolean
)
