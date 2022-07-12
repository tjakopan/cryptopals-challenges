package hr.tjakopan.cryptopalls.challenges

fun editDistance(first: ByteArray, second: ByteArray): Int {
  val xored = first xor second
  return xored.sumOf { it.countOneBits() }
}