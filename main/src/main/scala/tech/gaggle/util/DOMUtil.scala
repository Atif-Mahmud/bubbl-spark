package tech.gaggle.util

import org.w3c.dom.{Node, NodeList}
import tech.gaggle.util.StringUtil._

import scala.collection.mutable.ListBuffer

object DOMUtil {

  implicit class NodeListUtil(private val nodeList: NodeList) extends AnyVal {
    def list: List[Node] = {
      val buffer = ListBuffer[Node]()
      for (i <- 0 until nodeList.getLength) {
        buffer += nodeList.item(i)
      }
      buffer.toList
    }

    def nodeWithName(nodeName: String, ignoreCase: Boolean = false): Option[Node] = {
      for (i <- 0 until nodeList.getLength) {
        val possibleChild = nodeList.item(i)
        if (possibleChild.getNodeName.equalsMaybeIgnoreCase(nodeName, ignoreCase))
          return Option(possibleChild)
      }

      Option.empty
    }
  }

  implicit class NodeUtil(private val node: Node) extends AnyVal {
    def childNode(nodeName: String, ignoreCase: Boolean = false): Option[Node] =
      node.getChildNodes.nodeWithName(nodeName, ignoreCase)
  }

}
