package typo
package internal
package codegen

object DomainFile {
  def apply(naming: Naming, options: InternalOptions, typeMapperScala: TypeMapperScala)(dom: db.Domain): sc.File = {
    val qident = naming.domainName(dom.name)
    val tpe = sc.Type.Qualified(qident)

    val underlying: sc.Type = typeMapperScala.domain(dom.tpe)
    // todo: the implicit evidence below is to accommodate scala 2.12.4, which doesn't have an automatic `Ordering[ZonedDateTime]` (and maybe more)
    val str =
      code"""case class ${qident.name}(value: $underlying) extends AnyVal
            |object ${qident.name} {
            |  implicit def ordering(implicit ev: ${sc.Type.Ordering.of(underlying)}): ${sc.Type.Ordering.of(tpe)} = ${sc.Type.Ordering}.by(_.value)
            |  ${options.jsonLib.anyValInstances(wrapperType = tpe, underlying = underlying).mkCode("\n")}
            |  ${options.dbLib.anyValInstances(wrapperType = tpe, underlying = underlying).mkCode("\n")}
            |}
""".stripMargin

    sc.File(tpe, str, secondaryTypes = Nil)
  }
}
