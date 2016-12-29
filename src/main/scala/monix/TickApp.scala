package monix

import monix.execution.Scheduler.Implicits.global
import monix.reactive._

import scala.concurrent.duration._

object TickApp extends App {
  val tick = {
    Observable.interval(1.second)
  }

  private val cancelable = tick.dump("Out").subscribe()

  Thread.sleep(10000)
  cancelable.cancel()
}
