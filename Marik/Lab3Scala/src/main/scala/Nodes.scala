import com.sun.jmx.snmp.tasks.Task

import scala.collection.immutable.Queue
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

/**
 * Created by andriybas on 11/23/14.
 */


class Out(val node: Node,
          val p: Double) {
  override def toString = p + " : " + node.toString
}

class Node(val name: String,
           val resources: Int,
           val taskServeTime: Int
            ) {

  var currentResources = resources
  // all nodes in the beginning are not buzy
  var lastServeTime = 0

  val outConnections = scala.collection.mutable.ArrayBuffer.empty[Out]
  val outPSum = scala.collection.mutable.ArrayBuffer.empty[Double]

  val workQueue = scala.collection.mutable.Queue.empty[Task]

  val waitQueue = scala.collection.mutable.Queue.empty[Task]

  // statistic
  var totalServeTasks = 0
  val coreLoad = scala.collection.mutable.ArrayBuffer.fill(resources + 1)(0) // array of the length of resources, showing total time the resources were loaded (0 - none resource worked,  1 - one resource was busy, 2 - two ....

  val rand = new Random()

  def addOut(out: Out) = {
    outConnections += out
    outPSum += (outPSum.lastOption.getOrElse(0.0) + out.p)
  }

  def accept(task: Task, time: Int) = {
    if (!hasResources) {
      // system is busy, no resources available
      waitQueue += task // add task to queue
    } else {
      workQueue += task
      updateLastSearchTime(time)
      Simulator.acceptEvent(new Event(this, time + taskServeTime))
      takeResource()
    }
  }

  private def hasResources() = currentResources > 0

  private def freeResource() = {
    currentResources += 1
  }

  private def takeResource() = {
    currentResources -= 1
  }

  private def updateLastSearchTime(time: Int): Unit = {

    val numOfResBusy = resources - currentResources
    val serveTime = (time - lastServeTime)
    coreLoad(numOfResBusy) += serveTime

    lastServeTime = time
  }

  private def calculateStatisticOnFinishTask(time: Int) = {
    // TODO : move it
    totalServeTasks += 1

  }

  def getNextRandOut(): Int = {
    val randP = rand.nextDouble() + 0.001 // add additional value to go below 1.0 a little

    def loop(ax: List[Double], pos: Int): Int = {
      if (ax.isEmpty) pos - 1
      else if (ax.head < randP) loop(ax.tail, pos + 1) else pos
    }

    val p = loop(outPSum.toList, 0)
    p
  }

  def passTaskForward(task: Task, time: Int) = {

    val pos = getNextRandOut()

    val o = outConnections(pos)

    o.node.accept(task, time)

  }

  def serve(time: Int) = {

    val taskToGoForward = scala.collection.mutable.ArrayBuffer.empty[Task]

    if (!workQueue.isEmpty) {
      val task = workQueue.dequeue()
      updateLastSearchTime(time)
      calculateStatisticOnFinishTask(time)

      freeResource()

      taskToGoForward += task
    }

    // start working with tasks in queue if any and has resources
    while (hasResources() && !waitQueue.isEmpty) {
      accept(waitQueue.dequeue(), time)
    }

    for (tt <- taskToGoForward) {
      passTaskForward(tt, time)
    }

  }

  def printResults(totalTime: Int) = {
    println(name)
    println("Total serve tasks : " + totalServeTasks)
    for (i <- 1 to resources) {
      println(i + " : " + (100.0 * coreLoad(i) / totalTime) + " % load (" + coreLoad(i) + ")")
    }
    println("Tasks in queues : " + (workQueue.size + waitQueue.size))
    println("----------------------------------\n")

  }

  override def toString = name + ", cr : " + currentResources + ", lst : " + lastServeTime;
}


class CP1 extends Node("CP_1", 1, 10)

// 0.25e-9 // 1400 - 2700 MHz

class CP2 extends Node("CP_2", 2, 10)

// 0.25e-9 // 1400 - 2700 MHz

class CP3 extends Node("CP_3", 3, 10)

// 0.25e-9 // 1400 - 2700 MHz

class NorthBridge extends Node("NorthBridge", 1, 15)

// 5e-9 // (CPU-Frequency * 2 ) / 3.15 // MUL : 1.5

class SouthBridge extends Node("SouthBridge", 1, 30)

// 10e-9 //

class RAM extends Node("RAM", 1, 12)

// 0.75e-9 // 1600 MHz // MUL : 1.25

class GPU extends Node("GPU", 1, 17)

// 1e-9 // 1200 MHz //

class ISA extends Node("ISA", 1, 12500)

// first - 8 - 16 MHz

class LPT extends Node("LPT", 1, 50000)

// 0.04 MHz - very very slow -->> 500 000

class COM extends Node("COM", 1, 30000)

// 0.1 MHz -- very slow -->> 200 000

class KMD extends Node("KMD", 1, 7500)

// ???








