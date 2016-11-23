import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

val f = Future(1)

f.flatMap(r => Future(r + 1))
  .onComplete {
    case Success(r) => println(r)
    case Failure(e) =>
  }
