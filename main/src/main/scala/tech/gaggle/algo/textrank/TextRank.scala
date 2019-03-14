package tech.gaggle.algo.textrank

trait TextRank {
  def getKeywords(string: String, words: Int): List[String]
}

object TextRank {
  def apply(): TextRank = new TextRankImpl()
}