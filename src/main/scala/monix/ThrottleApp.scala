package monix

import monix.execution.Scheduler.Implicits.global
import monix.reactive._

import scala.concurrent.duration._

object ThrottleApp extends App {
  val observable = Observable.range(1, 100 + 1)

  val factorials = observable.scan(BigInt(1))((acc, next) => acc * next)

  val result = factorials
    .zipMap(Observable.range(1, 100 + 1))((num, idx) => s"$idx! = $num")
    .sampleBy(Observable.intervalAtFixedRate(50.millis, 1.second))

  val cancelable = result.dump("Out").subscribe()

  Thread.sleep(10000)
}
