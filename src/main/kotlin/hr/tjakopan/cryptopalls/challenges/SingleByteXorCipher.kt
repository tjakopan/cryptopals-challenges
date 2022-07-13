package hr.tjakopan.cryptopalls.challenges

object SingleByteXorCipher {
  fun decrypt(data: ByteArray, key: Byte): ByteArray = data xor key
}