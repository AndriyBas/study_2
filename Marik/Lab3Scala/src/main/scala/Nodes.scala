import com.sun.jmx.snmp.tasks.Task

import scala.collection.immutable.Queue

/**
 * Created by andriybas on 11/23/14.
 */


class OutConnection(val node: Node,
                    val p: Double)

class Node(val name: String,
           val taskServeTime: Int,
           var resources: Int
            ) {

  var currentResources = resources
  // all nodes in the beginning are not buzy
  var lastServeTime = 0

  var outConnections = scala.collection.mutable.ArrayBuffer.empty[OutConnection]

  val workQueue = scala.collection.mutable.Queue.empty[Task]

  val waitQueue = scala.collection.mutable.Queue.empty[Task]

  // statistic
  var totalServeTasks = 0
  var coreLoad = scala.collection.mutable.ArrayBuffer.fill(resources + 1)(0) // array of the length of resources, showing total time the resources were loaded (0 - none resource worked,  1 - one resource was busy, 2 - two ....

  def accept(task: Task, time: Int) = {
    if (!hasResources) {
      // system is busy, no resources available
      waitQueue += task // add task to queue
    } else {
      workQueue += task
      Simulator.acceptEvent(new Event(this, time + taskServeTime))
      takeResource()
    }
  }

  private def hasResources() = currentResources > 0

  private def freeResource() = {
    resources += 1
  }

  private def takeResource() = {
    resources -= 1
  }

  def calculateStatisticOnFinishTask(time: Int) = {
    val numOfResBusy = resources - currentResources
    totalServeTasks += 1

    coreLoad(numOfResBusy) += (time - lastServeTime)


  }

  def serve(time: Int) = {
    if (!workQueue.isEmpty) {
      val task = workQueue.dequeue()
      calculateStatisticOnFinishTask(time)
      freeResource()
      lastServeTime = time
    }

    // start working with tasks in queue if any and has resources
    while (hasResources() && !waitQueue.isEmpty) {
      accept(waitQueue.dequeue(), time)
    }
  }

}

class CP2 extends Node("CP", 1, 2)

class NorthBridge extends Node("NorthBridge", 2, 1)

class SouthBridge extends Node("SouthBridge", 2, 1)

class RAM extends Node("RAM", 2, 1)

class GPU extends Node("GPU", 2, 1)

class ISA extends Node("ISA", 2, 1)

class LPT extends Node("LPT", 2, 1)

class COM extends Node("COM", 2, 1)

class KMD extends Node("KMD", 2, 1)








