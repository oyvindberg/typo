package typo

case class ColumnComputed(name: sc.Ident, tpe: sc.Type, dbCol: db.Col)
