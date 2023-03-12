package scripts

import bleep.*
import bleep.logging.Logger
import typo.*

import java.nio.file.{Files, Path}

object GenPersons extends BleepCodegenScript("GenPersons") {
  val person = db.Table(
    name = db.TableName("person"),
    cols = List(
      db.Col(db.ColName("id"), db.Type.BigInt, isNotNull = true, hasDefault = true),
      db.Col(db.ColName("favourite_football_club_id"), db.Type.VarChar(50), isNotNull = true, hasDefault = false),
      db.Col(db.ColName("name"), db.Type.VarChar(100), isNotNull = true, hasDefault = false),
      db.Col(db.ColName("nick_name"), db.Type.VarChar(30), isNotNull = false, hasDefault = false),
      db.Col(db.ColName("blog_url"), db.Type.VarChar(100), isNotNull = false, hasDefault = false),
      db.Col(db.ColName("email"), db.Type.VarChar(254), isNotNull = true, hasDefault = false),
      db.Col(db.ColName("phone"), db.Type.VarChar(8), isNotNull = true, hasDefault = false),
      db.Col(db.ColName("likes_pizza"), db.Type.Boolean, isNotNull = true, hasDefault = false),
      db.Col(db.ColName("marital_status_id"), db.Type.VarChar(50), isNotNull = true, hasDefault = true),
      db.Col(db.ColName("work_email"), db.Type.VarChar(254), isNotNull = false, hasDefault = false),
      db.Col(
        db.ColName("sector"),
        db.Type.StringEnum(db.EnumName("sector"), List("PUBLIC", "PRIVATE", "OTHER")),
        isNotNull = true,
        hasDefault = true
      )
    ),
    Some(db.PrimaryKey(db.ColName("id"))),
    Nil,
    List(
      db.ForeignKey(db.ColName("favourite_football_club_id"), db.TableName("football_club"), db.ColName("id")),
      db.ForeignKey(db.ColName("marital_status_id"), db.TableName("marital_status"), db.ColName("id"))
    )
  )
  val football_club = db.Table(
    name = db.TableName("football_club"),
    cols = List(
      db.Col(db.ColName("id"), db.Type.BigInt, isNotNull = true, hasDefault = false),
      db.Col(db.ColName("name"), db.Type.VarChar(100), isNotNull = true, hasDefault = false)
    ),
    Some(db.PrimaryKey(db.ColName("id"))),
    Nil,
    Nil
  )
  val marital_status = db.Table(
    name = db.TableName("marital_status"),
    cols = List(
      db.Col(db.ColName("id"), db.Type.BigInt, isNotNull = true, hasDefault = false)
    ),
    Some(db.PrimaryKey(db.ColName("id"))),
    Nil,
    Nil
  )

  val all = List(person, football_club, marital_status)

  override def run(started: Started, commands: Commands, targets: List[Target], args: List[String]): Unit = {
    val filesByRelPath: Map[RelPath, String] =
      Gen
        .allTables(sc.QIdent(List(sc.Ident("testdb"))), all, JsonLib.PlayJson)
        .map { case sc.File(sc.Type.Qualified(sc.QIdent(path :+ name)), content) =>
          val relpath = RelPath(path.map(_.value) :+ (name.value + ".scala"))
          relpath -> content.render
        }
        .toMap

    targets.foreach { target =>
      FileSync
        .syncStrings(
          target.sources,
          filesByRelPath,
          deleteUnknowns = FileSync.DeleteUnknowns.Yes(maxDepth = None),
          soft = false // todo: bleep should use something better than timestamps
        )
      cli("add to git", target.sources, List("git", "add", "-f", target.sources.toString), Logger.DevNull, cli.Out.Raw)
    }
  }
}
