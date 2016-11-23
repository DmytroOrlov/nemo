import monix.execution.Scheduler.Implicits.global

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import monix.eval._
import monix.execution.Ack.Continue

import scala.util.Success

// A specification for evaluating a sum,
// nothing gets triggered at this point!
val task = Task {
  1 + 1
}

val f = task.runAsync
f.onComplete { r => println(r) }

import monix.reactive._

val tick = {
  Observable.interval(1.second)
}

tick.subscribe { i =>
  println(i)
  Continue
}
