package hr.tjakopan.cryptopalls.challenges

import java.nio.charset.Charset

class BreakSingleByteXorCipher(private val charset: Charset = DEFAULT_CHARSET) {
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

  fun guessTheEncryptedByteArrayAndKey(data: List<ByteArray>): Pair<ByteArray, Byte> {
    require(data.isNotEmpty())

    var selectedBytes: ByteArray = data[0]
    var maxKeyAndScore: Pair<Byte, Int> = guessKeyAndScore(selectedBytes)
    for (bytes in data.drop(1)) {
      val keyAndScore = guessKeyAndScore(bytes)
      if (keyAndScore.second >= maxKeyAndScore.second) {
        maxKeyAndScore = keyAndScore
        selectedBytes = bytes
      }
    }
    return Pair(selectedBytes, maxKeyAndScore.first)
  }
}