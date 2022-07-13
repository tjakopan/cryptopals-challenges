package hr.tjakopan.cryptopalls.challenges

import java.nio.charset.Charset

fun englishTextScore(data: ByteArray, charset: Charset = CHARSET): Int {
  val text = data.asString(charset).uppercase()
  val chars = arrayOf('E', 'T', 'A', 'I', 'N', 'O', 'S', ' ')
  return text.count { chars.contains(it) }
}

fun ByteArray.asString(charset: Charset = CHARSET): String = String(this, charset)

fun String.asByteArray(charset: Charset = CHARSET): ByteArray = this.toByteArray(charset)