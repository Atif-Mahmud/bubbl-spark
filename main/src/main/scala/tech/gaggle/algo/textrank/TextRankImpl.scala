package tech.gaggle.algo.textrank

import tech.gaggle.util.PythonUtil

private class TextRankImpl extends TextRank {
  override def getKeywords(string: String, words: Int): List[String] = {
    // Invoke textrank on temp file
    PythonUtil.runPythonScript(
      s"""
         |from sys import stdin
         |from summa import textrank
         |print(textrank.textrank(stdin.read(), summarize_by=${TextRankUnit.Word.id}, words=$words))
       """, string).split(" ").toList
  }
}
