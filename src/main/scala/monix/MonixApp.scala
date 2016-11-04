package monix

object MonixApp extends App {
  import monix.execution.Scheduler.Implicits.global

  import scala.concurrent.Await
  import scala.concurrent.duration._

  import monix.eval._

  // A specification for evaluating a sum,
  // nothing gets triggered at this point!
  val task = Task {
    val res = 1 + 1
    println(res)
    res
  }

  task.runAsync
}
