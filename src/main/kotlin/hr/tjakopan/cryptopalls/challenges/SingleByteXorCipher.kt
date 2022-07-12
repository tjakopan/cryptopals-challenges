package hr.tjakopan.cryptopalls.challenges

import java.nio.charset.Charset

class SingleByteXorCipher(private val charset: Charset = CHARSET) {
  fun keyAndScore(data: ByteArray): Pair<Byte, Int> {
    var maxScore = 0
    var guessedKey = Byte.MIN_VALUE
    for (key in Byte.MIN_VALUE..Byte.MAX_VALUE) {
      val decryptedData = decrypt(data, key.toByte())
      val score = score(decryptedData)
      if (score > maxScore) {
        maxScore = score
        guessedKey = key.toByte()
      }
    }
    return Pair(guessedKey, maxScore)
  }

  private fun score(data: ByteArray): Int {
    val text = String(data, charset).uppercase()
    val chars = arrayOf('E', 'T', 'A', 'I', 'N', 'O', 'S', ' ')
    return text.count { chars.contains(it) }
  }

  fun decrypt(data: ByteArray, key: Byte): ByteArray = data xor key
}