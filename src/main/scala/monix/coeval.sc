import monix.eval._
import monix.execution.Scheduler.Implicits.global

val task = Task.eval("Hello!")

val tryingNow = task.coeval
// tryingNow: Coeval[Either[CancelableFuture[String],String]] = ???

tryingNow.value match {
  case Left(future) =>
    // No luck, this Task really wants async execution
    future.foreach(r => println(s"Async: $r"))
  case Right(result) =>
    println(s"Got lucky: $result")
}

Thread.sleep(1000)
