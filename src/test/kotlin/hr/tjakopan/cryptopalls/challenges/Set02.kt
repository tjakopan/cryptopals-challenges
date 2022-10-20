package hr.tjakopan.cryptopalls.challenges

import io.kotest.matchers.shouldBe
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
    val data = AesCipher.decrypt(encryptedData, key, "CBC", iv = iv)

    val encryptedData2 = AesCipher.encryptCbcUsingEcb(data, key, initialIv = iv)

    val data2 = AesCipher.decrypt(encryptedData2, key, "CBC", iv = iv)
    data2 shouldBe data
  }
}