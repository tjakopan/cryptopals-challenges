package hr.tjakopan.cryptopalls.challenges

import kotlin.random.Random

object AesEncryptionOracle11 {
  fun encrypt(data: ByteArray): Pair<ByteArray, String> {
    require(data.size >= 16 * 3) { "Data needs to be at least 3 times block size (16 bytes) in order to use oracle." }
    val firstByte = data[0]
    require(data.count { it == firstByte } == data.size) { "All bytes need to be the same in order to use oracle." }

    val randomKey = AesCipher.generateKey(128)
    val newData = randomBytes() + data + randomBytes()
    val paddedData = Pkcs7Padding.pad(newData, 16.toByte())
    val mode = arrayOf("ECB", "CBC").random()
    val iv = if ("CBC" == mode) randomIv() else null
    val encryptedData = AesCipher.encrypt(paddedData, randomKey.encoded, mode, iv = iv)
    //println("data: ${data.encodeToHex()}\nnewData: ${paddedData.encodeToHex()}\nmode: $mode\niv: ${iv?.encodeToHex()}\nencryptedData: ${encryptedData.encodeToHex()}\n")
    return Pair(encryptedData, mode)
  }

  private fun randomBytes(): ByteArray = Random.nextBytes(IntRange(5, 10).random())

  private fun randomIv(): ByteArray = Random.nextBytes(16)

  fun detectMode(encryptedData: ByteArray): String {
    require(encryptedData.size >= 16 * 3) { "Encrypted data needs to be at least 3 times block size (16 bytes) in order to detect mode." }

    val hexData = encryptedData.encodeToHex()
    val countPerBlock = mutableMapOf<String, Int>()
    hexData.windowed(32, 32)
      .forEach { block ->
        countPerBlock.compute(block) { _, n -> if (n == null) 1 else n + 1 }
      }
    val isEcb = countPerBlock.values.any { it >= 2 }
    return if (isEcb) "ECB" else "CBC"
  }
}