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
}