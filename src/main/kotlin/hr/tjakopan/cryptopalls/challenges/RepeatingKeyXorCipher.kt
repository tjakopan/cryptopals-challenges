package hr.tjakopan.cryptopalls.challenges

import kotlin.experimental.xor

class RepeatingKeyXorCipher {
  fun encrypt(data: ByteArray, key: ByteArray): ByteArray {
    val keySize = key.size
    return ByteArray(data.size) { i ->
      data[i] xor key[i.mod(keySize)]
    }
  }

  fun decrypt(data: ByteArray, key: ByteArray): ByteArray = encrypt(data, key)
}