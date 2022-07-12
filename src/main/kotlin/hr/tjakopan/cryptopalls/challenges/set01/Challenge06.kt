package hr.tjakopan.cryptopalls.challenges.set01

import hr.tjakopan.cryptopalls.challenges.*

fun main() {
  val textBase64 = resourceAsText("/set01/challenge06.txt")
  val data = textBase64!!.decodeBase64()
  val breakCipher = BreakRepeatingKeyXorCipher()
  val keySizes = breakCipher.guessKeySizes(data, numberOfKeys = 1)

  for (keySize in keySizes) {
    val blocks = breakCipher.breakIntoBlocks(data, keySize)
    val transposedBlocks = breakCipher.transposeBlocks(blocks, keySize)
    println("key size: $keySize")
    println(blocks.map { it.encodeToHex() })
    println(transposedBlocks.map { it.encodeToHex() })

    val key = breakCipher.key(transposedBlocks)
    println("key: ${String(key, CHARSET)}")

    val cipher = RepeatingKeyXorCipher()
    val decryptedData = cipher.decrypt(data, key)
    println(String(decryptedData, CHARSET))
  }

  // keySize = 29
  // key: Terminator X: Bring the noise
}