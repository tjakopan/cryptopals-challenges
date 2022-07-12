package hr.tjakopan.cryptopalls.challenges

import kotlin.experimental.xor

infix fun ByteArray.xor(other: ByteArray): ByteArray {
  require(other.size == this.size)
  return ByteArray(this.size) { i -> this[i] xor other[i] }
}

infix fun ByteArray.xor(byte: Byte): ByteArray {
  return ByteArray(this.size) { i -> this[i] xor byte }
}