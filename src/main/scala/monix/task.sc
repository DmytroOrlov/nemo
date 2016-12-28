import monix.eval._
import monix.execution.Scheduler.Implicits.global

import scala.concurrent.Await
import scala.concurrent.duration._

// A specification for evaluating a sum,
// nothing gets triggered at this point!
val task = Task {
  1 + 1
}

val f = task.runAsync

Await.result(f, 1.second)
