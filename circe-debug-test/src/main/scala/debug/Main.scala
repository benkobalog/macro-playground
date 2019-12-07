package debug
import java.time.DayOfWeek

import io.circe.Encoder
import io.circe.syntax._
import io.circe.generic.semiauto._
import typeclass.Debug

object Main {

  def main(args: Array[String]): Unit = {
    Debug.getDecoders[TopLevel]
    Debug.getDecoders[SecondLevel]
  }


  case class TopLevel(x: Int, second: SecondLevel)

//  implicit val topEnc: Encoder[TopLevel] = deriveEncoder[TopLevel]
//  implicit val topSec: Encoder[SecondLevel] = deriveEncoder[SecondLevel]
//  implicit val topThr: Encoder[ThirdLevel] = deriveEncoder[ThirdLevel]

  case class SecondLevel(x: String, y: ThirdLevel, d: DayOfWeek)

  case class ThirdLevel(x: Double, asd: String)

}
