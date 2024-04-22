package typo

import java.nio.file.Path

/** this can be used to separate generated source in groups, typically because you want to put them in different projects in your build.
  *
  * Note that this is advanced functionality
  *
  * can be nice if have different "sub-graphs" of relations you want to put in different modules
  */
final case class ProjectGraph[T, S](name: String, target: Path, value: T, scripts: S, downstream: List[ProjectGraph[T, S]]) {
  def toList: List[ProjectGraph[T, S]] =
    (this :: downstream.flatMap(_.toList)).distinctBy(_.target)

  def valueFromProject[TT, SS](f: ProjectGraph[T, S] => (TT, SS)): ProjectGraph[TT, SS] = {
    val (tt, ss) = f(this)
    ProjectGraph(name, target, tt, ss, downstream.map(_.valueFromProject(f)))
  }

  def mapValue[TT](f: T => TT): ProjectGraph[TT, S] =
    ProjectGraph(name, target, f(value), scripts, downstream.map(_.mapValue(f)))

  def mapScripts[SS](f: S => SS): ProjectGraph[T, SS] =
    ProjectGraph(name, target, value, f(scripts), downstream.map(_.mapScripts(f)))
}
