package hr.tjakopan.cryptopalls.challenges

import kotlin.random.Random

class AesEncryptionOracle12(private val key: ByteArray) {
  fun encryptEcb(data: ByteArray): ByteArray {
    val unknownData = """
        Um9sbGluJyBpbiBteSA1LjAKV2l0aCBteSByYWctdG9wIGRvd24gc28gbXkg
        aGFpciBjYW4gYmxvdwpUaGUgZ2lybGllcyBvbiBzdGFuZGJ5IHdhdmluZyBq
        dXN0IHRvIHNheSBoaQpEaWQgeW91IHN0b3A/IE5vLCBJIGp1c3QgZHJvdmUg
        YnkK
      """.trimIndent()
      .decodeBase64()
    val newData = data + unknownData
    val paddedData = Pkcs7Padding.pad(newData, 16.toByte())
    return AesCipher.encrypt(paddedData, key, "ECB")
  }

  fun detectBlockSize(maxInputSize: Int = 100): Int {
    val randomByte = Random.nextInt().toByte()
    repeat(maxInputSize) { i ->
      val blockSize = i + 1
      val data = ByteArray(2 * blockSize) { randomByte }
      val encryptedData = encryptEcb(data)
//      val dataHex = data.encodeToHex()
//      val encryptedDataHex = encryptedData.encodeToHex()
//      println("block size: $blockSize")
//      println("data hex: $dataHex, encrypted data hex: $encryptedDataHex")
      val firstEncryptedBlock = encryptedData.copyOfRange(0, blockSize)
      val secondEncryptedBlock = encryptedData.copyOfRange(blockSize, 2 * blockSize)
      if (firstEncryptedBlock.contentEquals(secondEncryptedBlock)) return blockSize
    }
    throw IllegalStateException("Could not detect block size. Increase max input size?")
  }

  fun detectMode(encryptedData: ByteArray, blockSize: Int): String {
    require(encryptedData.size >= blockSize * 2) { "Encrypted data needs to be at least 2 times block size in order to detect mode." }

    val hexData = encryptedData.encodeToHex()
    val countPerBlock = mutableMapOf<String, Int>()
    hexData.windowed(blockSize * 2, blockSize * 2)
      .forEach { block ->
        countPerBlock.compute(block) { _, n -> if (n == null) 1 else n + 1 }
      }
    val isEcb = countPerBlock.values.any { it >= 2 }
    return if (isEcb) "ECB" else "CBC"
  }

  fun decryptUnknownData(blockSize: Int): ByteArray {
    val result = mutableListOf<Byte>()

    val randomByte = Random.nextInt().toByte()
    repeat(8) { n ->
      repeat(blockSize) { i ->
        val shortInput = ByteArray(blockSize - i - 1) { randomByte }
        val shortInputEncrypted = encryptEcb(shortInput)
        val shortEncryptedNBlocks = shortInputEncrypted.copyOfRange(0, (n + 1) * blockSize)
        val unknownByte = getUnknownByte(n, blockSize, shortInput + result, shortEncryptedNBlocks)
          ?: throw IllegalStateException("Couldn't find byte ${i + 1}.")
        result.add(unknownByte)
      }
    }
    repeat(11) { i ->
      val shortInput = ByteArray(blockSize - i - 1) { randomByte }
      val shortInputEncrypted = encryptEcb(shortInput)
      val shortEncryptedNBlocks = shortInputEncrypted.copyOfRange(0, (8 + 1) * blockSize)
      val unknownByte = getUnknownByte(8, blockSize, shortInput + result, shortEncryptedNBlocks)
        ?: throw IllegalStateException("Couldn't find byte ${i + 1}.")
      result.add(unknownByte)
    }

    return result.toByteArray()
  }

  private fun getUnknownByte(
    n: Int,
    blockSize: Int,
    oneByteShortInput: ByteArray,
    expectedNBlocks: ByteArray
  ): Byte? {
    for (b in Byte.MIN_VALUE..Byte.MAX_VALUE) {
      val input = oneByteShortInput + b.toByte()
      val encrypted = encryptEcb(input)
      val encryptedNBlocks = encrypted.copyOfRange(0, (n + 1) * blockSize)
      if (expectedNBlocks.contentEquals(encryptedNBlocks)) return b.toByte()
    }
    return null
  }
}