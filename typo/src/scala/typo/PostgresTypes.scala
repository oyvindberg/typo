package typo

object PostgresTypes {
  val all = List(
    sc.StrLit("box") -> sc.Type.PGbox,
    sc.StrLit("circle") -> sc.Type.PGcircle,
    sc.StrLit("line") -> sc.Type.PGline,
    sc.StrLit("lseg") -> sc.Type.PGlseg,
    sc.StrLit("path") -> sc.Type.PGpath,
    sc.StrLit("point") -> sc.Type.PGpoint,
    sc.StrLit("polygon") -> sc.Type.PGpolygon,
    sc.StrLit("interval") -> sc.Type.PGInterval,
    sc.StrLit("money") -> sc.Type.PGmoney
  )
}
