package hr.tjakopan.cryptopalls.challenges

object AesEncryptionOracle {
  fun encrypt(data: ByteArray): ByteArray {
    val randomKey = AesCipher.generateKey(128)
    val newData = randomBytes() + data + randomBytes()
    val mode = arrayOf("ECB", "CBC").random()
    val iv = if ("CBC" == mode) randomIv() else null
    val encryptedData = AesCipher.encrypt(newData, randomKey.encoded, mode, "PKCS5Padding", iv)
    println("data: ${data.encodeToHex()}\nnewData: ${newData.encodeToHex()}\nmode: $mode\niv: ${iv?.encodeToHex()}\nencryptedData: ${encryptedData.encodeToHex()}")
    return encryptedData
  }

  private fun randomBytes(): ByteArray =
    ByteArray(IntRange(5, 10).random()) { IntRange(Byte.MIN_VALUE.toInt(), Byte.MAX_VALUE.toInt()).random().toByte() }

  private fun randomIv(): ByteArray =
    ByteArray(16) { IntRange(Byte.MIN_VALUE.toInt(), Byte.MAX_VALUE.toInt()).random().toByte() }
}