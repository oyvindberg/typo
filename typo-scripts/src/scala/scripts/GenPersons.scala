package scripts

import bleep._
import bleep.logging.Logger
import play.api.libs.json.JsNull
import typo._
import typo.codegen.{DbLibAnorm, JsonLibPlay}
import typo.metadb.MetaDb
import typo.sc.syntax.CodeInterpolator

import java.sql.{Connection, DriverManager}
import java.util

object GenPersons extends BleepCodegenScript("GenPersons") {
  val enums = List(
    db.StringEnum(db.RelationName(Some("myschema"), "sector"), List("PUBLIC", "PRIVATE", "OTHER"))
  )

  val person = db.Table(
    name = db.RelationName(Some("myschema"), "person"),
    cols = List(
      db.Col(db.ColName("id"), db.Type.BigInt, Nullability.NoNulls, hasDefault = true, JsNull),
      db.Col(db.ColName("favourite_football_club_id"), db.Type.VarChar(Some(50)), Nullability.NoNulls, hasDefault = false, JsNull),
      db.Col(db.ColName("name"), db.Type.VarChar(Some(100)), Nullability.NoNulls, hasDefault = false, JsNull),
      db.Col(db.ColName("nick_name"), db.Type.VarChar(Some(30)), Nullability.Nullable, hasDefault = false, JsNull),
      db.Col(db.ColName("blog_url"), db.Type.VarChar(Some(100)), Nullability.Nullable, hasDefault = false, JsNull),
      db.Col(db.ColName("email"), db.Type.VarChar(Some(254)), Nullability.NoNulls, hasDefault = false, JsNull),
      db.Col(db.ColName("phone"), db.Type.VarChar(Some(8)), Nullability.NoNulls, hasDefault = false, JsNull),
      db.Col(db.ColName("likes_pizza"), db.Type.Boolean, Nullability.NoNulls, hasDefault = false, JsNull),
      db.Col(db.ColName("marital_status_id"), db.Type.VarChar(Some(50)), Nullability.NoNulls, hasDefault = true, JsNull),
      db.Col(db.ColName("work_email"), db.Type.VarChar(Some(254)), Nullability.Nullable, hasDefault = false, JsNull),
      db.Col(
        db.ColName("sector"),
        db.Type.StringEnum(db.RelationName(Some("myschema"), "sector")),
        Nullability.NoNulls,
        hasDefault = true,
        JsNull
      )
    ),
    Some(db.PrimaryKey(List(db.ColName("id")), db.RelationName(Some("myschema"), "person_pkey"))),
    Nil,
    List(
      db.ForeignKey(
        List(db.ColName("favourite_football_club_id")),
        db.RelationName(Some("myschema"), "football_club"),
        List(db.ColName("id")),
        db.RelationName(Some("myschema"), "person_favourite_football_club_id_fkey")
      ),
      db.ForeignKey(
        List(db.ColName("marital_status_id")),
        db.RelationName(Some("myschema"), "marital_status"),
        List(db.ColName("id")),
        db.RelationName(Some("myschema"), "person_marital_status_id_fkey")
      )
    )
  )
  val football_club = db.Table(
    name = db.RelationName(Some("myschema"), "football_club"),
    cols = List(
      db.Col(db.ColName("id"), db.Type.BigInt, Nullability.NoNulls, hasDefault = false, JsNull),
      db.Col(db.ColName("name"), db.Type.VarChar(Some(100)), Nullability.NoNulls, hasDefault = false, JsNull)
    ),
    Some(db.PrimaryKey(List(db.ColName("id")), db.RelationName(Some("myschema"), "football_club_pkey"))),
    Nil,
    Nil
  )
  val marital_status = db.Table(
    name = db.RelationName(Some("myschema"), "marital_status"),
    cols = List(
      db.Col(db.ColName("id"), db.Type.BigInt, Nullability.NoNulls, hasDefault = false, JsNull)
    ),
    Some(db.PrimaryKey(List(db.ColName("id")), db.RelationName(Some("myschema"), "marital_status_pkey"))),
    Nil,
    Nil
  )

  val cpk_person = db.Table(
    name = db.RelationName(Some("compositepk"), "person"), // name clash to ensure we handle it
    cols = List(
      db.Col(db.ColName("one"), db.Type.BigInt, Nullability.NoNulls, hasDefault = true, JsNull),
      db.Col(db.ColName("two"), db.Type.Text, Nullability.Nullable, hasDefault = true, JsNull),
      db.Col(db.ColName("name"), db.Type.Text, Nullability.Nullable, hasDefault = false, JsNull)
    ),
    Some(db.PrimaryKey(List(db.ColName("one"), db.ColName("two")), db.RelationName(Some("compositepk"), "person_pkey"))),
    Nil,
    Nil
  )
  val cpk_bike = db.Table(
    name = db.RelationName(Some("compositepk"), "bike"),
    cols = List(
      db.Col(db.ColName("id"), db.Type.BigInt, Nullability.NoNulls, hasDefault = true, JsNull),
      db.Col(db.ColName("owner_one"), db.Type.BigInt, Nullability.NoNulls, hasDefault = false, JsNull),
      db.Col(db.ColName("owner_two"), db.Type.Text, Nullability.Nullable, hasDefault = false, JsNull),
      db.Col(db.ColName("bike_name"), db.Type.Text, Nullability.NoNulls, hasDefault = false, JsNull)
    ),
    Some(db.PrimaryKey(List(db.ColName("id")), db.RelationName(Some("compositepk"), "bike_pkey"))),
    Nil,
    Nil
  )

  val all = List(person, football_club, marital_status, cpk_person)

  override def run(started: Started, commands: Commands, targets: List[Target], args: List[String]): Unit = {
    implicit val c: Connection = {
      val url = "jdbc:postgresql://localhost/postgres"
      val props = new util.Properties
      props.setProperty("user", "postgres")
      props.setProperty("password", "postgres")
      DriverManager.getConnection(url, props)
    }
    val header =
      code"""|/**
             | * File automatically generated by `typo` for its own test suite.
             | *
             | * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN
             | */
             |""".stripMargin

    val files1: Map[RelPath, String] = {
      val options = Options(pkg = sc.QIdent(List(sc.Ident("testdb"), sc.Ident("hardcoded"))), JsonLibPlay, DbLibAnorm, header, debugTypes = false)
      Gen(options, MetaDb(all, enums), sqlScripts = Nil).map { case sc.File(sc.Type.Qualified(sc.QIdent(path :+ name)), content) =>
        val relpath = RelPath(path.map(_.value) :+ (name.value + ".scala"))
        relpath -> content.render
      }.toMap
    }

    val files2: Map[RelPath, String] = {
      val options = Options(pkg = sc.QIdent(List(sc.Ident("testdb"), sc.Ident("postgres"))), JsonLibPlay, DbLibAnorm, header, debugTypes = false)
      Gen
        .fromDb(options, Selector.OnlyPostgresInternal)
        .map { case sc.File(sc.Type.Qualified(sc.QIdent(path :+ name)), content) =>
          val relpath = RelPath(path.map(_.value) :+ (name.value + ".scala"))
          relpath -> content.render
        }
        .toMap
    }

    targets.foreach { target =>
      FileSync
        .syncStrings(
          target.sources,
          files1 ++ files2,
          deleteUnknowns = FileSync.DeleteUnknowns.Yes(maxDepth = None),
          soft = false // todo: bleep should use something better than timestamps
        )
      cli("add to git", target.sources, List("git", "add", "-f", target.sources.toString), Logger.DevNull, cli.Out.Raw)
    }
  }
}
