package hr.tjakopan.cryptopalls.challenges

import java.nio.charset.Charset

class BreakSingleByteXorCipher(private val charset: Charset = CHARSET) {
  fun guessKeyAndScore(data: ByteArray): Pair<Byte, Int> {
    var maxScore = 0
    var guessedKey = Byte.MIN_VALUE
    for (key in Byte.MIN_VALUE..Byte.MAX_VALUE) {
      val decryptedData = SingleByteXorCipher.decrypt(data, key.toByte())
      val score = englishTextScore(decryptedData, charset)
      if (score > maxScore) {
        maxScore = score
        guessedKey = key.toByte()
      }
    }
    return Pair(guessedKey, maxScore)
  }

  fun tryDecrypt(data: ByteArray): ByteArray {
    val keyAndScore = guessKeyAndScore(data)
    return SingleByteXorCipher.decrypt(data, keyAndScore.first)
  }
}