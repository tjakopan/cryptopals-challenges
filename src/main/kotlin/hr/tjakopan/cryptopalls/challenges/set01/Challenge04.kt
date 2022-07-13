package hr.tjakopan.cryptopalls.challenges.set01

import hr.tjakopan.cryptopalls.challenges.*

fun main() {
  val text = resourceAsText("/set01/challenge04.txt")
  val lines = text?.lines() ?: listOf()
  var maxKeyAndScore: Pair<Byte, Int>? = null
  var selectedLine: String? = null
  val breakCipher = BreakSingleByteXorCipher()
  for (line in lines) {
    val keyAndScore = breakCipher.guessKeyAndScore(line.decodeHex())
    if (maxKeyAndScore == null || keyAndScore.second >= maxKeyAndScore.second) {
      maxKeyAndScore = keyAndScore
      selectedLine = line
    }
  }
  if (selectedLine != null && maxKeyAndScore != null) {
    println(selectedLine)
    val decryptedLine = SingleByteXorCipher.decrypt(selectedLine.decodeHex(), maxKeyAndScore.first)
    println(decryptedLine.asString())
    // Now that the party is jumping
  }
}