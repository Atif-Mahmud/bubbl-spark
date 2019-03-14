package tech.gaggle.extapi.search

trait SearchResult {
  /**
    * The url of the page
    */
  val url: String

  /**
    * The title of the page
    */
  val title: String

  /**
    * A snippet of the content of the page
    */
  val headline: String
}
