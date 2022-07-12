package hr.tjakopan.cryptopalls.challenges.set01

import hr.tjakopan.cryptopalls.challenges.CHARSET
import hr.tjakopan.cryptopalls.challenges.SingleByteXorCipher
import hr.tjakopan.cryptopalls.challenges.decodeHex

fun main() {
  val hex = "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736"
  val encryptedData = hex.decodeHex()
  val cipher = SingleByteXorCipher()
  val keyAndScore = cipher.keyAndScore(encryptedData)
  val decryptedData = cipher.decrypt(encryptedData, keyAndScore.first)
  println(String(decryptedData, CHARSET))
  // Cooking MC's like a pound of bacon
}