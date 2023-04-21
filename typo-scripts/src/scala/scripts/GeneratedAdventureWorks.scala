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

  class Psql(logger: Logger) {
    def apply(name: String, stdIn: Option[String] = None)(command: String*) = {
      cli(
        name,
        buildDir,
        List("psql", "-U", "postgres", "-h", "localhost") ++ command,
        logger = logger,
        out = cli.Out.ViaLogger(logger),
        in = stdIn match {
          case Some(value) => cli.In.Provided(value.getBytes(StandardCharsets.UTF_8))
          case None        => cli.In.No
        },
        env = List("PGPASSWORD" -> "postgres", "PATH" -> sys.env("PATH"))
      )
    }
  }

  def setup(psql: Psql): cli.WrittenLines = {
    psql("create database adventureworks")(
      "-c",
      """CREATE DATABASE "Adventureworks";"""
    )

    val uri = URI.create("https://raw.githubusercontent.com/lorint/AdventureWorks-for-Postgres/master/install.sql")

    val contents = HttpClient.newHttpClient
      .send(
        HttpRequest.newBuilder(uri).build,
        HttpResponse.BodyHandlers.ofString
      )
      .body

    val skipDataImport: String =
      contents.linesIterator.filterNot(line => line.startsWith("\\copy")).mkString("\n")

    psql("setup adventureworks", Some(skipDataImport))("-d", "Adventureworks")
  }

  def main(args: Array[String]): Unit =
    Loggers
      .stdout(LogPatterns.interface(None, noColor = false), disableProgress = true)
      .map(_.minLogLevel(LogLevel.info))
      .untyped
      .use { logger =>
        val psql = new Psql(logger)
        val exists = psql("check for database adventureworks", Some("\\l"))().stdout.exists(_.contains("Adventureworks"))

        if (!exists) setup(psql)

        implicit val c: Connection = {
          val url = "jdbc:postgresql://localhost/Adventureworks"
          val props = new util.Properties
          props.setProperty("user", "postgres")
          props.setProperty("password", "postgres")
          props.setProperty("port", "5432")
          DriverManager.getConnection(url, props)
        }

        val typoSources = buildDir.resolve("typo-tester/src/scala/adventureworks")

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
