package named

import machinist.DefaultOps

import scala.language.experimental.macros
import scala.{specialized => sp}

trait Named[@sp A] {
  def name(v: A): String
}

object Named {
  implicit val intStringNamed = new Named[Int => String] {
    def name(v: Int => String): String = "IntToString"
  }

  implicit class NamedOps[A](x: A)(implicit ev: Named[A]) {
    def name: String = macro DefaultOps.unop0[A]
  }
}
