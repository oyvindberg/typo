package scripts

import bleep.logging.{Formatter, LogLevel, Loggers}
import bleep.{FileWatching, LogPatterns, cli}
import typo.internal.FileSync

import java.nio.file.Path
import java.sql.{Connection, DriverManager}

object GeneratedAdventureWorks {
  val buildDir = Path.of(sys.props("user.dir"))

  // çlickable links in intellij
  implicit val PathFormatter: Formatter[Path] = _.toUri.toString

  def main(args: Array[String]): Unit =
    Loggers
      .stdout(LogPatterns.interface(None, noColor = false), disableProgress = true)
      .map(_.minLogLevel(LogLevel.info))
      .untyped
      .use { logger =>
        implicit val c: Connection = DriverManager.getConnection(
          "jdbc:postgresql://localhost:6432/Adventureworks?user=postgres&password=password"
        )
        val scriptsPath = buildDir.resolve("adventureworks_sql")
        val metadb = typo.MetaDb.fromDbAndScripts(scriptsPath)

        def go() = {
          val newSqlScripts = typo.internal.sqlfiles.Load(scriptsPath)

          List(
            (typo.DbLibName.Anorm, typo.JsonLibName.PlayJson, "typo-tester-anorm"),
            (typo.DbLibName.Doobie, typo.JsonLibName.Circe, "typo-tester-doobie")
          ).foreach { case (dbLib, jsonLib, projectPath) =>
            val options = typo.Options(
              pkg = "adventureworks",
              Some(dbLib),
              List(jsonLib),
              typeOverride = typo.TypeOverride.relation {
                case (_, "firstname")                     => "adventureworks.customtype.FirstName"
                case ("sales.creditcard", "creditcardid") => "adventureworks.customtype.CustomCreditcardId"
              },
              enableDsl = true,
              enableTestInserts = true
            )
            val targetSources = buildDir.resolve(s"$projectPath/generated-and-checked-in")

            typo
              .fromMetaDb(options, metadb.copy(sqlFiles = newSqlScripts), typo.Selector.All)
              .overwriteFolder(targetSources, soft = true)
              .filter { case (_, synced) => synced != FileSync.Synced.Unchanged }
              .foreach { case (path, synced) => logger.withContext(path).warn(synced.toString) }

            cli(
              "add files to git",
              buildDir,
              List("git", "add", "-f", targetSources.toString),
              logger = logger,
              cli.Out.Raw
            )
          }
        }

        go()

        // demonstrate how you can `watch` for changes in sql files and immediately regenerate code
        // note that this does not listen to changes in db schema naturally, though I'm sure that's possible to do as well
        if (args.contains("--watch")) {
          logger.warn(s"watching for changes in .sql files under $scriptsPath")
          bleep
            .FileWatching(logger, Map(scriptsPath -> List("sql scripts"))) { changed =>
              go()
            }
            .run(bleep.FileWatching.StopWhen.OnStdInput)
        }
      }
}
