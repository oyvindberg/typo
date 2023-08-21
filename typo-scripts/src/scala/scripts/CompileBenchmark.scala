package scripts

import bleep.*
import bleep.commands.SourceGen

import java.nio.file.Path
import java.sql.{Connection, DriverManager}

object CompileBenchmark extends BleepScript("CompileBenchmark") {
  val buildDir = Path.of(sys.props("user.dir"))

  case class Result(lib: String, crossId: model.CrossId, inlineImplicits: Boolean, avgtime: Long, times: List[Long])

  override def run(started: Started, commands: Commands, args: List[String]): Unit = {
    implicit val c: Connection = DriverManager.getConnection(
      "jdbc:postgresql://localhost:6432/Adventureworks?user=postgres&password=password"
    )
    val metadb = typo.MetaDb.fromDbAndScripts(buildDir.resolve("adventureworks_sql"))

    val crossIds = List("jvm212", "jvm213", "jvm3").map(str => model.CrossId(str))
    val variants = List(
      (Some(typo.DbLibName.Doobie), Nil, "typo-tester-doobie"),
      (Some(typo.DbLibName.Anorm), Nil, "typo-tester-anorm"),
      (None, List(typo.JsonLibName.Circe), "typo-tester-doobie"),
      (None, List(typo.JsonLibName.PlayJson), "typo-tester-anorm")
    )

    val results: List[Result] =
      variants.flatMap { case (dbLib, jsonLib, projectName) =>
        val lib = (dbLib ++ jsonLib).mkString
        val targetSources = buildDir.resolve(s"$projectName/generated-and-checked-in")
        List(false, true).flatMap { inlineImplicits =>
          typo
            .fromMetaDb(
              typo.Options(pkg = "adventureworks", dbLib, jsonLib, enableDsl = true, enableTestInserts = true, inlineImplicits = inlineImplicits),
              metadb,
              typo.Selector.All
            )
            .overwriteFolder(targetSources, soft = true, relPath => relPath.mapSegments(_.drop(1)))

          crossIds.map { crossId =>
            val desc = s"${crossId.value}, lib=$lib, inlineImplicits=$inlineImplicits"
            println(s"START $desc")
            val times = 0.to(2).map { _ =>
              val crossProjectName = model.CrossProjectName(model.ProjectName(projectName), Some(crossId))
              commands.clean(List(crossProjectName))
              SourceGen(false, Array(crossProjectName)).run(started)
              time(commands.compile(List(crossProjectName)))
            }
            val avgtime = times.sum / times.length
            println(s"END $desc, avgtime=$avgtime, times=$times")
            Result(lib, crossId, inlineImplicits, avgtime, times.toList)
          }
        }
      }

    results foreach println
  }

  def time(run: => Unit): Long = {
    val start = System.currentTimeMillis()
    run
    System.currentTimeMillis() - start
  }
}
