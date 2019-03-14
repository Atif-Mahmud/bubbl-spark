package tech.gaggle.algo.textrank

object TextRankUnit extends Enumeration {
  type TextRankUnit = Value
  val Sentence: TextRankUnit = Value(0, "Sentence")
  val Word: TextRankUnit = Value(1, "Word")
}
