package tech.gaggle.extapi.search

import javax.xml.parsers.DocumentBuilderFactory
import scalaj.http.{Http, HttpOptions}
import tech.gaggle.extapi.search.YandexSearchEngine.YandexSearchResult
import tech.gaggle.util.DOMUtil._

class YandexSearchEngine(private val yandexUrl: String) extends PagedSearchEngine[Int] {
  private val docBuilderFactory = DocumentBuilderFactory.newInstance()
  docBuilderFactory.setValidating(false)
  docBuilderFactory.setIgnoringElementContentWhitespace(true)

  override def performSearch(searchQuery: String, page: Int): List[YandexSearchResult] = {
    Http(yandexUrl)
      .param("text", searchQuery)
      .param("page", page.toString)
      .option(HttpOptions.connTimeout(10000))
      .execute { stream =>
        val doc = docBuilderFactory.newDocumentBuilder().parse(stream)
        doc.getElementsByTagName("group").list.map { node =>
          val group = node.getChildNodes.list.find { child => child.getNodeName.equalsIgnoreCase("doc") }.get

          val urlNode = group.childNode("url")
          val titleNode = group.childNode("title")
          val headlineNode = group.childNode("headline")
          val passagesNode = group.childNode("passages")

          val pageUrl = urlNode.get.getTextContent
          val title = titleNode.get.getTextContent
          val headline = headlineNode.orElse(passagesNode).get.getTextContent
          YandexSearchResult(pageUrl, title, headline)
        }
      }.body
  }
}

object YandexSearchEngine {

  case class YandexSearchResult(url: String, title: String, headline: String) extends SearchResult

}
