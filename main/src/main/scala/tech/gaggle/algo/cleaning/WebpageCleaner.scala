package tech.gaggle.algo.cleaning

import java.net.URI

import de.l3s.boilerpipe.extractors.CommonExtractors
import de.l3s.boilerpipe.sax.{HTMLDocument, HtmlArticleExtractor}
import org.jsoup.Jsoup

/**
  * Input format is: (HTML, URL)
  */
class WebpageCleaner extends Cleaner[(String, String), String] {
  override def clean(input: (String, String)): String = {
    val html = HtmlArticleExtractor.INSTANCE.process(new HTMLDocument(input._1),
      URI.create(input._2),
      CommonExtractors.ARTICLE_EXTRACTOR)

    Jsoup.parse(html).text()
  }
}
