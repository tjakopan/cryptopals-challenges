package hr.tjakopan.cryptopalls.challenges.set01

import hr.tjakopan.cryptopalls.challenges.Aes
import hr.tjakopan.cryptopalls.challenges.CHARSET
import hr.tjakopan.cryptopalls.challenges.decodeBase64
import hr.tjakopan.cryptopalls.challenges.resourceAsText

fun main() {
  val textBase64 = resourceAsText("/set01/challenge07.txt")
  val encryptedData = textBase64!!.decodeBase64()
  val key = "YELLOW SUBMARINE".toByteArray(CHARSET)

  val data = Aes.decrypt(encryptedData, key, 128, "ECB")
  println(String(data, CHARSET))
}