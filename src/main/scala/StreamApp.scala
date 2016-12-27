import akka.actor.ActorSystem
import akka.stream.scaladsl.Source
import akka.stream.{ActorMaterializer, OverflowStrategy, ThrottleMode}

import scala.concurrent.duration._

object StreamApp extends App {
  implicit val system = ActorSystem("StreamApp")
  implicit val materializer = ActorMaterializer()

  val source = Source(1 to 100)
  val factorials = source.scan(BigInt(1))((acc, next) => acc * next)

  val result = factorials
    .zipWith(Source(0 to 100))((num, idx) => s"$idx! = $num")
    .buffer(1, OverflowStrategy.dropHead)
    .throttle(1, 1.second, 1, ThrottleMode.shaping)
    .runForeach(println)

  result.onComplete(_ => system.terminate())(system.dispatcher)
}
