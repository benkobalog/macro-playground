package typeclass

import scala.reflect.macros.blackbox

abstract class TypeExtractor {
  val c: blackbox.Context

  import c.universe._

  trait DecoderEvidence[A] {
    def dummy(implicit ev: io.circe.Decoder[A]): Unit
  }

  implicitly[io.circe.Decoder[Int]]

  def getDecoders[A](implicit typeTag: c.WeakTypeTag[A]): Expr[List[Any]] = {
    val types = collectAllTypes(typeTag.tpe).distinct
    println(typeTag.tpe.typeSymbol.name)
    println("show: " + show(types))
    //    println("showraw: " + showRaw(types))
    // generate resolution method for them
    val summons = types.map(t =>
      q"""implicitly[io.circe.Decoder[$t]]"""
    )
//    q"""def aS[B](implicit ev: io.circe.Encoder[B]): Unit = ()"""
    Expr(q"List(..$summons)")
  }

  def collectAllTypes(tpe: Type): List[Type] =
    if (isCaseClass(tpe.typeSymbol)) {
      tpe.members
        .collect {
          case m: MethodSymbol if m.isCaseAccessor => m.returnType
        }
        .flatMap(collectAllTypes).toList
    } else List(tpe)

  private def isCaseClass(sym: c.universe.Symbol): Boolean = sym.isClass && sym.asClass.isCaseClass
}
