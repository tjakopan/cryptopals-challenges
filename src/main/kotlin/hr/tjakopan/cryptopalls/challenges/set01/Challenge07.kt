package hr.tjakopan.cryptopalls.challenges.set01

import hr.tjakopan.cryptopalls.challenges.*

fun main() {
  val textBase64 = resourceAsText("/set01/challenge07.txt")
  val encryptedData = textBase64!!.decodeBase64()
  val key = "YELLOW SUBMARINE".asByteArray()

  val data = AesCipher.decrypt(encryptedData, key, 128, "ECB")
  println(data.asString())
}