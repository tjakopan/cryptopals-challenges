package hr.tjakopan.cryptopalls.challenges.set02

import hr.tjakopan.cryptopalls.challenges.*

fun main() {
  val textBase64 = resourceAsText("/set02/challenge10.txt")
  val encryptedData = textBase64!!.decodeBase64()
  val key = "YELLOW SUBMARINE".asByteArray()
  val iv = ByteArray(16)
  val data = AesCipher.decrypt(encryptedData, key, "CBC", iv = iv)
  val encryptedText = AesEncryptionOracle.encrypt(data)
}