package typo

object PostgresTypes {
  val all = List(
    sc.StrLit("box") -> sc.Type.Qualified("org.postgresql.geometric.PGbox"),
    sc.StrLit("circle") -> sc.Type.Qualified("org.postgresql.geometric.PGcircle"),
    sc.StrLit("line") -> sc.Type.Qualified("org.postgresql.geometric.PGline"),
    sc.StrLit("lseg") -> sc.Type.Qualified("org.postgresql.geometric.PGlseg"),
    sc.StrLit("path") -> sc.Type.Qualified("org.postgresql.geometric.PGpath"),
    sc.StrLit("point") -> sc.Type.Qualified("org.postgresql.geometric.PGpoint"),
    sc.StrLit("polygon") -> sc.Type.Qualified("org.postgresql.geometric.PGpolygon"),
    sc.StrLit("money") -> sc.Type.Qualified("org.postgresql.util.PGInterval"),
    sc.StrLit("interval") -> sc.Type.Qualified("org.postgresql.util.PGmoney")
  )
}
