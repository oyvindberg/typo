//package typo
//
//import typo.internal.CustomType
//
//import scala.annotation.tailrec
//
//sealed trait TType {
//  final def dbType: db.Type =
//    this match {
//      case TType.Normal(_, dbType)       => dbType
//      case TType.Wrapper(_, underlying)  => underlying.dbType
//      case TType.ArrayOf(tpe)            => db.Type.Array(tpe.dbType)
//      case TType.Optional(tpe)           => tpe.dbType
//      case TType.Commented(tpe, _)       => tpe.dbType
//      case TType.UserDefined(_, dbType)  => dbType
//      case TType.OfCustomType(_, dbType) => dbType
//    }
//
//  final def comments: List[String] =
//    this match {
//      case TType.Normal(_, dbType)       => Nil
//      case TType.Wrapper(_, underlying)  => underlying.comments
//      case TType.ArrayOf(tpe)            => tpe.comments
//      case TType.Optional(tpe)           => tpe.comments
//      case TType.Commented(tpe, comment) => comment :: tpe.comments
//      case TType.UserDefined(_, dbType)  => Nil
//    }
//
//  final def mentionedTypes: List[sc.Type] =
//    this match {
//      case TType.Normal(scType, _)           => List(scType)
//      case TType.Wrapper(scType, underlying) => List(scType) ++ underlying.mentionedTypes
//      case TType.ArrayOf(tpe)                => tpe.mentionedTypes
//      case TType.Optional(tpe)               => tpe.mentionedTypes
//      case TType.Commented(tpe, _)           => tpe.mentionedTypes
//      case TType.UserDefined(picked, _)      => List(picked)
//    }
//
//  @tailrec
//  final def userDefined: Option[sc.Type] =
//    this match {
//      case TType.Normal(_, _)                   => None
//      case TType.Wrapper(scType, underlying)    => underlying.userDefined
//      case TType.ArrayOf(underlying)            => underlying.userDefined
//      case TType.Optional(underlying)           => underlying.userDefined
//      case TType.Commented(underlying, comment) => underlying.userDefined
//      case TType.UserDefined(picked, _)         => Some(picked)
//    }
//
//  final def tpe: sc.Type =
//    this match {
//      case TType.Normal(scType, _)        => scType
//      case TType.Wrapper(scType, _)       => scType
//      case TType.ArrayOf(underlying)      => sc.Type.ArrayOf(underlying.tpe)
//      case TType.Optional(underlying)     => TypesScala.Option.of(underlying.tpe)
//      case TType.Commented(underlying, _) => underlying.tpe
//      case TType.UserDefined(picked, _)   => picked
//    }
//}
//
//object TType {
//  case class Normal(scType: sc.Type, dbType: db.Type) extends TType
//  case class OfCustomType(customType: CustomType, dbType: db.Type) extends TType
//  case class UserDefined(picked: sc.Type, dbType: db.Type) extends TType
//  case class Wrapper(scType: sc.Type, underlying: TType) extends TType
//  case class ArrayOf(underlying: TType) extends TType
//  case class Optional(underlying: TType) extends TType
//  case class Commented(underlying: TType, comment: String) extends TType
//}
