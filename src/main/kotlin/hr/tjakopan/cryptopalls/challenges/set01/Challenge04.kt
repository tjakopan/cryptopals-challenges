package hr.tjakopan.cryptopalls.challenges.set01

import hr.tjakopan.cryptopalls.challenges.CHARSET
import hr.tjakopan.cryptopalls.challenges.SingleByteXorCipher
import hr.tjakopan.cryptopalls.challenges.decodeHex
import hr.tjakopan.cryptopalls.challenges.resourceAsText

fun main() {
  val text = resourceAsText("/set01/challenge04.txt")
  val lines = text?.lines() ?: listOf()
  var maxKeyAndScore: Pair<Byte, Int>? = null
  var selectedLine: String? = null
  val cipher = SingleByteXorCipher()
  for (line in lines) {
    val keyAndScore = cipher.keyAndScore(line.decodeHex())
    if (maxKeyAndScore == null || keyAndScore.second >= maxKeyAndScore.second) {
      maxKeyAndScore = keyAndScore
      selectedLine = line
    }
  }
  if (selectedLine != null && maxKeyAndScore != null) {
    println(selectedLine)
    val decryptedLine = cipher.decrypt(selectedLine.decodeHex(), maxKeyAndScore.first)
    println(String(decryptedLine, CHARSET))
    // Now that the party is jumping
  }
}