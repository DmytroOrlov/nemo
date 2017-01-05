import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl._
import akka.stream.stage._

import scala.concurrent.duration._

case class Throttle[T](per: FiniteDuration) extends akka.stream.impl.fusing.GraphStages.SimpleLinearGraphStage[T] {
  require(per > Duration.Zero, "per time must be > 0")
  private val timerName: String = "StreamAppThrottleTimer"

  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic = new TimerGraphStageLogic(shape) {
    override def preStart(): Unit = ()

    {
      val handler = new InHandler with OutHandler {
        override def onUpstreamFinish(): Unit = completeStage()

        override def onPush(): Unit = {
          val elem = grab(in)
          push(out, elem)
        }

        override def onPull(): Unit =
          if (!isTimerActive(timerName)) {
            pull(in)
            scheduleOnce(timerName, per)
          }
      }

      setHandler(in, handler)
      setHandler(out, handler)
    }

    override protected def onTimer(key: Any): Unit = pull(in)
  }

  override def toString = "Throttle"
}

object StreamApp extends App {
  implicit val system = ActorSystem("StreamApp")
  implicit val materializer = ActorMaterializer()

  val source = Source(1 to 100)
  val factorials = source.scan(BigInt(1))((acc, next) => acc * next)

  val result = factorials
    .zipWith(Source(0 to 100))((num, idx) => s"$idx! = $num")
    .buffer(1, OverflowStrategy.dropBuffer)
     .throttle(1, 1.second, 1, ThrottleMode.shaping)
//    .via(Throttle(1.second))
    .runForeach(println)

  result.onComplete(_ => system.terminate())(system.dispatcher)
}

/*
  val r = RunnableGraph.fromGraph(GraphDSL.create() { implicit b =>
    import GraphDSL.Implicits._


    val merge = b.add(Merge[Int](2))
    val bcast = b.add(Broadcast[Int](2))

    source ~> merge ~> Flow[Int].map { s => println(s); s } ~> bcast ~> Sink.ignore
    merge                    <~                      bcast
    ClosedShape
  }).run
*/
