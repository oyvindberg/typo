package scripts

import bleep.*
import bleep.logging.Logger
import typo.*
import typo.internal.FileSync.SoftWrite
import typo.internal.analysis.ParsedName
import typo.internal.{DebugJson, Lazy, TypeMapperDb, generate}

// this runs automatically at build time to instantly see results.
// it does not need a running database
object GenHardcodedFiles extends BleepCodegenScript("GenHardcodedFiles") {
  val enums = List(
    db.StringEnum(db.RelationName(Some("myschema"), "sector"), List("PUBLIC", "PRIVATE", "OTHER")),
    db.StringEnum(db.RelationName(Some("myschema"), "number"), List("one", "two", "three"))
  )

  val person = db.Table(
    name = db.RelationName(Some("myschema"), "person"),
    cols = NonEmptyList(
      db.Col(ParsedName.of("id"), db.Type.Int8, Some("int8"), Nullability.NoNulls, columnDefault = Some("auto-increment"), None, Nil, DebugJson.Empty),
      db.Col(
        ParsedName.of("favourite_football_club_id"),
        db.Type.VarChar(Some(50)),
        Some("varchar"),
        Nullability.NoNulls,
        columnDefault = None,
        None,
        Nil,
        DebugJson.Empty
      ),
      db.Col(ParsedName.of("name"), db.Type.VarChar(Some(100)), Some("varchar"), Nullability.NoNulls, columnDefault = None, None, Nil, DebugJson.Empty),
      db.Col(ParsedName.of("nick_name"), db.Type.VarChar(Some(30)), Some("varchar"), Nullability.Nullable, columnDefault = None, None, Nil, DebugJson.Empty),
      db.Col(ParsedName.of("blog_url"), db.Type.VarChar(Some(100)), Some("varchar"), Nullability.Nullable, columnDefault = None, None, Nil, DebugJson.Empty),
      db.Col(ParsedName.of("email"), db.Type.VarChar(Some(254)), Some("varchar"), Nullability.NoNulls, columnDefault = None, None, Nil, DebugJson.Empty),
      db.Col(ParsedName.of("phone"), db.Type.VarChar(Some(8)), Some("varchar"), Nullability.NoNulls, columnDefault = None, None, Nil, DebugJson.Empty),
      db.Col(ParsedName.of("likes_pizza"), db.Type.Boolean, Some("bool"), Nullability.NoNulls, columnDefault = None, None, Nil, DebugJson.Empty),
      db.Col(
        ParsedName.of("marital_status_id"),
        db.Type.VarChar(Some(50)),
        Some("varchar"),
        Nullability.NoNulls,
        columnDefault = Some("some-value"),
        None,
        Nil,
        DebugJson.Empty
      ),
      db.Col(ParsedName.of("work_email"), db.Type.VarChar(Some(254)), Some("varchar"), Nullability.Nullable, columnDefault = None, None, Nil, DebugJson.Empty),
      db.Col(
        ParsedName.of("sector"),
        db.Type.EnumRef(db.RelationName(Some("myschema"), "sector")),
        Some("myschema.sector"),
        Nullability.NoNulls,
        columnDefault = Some("PUBLIC"),
        None,
        Nil,
        DebugJson.Empty
      ),
      db.Col(
        ParsedName.of("favorite_number"),
        db.Type.EnumRef(db.RelationName(Some("myschema"), "number")),
        Some("myschema.number"),
        Nullability.NoNulls,
        columnDefault = Some("one"),
        None,
        Nil,
        DebugJson.Empty
      )
    ),
    Some(db.PrimaryKey(NonEmptyList(db.ColName("id")), db.RelationName(Some("myschema"), "person_pkey"))),
    Nil,
    List(
      db.ForeignKey(
        NonEmptyList(db.ColName("favourite_football_club_id")),
        db.RelationName(Some("myschema"), "football_club"),
        NonEmptyList(db.ColName("id")),
        db.RelationName(Some("myschema"), "person_favourite_football_club_id_fkey")
      ),
      db.ForeignKey(
        NonEmptyList(db.ColName("marital_status_id")),
        db.RelationName(Some("myschema"), "marital_status"),
        NonEmptyList(db.ColName("id")),
        db.RelationName(Some("myschema"), "person_marital_status_id_fkey")
      )
    )
  )
  val football_club = db.Table(
    name = db.RelationName(Some("myschema"), "football_club"),
    cols = NonEmptyList(
      db.Col(ParsedName.of("id"), db.Type.Int8, Some("int8"), Nullability.NoNulls, columnDefault = None, None, Nil, DebugJson.Empty),
      db.Col(ParsedName.of("name"), db.Type.VarChar(Some(100)), Some("varchar"), Nullability.NoNulls, columnDefault = None, None, Nil, DebugJson.Empty)
    ),
    Some(db.PrimaryKey(NonEmptyList(db.ColName("id")), db.RelationName(Some("myschema"), "football_club_pkey"))),
    Nil,
    Nil
  )
  val marital_status = db.Table(
    name = db.RelationName(Some("myschema"), "marital_status"),
    cols = NonEmptyList(
      db.Col(ParsedName.of("id"), db.Type.Int8, Some("int8"), Nullability.NoNulls, columnDefault = None, None, Nil, DebugJson.Empty)
    ),
    Some(db.PrimaryKey(NonEmptyList(db.ColName("id")), db.RelationName(Some("myschema"), "marital_status_pkey"))),
    Nil,
    Nil
  )

  val cpk_person = db.Table(
    name = db.RelationName(Some("compositepk"), "person"), // name clash to ensure we handle it
    cols = NonEmptyList(
      db.Col(ParsedName.of("one"), db.Type.Int8, Some("int8"), Nullability.NoNulls, columnDefault = Some("auto-increment"), None, Nil, DebugJson.Empty),
      db.Col(ParsedName.of("two"), db.Type.Text, Some("text"), Nullability.Nullable, columnDefault = Some("auto-increment"), None, Nil, DebugJson.Empty),
      db.Col(ParsedName.of("name"), db.Type.Text, Some("text"), Nullability.Nullable, columnDefault = None, None, Nil, DebugJson.Empty)
    ),
    Some(db.PrimaryKey(NonEmptyList(db.ColName("one"), db.ColName("two")), db.RelationName(Some("compositepk"), "person_pkey"))),
    Nil,
    Nil
  )

  val all = List(person, football_club, marital_status, cpk_person)

  override def run(started: Started, commands: Commands, targets: List[Target], args: List[String]): Unit = {
    val header =
      """|/**
         | * File automatically generated by `typo` for its own test suite.
         | *
         | * IF YOU CHANGE THIS FILE YOUR CHANGES WILL BE OVERWRITTEN
         | */
         |""".stripMargin

    targets.foreach { target =>
      val (dbLib, jsonLib) =
        if (target.project.value.contains("doobie"))
          (DbLibName.Doobie, JsonLibName.Circe)
        else (DbLibName.Anorm, JsonLibName.PlayJson)
      val domains = Nil

      val metaDb = MetaDb(
        relations = all.map(t => t.name -> Lazy(t)).toMap,
        enums = enums,
        domains = domains,
        TypeMapperDb(enums, domains)
      )

      val generated: Generated =
        generate(
          Options(
            pkg = "testdb.hardcoded",
            Some(dbLib),
            List(jsonLib),
            naming = pkg =>
              new Naming(pkg) {
                override def enumValue(name: String): sc.Ident = sc.Ident("_" + name.toLowerCase)
              },
            fileHeader = header,
            enableDsl = true,
            enableTestInserts = Selector.All,
            enableFieldValue = Selector.All,
            silentBanner = true
          ),
          metaDb,
          sqlFiles = Nil,
          Selector.All
        )

      generated.overwriteFolder(
        target.sources,
        // todo: bleep should use something better than timestamps
        softWrite = SoftWrite.No
      )
      cli("add to git", target.sources, List("git", "add", "-f", target.sources.toString), Logger.DevNull, cli.Out.Raw)
    }
  }
}
