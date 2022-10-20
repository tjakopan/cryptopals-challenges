package hr.tjakopan.cryptopalls.challenges

import io.kotest.matchers.shouldBe
import kotlin.test.Test

class Set01 {
  @Test
  fun challenge01() {
    val hex = "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d"

    val actualBase64 = hex.decodeHex().encodeToBase64()

    actualBase64 shouldBe "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t"
  }

  @Test
  fun challenge02() {
    val hex1 = "1c0111001f010100061a024b53535009181c"
    val hex2 = "686974207468652062756c6c277320657965"

    val actualXorHex = (hex1.decodeHex() xor hex2.decodeHex()).encodeToHex()

    actualXorHex shouldBe "746865206b696420646f6e277420706c6179"
  }

  @Test
  fun challenge03() {
    val hex = "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736"
    val encryptedData = hex.decodeHex()
    val breakCipher = BreakSingleByteXorCipher()

    val decryptedData = breakCipher.tryDecrypt(encryptedData)

    decryptedData.asString() shouldBe "Cooking MC's like a pound of bacon"
  }

  @Test
  fun challenge04() {
    val data = resourceAsText("/set01/challenge04.txt")
      ?.lines()
      ?.map { it.decodeHex() }
      ?: listOf()
    val breakCipher = BreakSingleByteXorCipher()

    val bytesAndKey = breakCipher.guessTheEncryptedByteArrayAndKey(data)
    val decryptedLine = SingleByteXorCipher.decrypt(bytesAndKey.first, bytesAndKey.second)

    decryptedLine.asString() shouldBe "Now that the party is jumping\n"
  }

  @Test
  fun challenge05() {
    val text = "Burning 'em, if you ain't quick and nimble\n" +
        "I go crazy when I hear a cymbal"
    val key = "ICE"

    val encryptedData = RepeatingKeyXorCipher.encrypt(text.asByteArray(), key.asByteArray())

    val encryptedHex = encryptedData.encodeToHex()
    encryptedHex shouldBe "0b3637272a2b2e63622c2e69692a23693a2a3c6324202d623d63343c2a26226324272765272a282b2f20430a652e2c652a3124333a653e2b2027630c692b20283165286326302e27282f"
  }

  @Test
  fun `challenge06 edit distance`() {
    val first = "this is a test"
    val second = "wokka wokka!!!"

    val editDistance = editDistance(first.asByteArray(), second.asByteArray())

    editDistance shouldBe 37
  }

  @Test
  fun challenge06() {
    val textBase64 = resourceAsText("/set01/challenge06.txt")
    val encryptedData = textBase64!!.decodeBase64()
    val breakCipher = BreakRepeatingKeyXorCipher()
    val expectedText = resourceAsText("/set01/challenge06-expected.txt")

    val decryptedData = breakCipher.tryDecrypt(encryptedData, numberOfKeySizeGuesses = 1)
    // keySize = 29
    // key: Terminator X: Bring the noise

    decryptedData[0].asString() shouldBe expectedText
  }

  @Test
  fun challenge07() {
    val textBase64 = resourceAsText("/set01/challenge07.txt")
    val encryptedData = textBase64!!.decodeBase64()
    val key = "YELLOW SUBMARINE".asByteArray()
    val expectedText = resourceAsText("/set01/challenge07-expected.txt")

    val decryptedData = AesCipher.decrypt(encryptedData, key, "ECB")

    decryptedData.asString() shouldBe expectedText
  }

  @Test
  fun challenge08() {
    val lines = resourceAsText("/set01/challenge08.txt")!!.lines()

    val detectedLine = detectEcb(lines)

    detectedLine shouldBe "d880619740a8a19b7840a8a31c810a3d08649af70dc06f4fd5d2d69c744cd283e2dd052f6b641dbf9d11b0348542bb5708649af70dc06f4fd5d2d69c744cd2839475c9dfdbc1d46597949d9c7e82bf5a08649af70dc06f4fd5d2d69c744cd28397a93eab8d6aecd566489154789a6b0308649af70dc06f4fd5d2d69c744cd283d403180c98c8f6db1f2a3f9c4040deb0ab51b29933f2c123c58386b06fba186a"
  }
}