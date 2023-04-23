package scripts

import bleep.logging.{Logger, Loggers, LogLevel}
import bleep.{LogPatterns, cli}

import java.net.URI
import java.net.http.{HttpClient, HttpRequest, HttpResponse}
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import java.sql.{Connection, DriverManager}
import java.util

object GeneratedAdventureWorks {
  val buildDir = Path.of(sys.props("user.dir"))

  def main(args: Array[String]): Unit =
    Loggers
      .stdout(LogPatterns.interface(None, noColor = false), disableProgress = true)
      .map(_.minLogLevel(LogLevel.info))
      .untyped
      .use { logger =>
        implicit val c: Connection = DriverManager.getConnection(
          "jdbc:postgresql://localhost:6432/Adventureworks?user=postgres&password=password"
        )

        val typoSources = buildDir.resolve("typo-tester/generated-and-checked-in")

        typo
          .fromDbAndScripts(
            typo.Options(
              pkg = "adventureworks",
              jsonLib = typo.JsonLibName.PlayJson,
              dbLib = typo.DbLibName.Anorm
            ),
            buildDir.resolve("adventureworks_sql"),
            typo.Selector.ExcludePostgresInternal
          )
          .overwriteFolder(typoSources, soft = true, relPath => relPath.mapSegments(_.drop(1)))

        cli(
          "add files to git",
          buildDir,
          List("git", "add", "-f", typoSources.toString),
          logger = logger,
          cli.Out.Raw
        )
        ()
      }
}
