package hr.tjakopan.cryptopalls.challenges

import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import kotlin.random.Random
import kotlin.test.Test

class Set02 {
  @Test
  fun challenge09() {
    val data = "YELLOW SUBMARINE"

    val paddedData = Pkcs7Padding.pad(data.asByteArray(), 20)

    paddedData.asString() shouldBe "YELLOW SUBMARINE\u0004\u0004\u0004\u0004"
  }

  @Test
  fun challenge10() {
    val textBase64 = resourceAsText("/set02/challenge10.txt")
    val encryptedData = textBase64!!.decodeBase64()
    val key = "YELLOW SUBMARINE".asByteArray()
    val iv = ByteArray(16)
    val expectedData = AesCipher.decrypt(encryptedData, key, "CBC", iv = iv)

    val encryptedData2 = AesCipher.encryptCbcUsingEcb(expectedData, key, initialIv = iv)

    val actualData = AesCipher.decrypt(encryptedData2, key, "CBC", iv = iv)
    actualData shouldBe expectedData
  }

  @Test
  fun challenge11() {
    repeat(1000) {
      val randomByte = Random.nextInt().toByte()
      val data = ByteArray(16 * 3) { randomByte }
      val encryptedDataAndMode = AesEncryptionOracle11.encrypt(data)
      val expectedMode = encryptedDataAndMode.second

      val actualMode = AesEncryptionOracle11.detectMode(encryptedDataAndMode.first)

      actualMode shouldBe expectedMode
    }
  }

  @Test
  fun challenge12() {
    val key = AesCipher.generateKey(128)
    val oracle = AesEncryptionOracle12(key.encoded)

    val blockSize = oracle.detectBlockSize()
    val actualModes = mutableListOf<String>()
    repeat(1000) {
      val randomByte = Random.nextInt().toByte()
      val data = ByteArray(blockSize * 2) {randomByte}
      val encryptedData = oracle.encryptEcb(data)
      val mode = oracle.detectMode(encryptedData, blockSize)
      actualModes.add(mode)
    }
    val unknownData = oracle.decryptUnknownData(blockSize)
    println(unknownData.asString())

    blockSize shouldBe 16
    actualModes.size shouldBe 1000
    actualModes.all { it == "ECB" }.shouldBeTrue()
  }
}