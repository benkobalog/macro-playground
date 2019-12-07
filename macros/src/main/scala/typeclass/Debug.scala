package typeclass

import scala.language.experimental.macros
import scala.reflect.macros.blackbox

object Debug {

  def getDecoders[A]: List[Any] = macro TypeExtractorImpl.impl[A]


  private class TypeExtractorImpl(val c: blackbox.Context) extends TypeExtractor {
    import c.universe._

    def impl[A](implicit t: WeakTypeTag[A]): c.universe.Expr[List[Any]] = getDecoders[A]
  }


}
