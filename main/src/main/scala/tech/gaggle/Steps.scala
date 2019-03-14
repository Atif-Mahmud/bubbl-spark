import java.net.URI

import de.l3s.boilerpipe.extractors.CommonExtractors
import de.l3s.boilerpipe.sax.{HTMLDocument, HtmlArticleExtractor}
import org.jsoup.Jsoup
import tech.gaggle.algo.textrank.TextRank

/*
def checkUrlCache(url: String): ResponseType = {

}
*/

def isolatePage(url: String, pageContentHint: Option[String]): String = {
  val html = HtmlArticleExtractor.INSTANCE.process(new HTMLDocument(pageContentHint.get),
    URI.create(url),
    CommonExtractors.ARTICLE_EXTRACTOR)

  Jsoup.parse(html).text()
}

def pageToKeywords(page: String): List[String] = {
  TextRank().getKeywords(page, 7)
}
