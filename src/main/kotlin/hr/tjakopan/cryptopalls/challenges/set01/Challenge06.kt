package hr.tjakopan.cryptopalls.challenges.set01

import hr.tjakopan.cryptopalls.challenges.BreakRepeatingKeyXorCipher
import hr.tjakopan.cryptopalls.challenges.asString
import hr.tjakopan.cryptopalls.challenges.decodeBase64
import hr.tjakopan.cryptopalls.challenges.resourceAsText

fun main() {
  val textBase64 = resourceAsText("/set01/challenge06.txt")
  val data = textBase64!!.decodeBase64()
  val breakCipher = BreakRepeatingKeyXorCipher()
  val decryptedData = breakCipher.tryDecrypt(data, numberOfKeySizeGuesses = 1)
  decryptedData.forEach { println(it.asString()) }
  // keySize = 29
  // key: Terminator X: Bring the noise
}