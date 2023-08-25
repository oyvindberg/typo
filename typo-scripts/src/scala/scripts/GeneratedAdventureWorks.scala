package scripts

import bleep.logging.{Formatter, LogLevel, Loggers}
import bleep.{FileWatching, LogPatterns, cli}
import typo.internal.sqlfiles.readSqlFileDirectories
import typo.internal.{FileSync, generate}
import typo.*

import java.nio.file.Path
import java.sql.{Connection, DriverManager}
import java.util.concurrent.atomic.AtomicReference

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
        val metadb = MetaDb.fromDb

        val variants = List(
          (DbLibName.Anorm, JsonLibName.PlayJson, "typo-tester-anorm", new AtomicReference(Map.empty[RelPath, String])),
          (DbLibName.Doobie, JsonLibName.Circe, "typo-tester-doobie", new AtomicReference(Map.empty[RelPath, String]))
        )

        def go(): Unit = {
          val newSqlScripts = readSqlFileDirectories(scriptsPath)

          variants.foreach { case (dbLib, jsonLib, projectPath, oldFilesRef) =>
            val options = Options(
              pkg = "adventureworks",
              Some(dbLib),
              List(jsonLib),
              typeOverride = TypeOverride.relation {
                case (_, "firstname")                     => "adventureworks.userdefined.FirstName"
                case ("sales.creditcard", "creditcardid") => "adventureworks.userdefined.CustomCreditcardId"
              },
              enableDsl = true,
              enableTestInserts = true
            )
            val targetSources = buildDir.resolve(s"$projectPath/generated-and-checked-in")

            val newFiles: Generated =
              generate(options, metadb, newSqlScripts, Selector.All)

            val knownUnchanged: Set[RelPath] = {
              val oldFiles = oldFilesRef.get()
              newFiles.files.iterator.collect { case (relPath, contents) if oldFiles.get(relPath).contains(contents) => relPath }.toSet
            }
            oldFilesRef.set(newFiles.files)

            newFiles
              .overwriteFolder(targetSources, softWrite = FileSync.SoftWrite.Yes(knownUnchanged))
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
          FileWatching(logger, Map(scriptsPath -> List("sql scripts")))(_ => go())
            .run(FileWatching.StopWhen.OnStdInput)
        }
      }
}
