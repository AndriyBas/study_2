/**
 * Created by andriybas on 11/23/14.
 */

class Event(val node: Node,
            time: Int)

class Task(val id: Int)


object Simulator {

  val events = scala.collection.mutable.ListBuffer.empty[Event]

  def acceptEvent(event: Event) = {

  }

}

object Runner {
  def main(args: Array[String]): Unit = {
    println("lol")
  }
}
