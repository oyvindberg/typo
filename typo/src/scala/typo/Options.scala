package typo

case class Options(
    pkg: sc.QIdent,
    jsonLib: JsonLib,
    dbLib: DbLib,
    header: sc.Code,
    debugTypes: Boolean
)
