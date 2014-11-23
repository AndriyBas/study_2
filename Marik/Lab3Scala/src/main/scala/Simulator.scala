import java.awt.Event

/**
 * Created by andriybas on 11/23/14.
 */

class Event(val node: Node,
            val time: Int) {
  override def toString = "Event[ t : " + time + ", node : " + node.toString
}

class Task(val id: Int) {
  override def toString = "task : " + id
}


object Simulator {

  var events = List.empty[Event]

  val initialTaskCount = 5000

  val simulationTime = 1000000

  var currentTime = 0

  def acceptEvent(event: Event) = {

    def loop(ax: List[Event], acc: List[Event]): List[Event] = ax match {
      case Nil => acc.reverse ::: List(event)
      case x :: ax1 => if (x.time > event.time) acc.reverse ::: event :: ax
      else loop(ax1, x :: acc)
    }

    events = loop(events, Nil)
  }

  val cp = new CP2
  val nb = new NorthBridge
  val ram = new RAM
  val gpu = new GPU
  val sb = new SouthBridge
  val isa = new ISA
  val lpt = new LPT
  val com = new COM
  val kmd = new KMD

  val nodes = List(cp, nb, ram, gpu, sb, isa, lpt, com, kmd)

  def buildComputer() = {

    // cp
    cp.addOut(new Out(cp, 0.8))
    cp.addOut(new Out(nb, 0.2))

    // nb
    nb.addOut(new Out(cp, 0.3))
    nb.addOut(new Out(ram, 0.4))
    nb.addOut(new Out(gpu, 0.25))
    nb.addOut(new Out(sb, 0.05))

    // ram
    ram.addOut(new Out(nb, 1.0))

    // gpu
    gpu.addOut(new Out(nb, 1.0))

    // sb
    sb.addOut(new Out(kmd, 0.3))
    sb.addOut(new Out(isa, 0.7))

    // isa
    isa.addOut(new Out(sb, 0.5))
    isa.addOut(new Out(lpt, 0.25))
    isa.addOut(new Out(com, 0.25))

    // kmd
    kmd.addOut(new Out(sb, 0.5))
    kmd.addOut(new Out(cp, 0.5)) // out of system - return to cp
    // TODO : should kmd return to cp ??? ???

    // lpt
    lpt.addOut(new Out(cp, 1.0)) // out of system - return to cp

    // com
    com.addOut(new Out(lpt, 0.5))
    com.addOut(new Out(cp, 0.5)) // out of system - return to cp
  }

  def initialLoad() = {

    for (i <- 0 until initialTaskCount) {
      cp.accept(new Task(i), 0)
    }

  }

  def runSimulation() = {

    while (!events.isEmpty && events.head.time < simulationTime) {
      val e = events.head

      events = events.tail

      e.node.serve(e.time)
    }

  }

  def printResults() = {
    for (n <- nodes) {
      n.printResults(simulationTime)
    }

  }


  def testAcceptEvent() = {
    val cp = new CP2
    acceptEvent(new Event(cp, 10))
    acceptEvent(new Event(cp, 1))
    acceptEvent(new Event(cp, 20))
    acceptEvent(new Event(cp, 15))
    acceptEvent(new Event(cp, 5))
    acceptEvent(new Event(cp, 16))
    acceptEvent(new Event(cp, 21))
  }
}

object Runner {
  def main(args: Array[String]): Unit = {

    //    Simulator.testAcceptEvent()
    Simulator.buildComputer()
    Simulator.initialLoad()
    Simulator.runSimulation()

    Simulator.printResults()
  }
}
