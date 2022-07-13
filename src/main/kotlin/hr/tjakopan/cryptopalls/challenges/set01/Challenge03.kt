package hr.tjakopan.cryptopalls.challenges.set01

import hr.tjakopan.cryptopalls.challenges.BreakSingleByteXorCipher
import hr.tjakopan.cryptopalls.challenges.asString
import hr.tjakopan.cryptopalls.challenges.decodeHex

fun main() {
  val hex = "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736"
  val encryptedData = hex.decodeHex()
  val breakCipher = BreakSingleByteXorCipher()
  val decryptedData = breakCipher.tryDecrypt(encryptedData)
  println(decryptedData.asString())
  // Cooking MC's like a pound of bacon
}