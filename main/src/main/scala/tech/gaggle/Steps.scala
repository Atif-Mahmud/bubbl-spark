import java.net.URI

import de.l3s.boilerpipe.extractors.CommonExtractors
import de.l3s.boilerpipe.sax.{HTMLDocument, HtmlArticleExtractor}
import org.apache.spark.sql.{Dataset, SparkSession}
import org.jsoup.Jsoup
import tech.gaggle.algo.textrank.TextRank
import tech.gaggle.extapi.search.YandexSearchEngine
import tech.gaggle.extapi.search.YandexSearchEngine.YandexSearchResult

/*
def checkUrlCache(url: String): ResponseType = {

}
*/

val ss = SparkSession.builder.appName("Yelp Analysis").getOrCreate()

import ss.implicits._

def isolatePage(url: String, pageContentHint: Option[String]): String = {
  val html = HtmlArticleExtractor.INSTANCE.process(new HTMLDocument(pageContentHint.get),
    URI.create(url),
    CommonExtractors.ARTICLE_EXTRACTOR)

  Jsoup.parse(html).text()
}

def pageToKeywords(page: String): List[String] = {
  TextRank().getKeywords(page, 7)
}

def querySearchEngine(keywords: List[String]): Dataset[YandexSearchResult] = {
  // TODO Yandex URL
  val searchEngine = new YandexSearchEngine("TODO")
  val searchResults = (1 to 5).flatMap { pageNumber =>
    searchEngine.performSearch(keywords.mkString(" "), pageNumber)
  }

  searchResults.toDS()
}