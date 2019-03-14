import org.apache.spark.sql.{Dataset, SparkSession}
import scalaj.http.{Http, HttpOptions}
import tech.gaggle.algo.cleaning.WebpageCleaner
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
  new WebpageCleaner().clean(pageContentHint.get -> url)
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

def downloadAndCleanSearchResult(result: YandexSearchResult): String = {
  def downloaded = Http(result.url)
    .option(HttpOptions.connTimeout(10000))
    .asString
    .body

  new WebpageCleaner().clean(downloaded -> result.url)
}