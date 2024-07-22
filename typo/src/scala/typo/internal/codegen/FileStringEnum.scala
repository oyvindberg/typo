package typo
package internal
package codegen

object FileStringEnum {
  def apply(options: InternalOptions, enm: ComputedStringEnum): sc.File = {

    val instances: List[sc.ClassMember] = List(
      options.dbLib.toList.flatMap(_.stringEnumInstances(enm.tpe, TypesJava.String, enm.dbEnum)),
      options.jsonLibs.flatMap(_.stringEnumInstances(enm.tpe, TypesJava.String))
    ).flatten
    val comments = scaladoc(s"Enum `${enm.name.value}`" +: enm.members.map { case (_, v) => " - " + v })

    sc.File(enm.tpe, sc.Enum(comments, enm.tpe, enm.members, instances), secondaryTypes = Nil, scope = Scope.Main)
  }
}
