import java.nio.file.Paths

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{FileIO, Source}
import akka.util.ByteString

object StreamApp extends App {
  implicit val system = ActorSystem("StreamApp")
  implicit val materializer = ActorMaterializer()

  val source = Source(1 to 100)
  val factorials = source.scan(BigInt(1))((acc, next) => acc * next)

  val result = factorials
    .map(num => ByteString(s"$num\n"))
    .runWith(FileIO.toPath(Paths.get("factorials.txt")))

  result.onComplete(_ => system.terminate())(system.dispatcher)
}
